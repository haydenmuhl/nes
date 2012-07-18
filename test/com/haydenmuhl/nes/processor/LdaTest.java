package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class LdaTest {
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

    // Immediate
    
    @Test
    public void ldaImmediateTest() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x42, 0x1235); // value
        proc.setMemory(mem);
        proc.tick();
        proc.tick();
        proc.tick();
        proc.tick();
        assertEquals(proc.regA.get(), (byte) 0x42);
        assertEquals(proc.status.getZeroFlag(), false);
        assertEquals(proc.status.getNegativeFlag(), false);
        assertEquals(proc.PCH.get(), 0x12, "PCH");
        assertEquals(proc.PCL.get(), 0x36, "PCL");
    }
    
    @Test
    public void ldaImmediateTestNegFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0xC4, 0x1235); // negative value
        proc.setMemory(mem);
        proc.tick();
        proc.tick();
        proc.tick();
        proc.tick();
        assertEquals(proc.regA.get(), (byte) 0xC4);
        assertEquals(proc.status.getZeroFlag(), false);
        assertEquals(proc.status.getNegativeFlag(), true);
    }
    
    @Test
    public void ldaImmedateTestZeroFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x00, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getZeroFlag(), true);
        assertEquals(proc.status.getNegativeFlag(), false);
    }
    
    // Zero Page
    
    @Test
    public void ldaZeroPageTest() {
        MemoryImpl mem = mem();
        mem.setByte(0xA5, 0x1234); // LDA zero page
        mem.setByte(0x99, 0x1235); // zero page address
        mem.setByte(0x42, 0x0099); // value to load
        proc.setMemory(mem);
        nTicks(proc, 5);
        assertEquals(proc.regA.get(), (byte) 0x42);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void ldaZeroPageTestZeroFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA5, 0x1234); // LDA zero page
        mem.setByte(0x99, 0x1235); // zero page address
        mem.setByte(0x00, 0x0099); // value to load
        proc.setMemory(mem);
        nTicks(proc, 5);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void ldaZeroPageTestNegativeFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA5, 0x1234); // LDA zero page
        mem.setByte(0x99, 0x1235); // zero page address
        mem.setByte(0xf0, 0x0099); // value to load
        proc.setMemory(mem);
        nTicks(proc, 5);
        assertEquals(proc.regA.get(), (byte) 0xf0);
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
}
