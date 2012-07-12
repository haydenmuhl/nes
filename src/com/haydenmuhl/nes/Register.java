package com.haydenmuhl.nes;

public class Register {
    private byte data;
    
    public byte get() {
        return data;
    }
    
    public void set(byte b) {
        data = b;
    }
    
    public void set(int i) {
        set((byte) (i & 0xff));
    }
}
