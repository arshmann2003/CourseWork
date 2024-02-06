package Model;

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

    public String getDateShipped() {
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

    public String toString() {
        return model + " " + serialNumber + " " + dateShipped + " " + tests;
    }
}

class Test {
    private String date;
    private boolean isTestPassed;
    private String testResultComment;

    // Getters and setters
    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isTestPassed() {
        return isTestPassed;
    }

    public void setTestPassed(boolean testPassed) {
        isTestPassed = testPassed;
    }

    public String getTestResultComment() {
        return testResultComment;
    }

    public void setTestResultComment(String testResultComment) {
        this.testResultComment = testResultComment;
    }
}

