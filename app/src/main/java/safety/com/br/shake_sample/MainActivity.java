package safety.com.br.shake_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import safety.com.br.android_shake_detector.core.ShakeCallback;
import safety.com.br.android_shake_detector.core.ShakeDetector;
import safety.com.br.android_shake_detector.core.ShakeOptions;

/**
 * @author netodevel
 */
public class MainActivity extends AppCompatActivity {

    private ShakeDetector shakeDetector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ShakeOptions options = new ShakeOptions()
                .background(true)
                .interval(1000)
                .shakeCount(2)
                .sensibility(2.0f);

        this.shakeDetector = new ShakeDetector(options).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                Log.d("event", "onShake");
            }
        });
    }

    @Override
    protected void onStop() {
        this.shakeDetector.stop(getBaseContext());
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        this.shakeDetector.stop(getBaseContext());
        super.onDestroy();
    }

}
