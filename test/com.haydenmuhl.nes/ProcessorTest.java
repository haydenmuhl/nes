package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;
import org.testng.Reporter;

import java.util.logging.*;

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
    }
}
