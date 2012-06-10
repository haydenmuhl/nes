package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class MuxTest {
    
    private DataSource<Integer> ds(final int i) {
        return new AbstractDataSource<Integer>() {
            public Integer output() { return new Integer(i); }
        };
    }
    
    @Test
    public void testMux() {
        Mux<String, Integer> m = new Mux<String, Integer>();
        m.add("foo", ds(123));
        m.add("bar", ds(234));
        m.select("foo");
        assertEquals(m.output(), (Integer) 123);
        m.select("bar");
        assertEquals(m.output(), (Integer) 234);
    }
}
