import java.util.Scanner;

public class VolumeControlScene {
    private MusicPlayer musicPlayer;  // The same instance as in the GUI

    public VolumeControlScene(MusicPlayer musicPlayer) {
        this.musicPlayer = musicPlayer;
    }

    public void changeVolume() {
        // Create a Scanner object for user input
        Scanner scanner = new Scanner(System.in);

        // Ask the user to enter the volume
        System.out.println("Enter the volume (0 to 1) (use comma for decimals) :");

        if (scanner.hasNextFloat()) {
            float volume = scanner.nextFloat();

            // Validate the volume
            if (volume < 0.0 || volume > 1.0) {
                System.out.println("Invalid volume. Please enter a number between 0.0 and 1.0.");
            } else {
                // Set the volume
                musicPlayer.setVolume(volume);
            }
        } else {
            System.out.println("Invalid input. Please enter a number.");
        }

        // Close the scanner
        scanner.close();
    }
    public static void main(String[] args) {
        MusicPlayer musicPlayer = new MusicPlayer();
        VolumeControlScene volumeControlScene = new VolumeControlScene(musicPlayer);
        volumeControlScene.changeVolume();
    }
}
