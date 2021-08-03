import java.io.IOException;
import java.util.List;

import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.exceptions.TelegramApiRequestException;
import responses.*;
import utils.*;

public class Bot extends TelegramLongPollingBot {

    private static int RECONNECT_PAUSE = 10000;

    String userName;
    String token;
    DbHandler handler;
    String password;
    List<Integer> whitelist;

    public Bot(String name, String token, DbHandler handler, String password) {
        this.userName = name;
        this.token = token;
        this.handler = handler;
        this.password = password;
    }

    public static void checkPassword(String password) throws Exception {
        if (password.equals("")) {
            throw new Exception("Forbidden to start bot without a password!");
        }
    }

    @Override
    public void onUpdateReceived(Update update) {

        try {
            whitelist = Utils.getWhiteList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            Integer userId = update.getMessage().getFrom().getId();
            Object response = null;

            if (messageText.equals("/" + password)) {

                try {
                    Utils.registerUser(userId);
                    whitelist.add(userId);
                    response = new SendMessage().setText(Responses.WELCOME.getResponse());

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            else if (!Utils.isUserRegistered(whitelist, userId)) {
                response = new SendMessage().setText(Responses.WHO_ARE_YOU.getResponse());
            }

            else if (messageText.charAt(0) != '/') {
                response = new SendMessage().setText(Responses.STOP_TALKING.getResponse());
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

        // Checking if password enable
        try {
            checkPassword(password);
        } catch (Exception e) {
            e.printStackTrace();
        }

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
