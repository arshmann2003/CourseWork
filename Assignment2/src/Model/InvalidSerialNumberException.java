package Model;

/**
 * Exception handler for invalid serial number
 */

public class InvalidSerialNumberException extends Exception {
    public InvalidSerialNumberException(String message) {
        super(message);
    }
}

