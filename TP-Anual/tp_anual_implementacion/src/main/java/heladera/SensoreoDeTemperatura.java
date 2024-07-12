package heladera;

import java.util.Timer;
import java.util.TimerTask;

public class SensoreoDeTemperatura {
    Float temperaturaRegistrada;
    Heladera heladera;

    void avisoDeTemperaturaActualizada() {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                heladera.setUltimaTemperaturaRegistrada(temperaturaRegistrada);
            }
        };
        timer.scheduleAtFixedRate(task, 0, 300000);
    }
}
