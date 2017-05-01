package safety.com.br.android_shake_detector.core;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import safety.com.br.android_shake_detector.ShakeDetector;
import safety.com.br.android_shake_detector.ShakeOptions;

/**
 * @author netodevel
 */
public class ShakeService extends Service {

    private ShakeDetector shakeDetector;

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ShakeOptions shakeOptions = new ShakeOptions()
                .background(true)
                .sensibility(1.2f)
                .shakeCount(2)
                .interval(2000);
        new ShakeDetector(shakeOptions).startService(getBaseContext());
        return START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
