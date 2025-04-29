import java.io.*;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        String PathAssembly = "C:/one pass assembler/Assembly code.txt";
        onePassAssembler one=new onePassAssembler();

        try {
            BufferedReader read = new BufferedReader(new FileReader(PathAssembly));
            String line; //ine file i read line by line using readLine() and this method returns string to the line variable
            while((line= read.readLine())!=null)
            {
                String[] parts = line.split("\\s+");
                one.intializeInstructions();
                String labels=(parts.length>=1)?parts[0]:"";
                one.addsymbol(labels);
                String instruction = (parts.length >= 2) ? parts[1] : "";
                String operand = (parts.length >= 3) ? parts[2] : "";
                one.Incremenlocation(instruction,operand);
                System.out.println(instruction);
                System.out.println(operand);


                  //  String[] parts = line.split("\\s+");
                    if (parts.length>=3) {
                        labels = parts[0];

                        System.out.println(labels);
//                    }
                        System.out.println(line); //
                    }
            }

            read.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(one.SymbolTablePath))) {
            writer.write("labels\t\t location counter"); // Writing a header or initial content

            for (Map.Entry<String, Integer> entry : one.symbolTable.entrySet()) {
                writer.newLine();
                writer.write(entry.getKey() + ": " + entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    } // i was trying to call lin in the method of incrementing the location counter
}