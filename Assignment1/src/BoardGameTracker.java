import java.util.ArrayList;

public class BoardGameTracker {
    public static void startApplication(){
        Ui textUi = new Ui();
        textUi.displayWelcomeMessage();
        textUi.displayMenu();
        textUi.getMenuOption();
    }

}