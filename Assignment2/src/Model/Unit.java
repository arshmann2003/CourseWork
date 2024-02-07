package Model;

/**
 * Low-level class to store model, serial nubmber, ship dates.
 */

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Unit {
    private String model;
    private String serialNumber;
    private String dateShipped;
    private List<Test> tests;


    // Getters and setters

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }
    public void validateSerialNumber(String serialNumber) throws InvalidSerialNumberException {
        if (serialNumber == null || serialNumber.length() < 3 || serialNumber.contains(" ") || !serialNumber.matches("\\d+")
            || !checkSum(serialNumber))
        {
            throw new InvalidSerialNumberException("\t'Serial Number Error: Checksum does not match.'");
        }

        // Additional validation logic if needed
    }

    private boolean checkSum(String serialNumber) {
        String cleanedDigits = serialNumber.replaceAll("\\D", "");
        int sum = 0;

        for (int i = 0; i < cleanedDigits.length() - 2; i++) {
            // Convert the character digit to its integer value
            int digit = Character.getNumericValue(cleanedDigits.charAt(i));
            // Add the digit to the sum
            sum += digit;
        }
        int checksum = sum % 100;
        int expectedChecksum = Integer.parseInt(cleanedDigits.substring(cleanedDigits.length() - 2));
        return checksum == expectedChecksum;
    }

    public String getDateShipped() {
        if(dateShipped == null)
            return "-";
        return dateShipped;
    }

    public void setDateShipped(String dateShipped) {
        this.dateShipped = dateShipped;
    }

    public List<Test> getTests() {
        return tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public void addTest(Test test) {
        if(tests==null) {
            tests = new ArrayList<>();
        }
        tests.add(test);
    }

    public int getNumberOfTests() {
        if(tests != null)
            return tests.size();
        return 0;
    }

    public Test mostRecentTest() {
        // Initialize with a very early date
        if(tests==null)
            return null;
        LocalDate maxDate = LocalDate.MIN;
        Test mostRecentTest = null;

        for(Test test : tests) {
            LocalDate date = LocalDate.parse(test.getDate());
            if (date.isAfter(maxDate)) {
                maxDate = date;
                mostRecentTest = test;
            }
        }
        return mostRecentTest;
    }

    public boolean isDefective() {
        Test mostRecentTest = mostRecentTest();
        if(mostRecentTest == null)
            return false;
        return !mostRecentTest.isTestPassed();
    }

    public boolean isReadyToShip() {
       Test mostRecentTest = mostRecentTest();
       if(mostRecentTest == null) {
           return false;
       }
       if(mostRecentTest.isTestPassed() && dateShipped == null) {
          return true;
       }
       return false;
    }

}

