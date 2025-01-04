import com.github.kwhat.jnativehook.GlobalScreen;
import com.github.kwhat.jnativehook.NativeHookException;
import com.github.kwhat.jnativehook.keyboard.NativeKeyEvent;
import com.github.kwhat.jnativehook.keyboard.NativeKeyListener;

import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.io.*;
import java.net.URI;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class Main implements NativeKeyListener {
    private boolean isActivated = false; // One activation flag for all commands
    private String MUSIC_FOLDER_PATH = "mood";
    private Clip currentClip = null;
    private JFrame musicWindow;
    private JLabel songLabel;
    private boolean isPlaying = false;
    private static String ROSE_IMAGE_PATH = "auntie.png";
    private String LOVE_STORIES_FOLDER_PATH = "love_stories";

    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook ();
            System.out.println("JNativeHook initialized successfully.");
            GlobalScreen.addNativeKeyListener(new Main());
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            ex.printStackTrace();
            System.exit(1);
        }
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        int keyCode = e.getKeyCode();

        // Single activation/deactivation (Ctrl + 2 and Ctrl + 3)
        if (isCtrlPressed(e) && keyCode == NativeKeyEvent.VC_4 && !isActivated) {
            isActivated = true;
            System.out.println("Activated!");
        }

        if (isCtrlPressed(e) && keyCode == NativeKeyEvent.VC_3 && isActivated) {
            isActivated = false;
            System.out.println("Deactivated!");
        }

        // Commands when activated
        if (isActivated) {
            switch (keyCode) {
                case NativeKeyEvent.VC_B: // Command 1: "I love you" window
                    createNewWindow();
                    break;
                case NativeKeyEvent.VC_F6: // Command 6: Display picture for command 6
                    // Specify the full path to the image for command 6
                    openPicture("C:\\Users\\Admin\\Documents\\Rhee2024\\auntie.png");  // Path to the image
                    break;
                case NativeKeyEvent.VC_L: // Open calculator
                    openCalculator();
                    break;
                case NativeKeyEvent.VC_F10:
                    lockComputer();
                    break;
                case NativeKeyEvent.VC_N:  // Command: Open Notepad
                    openNotepad();
                    break;
                case NativeKeyEvent.VC_P:  // Command: Open Control Panel
                    openControlPanel();
                    break;
                case NativeKeyEvent.VC_S: // Replace 'VC_S' with the desired key
                    openSettings();
                    break;
                case NativeKeyEvent.VC_W: // Replace 'VC_W' with your desired key
                    openWord();
                    break;
                case NativeKeyEvent.VC_Y: // Open YouTube
                    openYouTube();
                    break;

            }
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }

    private boolean isCtrlPressed(NativeKeyEvent e) {
        return (e.getModifiers() & NativeKeyEvent.CTRL_MASK) != 0;
    }



    private JFrame bibleVerseFrame = null;  // Store the reference of the JFrame
    private int currentVerseIndex = 0;  // Keeps track of which verse to display next

    private void createNewWindow() {
        // If the frame is already open, just return and do nothing
        if (bibleVerseFrame != null && bibleVerseFrame.isVisible()) {
            return;  // Prevent opening another window
        }

        // Bible verses in order
        String[] bibleVerses = {
                "For God so loved the world, that he gave his only Son. - John 3:16",
                "The LORD is my shepherd; I shall not want. - Psalm 23:1",
                "I can do all things through Christ who strengthens me. - Philippians 4:13",
                "Trust in the LORD with all your heart. - Proverbs 3:5",
                "Be strong and courageous. Do not be afraid. - Joshua 1:9"
        };

        // Ensure we get the next verse in order, loop back to start after last verse
        String verse = bibleVerses[currentVerseIndex];

        // Update the index for the next verse
        currentVerseIndex = (currentVerseIndex + 1) % bibleVerses.length;

        int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
        int randomX = (int) (Math.random() * (screenWidth - 350)); // Adjusted for new window width
        int randomY = (int) (Math.random() * (screenHeight - 200)); // Adjusted for new window height

        // If the frame is already created, just update its contents
        if (bibleVerseFrame == null) {
            bibleVerseFrame = new JFrame("Bible Verse");
            bibleVerseFrame.setSize(350, 200);
            bibleVerseFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            bibleVerseFrame.setUndecorated(true);
            bibleVerseFrame.setResizable(false);
            bibleVerseFrame.setOpacity(0.9f);
            bibleVerseFrame.setShape(new RoundRectangle2D.Float(0, 0, 350, 200, 30, 30));
            bibleVerseFrame.setAlwaysOnTop(true);
        }

        // Update the content of the existing frame
        JLabel label = new JLabel("<html><div style='text-align: center; padding: 10px;'>" + verse + "</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Serif", Font.BOLD, 16));
        label.setForeground(Color.WHITE);

        // Set the background
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;

                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Apply a softer gradient for better readability
                GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE, getWidth(), getHeight(), Color.CYAN);
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Add the label to the panel and panel to the frame
        panel.setLayout(new BorderLayout());
        panel.add(label, BorderLayout.CENTER);
        bibleVerseFrame.setContentPane(panel);

        // Set the window location and make it visible
        bibleVerseFrame.setLocation(randomX, randomY);
        bibleVerseFrame.setVisible(true);
    }



    private void openPicture(String imagePath) {
        try {
            // Load the image file
            File imageFile = new File(imagePath);
            if (!imageFile.exists()) {
                // Show a message dialog if the image is not found
                JOptionPane.showMessageDialog(null, "Image is not found: " + imagePath, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Create a JFrame to display the image
            JFrame imageFrame = new JFrame("Picture");
            imageFrame.setSize(600, 400);
            imageFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            // Load the image and add it to a JLabel
            ImageIcon imageIcon = new ImageIcon(imageFile.getAbsolutePath());
            JLabel label = new JLabel(imageIcon);
            imageFrame.add(label);

            // Make the window visible
            imageFrame.setLocationRelativeTo(null);
            imageFrame.setVisible(true);
        } catch (Exception e) {
            // Show a message dialog for any unexpected errors
            JOptionPane.showMessageDialog(null, "Error displaying the image.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    private void openCalculator() {
        try {
            // Open the calculator (Windows-specific command)
            Runtime.getRuntime().exec("calc");
            System.out.println("Calculator opened successfully.");
        } catch (IOException e) {
            System.err.println("Error opening calculator.");
            e.printStackTrace();
        }
    }

    private void lockComputer() {
        try {
            Runtime.getRuntime().exec("rundll32.exe user32.dll,LockWorkStation");
            System.out.println("Computer locked successfully.");
        } catch (IOException e) {
            System.err.println("Error locking computer.");
            e.printStackTrace();
        }
    }

    private void openNotepad() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Open Notepad on Windows
                Runtime.getRuntime().exec("notepad.exe");
            } else {
                System.out.println("Notepad is only available for Windows.");
            }
        } catch (IOException e) {
            System.err.println("Error opening Notepad.");
            e.printStackTrace();
        }
    }

    private void openControlPanel() {
        try {
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                // Open Control Panel on Windows
                Runtime.getRuntime().exec("control");
            } else {
                System.out.println("Control Panel is only available for Windows.");
            }
        } catch (IOException e) {
            System.err.println("Error opening Control Panel.");
            e.printStackTrace();
        }
    }
    private void openSettings() {
        try {
            // Command to open Windows Settings
            String command = "cmd /c start ms-settings:";
            Runtime.getRuntime().exec(command);

            System.out.println("Settings app opened successfully.");
        } catch (IOException e) {
            System.err.println("Failed to open Settings.");
            e.printStackTrace();
        }
    }

    private void openWord() {
        try {
            // Command to open Microsoft Word
            String command = "cmd /c start winword";
            Runtime.getRuntime().exec(command);

            System.out.println("Microsoft Word opened successfully.");
        } catch (IOException e) {
            System.err.println("Failed to open Microsoft Word.");
            e.printStackTrace();
        }
    }
    private static void openYouTube() {
        String youtubeUrl = "https://www.youtube.com";

        if (Desktop.isDesktopSupported()) {
            try {
                Desktop desktop = Desktop.getDesktop();
                desktop.browse(new URI(youtubeUrl));
            } catch (IOException | URISyntaxException e) {
                System.out.println("Error opening YouTube: " + e.getMessage());
            }
        } else {
            System.out.println("Desktop browsing is not supported on this system.");
        }
    }
}