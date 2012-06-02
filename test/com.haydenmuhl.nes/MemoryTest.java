package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MemoryTest {
    @Test
    public void setMemoryLocationTest() {
        Memory memory = new Memory();
        memory.setAddress((short)0x0123);
        memory.setByte((byte)0xab);
        memory.setAddress((short)0x0432);
        memory.setByte((byte)0x12);
        memory.setAddress((short)0x0123);
        assertEquals(memory.getByte(), (byte)0xab);
        memory.setByte((byte)0x33);
        memory.setAddress((short)0x0432);
        assertEquals(memory.getByte(), (byte)0x12);
        memory.setAddress((short)0x0123);
        assertEquals(memory.getByte(), (byte)0x33);
    }
}
