package com.haydenmuhl.nes;

public class Memory {
    private byte[] memory = new byte[0x10000]; // 16-bit addressable memory space
    private short address;
    
    public void setAddress(short address) {
        this.address = address;
        return;
    }
    
    public void setAddress(byte upper, byte lower) {
        int addr = upper & 0xff;
        addr = addr << 8;
        addr = addr | (lower & 0xff);
        setAddress((short) addr);
    }
    
    public Byte output() {
        return memory[address & 0xffff];
    }
    
}
