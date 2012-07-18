package com.haydenmuhl.nes.processor;

abstract class Instruction {
    protected Processor p;
    protected SubInstruction head;
    
    public Instruction(Mode mode) {
        switch (mode) {
        case immediate:
            immediate();
            break;
        case zeroPage:
            zeroPage();
            break;
        case absolute:
            absolute();
            break;
        }
    }
    
    protected void immediate() {
        throw new UnsupportedInstructionException();
    }
    
    protected void zeroPage() {
        throw new UnsupportedInstructionException();
    }
    
    protected void absolute() {
        throw new UnsupportedInstructionException();
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
