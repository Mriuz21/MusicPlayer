import org.junit.Test;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MusicPlayer implements PlayerControls {
    Clip clip;
    boolean isPaused;
    boolean isLooping = false;

    HistoryManager historyManager = new HistoryManager();
    JFileChooser fileChooser;


    public class FileNotFoundException extends Exception {
        public FileNotFoundException(String message) {
            super(message);
        }
    }

    public class UnsupportedFileTypeException extends Exception {
        public UnsupportedFileTypeException(String message) {
            super(message);
        }
    }

    public MusicPlayer() {
        isPaused = false;
        isLooping = false;
    }

    public void playMusic(String filePath) throws FileNotFoundException, UnsupportedFileTypeException {
        File file = new File(filePath);


        if (!file.exists()) {
            throw new FileNotFoundException("The file was not found: " + filePath);
        }


        if (!filePath.endsWith(".wav")) {
            throw new UnsupportedFileTypeException("The file type is not supported: " + filePath);
        }


        if (historyManager.getHistory().contains(filePath)) {
            System.out.println("This song has already been played.");
        }



        try {
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(file);

            clip = AudioSystem.getClip();
            if (clip == null) {
                System.out.println("Clip is null");
                return;
            }
            clip.open(audioIn);

            if (isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            clip.start();

        } catch(Exception e) {
            System.out.println(e);
        }
        historyManager.addToHistory(filePath);

    }

    public void pauseMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            isPaused = true;
        } else if (clip != null && isPaused) {
            clip.start();

            if(isLooping) {
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }

            isPaused = false;
        }
    }

    public void toggleLoop() {
        isLooping = !isLooping;
        if (isLooping && clip.isRunning()) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void setVolume(float volume) {
        if (clip == null) {
            System.out.println("Clip is null");
            return;
        }
        if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float min = volumeControl.getMinimum();
            float max = volumeControl.getMaximum();
            volumeControl.setValue((max - min) * volume + min);
        } else {
            System.out.println("Volume control not supported");
        }
    }
    public void testConstructor() {
        MusicPlayer musicPlayer = new MusicPlayer();
        assertFalse(musicPlayer.isPaused);
    }
    @Test
    public void testPlayMusic() {
        MusicPlayer musicPlayer = new MusicPlayer();
        assertThrows(MusicPlayer.FileNotFoundException.class, () -> {
            musicPlayer.playMusic("existingfile.txt"); // Ensure this file exists at the path
        });
    }

}
