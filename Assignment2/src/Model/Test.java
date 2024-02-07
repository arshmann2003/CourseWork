package Model;

/**
 * Low level class to store information relating to a test.
 */

public class Test {
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
