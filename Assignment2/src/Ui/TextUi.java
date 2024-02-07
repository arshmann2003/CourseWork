package Ui;

/*
 * User interaction class that handles which actions
 * to take based on user's choices.
**/

import Model.InvalidSerialNumberException;
import Model.Main;
import Model.Test;
import Model.Unit;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class TextUi {
    final private TextMenu textMenu;
    private Unit[] units;
    private ArrayList<Unit> mainUnits;
    private SortOptions reportSortOption;

    public TextUi() {
        textMenu = new TextMenu();
        reportSortOption = SortOptions.serialNumber;
        units = null;
    }
    public void start() {
        textMenu.displayIntroMessage();
        while(true) {
           textMenu.displayMenu(String.valueOf(Main.class).replace("class ", ""));
           if(!handleMenuOption(textMenu.getValueInRange(1, 8)))
               break;
        }

    }

    private boolean handleMenuOption(int selectedValue) {
        selectedValue--;
        MenuOptions mainOption = null;
        for(MenuOptions option: MenuOptions.values()) {
            if(option.ordinal() == selectedValue) {
                mainOption = option;
                break;
            }
        }
        switch(mainOption) {
            case readJson -> {
                readJson();
            }
            case displayUnitInfo -> {
                displayUnitInfo();
            }
            case createUnit -> {
                createUnit();
            }
            case testUnit -> {
                testUnit();
            }
            case shipUnit -> {
                shipUnit();
            }
            case printReport -> {
                printReport();
            }
            case reportSortOrder -> {
                setReportOrder();
            }
            case exit -> {
                return false;
            }

            case null, default -> {}
        }
        return true;
    }

    private void setReportOrder() {
        textMenu.displaySortOptions();
        SortOptions option = textMenu.getSortOption();
        reportSortOption = option;
    }

    private void shipUnit() {
        if(units==null) {
            System.out.println("No units defined.");
            return;
        }
       Unit unit = getValidUnit();
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        unit.setDateShipped(currentDate.format(formatter));
        System.out.println("Unit successfully shipped.");
    }

    private void testUnit() {
        if(units==null) {
            System.out.println("No units defined.");
            return;
        }
        Unit unit = getValidUnit();
        if(unit==null)
            return;
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> passedOptions = new ArrayList<>(Arrays.asList("y", "Y", ""));
        ArrayList<String> failedOptions = new ArrayList<>(Arrays.asList("n", "N"));
        String passed = "";
        boolean testPassed;
        while (true) {
            System.out.print("Pass? (Y/n): ");
            passed = scanner.nextLine();
            if(passedOptions.contains(passed)) {
                testPassed = true;
                break;
            }
            else if(failedOptions.contains(passed)){
                testPassed = false;
                break;
            }
        }
        System.out.print("Comment: ");
        String comment = scanner.nextLine();
        Test test = new Test();
        test.setTestPassed(testPassed);
        test.setTestResultComment(comment);
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        test.setDate(currentDate.format(formatter));
        unit.addTest(test);
        System.out.println("Test recorded.");
    }

    private void createUnit() {
        Unit newUnit = new Unit();
        System.out.println("Enter product info; blank line to quit.");
        String model = getUserModel();
        if(model.isEmpty())
            return;
        String serialNumber = getUserSerialNumber();
        if(serialNumber.isEmpty())
            return;
        newUnit.setSerialNumber(serialNumber);
        newUnit.setModel(model);

        if(units == null) {
            units = new Unit[1];
            units[0] = newUnit;
            return;
        }
        List<Unit> unitsList = new ArrayList<>();
        for(Unit unit : units)
            unitsList.add(unit);
        unitsList.add(newUnit);
        units = new Unit[unitsList.size()];
        for(int i=0; i<unitsList.size(); i++) {
            units[i] = unitsList.get(i);
        }
    }

    private String getUserSerialNumber() {
        Scanner scanner = new Scanner(System.in);
        Unit validator = new Unit();
        while (true) {
            System.out.print("Serial number: ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) {
                return "";
            }
            try {
                validator.validateSerialNumber(input);
                if(units!=null && !uniqueSerialNumber(input)) {
                   throw new InvalidSerialNumberException("\t'Serial Number Error: number in use.'");
                }
                return input;
            } catch (InvalidSerialNumberException e) {
                System.out.println("Unable to add the product.");
                System.out.println(e.getMessage());
                System.out.println("Please try again.");
            }
        }
    }

    private boolean uniqueSerialNumber(String input) {
       for(Unit unit : units) {
           if(unit.getSerialNumber().equals(input))
               return false;
       }
       return true;
    }

    private String getUserModel() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Model: ");
        return scanner.nextLine();
    }

    private void displayUnitInfo() {
        if(units == null) {
            System.out.println("No units defined.\nPlease create a unit and then re-try this option.");
            return;
        }
        Unit selectedUnit = getValidUnit();
        if (selectedUnit == null)
            return;
        System.out.println("\nUnit details:");
        System.out.printf("%12s%s\n", "Serial: ",selectedUnit.getSerialNumber());
        System.out.printf("%12s%s\n", "Model: ", selectedUnit.getModel());
        System.out.printf("%12s%s\n", "Ship Date: ", selectedUnit.getDateShipped());
        System.out.println("\nTests\n" + "*********");
        System.out.println("        Date   Passed?  Test Comments\n" +
                "------------  --------  -------------");
        List<Test> tests = selectedUnit.getTests();
        if(tests==null) {
            System.out.println("No tests.");
            return;
        }
        for(Test test : tests) {
            String passed = test.isTestPassed() ? "PASSED" : "FAILED";
            System.out.printf("%12s\t%6s\t%s\n", test.getDate(), passed, test.getTestResultComment());
        }
        if(tests.isEmpty())
            System.out.println("No tests.");;
    }

    private Unit getValidUnit() {
        Unit selectedUnit = null;
        boolean cont = true;
        Scanner scanner = new Scanner(System.in);
        while(cont) {
            System.out.print("Enter the serial number (0 for list, -1 for cancel): ");
            String serialNumber = scanner.nextLine();
            if(serialNumber.equals("-1"))
                break;
            if(serialNumber.equals("0")) {
               displayValidUnits(ReportOptions.all);
               continue;
            }
            if(units!=null) {
                for (Unit unit : units) {
                    if (unit.getSerialNumber().equals(serialNumber)) {
                        selectedUnit = unit;
                        cont = false;
                        break;
                    }
                }
            }
            if(cont)
                System.out.println("No unit found matching serial '"+serialNumber+"'");
        }
        return selectedUnit;
    }

    private void printReport() {
        System.out.println();
        int selectedValue = textMenu.getReportOption() - 1;
        ReportOptions selectedOption = null;
        for(ReportOptions option : ReportOptions.values()) {
            if(option.ordinal() == selectedValue) {
                selectedOption = option;
                break;
            }
        }
        displayValidUnits(selectedOption);
    }

    private void displayValidUnits(ReportOptions selectedOption) {
        if(selectedOption == ReportOptions.cancel) {
            return;
        }
        String msg = "";
        if(selectedOption == ReportOptions.all) {
            msg = "\nList of";
        }
        else if(selectedOption == ReportOptions.defective) {
            msg = "\nDEFECTIVE";
        }
        else if(selectedOption==ReportOptions.readyToShip) {
            msg = "\nREAD-TO-SHIP";
        }
        System.out.println(msg + " Water Purification Units:\n" +
                "*************************************");
        if(units == null) {
            System.out.println("No units found.");
            return;
        }
        sortUnits(reportSortOption);
        int i=0;
        for(Unit unit : units) {
            if(validUnit(unit, selectedOption)) {
                if(i==0) {
                    displayTableHeader(selectedOption);
                }
                i++;
                displayRow(selectedOption, unit);
            }
        }
        if(i==0) {
            System.out.println("No units found.");
        }
    }

    private void displayRow(ReportOptions selectedOption, Unit unit) {
        if(selectedOption==ReportOptions.all) {
            System.out.printf("%10s\t%15s\t %10s\t %10s\n", unit.getModel(),
                    unit.getSerialNumber(), unit.getNumberOfTests(), unit.getDateShipped());
        } else if(selectedOption == ReportOptions.defective) {
             Test test = unit.mostRecentTest();
             String date = test.getDate();
             String comment = test.getTestResultComment();
            System.out.printf("%10s\t%15s\t %10s\t%12s  %s\n", unit.getModel(),
                    unit.getSerialNumber(), unit.getNumberOfTests(), date, comment);
        } else if(selectedOption == ReportOptions.readyToShip) {
            Test test = unit.mostRecentTest();
            String date = test.getDate();
            System.out.printf("%10s\t%15s\t  %10s\n", unit.getModel(),
                    unit.getSerialNumber(), date);
        }
    }

    private void displayTableHeader(ReportOptions selectedOption) {
        if(selectedOption == ReportOptions.all) {
            System.out.println("     Model           Serial     # Tests   Ship Date\n" +
                    "----------  ---------------  ----------  ----------");
        } else if(selectedOption == ReportOptions.defective) {
            System.out.println("     Model           Serial     # Tests    Test Date  Test Comments\n" +
                    "----------  ---------------  ----------  -----------  -------------");
        } else if (selectedOption == ReportOptions.readyToShip) {
            System.out.println("     Model           Serial    Test Date\n" +
                    "----------  ---------------  -----------");
        }
    }

    private boolean validUnit(Unit unit, ReportOptions option) {
        switch(option) {
            case all:
                return true;
            case defective:
                return unit.isDefective();
            case readyToShip:
                return unit.isReadyToShip();
        }
        return false;
    }

    private void sortUnits(SortOptions selectedOption) {
        Arrays.sort(units, new Comparator<Unit>() {
            @Override
            public int compare(Unit o1, Unit o2) {
                return compareLarger(o1, o2, selectedOption);
            }
        });
    }

    private int compareLarger(Unit o1, Unit o2, SortOptions selectedOption) {
        if (selectedOption == SortOptions.serialNumber) {
            Integer x = sortBySerial(o1, o2);
            if (x != null) return x;
        } else if(selectedOption == SortOptions.model) {
            Integer x = lexoSort(o1, o2);
            if (x != null) return x;
        } else if(selectedOption == SortOptions.dateRecentTest) {
            Integer x = sortByRecentTest(o1, o2);
            if(x!=null) return x;
        }
        return 0;
    }

    private Integer sortByRecentTest(Unit o1, Unit o2) {
        Test test1 = o1.mostRecentTest();
        Test test2 = o2.mostRecentTest();
        if(test1==null && test2!=null)
            return 1;
        else if(test1!=null && test2==null)
            return -1;
        else if(test1==null && test2==null)
            return 1;

        LocalDate dat1 = LocalDate.parse(test1.getDate());
        LocalDate dat2 = LocalDate.parse(test2.getDate());
        if(dat1.isAfter(dat2)) {
           return 1;
        } else if(dat2.isAfter(dat1)){
            return -1;
        } else{
            return 0;
        }
    }

    private static Integer sortBySerial(Unit o1, Unit o2) {
        String serialOne = o1.getSerialNumber();
        String serialTwo = o2.getSerialNumber();
        if (serialOne.length() > serialTwo.length())
            return 1;
        else if (serialOne.length() < serialTwo.length())
            return -1;
        int n = serialOne.length();
        for (int i = 0; i < n; i++) {
            int digit1 = Integer.parseInt(String.valueOf(serialOne.charAt(i)));
            int digit2 = Integer.parseInt(String.valueOf(serialTwo.charAt(i)));
            if (digit1 > digit2) {
                return 1;
            } else if (digit1 < digit2) {
                return -1;
            }
        }
        return null;
    }

    private static Integer lexoSort(Unit o1, Unit o2) {
        String str1 = o1.getModel();
        String str2 = o2.getModel();
        int result = str1.compareTo(str2);
        if (result < 0) {
            return -1;
        } else if (result > 0) {
            return 1;
        } else {
            return sortBySerial(o1, o2);
        }
    }

    private void readJson() {
        System.out.println("Enter the path to the input JSON file; blank to cancel.\nWARNING: " +
                "This will replace all current data with the data from the file.");
        String path = textMenu.getFilePath();
        if(path.isEmpty()) {
            return;
        }
        Gson gson = new Gson();
        try {
            FileReader fileReader = new FileReader(path);
            units =  gson.fromJson(fileReader, Unit[].class);
            System.out.println("Read " + units.length + " products from JSON file '" + path + "'.");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

