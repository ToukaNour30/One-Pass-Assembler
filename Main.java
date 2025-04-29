import java.io.*;
import java.util.Map;

public class Main {
    public static void main(String[] args)
    {
        String PathAssembly = "C:/assembler 4/Assembler.txt";
        String outputpath="C:/assembler 4/output.txt";
        OnePassAssembler one = new OnePassAssembler();
        try {
            BufferedReader read = new BufferedReader(new FileReader(PathAssembly));
            String line; //ine file i read line by line using readLine() and this method returns string to the line variable
            one.intializeInstructions();
            while ((line = read.readLine()) != null) {


                String[] parts = line.split("\\s+");
                String instruction = (parts.length >= 2) ? parts[1] : "";
                String operand = (parts.length >= 3) ? parts[2] : "";
                line = (parts.length >= 1) ? parts[0] : "";

                one. addsymbol(line, OnePassAssembler.InstructionFormat.FORMAT_1);
                one. addsymbol(line,OnePassAssembler.InstructionFormat.FORMAT_3);

                System.out.println(instruction + "\t" + operand);

                if(parts.length>=2 && parts[1].equalsIgnoreCase("START"))
                {
                    //System.out.println("Setting location counter to: " + one.locationCounter);
                    if(parts.length>=3) {
                        int newLocationCounter = Integer.parseInt(parts[2]);
                        one.locationCounter=newLocationCounter;
                        System.out.println("new location ="+one.locationCounter);

                        String result = one.HandleInstruction(line);
                        System.out.println(" result of handle instruction::"+result);
                        if(line.endsWith("X")){
                            one.locationCounter+=8000;
                        }
                        one.HandleInstruction(line);

                    }
                }
                else {
                    one.locationCounter += 2;
                }

            }
            System.out.println(line); //
            read.close();
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
        one.writeAssemblyCodeAndLocationCountersToFile(PathAssembly,outputpath, one.symbolTable);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(one.SymbolTablePath))) {
            writer.write("labels\t\t location counter"); // Writing a header or initial content
            for (Map.Entry<String, Integer> entry : one.symbolTable.entrySet()) {
                writer.newLine();
                writer.write(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try  {
            BufferedWriter write2 = new BufferedWriter(new FileWriter("C:/assembler 2/HTE.txt"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
