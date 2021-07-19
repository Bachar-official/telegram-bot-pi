import DHT.DHT22;
import DHT.DHTData;
import DHT.DHTxx;
import com.pi4j.io.gpio.RaspiPin;
import measure.Measure;
import org.telegram.telegrambots.ApiContextInitializer;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

public class App {

    public static void main(String[] args) {
        Integer SLEEP_PERIOD = 300000;
        ApiContextInitializer.init();
        Scanner sc = new Scanner(System.in);
        DbHandler handler = null;

        System.out.println("Инициализация БД...");
        try {
            handler = DbHandler.getInstance();
            System.out.println("БД успешно проинициализирована.");
            System.out.println("Инициализация сенсора DHT-22...");

            try {
                DHTxx sensor = new DHT22(RaspiPin.GPIO_07);
                System.out.println("Сенсор инициализирован. Измерения каждые 5 минут.");
                while (true) {
                    DHTData data = sensor.getData();
                    Measure newMeasure = data.toMeasure();
                    System.out.println("Новое измерение:\n" + newMeasure);
                    handler.addMeasure(newMeasure);
                    Thread.sleep(SLEEP_PERIOD);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            List<Measure> measures = handler.getAllMeasures();
            measures.forEach(measure -> System.out.println(measure));
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
