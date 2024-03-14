package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;

public class SubInstruction extends Instruction {

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label         optional label (can be null)
     * @param opcode        operation name
     */

    private final InstructionDestination result;

    private final InstructionSource source;
    public final static String OP_CODE = "div";

    public SubInstruction(String label, InstructionSource source, InstructionDestination result) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;

    }

    @Override
    public int getSize() {
        return 1 + source.getSize() - result.getSize();
    }


}
