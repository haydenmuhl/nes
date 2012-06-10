package com.haydenmuhl.nes;

public class StatusRegister implements DataSource<Byte> {
    
    private boolean carryFlag;
    private boolean zeroFlag;
    private boolean interruptFlag;
    private boolean breakFlag;
    private boolean overflowFlag;
    private boolean negativeFlag;

    public Boolean getCarryFlag() {
        return carryFlag;
    }

    public void setCarryFlag(boolean value) {
        carryFlag = value;
    }

    public Boolean getZeroFlag() {
        return zeroFlag;
    }

    public void setZeroFlag(boolean value) {
        zeroFlag = value;
    }

    public Boolean getInterruptFlag() {
        return interruptFlag;
    }

    public void setInterruptFlag(boolean value) {
        interruptFlag = value;
    }

    public Boolean getBreakFlag() {
        return breakFlag;
    }

    public void setBreakFlag(boolean value) {
        breakFlag = value;
    }

    public Boolean getOverflowFlag() {
        return overflowFlag;
    }

    public void setOverflowFlag(boolean value) {
        overflowFlag = value;
    }

    public Boolean getNegativeFlag() {
        return negativeFlag;
    }

    public void setNegativeFlag(boolean value) {
        negativeFlag = value;
    }
    
    public Byte output() {
        int result = 0;
        if(carryFlag) {
            result |= 1;
        }
        if(zeroFlag) {
            result |= (1 << 1);
        }
        if(interruptFlag) {
            result |= (1 << 2);
        }
        if(breakFlag) {
            result |= (1 << 4);
        }
        result |= (1 << 5);
        if(overflowFlag) {
            result |= (1 << 6);
        }
        if(negativeFlag) {
            result |= (1 << 7);
        }
        return (byte)result;
    }
    
    public void setValue(Byte value) {
        carryFlag     = ((value & 0x01) > 0);
        zeroFlag      = ((value & 0x02) > 0);
        interruptFlag = ((value & 0x04) > 0);
        breakFlag     = ((value & 0x10) > 0);
        overflowFlag  = ((value & 0x40) > 0);
        negativeFlag  = ((value & 0x80) > 0);
    }
}
