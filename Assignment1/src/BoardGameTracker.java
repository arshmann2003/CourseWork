/**
 * Manages the several menu actions take by the users in the application,
 * stores the active boardgames.
 */

import java.util.ArrayList;

public class BoardGameTracker {
    private final ArrayList<BoardGame> boardGames;
    private final Ui textUi;

    public BoardGameTracker() {
        boardGames = new ArrayList<>();
        textUi = new Ui();
    }

    public void startApplication(){
        textUi.displayWelcomeMessage();
        while(true) {
            textUi.displayMenu();
            boolean cont = handleMenuAction(textUi.getMenuOption());
            if(!cont)
                break;
        }
    }

    private boolean handleMenuAction(MenuOptions menuOption) {
        switch (menuOption) {
            case MenuOptions.add:
                addNewGame();
                break;
            case MenuOptions.list:
                listGames();
                break;
            case MenuOptions.remove:
                removeGame();
                break;
            case MenuOptions.record:
                recordPlay();
                break;
            case MenuOptions.debug:
                dumpObjects();
                break;
            case MenuOptions.exit:
                return false;
        }
        return true;
    }

    private void addNewGame() {
        ArrayList<String> gameInfo =  textUi.getNewGameInfo();
        BoardGame boardGame = new BoardGame(gameInfo.get(0), Float.parseFloat(gameInfo.get(1)));
        boardGames.add(boardGame);
    }

    private void listGames() {
        textUi.displayGames(boardGames);
    }

    private void removeGame() {
        textUi.displayGames(boardGames);
        if(boardGames.isEmpty())
            return;
        int index = textUi.getGameNumber(boardGames.size(), MenuOptions.remove)-1;
        if(index >= 0)
            boardGames.remove(index);
    }

    private void recordPlay() {
        textUi.displayGames(boardGames);
        if(boardGames.isEmpty())
            return;
        int index = textUi.getGameNumber(boardGames.size(), MenuOptions.record)-1;
        if(index >= 0 && index<boardGames.size()) {
            BoardGame game =  boardGames.get(index);
            game.increaseNumOfPlays();
            textUi.displayIncreasedGameMessage(game.getName(), game.getNumOfPlays());
        }
    }

    private void dumpObjects() {
        textUi.dumpObjects(boardGames);
    }
}