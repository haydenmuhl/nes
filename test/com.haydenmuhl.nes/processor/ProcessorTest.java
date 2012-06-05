package com.haydenmuhl.nes.processor;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class ProcessorTest {
    
    @Test
    public void testInitialState() {
        Processor p = new Processor();
        assertEquals(p.getProgramCounter(), (short) 0xfffc);
        assertEquals(p.getStackPointer(), 0);
        assertEquals(p.getAccumulator(), 0);
        assertEquals(p.getRegisterX(), 0);
        assertEquals(p.getRegisterY(), 0);
    }
}
