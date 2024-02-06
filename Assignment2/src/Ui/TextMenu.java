package Ui;

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
                continue;
            }
            scanner.next();
        }
    }

    public String getFilePath() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public void getReportOption() {
        System.out.println("******************\n" +
                "* Report Options *\n" +
                "******************\n" +
                "1. ALL:           All products.\n" +
                "2. DEFECTIVE:     Products that failed their last test.\n" +
                "3. READY-TO-SHIP: Products passed tests, not shipped.\n" +
                "4. Cancel report request.");
        int chosen = getValueInRange(1, 4);
    }
}
