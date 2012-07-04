package com.haydenmuhl.nes;

public class Processor implements Clocked {
    private IncrementRegister regPCL;
    private IncrementRegister regPCH;
    
    private Memory memory;
    
    private Instruction instruction;
    private AddressingMode mode;
    
    public Processor() {
        reset();
    }
    
    public void reset() {
        instruction = Instruction.JMP;
        mode = AddressingMode.Absolute;
        regPCH = new IncrementRegister((byte)0xff);
        regPCL = new IncrementRegister((byte)0xfc);
    }

    public void tick() {
    
    }
    
    public static enum Instruction {
        JMP
    }

    public static enum AddressingMode {
        Absolute
    }
    
    private abstract class MicroInstruction {
        public abstract void go();
    }
}


