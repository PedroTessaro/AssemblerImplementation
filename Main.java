import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {

    private static void assembler() {
        try(Scanner fileReader = new Scanner(file)) {
            int counter = 0;

            while(fileReader.hasNextLine()) {
                counter++;
                String data = fileReader.nextLine();

                Pattern _pattern = Pattern.compile("^([0-9]+) ([A-Za-z]+) ([A-Za-z]+) ?([A-Za-z0-9]*)");
                Matcher _matcher = _pattern.matcher(data);

                int lineNumber = -1;
                String instruction = "";
                String firstRegister = "";
                String secondRegister = "";

                if(_matcher.find()) {
                    lineNumber = Integer.parseInt(_matcher.group(1));
                    instruction = _matcher.group(2);
                    firstRegister = _matcher.group(3);
                    secondRegister = _matcher.group(4);

                    System.out.println(lineNumber);
                    System.out.println(instruction);
                    System.out.println(firstRegister);
                    System.out.println(secondRegister);
                }
                else {
                    System.out.println("Erro de sintaxe na linha " + counter + " do arquivo");
                    break;
                }
            } 
        }
        catch(FileNotFoundException e) {
            System.out.println("Carregue um arquivo primeiro!");
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
