package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class CmpTest {
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

    // CMP
    
    @Test
    public void cmpImmediateTestMemEqAcc() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x12, 0x1235);
        mem.setByte(0xC9, 0x1236); // CMP immediate opcode
        mem.setByte(0x12, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
        assertEquals(proc.PCH.get(), 0x12, "PCH value");
        assertEquals(proc.PCL.get(), 0x38, "PCL value");
    }
    
    @Test
    public void cmpImmediateTestMemLtAcc() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x12, 0x1235);
        mem.setByte(0xC9, 0x1236); // CMP immediate opcode
        mem.setByte(0x11, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.status.getCarryFlag(), false, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void cmpImmediateTestMemGtAcc() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x12, 0x1235);
        mem.setByte(0xC9, 0x1236); // CMP immediate opcode
        mem.setByte(0x13, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
}
