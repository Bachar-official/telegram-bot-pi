package telegram.nonCommand;

import responses.Responses;

public class NonCommand {
    public String nonCommandExecute(Long chatId, String userName, String text) {
        System.out.println(String.format("User %s writes %s", userName, text));
        return Responses.WTF.getResponse();
    }
}
