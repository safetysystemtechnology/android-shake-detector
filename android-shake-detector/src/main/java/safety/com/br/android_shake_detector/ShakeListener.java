package safety.com.br.android_shake_detector;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * @author netodevel
 */
public class ShakeListener implements SensorEventListener {

    private static final int SHAKE_SLOP_TIME_MS = 500;

    private OnShakeListener mListener;

    private long mShakeTimestamp;

    private int mShakeCount;

    private ShakeCallback shakeCallback;

    private ShakeOptions shakeOptions;

    private Context context;

    public ShakeListener(){}

    public ShakeListener(ShakeOptions shakeOptions, ShakeCallback callback) {
        this.shakeCallback = callback;
        this.shakeOptions = shakeOptions;
    }

    public ShakeListener(ShakeOptions shakeOptions, Context context) {
        this.shakeOptions = shakeOptions;
        this.context = context;
    }

    public void setOnShakeListener(OnShakeListener listener) {
        this.mListener = listener;
    }

    public interface OnShakeListener {
        public void onShake(int count);
    }

    public void resetShakeCount() {
        mShakeCount = 0;
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // ignore
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0];
        float y = event.values[1];
        float z = event.values[2];

        float gX = x / SensorManager.GRAVITY_EARTH;
        float gY = y / SensorManager.GRAVITY_EARTH;
        float gZ = z / SensorManager.GRAVITY_EARTH;

        float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

        if (gForce > this.shakeOptions.getSensibility()) {
            final long now = System.currentTimeMillis();

            if (mShakeTimestamp + SHAKE_SLOP_TIME_MS > now) {
                return;
            }

            if (mShakeTimestamp + this.shakeOptions.getInterval() < now) {
                mShakeCount = 0;
            }

            mShakeTimestamp = now;
            mShakeCount++;

            if (this.shakeOptions.getShakeCounts() == mShakeCount) {
                if (this.shakeCallback != null) {
                    this.shakeCallback.onShake();
                } else {
                    sendToBroadCasts(this.context);
                }
            }
        }
    }

    private void sendToBroadCasts(Context context) {
        Intent locationIntent = new Intent();
        locationIntent.setAction("shake.detector");
        context.sendBroadcast(locationIntent);
    }
}