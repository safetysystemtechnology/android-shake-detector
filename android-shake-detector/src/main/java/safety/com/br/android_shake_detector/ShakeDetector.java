package safety.com.br.android_shake_detector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.widget.Toast;

import java.util.List;

import safety.com.br.android_shake_detector.core.ShakeService;

/**
 * @author netodevel
 */
public class ShakeDetector {

    private SensorManager sensorManager;

    private Context context;

    private ShakeCallback shakeCallback;

    private Sensor sensor;

    private boolean isRunning;

    private ShakeOptions shakeOptions;

    public ShakeDetector() {
    }

    public ShakeDetector(ShakeOptions shakeOptions) {
        this.shakeOptions = shakeOptions;
    }

    public ShakeDetector start(Context context, ShakeCallback shakeCallback) {
        this.shakeCallback = shakeCallback;
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            isRunning = this.sensorManager.registerListener(new ShakeListener(this.shakeOptions, shakeCallback), sensor, SensorManager.SENSOR_DELAY_GAME);
            startShakeService(context);
        }
        return this;
    }

    private void startShakeService(Context context) {
        Intent serviceIntent = new Intent(context, ShakeService.class);
        context.startService(serviceIntent);
    }

    public ShakeDetector startService(Context context) {
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            isRunning = this.sensorManager.registerListener(new ShakeListener(this.shakeOptions, context), sensor, SensorManager.SENSOR_DELAY_GAME);
        }
        return this;
    }

    private Boolean isSupported(Context context) {
        this.context = context;
        if (context != null) {
            sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
            List<Sensor> sensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            if (sensors.size() > 0) {
                return true;
            }
        }
        return false;
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

}

