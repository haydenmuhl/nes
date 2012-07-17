package com.haydenmuhl.nes.processor;

class EOR extends Instruction {
    public EOR(Mode mode) {
        super(mode);
    }
    
    @Override
    protected void immediate() {
        head = new SubInstruction() {
            public void go() {
                p.logger.finest("EOR immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                byte a = p.regA.get();
                byte m = p.memory.getByte();
                p.regA.set(a ^ m);
                p.status.setZeroFlag(p.regA.get() == 0);
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.incPC();
            }
        };
        head.next = null;
    }
}
