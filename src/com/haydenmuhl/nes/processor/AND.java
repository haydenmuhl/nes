package com.haydenmuhl.nes.processor;

class AND extends Instruction {
    public AND(Mode mode) {
        switch (mode) {
        case immediate:
            immediate();
            break;
        }
    }

    private void immediate() {
        head = this.new SubInstruction() {
            public void go() {
                p.logger.finest("AND immediate 1");
                p.memory.setAddress(p.PCH.get(), p.PCL.get());
                p.regA.set(p.regA.get() & p.memory.getByte());
                p.status.setNegativeFlag(p.regA.get() < 0);
                p.status.setZeroFlag(p.regA.get() == 0);
                
                p.incPC();
            }
        };
        head.next = null;
    }
}
