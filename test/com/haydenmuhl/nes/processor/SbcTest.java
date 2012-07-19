package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class SbcTest {
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
        // Set carry flag to make subtraction act as expected
        proc.status.setCarryFlag(true);
    }
    
    // Immediate
    
    @Test
    public void sbcImmediateTest() {
        MemoryImpl mem = mem();
        proc.regA.set(0x10);
        mem.setByte(0xE9, 0x1234); // SBC immediate opcode
        mem.setByte(0x02, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0x0e);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
        assertEquals(proc.PCH.get(), 0x12, "PCH");
        assertEquals(proc.PCL.get(), 0x36, "PCL");
    }
    
    @Test
    public void sbcImmediateTestZeroFlag() {
        MemoryImpl mem = mem();
        proc.regA.set(0x05);
        mem.setByte(0xE9, 0x1234); // SBC immediate opcode
        mem.setByte(0x05, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void sbcImmediateTestCarryFlag() {
        MemoryImpl mem = mem();
        proc.regA.set(0x10);
        mem.setByte(0xE9, 0x1234); // SBC immediate opcode
        mem.setByte(0x11, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0xff);
        assertEquals(proc.status.getCarryFlag(), false, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
    
    @Test
    public void sbcImmediateTestOverflowFlag() {
        MemoryImpl mem = mem();
        proc.regA.set(0xFD);
        mem.setByte(0xE9, 0x1234); // SBC immediate opcode
        mem.setByte(0x7F, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0x7E);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), true, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void sbcImmediateTestNegativeFlag() {
        MemoryImpl mem = mem();
        proc.regA.set(0x05);
        mem.setByte(0xE9, 0x1234); // SBC immediate opcode
        mem.setByte(0x06, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0xFF);
        assertEquals(proc.status.getCarryFlag(), false, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
    
    @Test
    public void sbcImmediateTestCarry() {
        MemoryImpl mem = mem();
        proc.regA.set(0x30);
        proc.status.setCarryFlag(false);
        mem.setByte(0xE9, 0x1234); // ADC immediate
        mem.setByte(0x01, 0x1235);
        proc.setMemory(mem);
        nTicks(proc, 4);
        assertEquals(proc.regA.get(), (byte) 0x2e);
    }
}
