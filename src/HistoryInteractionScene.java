import java.util.List;

public class HistoryInteractionScene {
    public static void main(String[] args) {
        // Create a new music player and history manager
        MusicPlayer musicPlayer = new MusicPlayer();
        HistoryManager historyManager = new HistoryManager();

        // User plays a song
        String song = "song.wav";
        try {
            musicPlayer.playMusic(song);
        } catch (MusicPlayer.FileNotFoundException | MusicPlayer.UnsupportedFileTypeException e) {
            e.printStackTrace();
            return;  // Exit if the song can't be played
        }

        // User views the song history
        List<String> history = historyManager.getHistory();
        for (String playedSong : history) {
            System.out.println(playedSong);
        }

        // User clears the song history
        // historyManager.clearHistory();  // Uncomment this line if you have a clearHistory() method
    }
}
