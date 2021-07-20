import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.List;

public class App {

    static String getTokenFromFile() throws IOException {
        List<String> strings = Files.readAllLines(Paths.get("token.txt"));
        return strings.get(0);
    }

    public static void main(String[] args) {
        ApiContextInitializer.init();
        DbHandler handler = null;
        String token = "";

        System.out.println("Database initialization...");
        try {
            handler = DbHandler.getInstance();
            System.out.println("Success. Getting bot's token...");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        try {
            token = getTokenFromFile();
            System.out.println("Success.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Bot weatherBot = new Bot("weatherAtHomeBot", token, handler);
        weatherBot.botConnect();
    }
}
