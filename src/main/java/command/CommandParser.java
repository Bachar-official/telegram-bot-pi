package command;

import com.pi4j.io.gpio.RaspiPin;

import DHT.DHT22;
import DHT.DHTData;
import DHT.DHTxx;
import measure.Measure;

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

    private Measure getMeasure() {
        System.out.println("Инициалиация сенсора...");
        DHTxx dht22 = new DHT22(RaspiPin.GPIO_07);
        try {
            dht22.init();
            System.out.println("Сенсор инициализирован.");
            DHTData data = dht22.getData();
            return new Measure(data.getTemperature(), data.getHumidity());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Measure(0.0, 0.0);
        }
    }

    public String getResponse() {
        if (!this.isCommandExists()) {
            return Responses.WTF.getResponse();
        } else {
            switch (command) {
                case "start":
                    return Responses.START.getResponse();
                case "measure":
                    return getMeasure().toString();
                default:
                    return Responses.WTF.getResponse();
            }
        }
    }
}
