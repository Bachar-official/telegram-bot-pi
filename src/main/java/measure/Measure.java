package measure;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import command.Responses;

public class Measure {
    private Integer id;
    private LocalDateTime date;
    private double temperature;
    private double humidity;

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

    public Measure(double temp, double humi) {
        this.date = LocalDateTime.now();
        this.temperature = temp;
        this.humidity = humi;
    }

    public Measure(Integer id, String date, double temp, double humi) {
        this.id = id;
        this.date = LocalDateTime.parse(date);
        this.temperature = temp;
        this.humidity = humi;
    }

    @Override
    public String toString() {
        if (temperature == 0 || humidity == 0) {
            return Responses.MEASURE_FAIL.getResponse();
        }
        return String.format("Дата: %s;\nТемпература: %.1f градусов;\nВлажность: %.1f процентов.", date, temperature,
                humidity);
    }
}
