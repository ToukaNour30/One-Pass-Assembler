import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class OnePassAssembler {
    //public Map<String, Integer> symbolTable = new HashMap<>();
    public Map<String, Integer> symbolTable = new LinkedHashMap<>();
    public static String SymbolTablePath = "C:/assembler 4/Symbol.txt";
    public static int locationCounter;
    public static int immediate;
    public static int indexed;
    public static ArrayList<String> AddressesList = new ArrayList<>();
    public static ArrayList<String> ObjectCodeList = new ArrayList<>();
    public static ArrayList<String> LocationCounterList = new ArrayList<>();
    public static String label;
    public static String opcode;
    public static String objectCode;
    String symbol;

    public static Map<String, InstructionInfo> Instruction_Set;

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

    public String HandleInstruction(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length >= 2) {
            opcode = parts[1];
            if (!"END".equalsIgnoreCase(opcode) && !"COPY".equalsIgnoreCase(opcode))
            {
                InstructionFormat format = getInstructionFormat(opcode);
                // Execute the corresponding handler based on the format
                switch (format) {
                    case FORMAT_1:
                        HandleFormat_1(format, line,String.valueOf(locationCounter) );
                        break;
                    case FORMAT_3:
                        HandleFormat_3(format, line,String.valueOf(locationCounter));
                        break;
                    default:
                        System.out.println("Unsupported instruction format");
                }
                //

            }
        }
        return "";
    }

    private boolean isFormat1(String opcode) {
        // Add your logic to determine if the opcode represents a Format 1 instruction
        // For example, check if it's in your Format 1 instruction set
        return Instruction_Set.containsKey(opcode)
                && Instruction_Set.get(opcode).getFormat() == InstructionFormat.FORMAT_1;
    }
    private boolean isFormat3(String opcode) {
        // Add your logic to determine if the opcode represents a Format 1 instruction
        // For example, check if it's in your Format 1 instruction set
        return Instruction_Set.containsKey(opcode)
                && Instruction_Set.get(opcode).getFormat() == InstructionFormat.FORMAT_3;
    }
    private void handleInstructions(String label, String opcode, String[] parts) {
        if (symbolTable.containsKey(label)) {
            // Symbol found in the symbol table, handle Format 3 or Format 1 accordingly
            int currentLocationCounter = symbolTable.get(label);

            if (isFormat1(opcode)) {
                HandleFormat_1(InstructionFormat.FORMAT_1, parts[1], String.valueOf(locationCounter));
            } else {
                HandleFormat_3(InstructionFormat.FORMAT_3, parts[1], String.valueOf(locationCounter));
            }
        } else {
            // Symbol not found in the symbol table, handle Format 1 or Format 3 accordingly
            if (isFormat1(opcode)) {
                HandleFormat_1(InstructionFormat.FORMAT_1, parts[1], String.valueOf(locationCounter) ); // Adjust -1 as needed
            } else {
                HandleFormat_3(InstructionFormat.FORMAT_3, parts[1], String.valueOf(locationCounter)); // Adjust -1 as needed
            }
        }
    }

    public void HandleFormat_1(InstructionFormat format, String line,String location) {
        if (format == InstructionFormat.FORMAT_1) {
            objectCode = opcode;
            ObjectCodeList.add(objectCode);
            locationCounter = locationCounter + 1;
            LocationCounterList.add(String.valueOf(locationCounter));
            String hexLocationCounter = Integer.toHexString(locationCounter);
            AddressesList.add(hexLocationCounter);
            System.out.println(hexLocationCounter);
            System.out.println(objectCode);
        }
    }

    public void HandleFormat_3(InstructionFormat format, String line,String location) {
        if (format == InstructionFormat.FORMAT_3) {
            int increment = 3;

            // String hexaLocationCounter3 = Integer.toHexString(locationCounter);
            if (line.trim().toUpperCase().endsWith("X")) {
                increment = 8000;
            }
            if (line.startsWith("#")) {
                increment += 3;
            }
            locationCounter += increment;
            LocationCounterList.add(String.valueOf(locationCounter));

            String hexaLocationCounter3 = Integer.toHexString(locationCounter);


        }
    }

    public void handleByteDirective(String line) {
        // Assuming format: BYTE C'characters' or BYTE X'hexadecimal'
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            String operand = parts[2];
            if (operand.startsWith("C'") && operand.endsWith("'")) {
                // Process character constant (e.g., C'HELLO')
                String characters = operand.substring(2, operand.length() - 1);
                for (char character : characters.toCharArray()) {
                    // Convert each character to ASCII and update location counter
                    int asciiValue = (int) character;
                    updateLocationCounterForByte(asciiValue);
                }
            } else if (operand.startsWith("X'") && operand.endsWith("'")) {
                // Process hexadecimal constant (e.g., X'1A')
                String hexValue = operand.substring(2, operand.length() - 1);
                try {
                    int decimalValue = Integer.parseInt(hexValue, 16);
                    updateLocationCounterForByte(decimalValue);
                } catch (NumberFormatException e) {
                    // Handle invalid hexadecimal format
                    System.out.println("Invalid hexadecimal format in BYTE directive: " + hexValue);
                }
            } else {
                // Invalid operand format for BYTE directive
                System.out.println("Invalid operand format for BYTE directive: " + operand);
            }
        }
    }

    private void updateLocationCounterForByte(int value) {
        // Update location counter based on the size of the byte
        locationCounter += 1;
        // Add logic to store the byte value or generate machine code as needed
        String hexValue = Integer.toHexString(value);
        ObjectCodeList.add(hexValue);
        AddressesList.add(Integer.toHexString(locationCounter));
        System.out.println("Updated Location Counter: " + locationCounter);
        System.out.println("Generated Machine Code: " + hexValue);
    }

    public void handleWordDirective(String line) {
        // Assuming format: WORD constant
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            String operand = parts[2];
            try {
                int constantValue = Integer.parseInt(operand);
                updateLocationCounterForWord(constantValue);
            } catch (NumberFormatException e) {
                // Handle invalid constant format
                System.out.println("Invalid constant format in WORD directive: " + operand);
            }
        }
    }

    private void updateLocationCounterForWord(int value) {
        // Update location counter based on the size of the word (3 bytes)
        locationCounter += 3;
        // Add logic to store the word value or generate machine code as needed
        String hexValue = String.format("%06X", value);
        ObjectCodeList.add(hexValue);
        AddressesList.add(Integer.toHexString(locationCounter - 2));  // Adjust for the 3-byte size
        System.out.println("Updated Location Counter: " + locationCounter);
        System.out.println("Generated Machine Code: " + hexValue);
    }

    public void handleResWDirective(String line) {
        // Assuming format: RESW size
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            String operand = parts[2];
            try {
                int size = Integer.parseInt(operand);
                updateLocationCounterForResW(size);
            } catch (NumberFormatException e) {
                // Handle invalid size format
                System.out.println("Invalid size format in RESW directive: " + operand);
            }
        }
    }

    private void updateLocationCounterForResW(int size) {
        // Update location counter based on the size of the reserved words
        locationCounter += 3 * size;
        // No machine code is generated for RESW
        // Add logic if you need to reserve space for the words
        AddressesList.add(Integer.toHexString(locationCounter - 3 * size));  // Start address of the reserved space
        System.out.println("Updated Location Counter: " + locationCounter);
        System.out.println("Reserved " + size + " words in memory.");
    }

    public void handleResBDirective(String line) {
        // Assuming format: RESB size
        String[] parts = line.split("\\s+");
        if (parts.length >= 3) {
            String operand = parts[2];
            try {
                int size = Integer.parseInt(operand);
                updateLocationCounterForResB(size);
            } catch (NumberFormatException e) {
                // Handle invalid size format
                System.out.println("Invalid size format in RESB directive: " + operand);
            }
        }
    }

    private void updateLocationCounterForResB(int size) {
        // Update location counter based on the size of the reserved bytes
        locationCounter += size;
        // No machine code is generated for RESB
        // Add logic if you need to reserve space for the bytes
        AddressesList.add(Integer.toHexString(locationCounter - size));  // Start address of the reserved space
        System.out.println("Updated Location Counter: " + locationCounter);
        System.out.println("Reserved " + size + " bytes in memory.");
    }

    public void writeAssemblyCodeAndLocationCountersToFile(String inputfilePath,String outputfilepath, Map<String, Integer> symbolTable) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("C:/assembeler 3/OUTPUT.txt"))) {
            writer.write("Line Number\tAssembly Code\tLocation Counter\n");
            try (BufferedReader read = new BufferedReader(new FileReader("C:/assembeler 3/assembly.txt"))) {
                int lineNumber = 1;
                String line;
                while ((line = read.readLine()) != null) {
                    String[] parts = line.split("\\s+");
                    String label = (parts.length >= 1) ? parts[0] : "";
                    String instruction=(parts.length>=2)?parts[1]:"";
                    HandleInstruction(line);
                    HandleFormat_1(InstructionFormat.FORMAT_1,instruction, String.valueOf(locationCounter));
                    HandleFormat_3(InstructionFormat.FORMAT_1,instruction, String.valueOf(locationCounter));
                    if (symbolTable.containsKey(label)) {
                        int locationCounter = symbolTable.get(label);
                        writer.write(lineNumber + "\t\t" + line + "\t\t" + locationCounter + "\n");
                    }
                    else {
                        writer.write(lineNumber + "\t\t" + line + "\t\t\n");
                    }

                    lineNumber++;

                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    private InstructionFormat getInstructionFormat(String opcode) {
        // Retrieve the InstructionInfo for the given opcode from the Instruction_Set
        InstructionInfo info = Instruction_Set.get(opcode);
        if (info != null) {
            // Return the format from the retrieved InstructionInfo
            return info.getFormat();
        } else {
            // Default to FORMAT_3 if not found (adjust as needed)
            return InstructionFormat.FORMAT_3;
        }
    }
    public void addsymbol(String labels,InstructionFormat format) {
        String[] parts = labels.split("\\s+");
        if (parts.length >= 1) {
            symbol = parts[0];
            if (!symbolTable.containsKey(symbol) && !"END".equalsIgnoreCase(symbol) && !"COPY".equalsIgnoreCase(symbol)) {
                if (!symbol.isEmpty()) {
                    symbolTable.put(symbol, locationCounter);
                    if (format == InstructionFormat.FORMAT_1) {
                        // Increment for Format 1
                        //locationCounter += 1;
                        HandleFormat_1(format,labels, String.valueOf(locationCounter));

                    } else if (format == InstructionFormat.FORMAT_3) {
                        // Increment for Format 3
                        HandleFormat_3(format,labels,String.valueOf(locationCounter));

                        // locationCounter += 3;
                    }
                }
            }
        }
    }

    public static  void intializeInstructions()
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




