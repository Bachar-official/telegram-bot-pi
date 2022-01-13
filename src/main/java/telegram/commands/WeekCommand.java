package telegram.commands;

import java.util.List;

import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.bots.AbsSender;

import chart.Chart;
import database.DbHandler;
import measure.Measure;
import utils.Utils;

public class WeekCommand extends Command {
    private Integer WEEK = 12 * 24 * 7;

    public WeekCommand(String identifier, String description, DbHandler handler) {
        super(identifier, description, handler);
    }

    @Override
    public void execute(AbsSender sender, User user, Chat chat, String[] strings) {
        String userName = Utils.getUserName(user);
        List<Measure> dataset = handler.getMeasures(WEEK);
        Chart chart = new Chart(dataset);
        chart.createChart();
        sendPhoto(sender, chat.getId(), this.getCommandIdentifier(), userName);
    }    
}
