package safety.com.br.shake_sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import safety.com.br.android_shake_detector.ShakeCallback;
import safety.com.br.android_shake_detector.ShakeDetector;
import safety.com.br.android_shake_detector.ShakeOptions;

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
                .interval(2000)
                .shakeCount(2)
                .sensibility(1.2f);

        this.shakeDetector = new ShakeDetector(options).start(this, new ShakeCallback() {
            @Override
            public void onShake() {
                Toast.makeText(getBaseContext(), "onShakeCallBack", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
