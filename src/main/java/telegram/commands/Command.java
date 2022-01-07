package telegram.commands;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.BotCommand;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import database.DbHandler;
import responses.Responses;

/**
 * Суперкласс для команд бота
 */
abstract class Command extends BotCommand{
    String filename = "chart.jpg";
    public DbHandler handler;

    Command(String identifier, String description, DbHandler handler) {
        super(identifier, description);
        this.handler = handler;
    }

    /*
    * Отправка ответа картинкой
    */
    void sendPhoto(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(createPhoto(chatId, filename));
        } catch (IOException | RuntimeException | TelegramApiException e) {
            e.printStackTrace();
            sendError(absSender, chatId, commandName, userName);
        }
    }

    /*
    * Отправка ответа текстом
    */
    void sendMessage(AbsSender absSender, Long chatId, String commandName, String userName, String message) {
        try {
            absSender.execute(createMessage(chatId, message));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            sendError(absSender, chatId, commandName, userName);
        }
    }


    /*
    * Формирование картинки
    */
    private SendPhoto createPhoto(Long chatId, String filename) throws IOException {
            FileInputStream stream = new FileInputStream(new File(filename));
            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId.toString());
            photo.setPhoto(new InputFile(stream, filename));
            return photo;
    }

    /*
    * Формирование текста
    */
    private SendMessage createMessage(Long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId.toString());
        message.setText(text);
        return message;
    }


    /**
     * Отправка пользователю сообщения об ошибке
     */
    private void sendError(AbsSender absSender, Long chatId, String commandName, String userName) {
        try {
            absSender.execute(new SendMessage(chatId.toString(), Responses.FAIL.getResponse()));
        } catch (TelegramApiException e) {
            System.out.println(String.format("Error %s. Command %s. User: %s", e.getMessage(), commandName, userName));
            e.printStackTrace();
        }
    }
}
