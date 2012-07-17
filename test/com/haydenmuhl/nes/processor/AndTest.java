package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class AndTest {
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
    
    // AND
    
    @Test
    public void andImmediateTest() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x0f, 0x1235);
        mem.setByte(0x29, 0x1236); // ADC immediate opcode
        mem.setByte(0x33, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x03);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void andImmediateTestNegativeFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0xf0, 0x1235);
        mem.setByte(0x29, 0x1236); // ADC immediate opcode
        mem.setByte(0xcc, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0xc0);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
    
    @Test
    public void andImmediateTestZeroFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x0f, 0x1235);
        mem.setByte(0x29, 0x1236); // ADC immediate opcode
        mem.setByte(0xf0, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
}
