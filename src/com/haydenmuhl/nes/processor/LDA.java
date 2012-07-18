package com.haydenmuhl.nes.processor;

class LDA extends Instruction {
    public LDA(Mode mode) {
        super(mode);
    }
    
    @Override
    protected void immediate() {
        head = this.new SubInstruction() {
            public void go() {
                p.logger.finest("LDA immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                p.regA.set(p.memory.getByte());
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.status.setZeroFlag(p.regA.get() == 0);
                p.incPC();
            }
        };
        head.next = null;
    }
    
    @Override
    protected void zeroPage() {
        head = this.new SubInstruction() {
            public void go() {
                p.logger.finest("LDA zero page 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                p.temp.set(p.memory.getByte());
                p.incPC();
            }
        };
        head.next = this.new SubInstruction() {
            public void go() {
                p.logger.finest("LDA zero page 2");
                p.memory.setAddress((byte) 0, p.temp.get());
                p.regA.set(p.memory.getByte());
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.status.setZeroFlag(p.regA.get() == 0);
            }
        };
        head.next.next = null;
    }
}
