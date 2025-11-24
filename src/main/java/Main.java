import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        while(true){
            System.out.print("$ ");
            String command = new Scanner(System.in).nextLine();
            if(command.startsWith("echo ")){
                System.out.println(command.substring(5));
                continue;
            }
            switch (command){
                case "exit" -> {return;}
                default -> {System.out.println(command + ": command not found");}
            }

        }
    }
}
