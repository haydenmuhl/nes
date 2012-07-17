package com.haydenmuhl.nes.processor;

abstract class Instruction {
    protected Processor p;
    protected SubInstruction head;
    
    public Instruction(Mode mode) {
        switch (mode) {
        case immediate:
            immediate();
            break;
        case absolute:
            absolute();
            break;
        }
    }
    
    protected void immediate() {
        throw new RuntimeException();
    }
    
    protected void absolute() {
        throw new RuntimeException();
    }
    
    public SubInstruction head() {
        return head;
    }
    
    public void setProcessor(Processor processor) {
        p = processor;
    }
    
    abstract class SubInstruction {
        protected SubInstruction next;
        
        public abstract void go();
        
        public SubInstruction next() {
            return next;
        }
    }
}
