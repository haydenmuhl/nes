package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MemoryTest {
    private Byte b(int i) {
        return new Byte((byte) i);
    }

    private static class SettableDataSource extends AbstractDataSource<Byte> {
        private Byte value;
        
        public void setValue(int i) {
            value = (byte)i;
        }
        
        @Override
        public Byte output() {
            return value;
        }
    }

    @Test
    public void readAndWriteMemory() {
        SettableDataSource data = new SettableDataSource();
        Memory memory = new Memory();
        memory.setDataSource(data);
        
        assertEquals(memory.output(), b(0));
        data.setValue(10);
        memory.setAddress((short)0x1234);
        assertEquals(memory.output(), b(0));
        memory.write();
        assertEquals(memory.output(), b(10));
    }
    
    @Test
    public void testSetAddressWithBytes() {
        SettableDataSource data = new SettableDataSource();
        Memory memory = new Memory();
        memory.setDataSource(data);
        
        assertEquals(memory.output(), b(0));
        data.setValue(10);
        memory.setAddress((byte)0xfe, (byte)0x34);
        assertEquals(memory.output(), b(0));
        memory.write();
        memory.setAddress((short)0xfe34);
        assertEquals(memory.output(), b(10));
    }
}
