package command;

import com.pi4j.io.gpio.RaspiPin;

import DHT.DHT22;
import DHT.DHTData;
import DHT.DHTxx;
import measure.Measure;

public class CommandParser {
    private String[] commands = { "start, measure" };
    private String command;
    DHTxx sensor;

    public CommandParser(String command) {
        this.command = command;
    }

    private Measure getMeasure() {
        System.out.println("Инициалиация сенсора...");
        try {
            sensor = new DHT22(RaspiPin.GPIO_07);
            try {
                sensor.init();
                System.out.println("Сенсор инициализирован.");
                try {
                    DHTData data = sensor.getData();
                    return new Measure(data.getTemperature(), data.getHumidity());
                } catch (Exception ex) {
                    System.out.println("ERROR: " + ex.getMessage());
                    return new Measure(0.0, 0.0);
                }

            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                return new Measure(0.0, 0.0);
            }
        } catch (Exception sensorException) {
            System.out.println("ERROR: " + sensorException.getMessage());
            return new Measure(0.0, 0.0);
        }

    }

    public String getResponse() {
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
