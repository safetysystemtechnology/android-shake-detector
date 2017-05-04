package safety.com.br.android_shake_detector.core;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

import java.util.List;

/**
 * @author netodevel
 */
public class ShakeService extends Service {

    private AppPreferences appPreferences;

    private ShakeOptions shakeOptions;

    private ShakeListener shakeListener;

    private SensorManager sensorManager;

    private Sensor sensor;

    @Override
    public void onCreate() {
        appPreferences = new AppPreferences(getBaseContext());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        this.shakeOptions = new ShakeOptions()
                .background(appPreferences.getBoolean("BACKGROUND", true))
                .sensibility(appPreferences.getFloat("SENSIBILITY", 1.2f))
                .shakeCount(appPreferences.getInt("SHAKE_COUNT", 1))
                .interval(appPreferences.getInt("INTERVAL", 2000));

        startShakeService(getBaseContext());

        if (shakeOptions.isBackground()) {
            return START_STICKY;
        } else {
            return START_NOT_STICKY;
        }
    }

    public void startShakeService(Context context) {
        this.shakeListener = new ShakeListener(this.shakeOptions, context);
        this.sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        List<Sensor> sensors = this.sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
        if (sensors.size() > 0) {
            sensor = sensors.get(0);
            this.sensorManager.registerListener(this.shakeListener, sensor, SensorManager.SENSOR_DELAY_GAME);
        }
    }

    @Override
    public void onDestroy() {
        this.sensorManager.unregisterListener(this.shakeListener);
        super.onDestroy();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
