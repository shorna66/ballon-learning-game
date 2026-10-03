import java.awt.Window;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class LevelOne extends GameLevel implements InterfaceLevel {
    LevelOne() {
        generateBalloons();
        startGame();
    }

    @Override
    public void generateBalloons() {
        for (int i = 0; i < 4; ++i) {
            this.balloons[i] = new BalloonLetter();
        }
    }

    @Override
    public void checkKeyTyped(KeyEvent event) {
        System.out.println("LevelOne checkKeyTyped called with key: " + event.getKeyChar());
        if (this.remainingLife <= 0) return;


        if (event.getKeyCode() != 16 && event.getKeyCode() != 20) {
            this.typedString = "";
            int maxHeight = -1;
            Balloon balloonToMove = null;

            for (Balloon balloon : this.balloons) {
                if (balloon != null &&
                        balloon.answerText.equalsIgnoreCase(String.valueOf(event.getKeyChar())) &&
                        balloon.positionY > 0 &&
                        balloon.positionY > maxHeight) {

                    maxHeight = balloon.positionY;
                    balloonToMove = balloon;
                }
            }

            if (balloonToMove != null) {
                ++this.score;
                System.out.println("Balloon popped! Score: " + this.score);
                playSound(this.scoreClip);
                balloonToMove.hideBalloon();
            } else {
                playSound(this.lifeClip);
                --this.remainingLife;
                System.out.println("Wrong key! Remaining life: " + this.remainingLife);
            }
        }
    }

    @Override
    public void startNextLevel() {
        JComponent comp = this;
        Window window = SwingUtilities.getWindowAncestor(comp);
        GameLevel levelTwo = new LevelTwo();
        window.add(levelTwo);
        this.setFocusable(false);
        window.pack();
        levelTwo.focus();
    }
}