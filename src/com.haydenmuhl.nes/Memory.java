package com.haydenmuhl.nes;

public class Memory implements DataSource<Byte> {
    private byte[] memory = new byte[0x10000]; // 16-bit addressable memory space
    private short address;
    private DataSource<Byte> dataSource;
    
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
    
    public void write() {
        memory[address & 0xffff] = dataSource.output();
    }
    
    public void setDataSource(DataSource<Byte> data) {
        dataSource = data;
    }
}
