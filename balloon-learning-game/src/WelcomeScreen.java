import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;

public class WelcomeScreen extends JPanel implements ActionListener {

    private final Image backgroundImage;
    private final GameButton startLevelOne;
    private final GameButton startLevelTwo;
    private final GameButton startLevelThree;
    private final GameButton exitButton;

    public WelcomeScreen() {
        this.setPreferredSize(new Dimension(900, 600));
        this.setFocusable(true);
        this.setLayout(null);
        this.setBackground(Color.CYAN);


       try (InputStream imageStream = getClass().getResourceAsStream("/assets/pexels-oandremoura-4696590.jpg")) {
            if (imageStream == null) {
               throw new RuntimeException("Background image resource not found! Check the file path in '/assets'.");
            }
            backgroundImage = ImageIO.read(imageStream);
            System.out.println("Background image loaded successfully!");
        } catch (IOException e) {
           e.printStackTrace();
           throw new RuntimeException("Failed to load background image. Ensure the file exists in '/assets'.");
        }

       this.startLevelOne = initializeButton("Start Level 1", 185);
        this.startLevelTwo = initializeButton("Start Level 2", 245);
        this.startLevelThree = initializeButton("Start Level 3", 305);
        this.exitButton = initializeButton("Exit", 365);
    }

    private GameButton initializeButton(String text, int positionY) {
        GameButton button = new GameButton(text, positionY);
        button.addActionListener(this);
        this.add(button);
        return button;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(backgroundImage, 0, 0, 900, 600, null);
    }

    private void hidePanel() {
        this.setVisible(false);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();
        Window window = SwingUtilities.getWindowAncestor((JComponent) source);

        if (source == exitButton) {
            window.dispose();
        } else if (source == startLevelOne) {
            startNewLevel(window, new LevelOne());
        } else if (source == startLevelTwo) {
            startNewLevel(window, new LevelTwo());
        } else if (source == startLevelThree) {
            startNewLevel(window, new LevelThree());
        }
    }

    private void startNewLevel(Window window, GameLevel newLevel) {
        window.add(newLevel);
        newLevel.focus();
        this.hidePanel();
        window.pack();
    }
}