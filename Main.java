import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    static Scanner scanner = new Scanner(System.in); 
    static File file = new File("");
    
    static String expression = "";

    static LinkedList list = new LinkedList();

    static final int NULL = -999999999;

    private static void loadToMemory() {
        short counter = 0;

        try(Scanner fileReader = new Scanner(file)) {
            while(fileReader.hasNextLine()) {
                counter++;
                String data = fileReader.nextLine();
                String code = "";

                int lineNumber = -1;

                Pattern _pattern = Pattern.compile("^([0-9]+) ([A-Za-z0-9 -]*)");
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
    }

    private static void assembler() {
        char[] registers    = "ABCDEFGHIJKLMNOPQRSWXYZ".toCharArray();;
        int[] values        = new int[26];

        for(int i = 0; i < values.length; i++) {
            values[i] = -999999999;
        }

        short registerCounter = 0; 
        short errorCode = 0;

        Node pointer = list.getHead();

        while(pointer != null) {
            String code = "";

            int greater = 0;
            int lineNumber = -1;
            int pointerCode = 0;

            lineNumber = pointer.getLine();
            code = pointer.getCode();

            char firstRegister = code.toUpperCase().charAt(4);

            String instruction = code.substring(0, 3).toUpperCase();

            short loopCode = 0;
            if(Character.isDigit(firstRegister)) {
                System.out.println("ERRO: o primeiro valor precisa ser um registrador!");
                break;
            }

            if(!instruction.equals("OUT") && !instruction.equals("INC") && !instruction.equals("DEC") && code.length() < 6) {
                System.out.println("ERRO: Falta de segundo registrador na linha " + lineNumber + " do código");
                break;
            }

            switch (instruction) {
                case "MOV":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.toUpperCase().substring(6);
                            // It is a numerical value
                            if(value.matches("^-?[0-9]*$")) {
                                values[i] = Integer.parseInt(value); 
                                loopCode = 0;
                                break;
                            } 
                            // It is not a numerical value
                            else {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        if(values[j] != NULL) {
                                            values[i] = values[j]; 
                                            loopCode = 0;
                                        }
                                        else {
                                            System.out.println("ERRO: o segundo registrador precisa ser inicializado!");
                                            errorCode = 1;
                                        }
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;

                case "OUT":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            if(values[i] != NULL) {
                                System.out.println("O registrador " + firstRegister + " tem o valor: " + values[i]);
                                loopCode = 0;
                            }
                            else {
                                System.out.println("ERRO: É necessário inicializar o registrador antes de utilizado!");
                                errorCode = 1;
                            }
                            break;
                        }
                    }
                    break;

                case "INC":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            if(values[i] != NULL) {
                                values[i] += 1;
                                loopCode = 0;
                            }
                            else {
                                System.out.println("ERRO: é necessário inicializar o registrador antes de utilizado!");
                                errorCode = 1;
                            }
                            break;
                        }
                    }
                    break;

                case "DEC":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            if(values[i] != NULL) {
                                values[i] -= 1;
                                loopCode = 0;
                            }
                            else {
                                System.out.println("ERRO: é necessário inicializar o registrador antes de utilizado!");
                                errorCode = 1;
                            }
                        }
                    }
                    break;

                case "ADD":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.toUpperCase().substring(6);
                            if(value.matches("^-?[0-9]*$erro")) {
                                if(values[i] != NULL) {
                                    values[i] += Integer.parseInt(value);
                                    loopCode = 0;
                                }
                                else {
                                    System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                    errorCode = 1;
                                }
                            }
                            else if(value.matches("^[a-zA-z]*$")){
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        if(values[i] != NULL) {
                                            values[i] += values[j];
                                            loopCode = 0;
                                        }
                                        else {
                                            System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                            errorCode = 1;
                                        }
                                    }
                                }
                            }
                            else {
                                System.out.println("ERRO: segundo registrador ou valor incorreto na linha " + lineNumber);
                                errorCode = 1;  
                            }
                            break;
                        }
                    }
                    break;

                case "SUB":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.toUpperCase().substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                if(values[i] != NULL) {
                                    values[i] -= Integer.parseInt(value);
                                    loopCode = 0;
                                }
                                else {
                                    System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                    errorCode = 1;
                                }
                            }
                            else if(value.matches("^[a-zA-Z]+$")){
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        if(values[i] != NULL) {
                                            values[i] -= values[j];
                                            loopCode = 0;
                                        }
                                        else {
                                            System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                            errorCode = 1;
                                        }
                                    }
                                }
                            } 
                            else {
                                System.out.println("ERRO: segundo registrador ou valor incorreto na linha " + lineNumber);
                                errorCode = 1;  
                            }
                            break;
                        }
                    }
                    break;

                case "MUL":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.toUpperCase().substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                if(values[i] != NULL) {
                                    values[i] *= Integer.parseInt(value);
                                    loopCode = 0;
                                }
                                else {
                                    System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                    errorCode = 1;
                                }
                            }
                            else if(value.matches("^[a-zA-Z]+$")) {
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        if(values[i] != NULL) {
                                            values[i] *= values[j];
                                            loopCode = 0;
                                        }
                                        else {
                                            System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                            errorCode = 1;
                                        }
                                    }
                                }
                                break;
                            }
                            else {
                                System.out.println("ERRO: segundo registrador ou valor incorreto na linha " + lineNumber);
                                errorCode = 1;  
                            }
                            break;
                        }
                    }
                    break;

                case "DIV":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i]) {
                            String value = code.toUpperCase().substring(6);
                            if(value.matches("^-?[0-9]*$")) {
                                int intValue = Integer.parseInt(value);
                                if(intValue == 0) {
                                    System.out.println("ERRO: Divisão por zero!");
                                    errorCode = 1;
                                }
                                else {
                                    if(values[i] != NULL) {
                                        values[i] /= Integer.parseInt(value);
                                        loopCode = 0;
                                    }
                                    else {
                                        System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                        errorCode = 1;
                                    }
                                }
                            }
                            else if(value.matches("^[a-zA-z]*$")){
                                for(int j = 0; j < registers.length; j++) {
                                    if(value.charAt(0) == registers[j]) {
                                        if(values[j] != 0) {
                                            if(values[i] != NULL) {
                                                values[i] *= values[j];
                                                loopCode = 0;
                                            }
                                            else {
                                                System.out.println("erro: é necessário inicializar o registrador antes de utilizado!");
                                                errorCode = 1;
                                            }
                                        }
                                        else {
                                            System.out.println("ERRO: Divisão por zero!");
                                            errorCode = 1;
                                        }
                                    }
                                }
                            }
                            else {
                                System.out.println("ERRO: segundo registrador ou valor incorreto na linha " + lineNumber);
                                errorCode = 1;  
                            }
                            break;
                        }
                    }
                    break;

                case "JNZ":
                    loopCode = 1;
                    for(int i = 0; i < registers.length; i++) {
                        if(firstRegister == registers[i] && values[i] != 0) {
                            String value = code.toUpperCase().substring(6);
                            if(value.matches("^[0-9]*$")) {
                                Node gotoNode = list.search(Integer.parseInt(value));
                                if(gotoNode == null) {
                                    System.out.println("O valor: " + value + " não é uma linha válida!");
                                    errorCode = 1;
                                }
                                else {
                                    pointer = gotoNode;
                                    pointerCode = 1;
                                    loopCode = 0;
                                }
                            }
                            break;
                        }
                        else if(values[i] == 0) {
                            loopCode = 0;
                        }
                    }
                    break;

                default:
                    System.out.println("Erro de sintaxe na linha " + lineNumber + " do código");
                    errorCode = 1;
                    break;
            }

            if(errorCode != 0) break;
            if(loopCode == 1) {
                System.out.println("ERRO: Valor incorreto no lugar do registrador!");
            }
            if(pointerCode == 0) {
                pointer = pointer.getNext();
            }
        }
    }

    private static void saveFile(String fileName) {
        try {
            PrintWriter writer = new PrintWriter(fileName, "UTF-8");
            Node pointer = list.getHead();

            while(pointer != null) {
                writer.println("" + pointer.getLine() + " " + pointer.getCode());
                System.out.println("" + pointer.getLine() + " " + pointer.getCode());
                pointer = pointer.getNext();
            } 
            writer.close();
        }
        catch(Exception e) { 
            e.printStackTrace();
        }
    }

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
                    if(!list.isEmpty()) {
                        list.print();
                    }
                    else {
                        System.out.println("Carregue um arquivo na memória primeiro!");
                    }
                    break;

                case "RUN":
                    if(!list.isEmpty()) {
                        assembler();
                    }
                    else {
                        System.out.println("Carreguei um arquivo na memória primeiro!");
                    }
                    break;

                case "SAVE":
                    saveFile(file.getName());
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
                        file = new File(fileName);

                        if(!file.exists()) { 
                            System.out.println("O arquivo espeficificado não existe!");
                            break;
                        }

                        loadToMemory();
                        System.out.println("O arquivo foi carregado com sucesso!");
                        // END OF LOAD INSTRUCTION
                    } 
                    else {
                        pattern = Pattern.compile("^INS ([0-9]+ [A-Za-z]+ [A-Za-z] ?[0-9A-Za-z]*)$");
                        matcher = pattern.matcher(expression);

                        if(matcher.matches()) {
                            // INS instruction code
                            // INS <LINE> <INSTRUCTION>
                            String data = matcher.group(1);

                            pattern = Pattern.compile("^([0-9]+) ([A-Za-z]+ [A-Za-z]+ ?[A-Za-z0-9]*)");
                            matcher = pattern.matcher(data);

                            if(matcher.matches()) {
                                int line = Integer.parseInt(matcher.group(1));
                                String code = matcher.group(2);

                                list.insertOrdered(line, code);
                                System.out.println("Linha inserida com sucesso!");
                            } 
                            else {
                                System.out.println("ERRO: sintaxe no trecho: '" + data + "' incorreta!");
                            }
                            // END of INS instruction
                        } 
                        else {
                            pattern = Pattern.compile("^DEL [0-9]+ *$");
                            matcher = pattern.matcher(expression);

                            if(matcher.matches()) {
                                // DEL instruction code
                                String line = expression.substring(4, expression.length());  
                                if(list.remove(Integer.parseInt(line))) {
                                    System.out.println("Linha removida com sucesso!");
                                }
                                else {
                                    System.out.println("Não foi possível remover a linha " + line);
                                }
                                // END of DEL instruction
                            } 
                            else {
                                pattern = Pattern.compile("^DEL ([0-9]+) ([0-9]+)$");
                                matcher = pattern.matcher(expression);
                                
                                if(matcher.matches()) {
                                    // DEL(range) instruction code
                                    int startLine = Integer.parseInt(matcher.group(1));
                                    int endLine   = Integer.parseInt(matcher.group(2));
                                    for(int i = startLine; i <= endLine; i++) {
                                        list.remove(i);
                                    } 
                                    System.out.println("Todas as linhas no intervalo " + startLine + ":" + endLine + " foram deletadas!");
                                    // END of DEL(range) instruction
                                } 
                                else {
                                    pattern = Pattern.compile("^SAVE ([A-Za-z0-9]+.ED1)$");
                                    matcher = pattern.matcher(expression);

                                    if(matcher.matches()) {
                                        // SAVE(especific file) instruction code
                                        String fileName = matcher.group(1).toLowerCase();

                                        if(file.exists()) {
                                            System.out.println("Deseja salvar as alterações feitas no arquivo atual?");
                                            String confirmation = "";
                                            while(!confirmation.toUpperCase().equals("S") && !confirmation.toUpperCase().equals("N")) {
                                                System.out.println("Digite uma resposta válida: S/N");
                                                confirmation = scanner.nextLine();
                                            }
                                            if(confirmation.equals("N")) break;

                                            try {
                                                file = new File(fileName);
                                                if(!file.exists()) { 
                                                    file.createNewFile();
                                                    System.out.println("Novo arquivo criado com nome " + fileName);
                                                }
                                                else {
                                                    System.out.println("Trocando para arquivo " + fileName);
                                                }
                                                saveFile(fileName);    
                                            }
                                            catch(Exception e) {
                                                System.out.println("ERRO: não foi possível abrir ou criar o arquivo!");
                                                e.printStackTrace();
                                            }
                                        }
                                        // END of SAVE(especific file) instruction
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
