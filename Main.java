import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    private static void assembler() {
        char[] registers    = "abcdefghijklmnopqrstuvwxyz".toCharArray();;
        int[] values        = new int[26];

        short registerCounter = 0; 
        short errorCode = 0;
        short counter = 0;

        try(Scanner fileReader = new Scanner(file)) {
            while(fileReader.hasNextLine()) {
                counter++;
                String data = fileReader.nextLine();
                String code = "";

                int lineNumber = -1;

                Pattern _pattern = Pattern.compile("^([0-9]+) ([A-Za-z]+ [A-Za-z]+ ?[A-Za-z0-9]*)");
                Matcher _matcher = _pattern.matcher(data);

                if(_matcher.find()) {
                    lineNumber = Integer.parseInt(_matcher.group(1));
                    code = _matcher.group(2);
                    list.insertOrdered(lineNumber, code);
                } 
                else {
                    System.out.println("Erro de sintaxe na linha " + counter + " do arquivo");
                    return;
                }
            }
        }
        catch(FileNotFoundException e) {
            System.out.println("Carregue um arquivo primeiro!");
            return;
        }

        Node pointer = list.getHead();

        while(pointer != null) {
            String code = "";

            int greater = 0;
            int lineNumber = -1;
            int pointerCode = 0;

            lineNumber = pointer.getLine();
            code = pointer.getCode();

            char firstRegister = code.charAt(4);

            String instruction = code.substring(0, 3).toUpperCase();
        

            if(!instruction.equals("OUT") && !instruction.equals("INC") && !instruction.equals("DEC") && code.length() < 6) {
                System.out.println("Falta de segundo registrador na linha " + lineNumber + " do código");
                break;
            }

            switch (instruction) {
                case "MOV":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.substring(6);
                            // It is a numerical value
                            if(value.matches("^-?[0-9]*$")) {
                                values[i] = Integer.parseInt(value); 
                                break;
                            } 
                            // It is not a numerical value
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        values[i] = values[j]; 
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "OUT":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            System.out.println("O registrador " + firstRegister + " tem o valor: " + values[i]);
                            break;
                        }
                    }
                    break;

                case "INC":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            values[i] += 1;
                            break;
                        }
                    }
                    break;

                case "DEC":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            values[i] -= 1;
                            break;
                        }
                    }
                    break;

                case "ADD":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                values[i] += Integer.parseInt(value);
                            }
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        values[i] += values[j];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "SUB":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                values[i] -= Integer.parseInt(value);
                            }
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        values[i] -= values[j];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "MUL":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                values[i] *= Integer.parseInt(value);
                            }
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        values[i] *= values[j];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "DIV":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                int intValue = Integer.parseInt(value);
                                if(intValue == 0) {
                                    System.out.println("ERRO: Divisão por zero!");
                                    errorCode = 1;
                                }
                                else {
                                    values[i] /= Integer.parseInt(value);
                                }
                            }
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        values[i] /= values[j];
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "JNZ":
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i] && values[i] != 0) {
                            String value = code.substring(6);
                            if(value.matches("^[0-9]*$")) {
                                Node gotoNode = list.search(Integer.parseInt(value));
                                if(gotoNode == null) {
                                    System.out.println("O valor: " + value + " não é uma linha válida!");
                                    errorCode = 1;
                                }
                                else {
                                    pointer = gotoNode;
                                    pointerCode = 1;
                                }
                            }
                            break;
                        }
                    }
                    break;

                default:
                    System.out.println("Erro de sintaxe na linha " + lineNumber + " do código");
                    errorCode = 1;
                    break;
            }

            if(errorCode != 0) break;
            if(pointerCode == 0) {
                pointer = pointer.getNext();
            }
        }
    }

    static Scanner scanner = new Scanner(System.in); 
    static File file = new File("");
    
    static String expression = "";

    static LinkedList list = new LinkedList();

    public static void main(String[] args) {
        while(true) {
            System.out.print("> ");
            expression = scanner.nextLine();
            expression = expression.toUpperCase();
            
            String result = "";

            int exit = 0;

            switch(expression) {
                case "EXIT":
                    System.out.println("EXIT");
                    exit = 1;
                    break;

                case "LIST":
                    System.out.println("LIST");
                    break;

                case "RUN":
                    assembler();
                    break;

                case "SAVE":
                    System.out.println("SAVE");
                    break;

                default:
                    Pattern pattern = Pattern.compile("^LOAD [a-zA-Z]+.ED1");
                    Matcher matcher = pattern.matcher(expression);

                    if(matcher.matches()) {
                        // LOAD instruction code
                        // LOAD <FILE_NAME.ED1>
                        if(file.exists()) {
                            System.out.println("Deseja substituir o arquivo?(S/N)");
                            String confirmation = "";
                            while(!confirmation.equals("S") && !confirmation.equals("N")) {
                                System.out.println("Digite uma resposta válida: S/N");
                                confirmation = scanner.nextLine();
                            }
                            if(confirmation.equals("N")) break;
                        } 
                         
                        String fileName = expression.substring(5, expression.length());  
                        System.out.println(fileName);
                        file = new File(fileName);

                        if(!file.exists()) { 
                            System.out.println("O arquivo espeficificado não existe!");
                            break;
                        }

                        System.out.println("O arquivo foi carregado com sucesso!");
                        // END OF LOAD INSTRUCTION
                    } 
                    else {
                        pattern = Pattern.compile("^INS [0-9]+ [A-Za-z]+ [A-Za-z] [0-9A-Za-z]*$");
                        matcher = pattern.matcher(expression);

                        if(matcher.matches()) {
                            // INS instruction code
                            // INS <LINE> <INSTRUCTION>
                            
                        } 
                        else {
                            pattern = Pattern.compile("^DEL [0-9]+ *$");
                            matcher = pattern.matcher(expression);

                            if(matcher.matches()) {
                                // DEL instruction code
                            } 
                            else {
                                pattern = Pattern.compile("^DEL [0-9]+ [0-9+]$");
                                matcher = pattern.matcher(expression);
                                
                                if(matcher.matches()) {
                                    // DEL(range) instruction code
                                } 
                                else {
                                    pattern = Pattern.compile("^SAVE [A-Za-z0-9]+.ed1$");

                                    if(matcher.matches()) {
                                        // SAVE(especific file) instruction code
                                    }
                                }
                            }
                        }
                    }
            }

            if(exit == 1) break;
        }
    }
}
