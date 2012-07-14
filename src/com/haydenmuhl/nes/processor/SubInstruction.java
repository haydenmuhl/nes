package com.haydenmuhl.nes.processor;

abstract class SubInstruction {
    protected Processor p;
    protected SubInstruction next;
    
    public abstract void go();
    
    public SubInstruction next() {
        return next;
    }
    
    public void setProcessor(Processor processor) {
        p = processor;
    }
}
