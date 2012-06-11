package com.haydenmuhl.nes;

public class IncrementRegister extends PullRegister<Byte> {
    protected boolean carry;
    protected boolean incremented;
    
    public IncrementRegister(Byte initialData) {
        super(initialData);
        carry = false;
        incremented = false;
    }
    
    public void increment() {
        incremented = true;
        toStore = null;
    };
    
    public boolean overflow() {
        return carry;
    }
    
    @Override
    public void pull() {
        incremented = false;
        super.pull();
    }
    
    @Override
    public void tick() {
        carry = false;
        if (incremented) {
            if (stored == (byte) 0xff) {
                carry = true;
            }
            stored++;
        } else {
            super.tick();
        }
        incremented = false;
        toStore = null;
    }
}
