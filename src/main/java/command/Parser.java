import java.util.function.Predicate;

public class Parser {
    private final String PREFIX = "/";
    private String botName;

    public Parser(String botName) {
        this.botName = botName;
    }

    private Command getCommandFromText(String text) {
        return text.contains(PREFIX) ?
        text.substring(1, text.indexOf(PREFIX)) :
        text.substring(1);
    }
}