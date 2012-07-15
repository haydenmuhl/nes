package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class StatusRegisterTest {
    
    private byte b(int i) {
        return (byte) i;
    }

    @Test
    public void testNewStatusRegister() {
        StatusRegister r = new StatusRegister();
        assertEquals(r.get(), b(0x20));
        assertFalse(r.getCarryFlag());
        assertFalse(r.getZeroFlag());
        assertFalse(r.getInterruptFlag());
        assertFalse(r.getBreakFlag());
        assertFalse(r.getOverflowFlag());
        assertFalse(r.getNegativeFlag());
    }
    
    @Test
    public void testCarryFlag() {
        StatusRegister r = new StatusRegister();
        r.setCarryFlag(true);
        assertEquals(r.get(), b(0x21));
    }
    
    @Test
    public void testZeroFlag() {
        StatusRegister r = new StatusRegister();
        r.setZeroFlag(true);
        assertEquals(r.get(), b(0x22));
    }
    
    @Test
    public void testInterruptFlag() {
        StatusRegister r = new StatusRegister();
        r.setInterruptFlag(true);
        assertEquals(r.get(), b(0x24));
    }
    
    @Test
    public void testBreakFlag() {
        StatusRegister r = new StatusRegister();
        r.setBreakFlag(true);
        assertEquals(r.get(), b(0x30));
    }
    
    @Test
    public void testOverflowFlag() {
        StatusRegister r = new StatusRegister();
        r.setOverflowFlag(true);
        assertEquals(r.get(), b(0x60));
    }
    
    @Test
    public void testNegativeFlag() {
        StatusRegister r = new StatusRegister();
        r.setNegativeFlag(true);
        assertEquals(r.get(), b(0xa0));
    }
    
    @Test
    public void testSetValueCarry() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x01));
        assertTrue(r.getCarryFlag());
    }
    
    @Test
    public void testSetValueZero() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x02));
        assertTrue(r.getZeroFlag());
    }
    
    @Test
    public void testSetValueInterrupt() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x04));
        assertTrue(r.getInterruptFlag());
    }
    
    @Test
    public void testSetValueBreak() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x10));
        assertTrue(r.getBreakFlag());
    }
    
    @Test
    public void testSetValueOverflow() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x40));
        assertTrue(r.getOverflowFlag());
    }
    
    @Test
    public void testSetValueNegative() {
        StatusRegister r = new StatusRegister();
        r.set(b(0x80));
        assertTrue(r.getNegativeFlag());
    }
}
