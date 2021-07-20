import java.io.File;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;

import measure.Measure;
import responses.*;

public class CommandParser {
    private String command;
    private DbHandler handler = null;

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

    public Object getResponse() {
        switch (command) {
            case "start":
                return replyByMessage(Responses.START.getResponse());
            case "measure":
                return replyByMessage(getLastMeasure().toString());
            case "test":
                return replyByPhoto();
            default:
                return replyByMessage(Responses.WTF.getResponse());
        }
    }
}
