import java.io.*;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HistoryManager {
    private List<String> history;
    private File historyFile;

    public HistoryManager() {
        this.history = new ArrayList<>();
        this.historyFile = new File("history.txt");
        loadHistory();
    }

    private void loadHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader(historyFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                history.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveHistory() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(historyFile))) {
            for (String song : history) {
                writer.println(song);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addToHistory(String song) {
        history.add(song);
        saveHistory();
    }

    public List<String> getHistory() {
        return new ArrayList<>(history);
    }

        @Test
        public void testConstructor() {
            HistoryManager historyManager = new HistoryManager();
            assertNotNull(historyManager.getHistory());
        }
    @Test
    public void testAddToHistory() {
        HistoryManager historyManager = new HistoryManager();
        int initialSize = historyManager.getHistory().size();

        // Add a song to the history
        historyManager.addToHistory("testSong.wav");

        // Check if the history size increased by 1
        assertEquals(initialSize + 1, historyManager.getHistory().size());

        // Check if the last song in the history is the one we just added
        assertEquals("testSong.wav", historyManager.getHistory().get(historyManager.getHistory().size() - 1));
    }
    }


