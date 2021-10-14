import java.io.File;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import chart.Chart;
import measure.Measure;
import responses.*;

public class CommandParser {
    private String command;
    private DbHandler handler = null;

    private Integer HOUR = 12;
    private Integer DAY = HOUR * 24;
    private Integer WEEK = DAY * 7;
    private Integer WORKDAY = HOUR * 8;

    public CommandParser(String command, DbHandler handler) {
        this.command = command;
        this.handler = handler;
    }

    public Measure getLastMeasure() {
        return handler.getLastMeasure();
    }

    public SendMessage replyByMessage(String message) {
        return new SendMessage().setText(message);
    }

    public SendPhoto replyByPhoto() {
        return new SendPhoto().setNewPhoto(new File("chart.jpg"));
    }

    public void drawChart(int count) {
        Chart chart = new Chart(handler.getMeasures(count));
        chart.createChart();
    }

    public Object getResponse() {
        switch (command) {
            case "start":
                return replyByMessage(Responses.START.getResponse());
            case "measure":
                return replyByMessage(getLastMeasure().toString());
            case "hour": {
                drawChart(HOUR);
                return replyByPhoto();
            }
            case "workday": {
                drawChart(WORKDAY);
                return replyByPhoto();
            }
            case "day": {
                drawChart(DAY);
                return replyByPhoto();
            }
            case "week": {
                drawChart(WEEK);
                return replyByPhoto();
            }
            default:
                return replyByMessage(Responses.WTF.getResponse());
        }
    }
}
