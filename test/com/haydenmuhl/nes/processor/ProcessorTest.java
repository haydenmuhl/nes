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
    }
}
