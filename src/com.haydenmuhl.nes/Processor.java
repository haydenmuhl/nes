package com.haydenmuhl.nes;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Processor implements Clocked {
    private byte PCL;
    private byte PCH;
    
    private byte temp;

    private Memory memory;
    
    private SubInstruction currentInstruction;
    
    private Logger logger;
    
    {
        logger = Logger.getAnonymousLogger();
        logger.setLevel(Level.OFF);
    }
    
    public Processor() {
        reset();
    }
    
    public void reset() {
        PCL = (byte) 0xfc;
        PCH = (byte) 0xff;
        currentInstruction = JMP0;
    }

    public void tick() {
        currentInstruction.go();
        currentInstruction = currentInstruction.next;
    }
    
    public void setMemory(Memory mem) {
        memory = mem;
    }
    
    public void setLogger(Logger l) {
        l.info("proc");
        
        logger = l;
        
    }
    
    private void incPC() {
        PCL++;
        if (PCL == 0) {
            PCH++;
        }
    }
    
    private final SubInstruction JMP0 = this.new SubInstruction() {
        public void go() {
            logger.finer(String.format("JMP0 - Read value from address 0x%x%x", PCH, PCL));
            memory.setAddress(PCH, PCL);
            temp = memory.getByte();
            logger.finer(String.format("JMP0 - Value read from memory: 0x%x", temp));
            incPC();
        }

        { next = JMP1; }
    };
    
    private final SubInstruction JMP1 = this.new SubInstruction() {
        public void go() {
            logger.finer(String.format("JMP1 - Read value from address 0x%x%x", PCH, PCL));
            memory.setAddress(PCH, PCL);
            PCH = memory.getByte();
            logger.finer(String.format("JMP1 - Value read from memory: 0x%x", PCH));
            PCL = temp;
            logger.finer(String.format("JMP1 - Setting program counter to 0x%x%x", PCH, PCL));
        }
        
        { next = DECODE; }
    };
    
    private final SubInstruction DECODE = this.new SubInstruction() {
        public void go() {
        }
    };
    
    private abstract class SubInstruction {
        public abstract void go();
        public SubInstruction next;
    }
}


