package telegram;
import java.sql.SQLException;
import java.util.List;
import org.telegram.telegrambots.extensions.bots.commandbot.TelegramLongPollingCommandBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import database.DbHandler;
import telegram.commands.CurrentCommand;
import telegram.commands.DayCommand;
import telegram.commands.HourCommand;
import telegram.commands.WeekCommand;
import telegram.commands.WorkDayCommand;
import telegram.nonCommand.NonCommand;

public final class Bot extends TelegramLongPollingCommandBot {
    private DbHandler dbHandler;
    private final String userName;
    private final String token;
    private final NonCommand nonCommand;
    String password;
    List<Integer> whitelist;

    public Bot(String name, String token, String password) {
        super();
        this.userName = name;
        this.token = token;
        this.password = password;
        // вспомогательный класс для работы с сообщениями, не являющимися командами
        this.nonCommand = new NonCommand();
        System.out.println("Getting database instance...");
        try {
            this.dbHandler = DbHandler.getInstance();
            System.out.println("Success.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // регистрируем команды
        register(new CurrentCommand("measure", "Get current measure", this.dbHandler));
        register(new HourCommand("hour", "Get last measures of hour", this.dbHandler));
        register(new WorkDayCommand("workday", "Get last measures of 8 hours", this.dbHandler));
        register(new DayCommand("day", "Get last measures of the day", this.dbHandler));
        register(new WeekCommand("week", "Get last measures of the week", this.dbHandler));
    }

    public static void checkPassword(String password) throws Exception {
        if (password.equals("")) {
            throw new Exception("Forbidden to start bot without a password!");
        }
    }

    @Override
    public void processNonCommandUpdate(Update update) {
        Message msg = update.getMessage();
        Long chatId = msg.getChatId();
        String userName = getUserName(msg);

        String answer = nonCommand.nonCommandExecute(chatId, userName, msg.getText());
        setAnswer(chatId, userName, answer);
        
    }

    /**
     * Формирование имени пользователя
     * @param msg сообщение
     */
    private String getUserName(Message msg) {
        User user = msg.getFrom();
        String userName = user.getUserName();
        return (userName != null) ? userName : String.format("%s %s", user.getLastName(), user.getFirstName());
    }

    /**
     * Отправка ответа
     * @param chatId id чата
     * @param userName имя пользователя
     * @param text текст ответа
     */
    private void setAnswer(Long chatId, String userName, String text) {
        SendMessage answer = new SendMessage();
        answer.setText(text);
        answer.setChatId(chatId.toString());
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            //логируем сбой Telegram Bot API, используя userName
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
}
