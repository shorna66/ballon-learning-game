import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;

public class GameLevel extends JPanel implements ActionListener {
    Timer timer;
    Random random = new Random();

    public Clip backgroundClip;
    public Clip gameOverClip;
    public Clip scoreClip;
    public Clip lifeClip;

    boolean running = false;
    int remainingLife = 5;
    int score = 0;
    int target;
    String typedString = "";
    Balloon[] balloons = new Balloon[4];
    private Image backgroundImage;

    GameLevel() {
        this.setPreferredSize(new Dimension(900, 600));
        this.setBackground(Color.CYAN);
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(new InnerKeyAdapter());
        this.target = this.random.nextInt(5) + 10;

        loadBackgroundImage();
        loadSounds(); // সাউন্ড লোড মেথড কল

        this.generateBalloons();
        this.startGame();
    }

    // রিসোর্স থেকে সাউন্ড লোড করার মেথড
    private void loadSounds() {
        this.backgroundClip = loadClip("/assets/background.wav");
        this.scoreClip = loadClip("/assets/score.wav");
        this.lifeClip = loadClip("/assets/life.wav");
        this.gameOverClip = loadClip("/assets/game-over.wav");
    }

    private Clip loadClip(String path) {
        try {
            URL url = getClass().getResource(path);
            if (url != null) {
                AudioInputStream audioIn = AudioSystem.getAudioInputStream(url);
                Clip clip = AudioSystem.getClip();
                clip.open(audioIn);
                System.out.println("Loaded sound successfully: " + path);
                return clip;
            } else {
                System.out.println("Sound file not found: " + path);
            }
        } catch (Exception e) {
            System.out.println("Error loading sound: " + path);
            e.printStackTrace();
        }
        return null;
    }

    // ক্লিপ নিরাপদে প্লে করার Helper Method
    public void playSound(Clip clip) {
        if (clip != null) {
            if (clip.isRunning()) {
                clip.stop();
            }
            clip.setFramePosition(0); // একদম শুরুতে নিয়ে আসা
            clip.start(); // প্লে করা
        } else {
            System.out.println("Sound Clip is null!");
        }
    }

    private void loadBackgroundImage() {
        try (InputStream imageStream = getClass().getResourceAsStream("/assets/pexels-oandremoura-4696590.jpg")) {
            if (imageStream == null) {
                System.out.println("Background image resource not found!");
                return;
            }
            backgroundImage = ImageIO.read(imageStream);
            System.out.println("Background image loaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void focus() {
        this.setFocusable(true);
        this.requestFocus();
    }

    public void generateBalloons() {
        for (int i = 0; i < 4; ++i) {
            this.balloons[i] = new Balloon();
        }
    }

    public void showInformation(Graphics g) {
        g.setFont(new Font("Monospace", Font.BOLD, 16));
        g.setColor(Color.BLACK);
        g.drawString("Score: " + this.score, 10, 25);
        g.drawString("Target: " + this.target, 10, 50);
        g.drawString("Life: " + this.remainingLife, 10, 75);
    }

    private void drawGrid(Graphics g) {
        if (this.backgroundImage != null) {
            g.drawImage(this.backgroundImage, 0, 0, 900, 600, null);
        } else {
            g.setColor(new Color(135, 206, 235));
            g.fillRect(0, 0, 900, 600);
        }
    }

    public void drawBalloons(Graphics g) {
        for (Balloon balloon : this.balloons) {
            balloon.draw(g);
        }
    }

    public void moveBalloon() {
        for (Balloon balloon : this.balloons) {
            balloon.update();
        }
    }

    public void checkBalloonDrop() {
        for (Balloon balloon : this.balloons) {
            if (balloon.checkCollision()) {
                --this.remainingLife;
                playSound(this.lifeClip); // বেলুন মাটিতে পড়লে লাইফ সাউন্ড
                balloon.hideBalloon();
            }
        }
    }

    public void checkRemainingLife() {
        if (this.remainingLife <= 0) {
            this.running = false;
        }
    }

    public void draw(Graphics g) {
        this.drawGrid(g);
        this.drawBalloons(g);
        this.showInformation(g);
        this.gameOver(g);
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        this.draw(g);
    }

    public void checkTarget() {
        if (this.score >= this.target) {
            this.running = false;
        }
    }

    public void startNextLevel() {
        System.out.println("Starting next level");
    }

    public void gameOver(Graphics g) {
        if (this.isGameOver()) {
            g.setFont(new Font("Monospace", Font.BOLD, 50));
            g.setColor(Color.MAGENTA);
            int width;
            int height;
            if (this.score >= this.target) {
                width = g.getFontMetrics().stringWidth("Congratulations");
                height = g.getFontMetrics().getHeight();
                g.drawString("Congratulations", 450 - width / 2, 300 - height / 2);

                int widthNext = g.getFontMetrics().stringWidth("Welcome to Next Level");
                int heightNext = g.getFontMetrics().getHeight();
                g.drawString("Welcome to Next Level", 450 - widthNext / 2, 300 - heightNext / 2 + 50);

                if (this.backgroundClip != null) {
                    this.backgroundClip.stop();
                }
                playSound(this.gameOverClip);

                int delay = 5000;
                Timer nextLevelTimer = new Timer(delay, new ActionListener() {
                    public void actionPerformed(ActionEvent event) {
                        System.out.println("Game Won");
                        startNextLevel();
                    }
                });
                nextLevelTimer.setRepeats(false);
                nextLevelTimer.start();
            } else {
                width = g.getFontMetrics().stringWidth("Game Over");
                height = g.getFontMetrics().getHeight();
                g.drawString("Game Over", 450 - width / 2, 300 - height / 2);

                if (this.backgroundClip != null) {
                    this.backgroundClip.stop();
                }
                playSound(this.gameOverClip);
            }
        }
    }

    public boolean isGameOver() {
        return !this.running;
    }

    public void actionPerformed(ActionEvent e) {
        this.moveBalloon();
        this.checkBalloonDrop();
        this.checkRemainingLife();
        this.checkTarget();
        this.repaint();
        if (!this.running) {
            this.timer.stop();
        }
    }

    public void startGame() {
        this.running = true;
        this.timer = new Timer(75, this);
        this.timer.start();

        if (this.backgroundClip != null) {
            this.backgroundClip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void checkKeyTyped(KeyEvent event) {
        System.out.println("Base checkKeyTyped called with key: " + event.getKeyChar());
    }

    private class InnerKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            System.out.println("Key Pressed: " + e.getKeyChar());
            String lettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
            char keyChar = e.getKeyChar();

            if (lettersAndNumbers.contains(Character.toString(keyChar))) {
                typedString = typedString + keyChar;
            }

            checkKeyTyped(e);
        }
    }
}