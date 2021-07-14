package command;

public enum Responses {
    STOP_TALKING("Я - не бот для поболтушек.\n Отправляйте мне команды.\n Список команд доступен по команде /start.");

    public final String label;

    private Responses(String label) {
        this.label = label;
    }
}
