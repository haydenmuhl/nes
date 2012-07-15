package com.haydenmuhl.nes.processor;

class LDA extends Instruction {
    public LDA(Mode mode) {
        switch (mode) {
        case immediate:
            immediate();
            break;
        }
    }
    
    private void immediate() {
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
}
