/**
 * Individual BoardGame that keeps track of name, weight, and number of plays of the game
 */

public class BoardGame {
    private final String name;
    private final float weight;
    private int numOfPlays;

    public BoardGame(String name, float weight) {
        this.name = name;
        this.weight = weight;
        this.numOfPlays = 0;
    }

    public String getName() {
        return this.name;
    }

    public float getWeight() {
        return this.weight;
    }

    public int getNumOfPlays() {
        return this.numOfPlays;
    }

    public void increaseNumOfPlays() {
        this.numOfPlays++;
    }

    public String toString() {
        return getClass().getName() + "[Name:" + getName() + ", Weight:" + getWeight()
                + ", Games played:" + getNumOfPlays() + "]";
    }
}
