import org.telegram.telegrambots.ApiContextInitializer;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Scanner sc = new Scanner(System.in);

        System.out.println("Please enter the bot token: ");

        String token = sc.next();
        Bot weatherBot = new Bot("weatherAtHomeBot", token);
        weatherBot.botConnect();
    }
}
