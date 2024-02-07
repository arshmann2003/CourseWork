package Ui;

/**
 * Text menu that displays the menu's to the user and get the
 * input choices from the menu's.
 */

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class TextMenu {


    public void displayIntroMessage() {
        System.out.println("***************************************\n" +
                "Water Purification Inventory Management\n" +
                "by Your Name Here.\n" +
                "***************************************");
    }

    public void displayMenu(String className) {
        System.out.println("\n*************\n" +
                "* " + className + " Menu *\n" +
                "*************\n" +
                "1. Read JSON input file.\n" +
                "2. Display info on a unit.\n" +
                "3. Create new unit.\n" +
                "4. Test a unit.\n" +
                "5. Ship a unit.\n" +
                "6. Print report.\n" +
                "7. Set report sort order.\n" +
                "8. Exit");
    }

    public int getValueInRange(int min, int max) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            if(scanner.hasNextInt()) {
                int num = scanner.nextInt();
                if(num<=max && num>=min)
                    return num;
                System.out.println("Error: Please enter a selection between " + min + " and " +max);
                continue;
            }
            System.out.println("Error: Enter a number please.");
            scanner.next();
        }
    }

    public String getFilePath() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String path = scanner.nextLine();
            if(path.equals(""))
                return path;
            if(Files.exists(Paths.get(path)))
                return path;
            System.out.println("Invalid Path Try again");
        }
    }

    public int getReportOption() {
        System.out.println("******************\n" +
                "* Report Options *\n" +
                "******************\n" +
                "1. ALL:           All products.\n" +
                "2. DEFECTIVE:     Products that failed their last test.\n" +
                "3. READY-TO-SHIP: Products passed tests, not shipped.\n" +
                "4. Cancel report request.");
        return getValueInRange(1, 4);
    }

    public void displaySortOptions() {
        System.out.println("*************************************\n" +
                "* Select desired report sort order: *\n" +
                "*************************************");
        System.out.println("1. Sort by serial number\n" +
                "2. Sort by model, then serial number.\n" +
                "3. Sort by most recent test date.\n" +
                "4. Cancel");
    }

    public SortOptions getSortOption() {
        int val = getValueInRange(1, 4)-1;
        SortOptions selectedOption = null;
        for(SortOptions option : SortOptions.values()) {
            if(option.ordinal() == val) {
                selectedOption = option;
                break;
            }
        }
        return selectedOption;
    }
}
