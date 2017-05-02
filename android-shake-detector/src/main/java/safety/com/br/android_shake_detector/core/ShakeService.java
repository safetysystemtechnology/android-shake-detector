package safety.com.br.android_shake_detector.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * @author netodevel
 */
public class ShakeService extends Service {

    private ShakeDetector shakeDetector;

    private AppPreferences appPreferences;

    private int serviceMode;

    @Override
    public void onCreate() {
        appPreferences = new AppPreferences(getBaseContext());
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ShakeOptions shakeOptions = new ShakeOptions()
                .background(appPreferences.getBoolean("BACKGROUND", true))
                .sensibility(appPreferences.getFloat("SENSIBILITY", 1.2f))
                .shakeCount(appPreferences.getInt("SHAKE_COUNT", 1))
                .interval(appPreferences.getInt("INTERVAL", 2000));

        new ShakeDetector(shakeOptions).startService(getBaseContext());
        if (shakeOptions.isBackground()) {
            return START_STICKY;
        } else {
            return START_NOT_STICKY;
        }
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
