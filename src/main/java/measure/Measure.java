package measure;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import responses.*;

public class Measure {
    private Integer id;
    private LocalDateTime date;
    private double temperature;
    private double humidity;
    private double pressure;

    public Integer getId() {
        return id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getPressure() {
        return pressure;
    }

    public Measure(double temp, double humi, double press) {
        this.date = LocalDateTime.now();
        this.temperature = temp;
        this.humidity = humi;
        this.pressure = press;
    }

    public Measure(Integer id, String date, double temp, double humi, double press) {
        this.id = id;
        this.date = LocalDateTime.parse(date);
        this.temperature = temp;
        this.humidity = humi;
        this.pressure = press;
    }

    public String getTime() {
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("dd.MM, HH:mm");
        return this.date.format(timeFormat);
    }

    @Override
    public String toString() {
        if (temperature == 0 || humidity == 0) {
            return Responses.MEASURE_FAIL.getResponse();
        }
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm:ss");
        return String.format("Date: %s;\nTime: %s;\nTemperature: %.1f°C;\nHumidity: %.1f%%;\nPressure: %.1f mm", date.format(dateFormat),
                date.format(timeFormat), temperature, humidity, pressure);
    }
}
