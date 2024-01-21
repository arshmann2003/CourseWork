/**
 * User text interface that interacts with the user of the application
 */

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    public void displayWelcomeMessage() {
        System.out.println("**********************************");
        System.out.println("Welcome to the Board Game Tracker\nby Arshdeep Mann");
        System.out.println("**********************************");
    }

    public void displayMenu() {
        System.out.println("\n*************\n* Main Menu *\n*************");
        System.out.println("1. List games\n" +
                "2. Add a new game\n" +
                "3. Remove a game\n" +
                "4. Record that you played a game\n" +
                "5. DEBUG: Dump objects (toString)\n" +
                "6. Exit");
    }

    public MenuOptions getMenuOption() {
        int userInput = getValidMenuOption(1, 6, MenuOptions.add) - 1;
        MenuOptions mainOption = null;

        for(MenuOptions option: MenuOptions.values()) {
            if(option.ordinal() == userInput) {
                mainOption = option;
                break;
            }
        }
        return mainOption;
    }

    public ArrayList<String> getNewGameInfo() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> gameInfo = new ArrayList<>();
        gameInfo.add(getValidGameName());
        gameInfo.add(String.valueOf(getValidGameWeight()));
        return gameInfo;
    }

    private String getValidGameName() {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Enter the game's name: ");
            String name = scanner.nextLine();
            if(name.length() >= 1)
                return name;
            else {
                System.out.println("ERROR: Name must be at least 1 character long.");
            }
        }
    }

    public void displayGames(ArrayList<BoardGame> boardGames) {
        System.out.println("\nList of Games:\n****************");
        int i=1;
        for(BoardGame boardGame : boardGames) {
            System.out.println(i + ". " + boardGame.getName() + ", " + boardGame.getWeight()
                    + " weight, " + boardGame.getNumOfPlays() + " play(s)");
            i++;
        }
        if(i==1) {
            System.out.println("No Games Found.");
        }

    }

    public void dumpObjects(ArrayList<BoardGame> boardGames) {
        int i=1;
        System.out.println("All game objects:");
        for(BoardGame game: boardGames) {
            System.out.print(i++ + ". ");
            System.out.println(game);
        }
    }

    public int getGameNumber(int numOfGames, MenuOptions options) {
        System.out.println("(Enter 0 to cancel)");
        return getValidMenuOption(0, numOfGames, options);
    }

    private int getValidMenuOption(int min, int max, MenuOptions option) {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Error: Please enter a number");
                scanner.next();
            }
            number = scanner.nextInt();
            if(number==0 && (option==MenuOptions.remove || option==MenuOptions.record))
                return number;
            if(number >= min && number <= max )
                break;
            else{
                System.out.println("Error: Please enter a selection between " + min
                        + " and " + max);
            }
        } while (true);
        return number;
    }

    private float getValidGameWeight() {
        Scanner scanner = new Scanner(System.in);
        float result = 0.0f;
        boolean isValid = false;
        do {
            System.out.print("Enter the game's weight: ");
            String userInput = scanner.next();
            if (isValidFloat(userInput)) {
                result = Float.parseFloat(userInput);
                if(result >= 1.0 && result <= 5.0)
                    isValid = true;
                else
                    System.out.println("ERROR: Weight must be between 1.0 and 5.0");
            }
        } while (!isValid);
        return result;
    }

    private boolean isValidFloat(String input) {
        try {
            Float.parseFloat(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public void displayIncreasedGameMessage(String name, int numOfPlays) {
        System.out.println(name + " has been played " + numOfPlays + " time(s)!");
    }
}
