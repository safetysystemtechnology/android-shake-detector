package safety.com.br.android_shake_detector.core;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.util.Log;

import java.util.List;

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

    private AppPreferences appPreferences;

    private ShakeBroadCastReceiver shakeBroadCastReceiver;

    public ShakeDetector() {
    }

    public ShakeDetector(ShakeOptions shakeOptions) {
        this.shakeOptions = shakeOptions;
    }

    public ShakeDetector start(Context context, ShakeCallback shakeCallback) {
        this.shakeCallback = shakeCallback;
        this.shakeBroadCastReceiver = new ShakeBroadCastReceiver(shakeCallback);

        if (this.shakeOptions.isBackground()) {
            if (!this.shakeBroadCastReceiver.isOrderedBroadcast() == false) {
                registerPrivateBroadCast(context);
            }
        }
        saveOptionsInStorage(context);
        startShakeService(context);
        return this;
    }

    public void stop(Context context) {
        if (this.shakeBroadCastReceiver.isOrderedBroadcast()) {
            context.unregisterReceiver(this.shakeBroadCastReceiver);
        }
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

    public void saveOptionsInStorage(Context context) {
        this.appPreferences = new AppPreferences(context);
        this.appPreferences.putBoolean("BACKGROUND", this.shakeOptions.isBackground());
        this.appPreferences.putInt("SHAKE_COUNT", this.shakeOptions.getShakeCounts());
        this.appPreferences.putInt("INTERVAL", this.shakeOptions.getInterval());
        this.appPreferences.putFloat("SENSIBILITY", this.shakeOptions.getSensibility());
    }

    private void registerPrivateBroadCast(Context context) {
        IntentFilter filter = new IntentFilter();
        filter.addAction("shake.detector");
        context.registerReceiver(this.shakeBroadCastReceiver, filter);
    }

    public Boolean isRunning() {
        return this.isRunning;
    }

}

