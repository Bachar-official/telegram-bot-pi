package command;

public class CommandParser {
    private String[] commands = { "start, measure" };
    private String command;

    public CommandParser(String command) {
        this.command = command;
    }

    private Boolean isCommandExists() {
        for (String element : commands) {
            if (command.equals(element)) {
                return false;
            }
        }
        return true;
    }

    public String getResponse() {
        if (!this.isCommandExists()) {
            return Responses.WTF.getResponse();
        } else {
            switch (command) {
                case "start":
                    return Responses.START.getResponse();
                case "measure":
                    return Responses.NOT_IMPLEMENTED.getResponse();
                default:
                    return Responses.WTF.getResponse();
            }
        }
    }
}
