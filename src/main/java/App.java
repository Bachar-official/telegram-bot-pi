import measure.Measure;
import org.telegram.telegrambots.ApiContextInitializer;

import java.sql.Array;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        ApiContextInitializer.init();
        Scanner sc = new Scanner(System.in);

        System.out.println("Инициализация БД...");
        try {
            DbHandler handler = DbHandler.getInstance();
            System.out.println("БД успешно проинициализирована.");
            List<Measure> measures = handler.getAllMeasures();
            measures.forEach(measure -> System.out.println(measure));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        System.out.println("Введите токен бота: ");

        String token = sc.next();
        sc.close();
        Bot weatherBot = new Bot("weatherAtHomeBot", token);
        weatherBot.botConnect();
    }
}
