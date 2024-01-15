import org.junit.Test;

import javax.swing.*;

import static org.junit.Assert.*;

public class PlaylistManager {
    DefaultListModel<String> listModel;
    public PlaylistManager() {
        listModel = new DefaultListModel<>();
    }

    public DefaultListModel<String> getListModel() {
        return listModel;
    }
    public void addToPlaylist(String filePath) {
        if (!listModel.contains(filePath)) {
            listModel.addElement(filePath);
        }
    }

    public void removeFromPlaylist(String filePath) {
        listModel.removeElement(filePath);
    }
    @Test
    public void testConstructor() {
        PlaylistManager playlistManager = new PlaylistManager();
        assertNotNull(playlistManager.getListModel());
    }
    @Test
    public void testPlaylistManagement() {
        PlaylistManager playlistManager = new PlaylistManager();

        // Test addToPlaylist
        String testSong = "testSong.wav";
        playlistManager.addToPlaylist(testSong);
        assertTrue(playlistManager.getListModel().contains(testSong));

        // Test removeFromPlaylist
        playlistManager.removeFromPlaylist(testSong);
        assertFalse(playlistManager.getListModel().contains(testSong));
    }

}
