package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

// TODO: write a JavaDoc for the class

/**
 * Registers Class that implements the Registers Name Interface
 * and the emthods of the machine registers
 * @author ...
 */
public final class Registers {

    /**
     * hold the machine's registers
     */
    private final Map<RegisterNameImpl, Integer> registers = new HashMap<>();

    /**
     * Implements the Register name interface class for each register name(variables)
     */
    public enum RegisterNameImpl implements RegisterName {
        AX, BX, CX, DX, SP, BP;
    }

    /**
     * Initializes the registers by assigning a value of 0 to each register
     */
    public Registers() {
        //TODO: In general, it is not recommended to call
        //      methods of the class in constructors.
        //      Why is it? And why is it safe in this case?
        //      Write a short explanation.
        //      This is due to the fact that, it can expose the methods of the class
        //      to attackers even before the class is initialized. Attackers in this case
        //      can easily have access tot he memory and cause a high risk of code injection which may
        //      lead to the program not functioning well or to their own bad intentions. It is safe here because
        //      the method being called here only does the tasks of clearing whatever value that was in the register
        //      and replacing it with a zero.
        clear();
    }

    /**
     * method to implement the initialization of the registers by assigning each register a value of zero
     */
    public void clear() {
        for (RegisterNameImpl register : RegisterNameImpl.values())
            registers.put(register, 0);
    }

    public Optional<RegisterName> parseRegisterName(String s) {
        return Stream.of(RegisterNameImpl.values())
                .filter(r -> r.name().equals(s))
                //TODO: The next line of code does not seem to do much
                //      (r is mapped to r).
                //      What is the purpose of the next line of code?
                //      The purpose of the next line is to help in finding if any of the
                //      register name (in string format) that is passed as register name
                //      is on the list of selected register names - AX, CX, BX, DX, SP, and BP
                //      using Java's Stream API and goes ahead to directly map the name with the variable r
                .<RegisterName>map(r -> r)
                .findAny();
    }

    /**
     * Sets the given register to the value.
     *
     * @param register register name
     * @param value new value
     */
    public void set(RegisterName register, int value) {
        registers.put((RegisterNameImpl)register, value);
    }

    /**
     * Returns the value stored in the register.
     *
     * @param register register name
     * @return value
     */
    public int get(RegisterName register) {
        //TODO: Explain what happens if register is not an instance of RegisterName.
        //      Consider, for example, a call of the form registers.get(() -> "NEW").
        // It will give a name error which does not exists
        return registers.get((RegisterNameImpl)register);
    }

    // TODO: use pattern matching for instanceof
    // https://docs.oracle.com/en/java/javase/14/language/pattern-matching-instanceof-operator.html

    /**
     * Checks for the equality of an instance of the Registers object
     * @param o a possible instance of the Register Object
     * @return true or false if it's equal or not
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof Registers other) {
            return registers.equals(other.registers);
        }
        return false;
    }

    /**
     * implements the register own hashcode by overwriting the Map's Class hashCode method
     * @return
     */
    @Override
    public int hashCode() {
        return registers.hashCode();
    }

    /**
     * helps to print/stringify the register output which is a Map data structure.
     * @return the stringified register.
     */
    @Override
    public String toString() {
        return registers.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }
}
