import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;
public class onePassAssembler {
    public Map<String, Integer> symbolTable = new HashMap<>();

     public String SymbolTablePath="C:/one pass assembler/symbol table.txt";

    public int locationCounter=0;

    String symbol;

    public static  Map<String, InstructionInfo> Instruction_Set;

    public static class InstructionInfo {
        private String opcode;
        private InstructionFormat format;

        public InstructionInfo(String opcode, InstructionFormat format) {
            this.opcode = opcode;
            this.format = format;
        }

        public String getOpcode() {
            return opcode;
        }

        public InstructionFormat getFormat() {
            return format;
        }
    }


    public enum InstructionFormat {
        FORMAT_1,
        FORMAT_3
    }

     public void addsymbol(String labels) {
         String[] parts = labels.split("\\s+");
         if (parts.length >= 1) {
             symbol = parts[0];
             if (!symbolTable.containsKey(symbol)) {
                 symbolTable.put(symbol, locationCounter);
             } else {
                 System.err.println("Error: Duplicate symbol '" + symbol + "' detected. Skipping.");//will print a message and won't add the duplicates
             }
         }
     }


    private int handleFormat3(String instruction,String operand) {
        InstructionInfo instructionInfo = Instruction_Set.get(instruction);
        if (instructionInfo != null)
        {
            if(instructionInfo.getFormat()==InstructionFormat.FORMAT_3)
            {
                if(operand.startsWith("#"))
                {
                    locationCounter=+3;
                    //handle object code
                }
                if(operand.endsWith("X"))
                {
                   locationCounter=+3;
                   //handle object code
                }

//                int address;
//                try{
//                    address=Integer.parseInt(operand,16);
//                } catch (Exception e) {
//                    throw new RuntimeException(e);
//                }
            }
            return 3;
        }

        else
        {
            // Handle unknown instruction
            System.err.println("Error: Unknown instruction '" + instruction + "'. Skipping.");
            return 0; // or throw an exception, depending on your design
        }
    }
    public InstructionFormat getInstructionFormat(String instruction ){
        InstructionInfo instructionInfo = Instruction_Set.get(instruction);
        return (instructionInfo != null) ? instructionInfo.getFormat() : null;
    }

    public void Incremenlocation(String instruction,String operand) {
        //call instructions
        InstructionFormat format = getInstructionFormat(instruction);
        if (format != null) {


            switch (format) {
                case FORMAT_1:
                    locationCounter += 3; // Assuming FORMAT_1 increments by 3
                    break;
                case FORMAT_3:
                    locationCounter += handleFormat3(instruction, operand);
                    break;
            }

        }
    }
    public void intializeInstructions()
    {
        Instruction_Set = new HashMap<>();
        Instruction_Set.put("ADD", new InstructionInfo("18", InstructionFormat.FORMAT_3));
        Instruction_Set.put("AND", new InstructionInfo("14", InstructionFormat.FORMAT_3));
        Instruction_Set.put("COMP", new InstructionInfo("28", InstructionFormat.FORMAT_3));
        Instruction_Set.put("DIV", new InstructionInfo("24", InstructionFormat.FORMAT_3));
        Instruction_Set.put("J", new InstructionInfo("3C", InstructionFormat.FORMAT_3));
        Instruction_Set.put("JEQ", new InstructionInfo("30", InstructionFormat.FORMAT_3));
        Instruction_Set.put("JGT", new InstructionInfo("34", InstructionFormat.FORMAT_3));
        Instruction_Set.put("JLT", new InstructionInfo("38", InstructionFormat.FORMAT_3));
        Instruction_Set.put("JSUB", new InstructionInfo("48", InstructionFormat.FORMAT_3));
        Instruction_Set.put("LDA", new InstructionInfo("00", InstructionFormat.FORMAT_3));
        Instruction_Set.put("LDCH", new InstructionInfo("50", InstructionFormat.FORMAT_3));
        Instruction_Set.put("LDL", new InstructionInfo("08", InstructionFormat.FORMAT_3));
        Instruction_Set.put("LDX", new InstructionInfo("04", InstructionFormat.FORMAT_3));
        Instruction_Set.put("MUL", new InstructionInfo("20", InstructionFormat.FORMAT_3));
        Instruction_Set.put("OR", new InstructionInfo("44", InstructionFormat.FORMAT_3));
        Instruction_Set.put("RD", new InstructionInfo("D8", InstructionFormat.FORMAT_3));
        Instruction_Set.put("RSUB", new InstructionInfo("4C", InstructionFormat.FORMAT_3));
        Instruction_Set.put("STA", new InstructionInfo("0C", InstructionFormat.FORMAT_3));
        Instruction_Set.put("STCH", new InstructionInfo("54", InstructionFormat.FORMAT_3));
        Instruction_Set.put("STSW", new InstructionInfo("E8", InstructionFormat.FORMAT_3));
        Instruction_Set.put("STX", new InstructionInfo("10", InstructionFormat.FORMAT_3));
        Instruction_Set.put("SUB", new InstructionInfo("1C", InstructionFormat.FORMAT_3));
        Instruction_Set.put("TD", new InstructionInfo("E0", InstructionFormat.FORMAT_3));
        Instruction_Set.put("TIX", new InstructionInfo("2C", InstructionFormat.FORMAT_3));
        Instruction_Set.put("WD", new InstructionInfo("DC", InstructionFormat.FORMAT_3));
        Instruction_Set.put("FIX", new InstructionInfo("C4", InstructionFormat.FORMAT_1));
        Instruction_Set.put("FLOAT", new InstructionInfo("C0", InstructionFormat.FORMAT_1));
        Instruction_Set.put("HIO", new InstructionInfo("F4", InstructionFormat.FORMAT_1));
        Instruction_Set.put("NORM", new InstructionInfo("C8", InstructionFormat.FORMAT_1));
        Instruction_Set.put("SIO", new InstructionInfo("F0", InstructionFormat.FORMAT_1));
        Instruction_Set.put("TIO", new InstructionInfo("F8", InstructionFormat.FORMAT_1));
    }

}
