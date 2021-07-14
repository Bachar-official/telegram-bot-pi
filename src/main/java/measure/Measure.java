package measure;

import java.time.LocalDateTime;

import command.Responses;

public class Measure {
    private LocalDateTime date;
    private double temperature;
    private double humidity;

    public Measure(double temp, double humi) {
        this.date = LocalDateTime.now();
        this.temperature = temp;
        this.humidity = humi;
    }

    @Override
    public String toString() {
        if (temperature == 0 || humidity == 0) {
            return Responses.MEASURE_FAIL.getResponse();
        }
        return String.format("Дата: %s, температура: %f градусов, влажность: %f процентов.", date, temperature,
                humidity);
    }
}
