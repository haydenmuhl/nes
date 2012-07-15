package com.haydenmuhl.nes.processor;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.HashMap;

import com.haydenmuhl.nes.Clocked;
import com.haydenmuhl.nes.Memory;
import com.haydenmuhl.nes.Register;
import com.haydenmuhl.nes.StatusRegister;

public class Processor implements Clocked {
    Register PCL = new Register();
    Register PCH = new Register();
    StatusRegister status = new StatusRegister();
    
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
        byte instructionCode = memory.getByte();
        logger.finer(String.format("Instruction: 0x%x", instructionCode));
        incPC();
        if ((instructionCode & 0x01) == 0x01) {
            logger.finer("Instruction Group 1");
            Mode mode = Mode.immediate;
            int op = (instructionCode & 0xff) >>> 5;
            logger.finer("Opcode: " + op);
            
            if (op == 5) {
                instr = new LDA(mode);
            } else if (op == 3) {
                instr = new ADC(mode);
            } else if (op == 1) {
                instr = new AND(mode);
            }
        }
        instr.setProcessor(this);
        currentInstruction = instr.head();
    }
}


