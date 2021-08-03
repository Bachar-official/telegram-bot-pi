import org.telegram.telegrambots.ApiContextInitializer;

import java.io.IOException;

import utils.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class App {

    static final Integer TOKEN_LINE = 0;
    static final Integer PASSWORD_LINE = 1;

    public static void main(String[] args) {
        ApiContextInitializer.init();
        DbHandler handler = null;
        String token = "";
        String password = "";

        System.out.println("Database initialization...");
        try {
            handler = DbHandler.getInstance();
            System.out.println("Success. Getting bot's token...");

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

        try {
            token = Utils.getTokenFromFile(TOKEN_LINE);
            password = Utils.getTokenFromFile(PASSWORD_LINE);
            System.out.println("Success.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Bot weatherBot = new Bot("weatherAtHomeBot", token, handler, password);
        weatherBot.botConnect();
    }
}
