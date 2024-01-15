import org.junit.Test;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.*;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JavaGUIMusicPlayerJFrame extends JFrame implements ActionListener,FileSelection {
    JTextField filePathField;
    JButton playButton;
    JButton pauseButton;
    JButton chooseButton;
    JButton loopButton;
    JFileChooser fileChooser;
    MusicPlayer musicPlayer;
    PlaylistManager playlistManager;
    JList<String> playlist;
    JSlider volumeSlider;



    public JavaGUIMusicPlayerJFrame() {

        super("Music Player");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(10);
        volumeSlider.setPaintTicks(true);
        volumeSlider.setPaintLabels(true);

        playlistManager = new PlaylistManager();
        playlist = new JList<>(playlistManager.getListModel());

        add(new JScrollPane(playlist));

        filePathField = new JTextField(20);
        playButton = new JButton("Play");
        pauseButton = new JButton("Pause");
        chooseButton = new JButton("Choose File");
        loopButton = new JButton("Loop");

        playButton.addActionListener(this);
        pauseButton.addActionListener(this);
        chooseButton.addActionListener(this);
        loopButton.addActionListener(this);

        add(filePathField);
        add(chooseButton);
        add(playButton);
        add(pauseButton);
        add(loopButton);

        fileChooser = new JFileChooser(".");
        fileChooser.setFileFilter(new FileNameExtensionFilter("WAV Files", "wav"));

        musicPlayer = new MusicPlayer();

        setSize(500, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int volumeValue = volumeSlider.getValue();
                float volume = volumeValue / 100.0f;
                musicPlayer.setVolume(volume);
            }
        });
        add(volumeSlider);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == playButton) {
            String selectedSong = playlist.getSelectedValue();
            try {
                musicPlayer.playMusic(selectedSong);
            } catch (MusicPlayer.FileNotFoundException e) {
                throw new RuntimeException(e);
            } catch (MusicPlayer.UnsupportedFileTypeException e) {
                throw new RuntimeException(e);
            }
        } else if (event.getSource() == pauseButton) {
            musicPlayer.pauseMusic();
        } else if (event.getSource() == chooseButton) {
            chooseFile();
        } else if (event.getSource() == loopButton) {
            musicPlayer.toggleLoop();
        }
    }

    public void chooseFile() {
        fileChooser.setCurrentDirectory(new File("."));
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String filePath = selectedFile.getAbsolutePath();

            if (validateFilePath(filePath)) {
                filePathField.setText(filePath);
                playlistManager.addToPlaylist(filePath);
            } else {

                JOptionPane.showMessageDialog(this, "Invalid file path: " + filePath);
            }
        }
    }

    private boolean validateFilePath(String filePath) {
        File file = new File(filePath);
        return file.exists() && filePath.endsWith(".wav");
    }

        @Test
        public void testConstructor() {
            JavaGUIMusicPlayerJFrame guiMusicPlayerJFrame = new JavaGUIMusicPlayerJFrame();
            assertNotNull(guiMusicPlayerJFrame);
        }
    }



