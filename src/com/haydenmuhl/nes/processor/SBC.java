package com.haydenmuhl.nes.processor;

class SBC extends Instruction {
    public SBC(Mode mode) {
        super(mode);
    }
    
    @Override
    protected void immediate() {
        head = this.new SubInstruction() {
            @Override
            public void go() {
                p.logger.finest("SBC immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                byte m = p.memory.getByte();
                byte a = p.regA.get();
                p.regA.set(a - m - (p.status.getCarryFlag() ? 0 : 1));
                p.status.setZeroFlag(p.regA.get() == 0);
                p.status.setCarryFlag((m & 0xff) <= (a & 0xff));
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.status.setOverflowFlag((a & 0x80) != (m & 0x80)
                                      && (a & 0x80) != (p.regA.get() & 0x80));
                p.incPC();
            }
        };
    }
}
