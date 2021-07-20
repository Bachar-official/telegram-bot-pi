import java.io.File;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import responses.*;

public class Bot extends TelegramLongPollingBot {

    private static int RECONNECT_PAUSE = 10000;

    String userName;
    String token;
    DbHandler handler;

    public Bot(String name, String token, DbHandler handler) {
        this.userName = name;
        this.token = token;
        this.handler = handler;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Object response = null;

            if (messageText.charAt(0) != '/') {
                response = Responses.STOP_TALKING.getResponse();
            }

            else {
                CommandParser parser = new CommandParser(messageText.substring(1).toLowerCase(), handler);
                response = parser.getResponse();
            }

            if (response instanceof SendMessage) {
                SendMessage msg = (SendMessage) response;
                msg.setChatId(chatId);
                try {
                    execute(msg);// Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            } else {
                SendPhoto msg = (SendPhoto) response;
                msg.setChatId(chatId);
                try {
                    sendPhoto(msg); // Sending our message object to user
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return userName;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    public void botConnect() {
        TelegramBotsApi tBotsApi = new TelegramBotsApi();

        try {
            tBotsApi.registerBot(this);
            System.out.println(Responses.STARTED.getResponse());
        } catch (TelegramApiRequestException e) {
            System.out.println("Unable to connect. Waiting for " + RECONNECT_PAUSE / 1000
                    + " seconds and try again. Message: " + e.getMessage());
            try {
                Thread.sleep(RECONNECT_PAUSE);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
                return;
            }
            botConnect();
        }
    }

}
