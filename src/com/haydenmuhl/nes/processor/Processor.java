package com.haydenmuhl.nes.processor;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.Handler;
import java.util.HashMap;

import com.haydenmuhl.nes.Clocked;
import com.haydenmuhl.nes.Memory;
import com.haydenmuhl.nes.Register;

public class Processor implements Clocked {
    private Register PCL = new Register();
    private Register PCH = new Register();
    
    private Register temp = new Register();

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
        PCL.set(0xfc);
        PCH.set(0xff);
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
        PCL.set(PCL.get() + 1);
        if (PCL.get() == 0) {
            PCH.set(PCH.get() + 1);
        }
    }
    
    
    {
        logger = Logger.getLogger("com.haydenmuhl.nes.Processor");
        logger.setUseParentHandlers(false);
        logger.setLevel(Level.OFF);
        
        subInstr.put("JMP0", this.new SubInstruction() {
            public void go() {
                logger.finer(String.format("JMP0 - Read value from address 0x%x%x", PCH.get(), PCL.get()));
                memory.setAddress(PCH.get(), PCL.get());
                temp.set(memory.getByte());
                logger.finer(String.format("JMP0 - Value read from memory: 0x%x", temp.get()));
                incPC();
            }
            { next = "JMP1"; }
        });
        subInstr.put("JMP1", this.new SubInstruction() {
            public void go() {
                logger.finer(String.format("JMP1 - Read value from address 0x%x%x", PCH.get(), PCL.get()));
                memory.setAddress(PCH.get(), PCL.get());
                PCH.set(memory.getByte());
                logger.finer(String.format("JMP1 - Value read from memory: 0x%x", PCH.get()));
                PCL.set(temp.get());
                logger.finer(String.format("JMP1 - Setting program counter to 0x%x%x", PCH.get(), PCL.get()));
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


