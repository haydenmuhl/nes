package com.haydenmuhl.nes;

import org.testng.annotations.*;
import static org.testng.Assert.*;

public class AluTest {
    private DataSource<Byte> ds(final int i) {
        return new AbstractDataSource<Byte>() {
            public Byte output() { return new Byte((byte)i); }
        };
    }
    
    private Byte b(int i) {
        return new Byte((byte) i);
    }

    @Test
    public void testAdd() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(10));
        alu.setDataSourceB(ds(15));
        alu.setOperation(Alu.Op.Add);
        assertEquals(alu.output(), b(25));
    }
    
    @Test
    public void testSubtract() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(35));
        alu.setDataSourceB(ds(20));
        alu.setOperation(Alu.Op.Subtract);
        assertEquals(alu.output(), b(15));
    }
    
    @Test
    public void testAnd() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0x0f));
        alu.setDataSourceB(ds(0xcc));
        alu.setOperation(Alu.Op.And);
        assertEquals(alu.output(), b(0x0c));
    }
    
    @Test
    public void testOr() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0x0f));
        alu.setDataSourceB(ds(0xcc));
        alu.setOperation(Alu.Op.Or);
        assertEquals(alu.output(), b(0xcf));
    }
    
    @Test
    public void testXor() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0x0f));
        alu.setDataSourceB(ds(0xcc));
        alu.setOperation(Alu.Op.Xor);
        assertEquals(alu.output(), b(0xc3));
    }
    
    @Test
    public void testShiftLeft() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0xcf));
        alu.setOperation(Alu.Op.ShiftLeft);
        assertEquals(alu.output(), b(0x9e));
    }
    
    @Test
    public void testShiftRight() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0xcf));
        alu.setOperation(Alu.Op.ShiftRight);
        assertEquals(alu.output(), b(0x67));
    }
    
    @Test
    public void testRotateLeft() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0xcf));
        alu.setOperation(Alu.Op.RotateLeft);
        assertEquals(alu.output(), b(0x9f));
    }
    
    @Test
    public void testRotateRight() {
        Alu alu = new Alu();
        alu.setDataSourceA(ds(0xcf));
        alu.setOperation(Alu.Op.RotateRight);
        assertEquals(alu.output(), b(0xe7));
    }
//        And, Or, Xor, BitTest, Add, Subtract, Compare,
//        ShiftLeft, ShiftRight, RotateLeft, RotateRight
}
