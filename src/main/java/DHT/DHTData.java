package DHT;

import measure.Measure;

public class DHTData {
    private double temperature;
    private double humidity;

    public DHTData() {
        super();
    }

    public DHTData(double temperature, double humidity) {
        super();
        this.temperature = temperature;
        this.humidity = humidity;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public Measure toMeasure() {
        return new Measure(temperature, humidity);
    }

    @Override
    public String toString() {
        return "Temperature: " + temperature + "°C\nHumidity: " + humidity + "%";
    }
}
