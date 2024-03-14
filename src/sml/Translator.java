package sml;

import sml.instruction.*;
import sml.Labels;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.Scanner;

/**
 * This class ....
 * <p>
 * The translator of a <b>S</b><b>M</b>al<b>L</b> program.
 *
 * @author ...
 */
public final class Translator {

    private final String fileName; // source file of SML code

    // line contains the characters in the current line that's not been processed yet
    private String line = "";

    public Translator(String fileName) {
        this.fileName = fileName;
    }

    public void readAndTranslate(Machine machine) throws IOException {
        Labels labels = machine.getLabels();
        Map<Integer, Instruction> program = machine.getProgram();

        // translate the small program in the file into
        // the labels and the program

        try (var sc = new Scanner(new File(fileName), StandardCharsets.UTF_8)) {
            labels.reset();
            program.clear();
            int programCounter = 0;

            // each iteration processes the contents of line
            // and reads the next input line into "line"
            while (sc.hasNextLine()) {
                line = sc.nextLine();
                String label = getLabel();

                Instruction instruction = getInstruction(label, machine);
                if (instruction != null) {
                    if (label != null)
                        labels.addLabel(label, programCounter);
                    program.put(programCounter, instruction);
                    programCounter += instruction.getSize();
                }
            }
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Translates the current line into an instruction with the given label
     *
     * @param label the instruction label
     * @return the new instruction
     * <p>
     * The input line should consist of a single SML instruction,
     * with its label already removed.
     */
    private Instruction getInstruction(String label, Machine machine) throws NoSuchFieldException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException, NoSuchMethodException {
        if (line.isEmpty())
            return null;

        String opcode = scan(false);

        // Using Java Reflection API
        if ( opcode.equals(MovInstruction.OP_CODE) || opcode.equals(DivInstruction.OP_CODE) || opcode.equals(MulInstruction.OP_CODE) ) {

            String d = scan(false);
            String s = scan(true);

            Class<?> instructionClass;

            if ( opcode.equals(MovInstruction.OP_CODE)) {
                instructionClass = Class.forName("sml.instruction.MovInstruction");
            }
            else if ( opcode.equals(DivInstruction.OP_CODE) ) {
                instructionClass = Class.forName("sml.instruction.DivInstruction");
            }
            else {
                instructionClass = Class.forName("sml.instruction.MulInstruction");
            }

            Constructor<?> constructor = instructionClass.getConstructor(String.class, InstructionDestination.class, InstructionSource.class);
            return (Instruction) constructor.newInstance(label, getDestination(d, machine), getSource(s, machine));
        }
        else if ( opcode.equals(AddInstruction.OP_CODE) || opcode.equals(SubInstruction.OP_CODE)) {

            String d = scan(true);
            String s = scan(false);

            Class<?> instructionClass = Class.forName("sml.instruction.MovInstruction");
            Constructor<?> constructor = instructionClass.getConstructor(String.class, InstructionDestination.class, InstructionSource.class);
            return (Instruction) constructor.newInstance(label, getDestination(d, machine), getSource(s, machine));
        }

        return null;

        // TODO: Next, use dependency injection to allow this machine class
        //       to work with different sets of opcodes (different CPUs)
    }

    private InstructionSource getSource(String s, Machine machine) {
        return Optional.<InstructionSource>empty()
                .or(() -> OperandImmediate.parseOperandImmediate(s))
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction source: " + s));
    }

    private InstructionDestination getDestination(String s, Machine machine) {
        return Optional.<InstructionDestination>empty()
                .or(() -> OperandMemory.parseOperandMemory(s, machine.getMemory()))
                .or(() -> OperandMemoryWithBase.parseOperandMemoryWithBase(s, machine.getMemory(), machine.getRegisters()))
                .or(() -> OperandRegister.parseOperandRegister(s, machine.getRegisters()))
                .orElseThrow(() -> new IllegalArgumentException("invalid instruction destination: " + s));
    }

    private String getLabel() {
        String word = scan(false);
        if (word.endsWith(":"))
            return word.substring(0, word.length() - 1);

        // undo scanning the word
        line = word + " " + line;
        return null;
    }

    /**
     * Return the first word of line and remove it from line.
     * If there is no word, return "".
     *
     * @param comma remove the trailing comma if set to true
     */
    private String scan(boolean comma) {
        line = line.trim();

        int whiteSpacePosition = 0;
        while (whiteSpacePosition < line.length()) {
            if (Character.isWhitespace(line.charAt(whiteSpacePosition)))
                break;
            whiteSpacePosition++;
        }

        String word = line.substring(0, whiteSpacePosition);
        line = line.substring(whiteSpacePosition);
        if (comma) {
            if (word.endsWith(","))
                return word.substring(0, word.length() - 1);
            throw new IllegalArgumentException("Expected a comma after " + word);
        }
        return word;
    }
}