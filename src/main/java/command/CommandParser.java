package command;

public class CommandParser {
    private String wtf = "Меня такому не учили!";
    private String welcome = "Привет!\n" +
    "Я - бот для мониторинга погоды в доме.\n" +
    "/start - ещё раз познакомиться.\n" +
    "/measure - измерить текущие температуру и влажность.";
    private String[] commands = {"start, measure"};
    private String command;


    public CommandParser(String command) {
        this.command = command;
    }

    private Boolean isCommandExists() {
        for (String element : commands) {
            if (command == element) {
                return false;
            }
        }
        return true;
    }

    public String getResponse() {
        if (!this.isCommandExists()) {
            return wtf;
        }
        else {
            switch (command) {
                case "start" : return welcome;
                case "measure" : return "Я ещё учусь";
                default: return wtf;
            }
        }
    }
}
