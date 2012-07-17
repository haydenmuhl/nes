package com.haydenmuhl.nes.processor;

class CMP extends Instruction {
    public CMP(Mode mode) {
        super(mode);
    }
    
    @Override
    protected void immediate() {
        head = new SubInstruction() {
            public void go() {
                p.logger.finest("CMP immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                byte a = p.regA.get();
                byte m = p.memory.getByte();
                p.status.setZeroFlag(a == m);
                p.status.setCarryFlag(m >= a);
                p.status.setNegativeFlag((a - m) < 0);
            }
        };
        head.next = null;
    }
}
