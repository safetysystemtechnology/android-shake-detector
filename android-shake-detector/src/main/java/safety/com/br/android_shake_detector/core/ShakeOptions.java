package safety.com.br.android_shake_detector.core;

/**
 * @author netodevel
 */
public class ShakeOptions {

    private boolean background;

    private int shakeCounts;

    private int interval;

    private float sensibility;

    public ShakeOptions background(Boolean background) {
        this.background = background;
        return this;
    }

    public ShakeOptions shakeCount(int shakeCount) {
        this.shakeCounts = shakeCount;
        return this;
    }

    public ShakeOptions interval(int interval) {
        this.interval = interval;
        return this;
    }

    public ShakeOptions sensibility(float sensibility) {
        this.sensibility = sensibility;
        return this;
    }

    public boolean isBackground() {
        return background;
    }

    public void setBackground(boolean background) {
        this.background = background;
    }

    public int getShakeCounts() {
        return shakeCounts;
    }

    public void setShakeCounts(int shakeCounts) {
        this.shakeCounts = shakeCounts;
    }

    public int getInterval() {
        return interval;
    }

    public float getSensibility() {  return sensibility;  }
}
