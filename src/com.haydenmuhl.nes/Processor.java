package com.haydenmuhl.nes;

public class Processor {
    private short programCounter;
    private byte stackPointer;
    private byte accumulator;
    private byte registerX;
    private byte registerY;
    
    // Status flags
    private boolean fNegative;
    private boolean fOverflow;
    private boolean fBreak;
    private boolean fIrqDisable;
    private boolean fZero;
    private boolean fCarry;
    
    private Memory memory;
    
    public Processor() {
        programCounter = (short) 0xfffc;
    }
    
    public short getProgramCounter() {
        return programCounter;
    }
    
    public byte getStackPointer() {
        return stackPointer;
    }
    
    public byte getAccumulator() {
        return accumulator;
    }
    
    public byte getRegisterX() {
        return registerX;
    }
    
    public byte getRegisterY() {
        return registerY;
    }
    
    public byte getFlags() {
        int flags = 0;
        if (fNegative) {
            flags = flags | 0x80;
        }
        if (fOverflow) {
            flags = flags | 0x40;
        }
        if (fBreak) {
            flags = flags | 0x10;
        }
        if (fIrqDisable) {
            flags = flags | 0x04;
        }
        if (fZero) {
            flags = flags | 0x02;
        }
        if (fCarry) {
            flags = flags | 0x01;
        }
        return (byte) flags;
    }
    
    public void setMemory(Memory mem) {
        memory = mem;
    }
    
    public void tick() {
    }
}
