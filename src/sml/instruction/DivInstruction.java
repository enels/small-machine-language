package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class DivInstruction extends Instruction {

    private final InstructionDestination result;

    private final InstructionSource source;
    public final static String OP_CODE = "div";

    public DivInstruction(String label, InstructionSource source, InstructionDestination result) {
        super(label, OP_CODE);
        this.source = source;
        this.result = result;
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() * result.getSize();
    }

    @Override
    public int execute(Machine machine) {
        int sourceValue = this.source.getValue();
        int resultValue = this.result.getValue();

        this.result.setValue(resultValue / sourceValue);

        return this.getSize();
    }
}
