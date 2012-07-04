package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class PullRegisterTest {
    
    @Test
    public void testInitializeRegister() {
        PullRegister<Integer> r = new PullRegister<Integer>(123);
        assertEquals(r.output(), new Integer(123));
    }
    
    @Test
    public void testSetAndTick() {
        PullRegister<Integer> r = new PullRegister<Integer>(123);
        r.setDataSource(new AbstractDataSource<Integer>() {
            public Integer output() { return new Integer(234); }
        });
        r.pull();
        assertEquals(r.output(), new Integer(123));
        r.tick();
        assertEquals(r.output(), new Integer(234));
    }
    
    @Test
    public void testSetNewValue() {
        PullRegister<Integer> r = new PullRegister<Integer>(321);
        assertEquals(r.output(), new Integer(321));
        r.setValue(234);
        assertEquals(r.output(), new Integer(234));
    }
}
