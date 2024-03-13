package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class AddInstruction extends Instruction {

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label         optional label (can be null)
     * @param opcode        operation name
     */

    public final static String OP_CODE = "add";

    private InstructionSource source;
    private InstructionDestination result;

    public AddInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.source = source;
        this.result = result;
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() - result.getSize();
    }

    @Override
    public int execute(Machine machine) {
        int sourceValue = this.source.getValue();
        int resultValue = this.result.getValue();

        result.setValue(sourceValue + resultValue);

        return this.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }
}
