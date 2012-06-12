package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class IncrementRegisterTest {
    private DataSource<Byte> ds(final int i) {
        return new AbstractDataSource<Byte>() {
            public Byte output() { return new Byte((byte)i); }
        };
    }
    
    private Byte b(int i) {
        return new Byte((byte) i);
    }

    @Test
    public void testInitialize() {
        IncrementRegister r = new IncrementRegister(b(0));
        assertEquals(r.output(), b(0));
    }
    
    @Test
    public void testPull() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.setDataSource(ds(100));
        r.pull();
        assertEquals(r.output(), b(0));
        r.tick();
        assertEquals(r.output(), b(100));
    }
    
    @Test
    public void testIncrement() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.increment();
        assertEquals(r.output(), b(0));
        r.tick();
        assertEquals(r.output(), b(1));
    }
    
    @Test
    public void testPullIncrement() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.setDataSource(ds(100));
        r.pull();
        r.increment();
        assertEquals(r.output(), b(0));
        r.tick();
        assertEquals(r.output(), b(1));
    }
    
    @Test
    public void testIncrementPull() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.setDataSource(ds(100));
        r.increment();
        r.pull();
        assertEquals(r.output(), b(0));
        r.tick();
        assertEquals(r.output(), b(100));
    }
    
    @Test
    public void testIncrementOnce() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.increment();
        assertEquals(r.output(), b(0));
        r.tick();
        assertEquals(r.output(), b(1));
        r.tick();
        assertEquals(r.output(), b(1));
    }
    
    @Test
    public void testOverflow() {
        IncrementRegister r = new IncrementRegister(b(0xff));
        r.increment();
        r.tick();
        assertEquals(r.output(), b(0));
        assertTrue(r.overflow());
    }
    
    @Test
    public void testNoOverflowOnPull() {
        IncrementRegister r = new IncrementRegister(b(0xff));
        r.setDataSource(ds(0x00));
        r.pull();
        r.tick();
        assertEquals(r.output(), b(0));
        assertFalse(r.overflow());
    }
    
    @Test
    public void testDecrement() {
        IncrementRegister r = new IncrementRegister(b(0x12));
        r.decrement();
        assertEquals(r.output(), b(0x12));
        r.tick();
        assertEquals(r.output(), b(0x11));
        r.tick();
        assertEquals(r.output(), b(0x11));
    }
    
    @Test
    public void testIncrementDecrement() {
        IncrementRegister r = new IncrementRegister(b(0x12));
        r.increment();
        r.decrement();
        assertEquals(r.output(), b(0x12));
        r.tick();
        assertEquals(r.output(), b(0x11));
    }
    
    @Test
    public void testDecrementIncrement() {
        IncrementRegister r = new IncrementRegister(b(0x12));
        r.decrement();
        r.increment();
        assertEquals(r.output(), b(0x12));
        r.tick();
        assertEquals(r.output(), b(0x13));
    }
    
    @Test
    public void testUnderflow() {
        IncrementRegister r = new IncrementRegister(b(0));
        r.decrement();
        r.tick();
        assertEquals(r.output(), b(0xff));
        assertTrue(r.underflow());
    }
}
