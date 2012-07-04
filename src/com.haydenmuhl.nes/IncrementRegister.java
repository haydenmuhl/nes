package com.haydenmuhl.nes;

public class IncrementRegister extends PullRegister<Byte> {
    protected boolean carry; // for overflow
    protected boolean uncarry; // for underflow
    protected boolean incremented;
    protected boolean decremented;
    
    protected IncrementRegister upper;
    
    public IncrementRegister(Byte initialData) {
        super(initialData);
        carry = false;
        uncarry = false;
        clearInputs();
    }
    
    public void increment() {
        clearInputs();
        incremented = true;
    }
    
    public void decrement() {
        clearInputs();
        decremented = true;
    }
    
    public boolean overflow() {
        return carry;
    }
    
    public boolean underflow() {
        return uncarry;
    }
    
    public void setUpper(IncrementRegister reg) {
        upper = reg;
    }
    
    @Override
    public void pull() {
        clearInputs();
        super.pull();
    }
    
    @Override
    public void tick() {
        uncarry = false;
        carry = false;
        if (incremented) {
            if (stored == (byte) 0xff) {
                carry = true;
                if (upper != null) {
                    upper.increment();
                }
            }
            stored++;
        } else if (decremented) {
            if (stored == (byte) 0x00) {
                uncarry = true;
                if (upper != null) {
                    upper.decrement();
                }
            }
            stored--;
        } else {
            super.tick();
        }
        clearInputs();
    }
    
    private void clearInputs() {
        incremented = false;
        decremented = false;
        toStore = null;
    }
}
