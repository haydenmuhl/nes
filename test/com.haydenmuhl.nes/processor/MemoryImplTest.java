package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MemoryImplTest {
    @Test
    public void testSetByte() {
        MemoryImpl mem = new MemoryImpl();
        mem.memory[0x1234] = (byte) 0;
        mem.setAddress((byte) 0x12, (byte) 0x34);
        mem.setByte((byte) 0x42);
        assertEquals(mem.memory[0x1234], (byte) 0x42);
    }
    
    @Test
    public void testGetByte() {
        MemoryImpl mem = new MemoryImpl();
        mem.memory[0x1234] = (byte) 0x42;
        mem.setAddress((byte) 0x12, (byte) 0x34);
        assertEquals(mem.getByte(), (byte) 0x42);
        mem.memory[0x1234] = (byte) 0x99;
        assertEquals(mem.getByte(), (byte) 0x99);
    }
}
