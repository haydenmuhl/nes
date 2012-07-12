package com.haydenmuhl.nes;

public interface Memory {
    public void setAddress(byte upper, byte lower);
    public byte getByte();
    public void setByte(byte b);
}
