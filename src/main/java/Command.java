import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Command {
    private static List<String> shellCommands = new ArrayList<>(Arrays.asList("echo", "exit", "type"));
    private static String path = System.getenv("PATH");
    private static String[] pathCommands = path.split(File.pathSeparator);

    private String command;

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Command(String command) {
        this.command = command;
    }
    String checkType(String type) {
        if (shellCommands.contains(type)) {
            return type + " is a shell builtin";
        } else {
            return checkOriginalType(type);
        }
    }
    String checkOriginalType(String command) {
        if (path == null) return "invalid_command: not found";
        for (String pathCommand : pathCommands) {
            File file = new File(pathCommand, command);
            if (file.exists() && Files.isExecutable(file.toPath())) return command + " is " + file.getAbsolutePath();
        }
        return command + ": not found";
    }
    String splitCommand(String command, String part) {
        String[] subCommands = command.split(" ");
        if (part.equals("first")) {
            return subCommands[0];
        }
        return command.substring(5);
    }
    String[] splitCommand(String command) {
        try {
            return command.split(" ");
        } catch (Exception e) {
            return null;
        }
    }

    int checkExecutability(String command){
        if(shellCommands.contains(command)) return 1;
        for (String pathCommand : pathCommands) {
            File file = new File(pathCommand, command);
            if (file.exists() && Files.isExecutable(file.toPath())) return 2;
        }
        return 3;
    }
    void runCommand(String command){
        int checker = checkExecutability(splitCommand(command, "first"));
        if(checker==2){
            String[] commands = splitCommand(command);
            String command1 = commands[0];
            try{
                Process process = Runtime.getRuntime().exec(commands);
                process.getInputStream().transferTo(System.out);
            } catch (IOException e) {
                System.out.println(command1 + ": not found");
            }
        }else{
            System.out.println(command +": command not found");
        }
    }
    List<String> readQuotesForEcho(String input){
        boolean singleQuoteIsOpen = false;
        boolean doubleQuoteIsOpen = false;
        boolean isOpen = false;
        char previous = '2';
        List<String> args = new ArrayList<>();
        String content = "";
        for(char ch : input.toCharArray()){
            if(ch == '\\'){}
            else if(previous == '\\'){
                content+=ch;
            }
            else if(ch == '"' && !singleQuoteIsOpen && previous!= '\\'){
                doubleQuoteIsOpen = !doubleQuoteIsOpen;
                isOpen = doubleQuoteIsOpen;
            }
            else if(ch == '\'' && !doubleQuoteIsOpen){
                singleQuoteIsOpen = !singleQuoteIsOpen;
                isOpen = singleQuoteIsOpen;
            }
            else if(!isOpen && ch == ' '){
                if(content.length()>0) args.add(content);
                content = "";
            }
            else{
                content+=ch;
            }
            previous = ch;
        }
        if(content.length()>0) args.add(content);

        return args;
    }

    List<String> readQuotesForCat(String input){
        boolean singleQuoteIsOpen = false;
        boolean doubleQuoteIsOpen = false;
        boolean isOpen = false;
        List<String> args = new ArrayList<>();
        String content = "";
        for(char ch : input.toCharArray()){
            if(ch == '"' && !singleQuoteIsOpen){
                doubleQuoteIsOpen = !doubleQuoteIsOpen;
                isOpen = doubleQuoteIsOpen;
            }
            else if(ch == '\'' && !doubleQuoteIsOpen){
                singleQuoteIsOpen = !singleQuoteIsOpen;
                isOpen = singleQuoteIsOpen;
            }
            else if(!isOpen && ch == ' '){
                if(content.length()>0) args.add(content);
                content = "";
            }
            else{
                content+=ch;
            }
        }
        if(content.length()>0) args.add(content);

        return args;
    }
}
