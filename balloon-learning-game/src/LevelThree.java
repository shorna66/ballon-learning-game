import java.awt.event.KeyEvent;

public class LevelThree extends GameLevel implements InterfaceLevel {

    LevelThree() {
        generateBalloons();
        startGame();
    }

    @Override
    public void generateBalloons() {
        for (int i = 0; i < 4; ++i) {
            this.balloons[i] = new BalloonMath();
        }
    }

    @Override
    public void checkKeyTyped(KeyEvent event) {
        if (this.remainingLife <= 0) return;

        char typedChar = event.getKeyChar();
        if (typedChar == KeyEvent.CHAR_UNDEFINED) return;

        int maxHeight = -1;
        Balloon balloonToMove = null;

        for (Balloon balloon : this.balloons) {
            if (balloon != null && balloon.positionY > 0) {

                if (balloon.answerText.equals(String.valueOf(typedChar)) || balloon.answerText.equals(this.typedString)) {
                    if (balloon.positionY > maxHeight) {
                        maxHeight = balloon.positionY;
                        balloonToMove = balloon;
                    }
                }
            }
        }

        if (balloonToMove != null) {
            ++this.score;
            playSound(this.scoreClip);
            balloonToMove.hideBalloon();
            this.typedString = "";
        } else {

            playSound(this.lifeClip);
            --this.remainingLife;
            this.typedString = "";
        }
    }
}