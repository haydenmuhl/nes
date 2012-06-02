package com.haydenmuhl.nes;

public class Memory {
    private byte[] memory = new byte[0x10000]; // 16-bit addressable memory space
    private short address;
    
    public void setAddress(short address) {
        this.address = address;
        return;
    }
    
    public byte getByte() {
        return memory[address & 0xffff];
    }
    
    public void setByte(byte data) {
        memory[address & 0xffff] = data;
        return;
    }
}
