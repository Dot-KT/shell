import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) throws Exception {
        while (true) {
            System.out.print("$ ");
            String command = scanner.nextLine();
            Command newCommand = new Command(command);
            String firstPart = newCommand.splitCommand(command, "first");

            switch (firstPart) {
                case ""-> {break;}
                case "echo" -> {
                    String secondPart = command.replaceFirst("echo ", "");
                    System.out.println(String.join(" ", newCommand.readQuotesForEcho(secondPart)));
                    break;
                }
                case "type" -> {
                    String secondPart = command.replaceFirst("type ", "");
                    System.out.println(newCommand.checkType(secondPart));
                    break;
                }
                case "cat" -> {
                    try{
                        String secondPart = command.replaceFirst("cat ", "");
                        List<String> contents = new ArrayList<>(List.of("cat"));
                        contents.addAll(newCommand.readQuotesForCat(secondPart));
                        StringBuilder builder = new StringBuilder();
                        ProcessBuilder pBuilder = new ProcessBuilder(contents);
                        Process process = pBuilder.start();
                        process.getInputStream().transferTo(System.out);
                        process.getErrorStream().transferTo(System.out);
                        process.wait();
                    } catch (Exception e) {
                        System.out.print("");
                    }
                    break;
                }
                case "exit" -> {
                    return;
                }
                default -> newCommand.runCommand(command);
            }
        }
    }
}
