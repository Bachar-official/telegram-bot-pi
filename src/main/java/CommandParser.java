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

    public String getResponse() {
        switch (command) {
            case "start":
                return Responses.START.getResponse();
            case "measure":
                return getLastMeasure().toString();
            default:
                return Responses.WTF.getResponse();
        }
    }
}
