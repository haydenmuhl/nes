package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

import com.haydenmuhl.nes.TestNgReporterHandler;

public class ProcessorTest {
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

    @Test
    public void initialTest() {
        proc.setMemory(mem());
        proc.tick();
        proc.tick();
        assertEquals(proc.PCH.get(), (byte) 0x12);
        assertEquals(proc.PCL.get(), (byte) 0x34);
    }
    
    // LDA
    
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
    
    // ADC
    
    @Test
    public void adcImmediateTest() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x02, 0x1235);
        mem.setByte(0x69, 0x1236); // ADC immediate opcode
        mem.setByte(0x03, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x05);
        assertEquals(proc.status.getCarryFlag(), false, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void adcImmediateTestZeroFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x02, 0x1235);
        mem.setByte(0x69, 0x1236); // ADC immediate opcode
        mem.setByte(0xfe, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x00);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), true, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void adcImmediateTestCarryFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0xff, 0x1235);
        mem.setByte(0x69, 0x1236); // ADC immediate opcode
        mem.setByte(0x02, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x01);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void adcImmediateTestOverflowFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x80, 0x1235);
        mem.setByte(0x69, 0x1236); // ADC immediate opcode
        mem.setByte(0x81, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0x01);
        assertEquals(proc.status.getCarryFlag(), true, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), true, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), false, "Negative flag");
    }
    
    @Test
    public void adcImmediateTestNegativeFlag() {
        MemoryImpl mem = mem();
        mem.setByte(0xA9, 0x1234); // LDA immediate opcode
        mem.setByte(0x01, 0x1235);
        mem.setByte(0x69, 0x1236); // ADC immediate opcode
        mem.setByte(0xF0, 0x1237);
        proc.setMemory(mem);
        nTicks(proc, 6);
        assertEquals(proc.regA.get(), (byte) 0xF1);
        assertEquals(proc.status.getCarryFlag(), false, "Carry flag");
        assertEquals(proc.status.getZeroFlag(), false, "Zero flag");
        assertEquals(proc.status.getOverflowFlag(), false, "Overflow flag");
        assertEquals(proc.status.getNegativeFlag(), true, "Negative flag");
    }
}
