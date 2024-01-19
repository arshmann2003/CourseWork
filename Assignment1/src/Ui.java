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
        int userInput = getValidMenuOption(1, 6) - 1;
        MenuOptions mainOption = null;

        for(MenuOptions option: MenuOptions.values()) {
            if(option.ordinal() == userInput) {
                mainOption = option;
                break;
            }
        }
        return mainOption;
    }

    private int getValidMenuOption(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        int number;
        do {
            String rangeMsg = "Enter a number [" + min + ", " + max + "]: ";
            System.out.print(rangeMsg);
            while (!scanner.hasNextInt()) {
                scanner.next();
                System.out.print(rangeMsg);
            }
            number = scanner.nextInt();
        } while (number < min || number > max);
        return number;
    }

    public ArrayList<String> getNewGameInfo() {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> gameInfo = new ArrayList<>();
        System.out.print("Enter the game's name: ");
        gameInfo.add(scanner.nextLine());
        gameInfo.add(String.valueOf(getValidGameWeight()));
        return gameInfo;
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
                isValid = true;
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

    public void displayGames(ArrayList<BoardGame> boardGames) {
        System.out.println("\nList of Games:\n****************");
        int i=1;
        for(BoardGame boardGame : boardGames) {
            System.out.println(i + ". " + boardGame.getName() + ", " + boardGame.getWeight()
                    + " weight, " + boardGame.getNumOfPlays() + " play(s)");
            i++;
        }
    }
}
