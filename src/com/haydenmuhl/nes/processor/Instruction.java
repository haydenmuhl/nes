package com.haydenmuhl.nes.processor;

abstract class Instruction {
    protected Processor p;
    protected SubInstruction head;
    
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
