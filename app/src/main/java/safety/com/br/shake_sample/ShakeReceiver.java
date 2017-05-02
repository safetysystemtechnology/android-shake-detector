package safety.com.br.shake_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * @author netodevel
 */
public class ShakeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent && intent.getAction().equals("shake.detector")) {
            Log.d("my receiver", ">>>>>> my receiver <<<<<");
        }
    }

}
