// PlayerControls interface
public interface PlayerControls {
    void playMusic(String filePath) throws MusicPlayer.FileNotFoundException, MusicPlayer.UnsupportedFileTypeException;

    void pauseMusic();


    void toggleLoop();
}


