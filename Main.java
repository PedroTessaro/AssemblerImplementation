import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in); 
        File file = new File("");
        // test account
        
        String expression = "";
        
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
                    System.out.println("RUN");
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
