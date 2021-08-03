package responses;

public enum Responses {
    START("/start - read this message.\n" + "/measure - get current temperature and humidity."),
    STOP_TALKING("Unknown message. Commands in /start."), NOT_IMPLEMENTED("Not implemented"), WTF("Unknown command!"),
    STARTED("Started!"), MEASURE_FAIL("Getting measure fail."), WHO_ARE_YOU("Dad won't let me talk to strangers."),
    WELCOME("Welcome to the club, buddy!"), FAIL("Something went wrong :(");

    public final String response;

    private Responses(String text) {
        this.response = text;
    }

    public String getResponse() {
        return response;
    }
}
