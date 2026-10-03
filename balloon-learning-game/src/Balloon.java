import java.awt.*;
import java.util.Random;

class Balloon {
    Random random = new Random();
    int positionX;
    int positionY;
    int velocityY;
    int acceleration;
    String answerText;
    String displayText;
    Color balloonColor;
    private static final Color[] COLORS = new Color[]{new Color(255, 255, 255, 153), new Color(255, 192, 203, 153), new Color(255, 255, 0, 77)};

    Balloon() {
        this.positionX = this.random.nextInt(850) + 25;
        this.positionY = this.random.nextInt(300) - 300;
        this.velocityY = 3;
        this.acceleration = 1;
        this.generateText();
        this.assignRandomColor();
    }

    public void generateText() {
        this.answerText = "A";
        this.displayText = this.answerText;
    }

    public void assignRandomColor() {
        this.balloonColor = COLORS[this.random.nextInt(COLORS.length)];
    }

    public void draw(Graphics g) {
        // Draw the balloon
        g.setColor(this.balloonColor);
        g.fillOval(this.positionX - 25, this.positionY - 35, 50, 70);

        // Set up the font for the text
        g.setColor(Color.BLACK);
        g.setFont(new Font("Monospace", Font.BOLD, 20));

        // Calculate text dimensions for proper centering
        FontMetrics metrics = g.getFontMetrics();
        int textWidth = metrics.stringWidth(this.displayText);
        int textHeight = metrics.getHeight();

        // Draw the text centered in the balloon
        g.drawString(this.displayText,
                this.positionX - textWidth / 2,
                this.positionY - 5 + textHeight / 4);
    }
//falling balloons
    public void update() {
        this.positionY += this.velocityY;
    }

    public boolean checkCollision() {
        if (this.positionY > 575) {
            this.positionY = -100;
            this.positionX = this.random.nextInt(850) + 25;
            this.velocityY += this.acceleration;
            this.generateText();
            this.assignRandomColor();
            return true;
        } else {
            return false;
        }
    }

    public boolean checkAnswer(String answer) {
        return answer.equals(this.answerText);
    }

    public void hideBalloon() {
        this.positionY = -100;
        this.positionX = this.random.nextInt(850) + 25;
        this.velocityY += this.acceleration;
        this.generateText();
        this.assignRandomColor();
    }
}