import java.io.IOException;

import utils.*;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import telegram.Bot;

public class App {

    static final Integer TOKEN_LINE = 0;
    static final Integer PASSWORD_LINE = 1;

    public static void main(String[] args) {
        String token = "";
        String password = "";
        System.out.println("Getting bot's token...");
        try {
            token = Utils.getTokenFromFile(TOKEN_LINE);
            password = Utils.getTokenFromFile(PASSWORD_LINE);
            System.out.println("Success.");
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Bot("weatherAtHomeBot", token, password));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }        
    }
}
