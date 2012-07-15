package com.haydenmuhl.nes.processor;

import java.util.HashMap;

class JMP extends Instruction {
    public JMP(Mode mode) {
        switch (mode) {
        case absolute:
            absolute();
            break;
        }
    }
    
    private void absolute() {
        SubInstruction sub;
        
        head = this.new SubInstruction() {
            public void go() {
                p.logger.finest("JMP absolute 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                p.temp.set(p.memory.getByte());
                p.incPC();
            }
        };
        sub = head;
        sub.next = this.new SubInstruction() {
            public void go() {
                p.logger.finest("JMP absolute 2");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                p.PCH.set(p.memory.getByte());
                p.PCL.set(p.temp.get());
            }
        };
        sub = sub.next;
        sub.next = null;
    }
}
