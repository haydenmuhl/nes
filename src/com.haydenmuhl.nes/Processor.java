package com.haydenmuhl.nes;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.HashMap;

public class Processor implements Clocked {
    private byte PCL;
    private byte PCH;
    
    private byte temp;

    private Memory memory;
    
    private SubInstruction currentInstruction;
    
    private Logger logger;
    private HashMap<String, SubInstruction> subInstr = new HashMap<String, SubInstruction>();
    
    public Processor() {
        reset();
    }
    
    public void setLoggerLevel(Level level) {
        logger.setLevel(level);
    }
    
    public void addLoggerHandler(Handler handler) {
        logger.addHandler(handler);
    }
    
    public void reset() {
        PCL = (byte) 0xfc;
        PCH = (byte) 0xff;
        currentInstruction = subInstr.get("JMP0");
    }

    public void tick() {
        currentInstruction.go();
        currentInstruction = subInstr.get(currentInstruction.next);
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
    
    
    {
        logger = Logger.getLogger("com.haydenmuhl.nes.Processor");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.OFF);
        
        subInstr.put("JMP0", this.new SubInstruction() {
            public void go() {
                logger.finer(String.format("JMP0 - Read value from address 0x%x%x", PCH, PCL));
                memory.setAddress(PCH, PCL);
                temp = memory.getByte();
                logger.finer(String.format("JMP0 - Value read from memory: 0x%x", temp));
                incPC();
            }
            { next = "JMP1"; }
        });
        subInstr.put("JMP1", this.new SubInstruction() {
            public void go() {
                logger.finer(String.format("JMP1 - Read value from address 0x%x%x", PCH, PCL));
                memory.setAddress(PCH, PCL);
                PCH = memory.getByte();
                logger.finer(String.format("JMP1 - Value read from memory: 0x%x", PCH));
                PCL = temp;
                logger.finer(String.format("JMP1 - Setting program counter to 0x%x%x", PCH, PCL));
            }
            
            { next = "DECODE"; }
        });
        subInstr.put("DECODE", this.new SubInstruction() {
            public void go() {
            }
        });
    }

    private abstract class SubInstruction {
        public abstract void go();
        public String next;
    }
}


