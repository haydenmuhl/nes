package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class EorTest {
    private Processor proc;

    private static MemoryImpl mem() {
        MemoryImpl mem = new MemoryImpl();
        mem.memory[0xfffc] = (byte) 0x34;
        mem.memory[0xfffd] = (byte) 0x12;
        return mem;
    }
    
    private static void nTicks(Processor p, int n) {
        for (int i = 0; i < n; i++) {
            p.tick();
        }
    }
    
    @BeforeTest
    public void beforeTest() {
        proc = new Processor();
        Handler handler = new TestNgReporterHandler();
        handler.setLevel(Level.ALL);
        handler.setFormatter(new SimpleFormatter());
        proc.addLoggerHandler(handler);
        proc.setLoggerLevel(Level.ALL);
    }
    
    @BeforeMethod
    public void beforeMethod() {
        proc.reset();
    }

    // EOR
    
    @Test
    public void eorImmediateTest() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x0f, 0x1235);
        mem.setByte(0x49, 0x1236); // EOR immediate opcode
        mem.setByte(0x33, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x3C);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
        assertEquals(proc.PCH.get(), 0x12, "PCH");
        assertEquals(proc.PCL.get(), 0x38, "PCL");
    }
    
    @Test
    public void eorImmediateTestZeroFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x55, 0x1235);
        mem.setByte(0x49, 0x1236); // EOR immediate opcode
        mem.setByte(0x55, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void eorImmediateTestNegativeFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0xf0, 0x1235);
        mem.setByte(0x49, 0x1236); // EOR immediate opcode
        mem.setByte(0x5A, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0xAA);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
}
