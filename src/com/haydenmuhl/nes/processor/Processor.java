package com.haydenmuhl.nes.processor;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.HashMap;

import com.haydenmuhl.nes.Clocked;
import com.haydenmuhl.nes.Memory;
import com.haydenmuhl.nes.Register;

public class Processor implements Clocked {
    Register PCL = new Register();
    Register PCH = new Register();
    
    Register regA = new Register();
    
    Register temp = new Register();

    Memory memory;
    
    private Instruction.SubInstruction currentInstruction;
    
    Logger logger;
    
    public Processor() {
        logger = Logger.getLogger("com.haydenmuhl.nes.processor.Processor");
        reset();
    }
    
    public void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public void reset() {
        PCL.set(0xfc);
        PCH.set(0xff);
        Instruction i = new JMP(Mode.absolute);
        i.setProcessor(this);
        currentInstruction = i.head();
    }

    public void tick() {
        
        if (currentInstruction == null) {
            logger.finer("Decode");
            decode();
        } else {
            logger.finer("Go");
            currentInstruction.go();
            currentInstruction = currentInstruction.next();
        }
    }
    
    public void setMemory(Memory mem) {
        memory = mem;
    }
    
    void incPC() {
        PCL.set(PCL.get() + 1);
        if (PCL.get() == 0) {
            PCH.set(PCH.get() + 1);
        }
    }
    
    private void decode() {
        Instruction instr = null;
        logger.finer(String.format("Loading opcode from 0x%x%x", PCH.get(), PCL.get()));
        memory.setAddress(PCH.get(), PCL.get());
        byte opcode = memory.getByte();
        logger.finer(String.format("Opcode: 0x%x", opcode));
        incPC();
        if ((opcode & 0x01) == 0x01) {
            logger.finer("LDA immediate");
            instr = new LDA(Mode.immediate);
        }
        instr.setProcessor(this);
        currentInstruction = instr.head();
    }
}


