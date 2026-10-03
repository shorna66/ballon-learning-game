class BalloonLetter extends Balloon {
    public BalloonLetter() {
        super();
    }

    @Override
    public void generateText() {
        String[] lettersAndNumbers = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".split("");
        this.answerText = lettersAndNumbers[this.random.nextInt(lettersAndNumbers.length)];
        this.displayText = this.answerText;
    }
}