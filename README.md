![](https://jitpack.io/v/safetysystemtechnology/android-shake-detector.svg)


# android-shake-detector
Detect shaking of the device 

## Install 
Add the dependecy

```gradle
allprojects {
  repositories {
    ...
    maven { url 'https://jitpack.io' }
  }
}

dependencies {
   compile 'com.github.safetysystemtechnology:android-shake-detector:v1.2'
}

```

## Add permissions in your AndroidManifest.xml
```xml
<uses-feature android:name="android.hardware.sensor.accelerometer" android:required="true" />
```
if you will run in background, register your broadcast receiver

```xml
<receiver android:name=".ShakeReceiver">
    <intent-filter>
        <action android:name="shake.detector" />
    </intent-filter>
</receiver>

```


## Usage
  
```java
private ShakeDetector shakeDetector;

@Override
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    buildView();

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

    //IF YOU WANT JUST IN BACKGROUND
    //this.shakeDetector = new ShakeDetector(options).start(this);
}

private void buildView() {
    Button btnStopService = (Button) findViewById(R.id.btnStopService);
    btnStopService.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("destroy", "destroy service shake");
            shakeDetector.stopShakeDetector(getBaseContext());
        }
    });
}

@Override
protected void onStop() {
    super.onStop();
}

@Override
protected void onDestroy() {
    shakeDetector.destroy(getBaseContext());
    super.onDestroy();
}
```
if you will run in background, create your broadcast receiver

```java
public class ShakeReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (null != intent && intent.getAction().equals("shake.detector")) {
            ...
        }
    }
}

```

## License

    The MIT License (MIT)

    Copyright (c) Safety System Technology

    Permission is hereby granted, free of charge, to any person obtaining a 
    copy of this software and associated documentation files (the "Software"), 
    to deal in the Software without restriction, including without limitation 
    the rights to use, copy, modify, merge, publish, distribute, sublicense, 
    and/or sell copies of the Software, and to permit persons to whom the Software is 
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included 
    in all copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, 
    INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR 
    PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE 
    FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE,
    ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.


