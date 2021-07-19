import org.telegram.telegrambots.ApiContextInitializer;
import java.sql.SQLException;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Scanner sc = new Scanner(System.in, "utf-8");
        DbHandler handler = null;

        System.out.println("Инициализация БД...");
        try {
            handler = DbHandler.getInstance();
            System.out.println("БД успешно проинициализирована.");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        System.out.println("Введите токен бота: ");

        String token = sc.next();
        sc.close();
        Bot weatherBot = new Bot("weatherAtHomeBot", token, handler);
        weatherBot.botConnect();
    }
}
