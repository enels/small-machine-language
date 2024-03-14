package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;

public class DivInstruction extends Instruction {

    private final InstructionDestination result;

    private final InstructionSource source;
    public final static String OP_CODE = "div";

}
