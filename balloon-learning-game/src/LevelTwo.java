import java.awt.Window;
import java.awt.event.KeyEvent;
import javax.swing.JComponent;
import javax.swing.SwingUtilities;

public class LevelTwo extends GameLevel implements InterfaceLevel {
    LevelTwo() {
        generateBalloons();
        startGame();
    }

    @Override
    public void generateBalloons() {
        for (int i = 0; i < 4; ++i) {
            this.balloons[i] = new BalloonWord();
        }
    }

    @Override
    public void checkKeyTyped(KeyEvent event) {
        if (this.remainingLife <= 0) return;


        if (event.getKeyCode() != 16 && event.getKeyCode() != 20) {
            System.out.println("Typed String: " + this.typedString);


            if (this.typedString.length() >= 3) {
                Balloon balloonToMove = null;
                int maxHeight = -1;

                for (Balloon balloon : this.balloons) {
                    if (balloon != null && balloon.answerText.equalsIgnoreCase(this.typedString) && balloon.positionY > 0) {
                        if (balloon.positionY > maxHeight) {
                            maxHeight = balloon.positionY;
                            balloonToMove = balloon;
                        }
                    }
                }

                if (balloonToMove != null) {
                    ++this.score;
                    this.typedString = "";
                    balloonToMove.hideBalloon();
                    playSound(this.scoreClip);
                    return;
                }


                playSound(this.lifeClip);
                --this.remainingLife;
                this.typedString = "";
            }
        }
    }

    @Override
    public void startNextLevel() {
        JComponent comp = this;
        Window window = SwingUtilities.getWindowAncestor(comp);
        GameLevel levelThree = new LevelThree();
        window.add(levelThree);
        this.setFocusable(false);
        window.pack();
        levelThree.focus();
    }
}