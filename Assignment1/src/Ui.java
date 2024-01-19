import java.util.Scanner;

public class Ui {
    public Scanner scanner;

    public Ui() {
        scanner = new Scanner(System.in);
    }

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
        int userInput = getValidMenuOption();
        MenuOptions mainOption = null;

        for(MenuOptions option: MenuOptions.values()) {
            if(option.ordinal() == userInput) {
                mainOption = option;
                break;
            }
        }
        return mainOption;
    }

    private int getValidMenuOption() {
        while (!scanner.hasNextInt()) {
            String input = scanner.next();
            System.out.println("invalid");
        }
        int number;
       number = scanner.nextInt();
       return number;
    }
}
