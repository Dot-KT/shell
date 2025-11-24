import java.util.Scanner;

public class Main {
    Scanner scanner = new Scanner(System.in);

    static void main(String[] args) throws Exception {
        // TODO: Uncomment the code below to pass the first stage
        String command = "exit";
        while(true){
            System.out.print("$ ");
            command = new Scanner(System.in).nextLine();
            if(command.equals("exit")) break;
            System.out.println(command + ": command not found");
        }
    }
}
