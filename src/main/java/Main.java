import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {
    private static List<String> shellCommands = new ArrayList<>(Arrays.asList("echo", "exit", "type"));
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) throws Exception {
        while(true){
            System.out.print("$ ");
            String command = scanner.nextLine();
            String firstCommand = splitCommand(command, "first");
            switch (firstCommand){
                case "echo" -> System.out.println(splitCommand(command, ""));
                case "type" -> System.out.println(checkType(splitCommand(command, "")));
                case "exit" -> {return;}
                default -> System.out.println(command + ": command not found");
            }
        }
    }
    public static String checkType(String type){
        if (shellCommands.contains(type)){
            return type + " is a shell builtin";}
        else {
            return checkOriginalType(type);
        }
    }
    public static String checkOriginalType(String command){
        String path = System.getenv("PATH");
        if(path == null) return "invalid_command: not found";
        String[] pathCommands = path.split(File.pathSeparator);
        for(String pathCommand : pathCommands){
            File file = new File(pathCommand, command);
            if(file.exists() && Files.isExecutable(file.toPath())) return command + " is "+file.getAbsolutePath();
        }
        return command+": not found";
    }
    public static String splitCommand(String command, String part){
        String[] subCommands = command.split(" ");
        if(part.equals("first")) {
            return subCommands[0];
        }
        return command.substring(5);
    }
}
