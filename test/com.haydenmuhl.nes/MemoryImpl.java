package com.haydenmuhl.nes;

public class MemoryImpl implements Memory {
    public byte[] memory = new byte[0x10000]; // 16-bit addressable memory space
    public short address;
    
    public void setAddress(short address) {
        this.address = address;
        return;
    }
    
    public void setAddress(byte upper, byte lower) {
        int addr = upper & 0xff;
        addr = addr << 8;
        addr = addr | (lower & 0xff);
        setAddress((short) addr);
        return;
    }
    
    public byte getByte() {
        return memory[address & 0xffff];
    }
    
    public void setByte(byte b) {
        memory[address & 0xffff] = b;
        return;
    }
}
