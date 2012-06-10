package com.haydenmuhl.nes;

public class Alu implements DataSource<Byte> {
    private DataSource<Byte> a;
    private DataSource<Byte> b;
    private Op operation;
    
    public enum Op {
        And, Or, Xor, BitTest, Add, Subtract, Compare,
        ShiftLeft, ShiftRight, RotateLeft, RotateRight
    }

    public void setDataSourceA(DataSource<Byte> dataSourceA) {
        a = dataSourceA;
    }

    public void setDataSourceB(DataSource<Byte> dataSourceB) {
        b = dataSourceB;
    }
    
    public void setOperation(Op op) {
        operation = op;
    }

    public Byte output() {
        int result = 0;
        switch (operation) {
        case Add:
            result = a.output() + b.output();
            break;
        case Subtract:
            result = a.output() - b.output();
            break;
        case And:
            result = a.output() & b.output();
            break;
        case Or:
            result = a.output() | b.output();
            break;
        case Xor:
            result = a.output() ^ b.output();
            break;
        case ShiftLeft:
            result = a.output() << 1;
            break;
        case ShiftRight:
            result = (a.output() & 0xff) >> 1;
            break;
        case RotateLeft:
            result = (a.output() << 1) | ((a.output() & 0xff) >> 7);
            break;
        case RotateRight:
            result = ((a.output() & 0xff) >> 1) | (a.output() << 7);
            break;
        }
        return (byte)result;
    }

}
