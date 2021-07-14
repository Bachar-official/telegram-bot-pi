package command;

public enum Responses {
    START("Привет!\n" + "Я - бот для мониторинга погоды в доме.\n" + "/start - ещё раз познакомиться.\n"
            + "/measure - измерить текущие температуру и влажность."),
    STOP_TALKING("Я - не бот для поболтушек.\n Отправляйте мне команды.\n Список команд доступен по команде /start."),
    NOT_IMPLEMENTED("Я такое ещё не умею :("), WTF("Меня такому не учили!"), STARTED("Бот стартовал!");

    public final String response;

    private Responses(String text) {
        this.response = text;
    }

    public String getResponse() {
        return response;
    }
}
