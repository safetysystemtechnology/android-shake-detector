package safety.com.br.shake_sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * @author netodevel
 */
public class ShakeReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "RECEIVER?", Toast.LENGTH_SHORT).show();
        if (null != intent && intent.getAction().equals("shake.detector")) {
            Toast.makeText(context, "onService", Toast.LENGTH_SHORT).show();
        }
    }

}
