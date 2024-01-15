import org.junit.Test;

import javax.sound.sampled.*;

import static org.junit.Assert.assertNotNull;

public class VolumeControl {

    private Clip clip;
    private FloatControl floatControl;

    public VolumeControl(Clip clip) {
        this.clip = clip;
        openClip();
    }

    private void openClip() {
        if (clip != null && !clip.isOpen()) {
            try {
                clip.open();
                Control[] controls = clip.getControls();
                for (Control control : controls) {
                    if (control instanceof FloatControl && ((FloatControl) control).getType() == FloatControl.Type.MASTER_GAIN) {
                        floatControl = (FloatControl) control;
                        break;
                    }
                }
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
        }
    }

    public void setVolume(float sliderValue) {
        if (floatControl != null) {
            if (sliderValue > 0) {
                float dB = (float) (Math.log(sliderValue / 100) / Math.log(10.0) * 20.0);
                floatControl.setValue(dB);
                System.out.println("Setting volume to: " + dB + " dB");
            } else {
                floatControl.setValue(-80); // Mute
                System.out.println("Muting volume");
            }
        } else {
            System.out.println("Volume control not supported or Clip is null");
        }
    }
    @Test
    public void testConstructor() throws LineUnavailableException {
        Clip clip = AudioSystem.getClip();
        VolumeControl volumeControl = new VolumeControl(clip);
        assertNotNull(volumeControl);
    }
}
