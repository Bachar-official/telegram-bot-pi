package telegram.commands;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import database.DbHandler;
import measure.Measure;
import utils.Utils;

public class CurrentCommand extends Command {

    public CurrentCommand(String identifier, String description, DbHandler handler) {
        super(identifier, description, handler);
    }

    @Override
    public void execute(AbsSender sender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        Measure lastMeasure = handler.getLastMeasure();
        sendMessage(sender, chat.getId(), this.getCommandIdentifier(), userName, lastMeasure.toString());
    }
    
}
