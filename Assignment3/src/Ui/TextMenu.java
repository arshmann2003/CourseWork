package Ui;

/**
 * Class to display various messages for user feedback during gameplay
 */

public class TextMenu {
    public void displayLowerCaseMessage() {
        System.out.println("(Lower case fort letters are where you shot.)\n");
    }

    public void displayWelcome(int numOfForts) {
        System.out.println("Starting game with " + numOfForts + " forts.\n" +
                "------------------------\n" +
                "Welcome to Fort Defense!\n" +
                "by Arshdeep Mann\n" +
                "------------------------\n");
    }

    public void displayWinMessage() {
        System.out.println("Congratulations! You won!\n");
    }

    public void displayLooseMessage() {
        System.out.println("I'm sorry, your fort is all wet! They win!\n");
    }

    public void displayUnableToStartMessage() {
        System.out.println("Error: Unable to place 20 on the board.\n" +
                "       Try running game again with fewer forts.");
    }
}
