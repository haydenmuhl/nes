package com.haydenmuhl.nes;

public class Processor implements Clocked {
    private static String MEMORY = "mem";

    private IncrementRegister regPCH = new IncrementRegister((byte)0xff);
    private IncrementRegister regPCL = new IncrementRegister((byte)0xfc);
    
    private Mux<String, Byte> muxTemp = new Mux<String, Byte>();
    private PullRegister<Byte> regTemp = new PullRegister<Byte>((byte)0);
    
    private DataSourceImpl<Byte, Memory> memory = new DataSourceImpl<Byte, Memory>();
    
    private MicroInstruction currentInstruction;
    
    public Processor() {
        muxTemp.add(MEMORY, memory);
        regTemp.setDataSource(muxTemp);
        regPCL.setUpper(regPCH);

        reset();
    }
    
    public void reset() {
        regPCH.setValue((byte)0xff);
        regPCL.setValue((byte)0xfc);
        currentInstruction = JMP0;
    }

    public void tick() {
        currentInstruction.go();
        currentInstruction = currentInstruction.next;
        
        regTemp.tick();
        regPCL.tick();
        regPCH.tick();
    }
    
    public void setMemory(Memory mem) {
        memory.setDataSource(mem);
    }
    
    private final MicroInstruction JMP0 = this.new MicroInstruction() {
        public void go() {
            memory.getSource().setAddress(regPCH.output(), regPCL.output());
            muxTemp.select(MEMORY);
            regTemp.pull();
            
            regPCL.increment();
        }
    };
    
    private abstract class MicroInstruction {
        public abstract void go();
        public MicroInstruction next;
    }
}


