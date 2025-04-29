import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedWriter;

//public class assembler {
//
//    public assembler() {
//        HashMap<String,InstructionInfo>Instructions=new HashMap<>();
//        Instructions.put("ADD", new InstructionInfo("18", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("AND", new InstructionInfo("40", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("COMP", new InstructionInfo("28", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("DIV", new InstructionInfo("24", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("J", new InstructionInfo("3C", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("JEQ", new InstructionInfo("30", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("JGT", new InstructionInfo("34", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("JLT", new InstructionInfo("38", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("JSUB", new InstructionInfo("48", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("LDA", new InstructionInfo("00", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("LDCH", new InstructionInfo("50", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("LDL", new InstructionInfo("08", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("LDX", new InstructionInfo("04", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("MUL", new InstructionInfo("20", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("OR", new InstructionInfo("44", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("RD", new InstructionInfo("D8", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("RSUB", new InstructionInfo("4C", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("STA", new InstructionInfo("0C", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("STCH", new InstructionInfo("54", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("STL", new InstructionInfo("14", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("STSW", new InstructionInfo("E8", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("STX", new InstructionInfo("10", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("SUB", new InstructionInfo("1C", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("TD", new InstructionInfo("E0", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("TIX", new InstructionInfo("2C", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("WD", new InstructionInfo("DC", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("FIX", new InstructionInfo("C4", InstructionInfo.InstructionFormat.Format_1));
//        Instructions.put("FLOAT", new InstructionInfo("C0", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("HIO", new InstructionInfo("F4", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("NORM", new InstructionInfo("C8", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("SIO", new InstructionInfo("F0", InstructionInfo.InstructionFormat.Format_3));
//        Instructions.put("TIO", new InstructionInfo("F8", InstructionInfo.InstructionFormat.Format_3));
//
//    }
//
//    for()
//
//    public static class  InstructionInfo {
//      //  public static Map<String, InstructionInfo> Instruction_Set;
//        private String opcode;
//       private InstructionFormat format;
//        public enum InstructionFormat
//        {
//            Format_1,
//            Format_3
//        }
//
//        public InstructionInfo(String opcode, InstructionFormat format) {
//            this.opcode = opcode;
//            this.format = format;
//        }
//
//        public String getOpcode() {
//            return opcode;
//        }
//
//        public InstructionFormat getFormat() {
//            return format;
//        }
//
//
//
//
//    }
//
//
//
//}
