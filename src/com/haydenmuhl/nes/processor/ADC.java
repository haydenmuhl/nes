package com.haydenmuhl.nes.processor;

class ADC extends Instruction {
    public ADC(Mode mode) {
        super(mode);
    }
    
    @Override
    protected void immediate() {
        head = this.new SubInstruction() {
            public void go() {
                p.logger.finest("ADC immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                byte a = p.regA.get();
                byte m = p.memory.getByte();
                p.regA.set(m + a);
                p.status.setZeroFlag(p.regA.get() == 0);
                p.status.setCarryFlag((0xff - (a & 0xff)) < (m & 0xff));
                p.status.setOverflowFlag(((a & 0x80) == (m & 0x80)) && 
                                         ((a & 0x80) != (p.regA.get() & 0x80)));
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.incPC();
            }
        };
        head.next = null;
    }
}

