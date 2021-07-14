import org.telegram.telegrambots.ApiContextInitializer;

import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Scanner sc = new Scanner(System.in);

        System.out.println("Введите токен бота: ");

        String token = sc.next();
        sc.close();
        Bot weatherBot = new Bot("weatherAtHomeBot", token);
        weatherBot.botConnect();
    }
}
