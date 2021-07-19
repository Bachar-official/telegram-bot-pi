public class CommandParser {
    private String command;
    private DbHandler handler;

    public CommandParser(String command, DbHandler handler)
    {
        this.command = command;
        this.handler = handler;
    }

    public String getResponse() {
        switch (command) {
            case "start":
                return Responses.START.getResponse();
            case "measure":
                return "Under construction";
            default:
                return Responses.WTF.getResponse();
        }
    }
}
