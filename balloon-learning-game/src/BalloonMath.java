import java.util.Random;

public class BalloonMath extends Balloon {
    public BalloonMath() {
        super();
    }

    @Override
    public void generateText() {
        this.generateBasicCalculation();
    }

    public void generateBasicCalculation() {
        Random random = new Random();
        int num1 = 0;
        int num2 = 0;
        int answer = 0;
        String operator = "";
        int operatorChoice = random.nextInt(4);
        if (operatorChoice == 0) {
            operator = "+";

            do {
                num1 = random.nextInt(10);
                num2 = random.nextInt(10);
                answer = num1 + num2;
            } while(answer >= 10);
        } else if (operatorChoice == 1) {
            operator = "-";

            do {
                num1 = random.nextInt(10);
                num2 = random.nextInt(10);
                if (num1 < num2) {
                    int temp = num1;
                    num1 = num2;
                    num2 = temp;
                }

                answer = num1 - num2;
            } while(answer < 0);
        } else if (operatorChoice == 2) {
            operator = "*";

            do {
                num1 = random.nextInt(10);
                num2 = random.nextInt(10);
                answer = num1 * num2;
            } while(answer >= 10);
        } else if (operatorChoice == 3) {
            operator = "/";

            do {
                num2 = random.nextInt(9) + 1;
                num1 = num2 * random.nextInt(10);
                answer = num1 / num2;
            } while(answer >= 10);
        }

        this.displayText = "" + num1 + " " + operator + " " + num2;
        this.answerText = String.valueOf(answer);
    }
}


