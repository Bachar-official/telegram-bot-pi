package DHT;

import com.pi4j.io.gpio.Pin;

public interface DHTxx {
    public void init() throws Exception;

    public Pin getPin();

    public void setPin(Pin pin);

    public DHTData getData() throws Exception;
}