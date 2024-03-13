package sml;

// TODO: write a JavaDoc for the class

/**
 * Represents an abstract instruction.
 *
 * @author ...
 */
public abstract class Instruction {
    protected final String label;
    protected final String opcode;

    protected final Flags flag = new Flags();

    /**
     * Constructor: an instruction with a label and an opcode
     * (opcode must be an operation of the language)
     *
     * @param label optional label (can be null)
     * @param opcode operation name
     */
    public Instruction(String label, String opcode) {
        this.label = label;
        this.opcode = opcode;
    }

    /**
     * Get the label of the instruction class
     * @return instruction label
     */
    public String getLabel() {
        return label;
    }

    /**
     * get the operation code - mov, add, sub, mul e.t.c
     * @return
     */

    public void setZF() {
        flag.setZF(true);
    }

    public boolean getZF() {
        return flag.getZF();
    }

    public boolean getSF() {
        return flag.getSF();
    }

    public void setSF() {
        flag.setSF(true);
    }

    public String getOpcode() {
        return opcode;
    }
    /**
     *
     * @return
     */

    public abstract int getSize();

    /**
     * Executes the instruction in the given machine.
     *
     * @param machine the machine the instruction runs on
     * @return the new program counter
     */

    public abstract int execute(Machine machine);

    /**
     * get the label in String format
     * @return
     */
    protected String getLabelString() {
        return (getLabel() == null) ? "" : getLabel() + ": ";
    }

    // TODO: What exactly is the meaning of abstract in the declaration below?
    //       Note that the method is declared in the superclass.
    //       The abstract in the method below helps the subclasses of this class to have a seperate
    //       definition on how to convert their result to their own custom string for an output replacing the already defined
    //       concrete method in the super class
    @Override
    public abstract String toString();

    // TODO: Make sure that subclasses also implement equals and hashCode (needed in class Machine).
}
