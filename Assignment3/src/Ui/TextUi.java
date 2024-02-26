package Ui;

/**
 * Class to interact with the user to take actions during the game
 */

import Model.Board;
import Model.Cell;

public class TextUi {
    private TextMenu textMenu;
    private Board board;

    public TextUi() {textMenu = new TextMenu();}

    public void start(String[] args) {
        int numOfForts = 5;
        String cheat = "";
        if(args.length > 0) {
            if(args[0].matches("-?\\d+(\\.\\d+)?"))
                numOfForts = Integer.parseInt(args[0]);
        }
        if(args.length > 1) cheat = args[1];
        if(numOfForts > 19)
            textMenu.displayUnableToStartMessage();
        else {
            board = new Board(numOfForts);
            gameLogic(cheat.equals("--cheat"), numOfForts);
        }
    }

    private void gameLogic(boolean cheating, int numOfForts) {
        textBoardInfo info = new textBoardInfo(board);
        if(cheating) {
            info.displayBoard(cheating);
            info.displayOpponentPoints(board.getPoints());
            textMenu.displayLowerCaseMessage();
        }
        textMenu.displayWelcome(numOfForts);
        while(true) {
            info.displayBoard(false);
            info.displayOpponentPoints(board.getPoints());
            if (endCondition(textMenu, info)) break;
            String move = info.getMoveInput();
            char rowLetter = move.charAt(0);
            int row=0;
            if(Character.isLowerCase(rowLetter)) row = rowLetter - 'a' + 1;
            else if(Character.isUpperCase(rowLetter)) row = rowLetter - 'A' + 1;
            int col=0;
            if(move.length() > 2) col = 10;
            else col = move.charAt(1) - '0';
            Cell cell = board.getCell(row-1, col-1);
            cell.hit();
            if(cell.isFort()) System.out.println("HIT!");
            else System.out.println("Miss.");
            info.displayOpponentFortPoints(board);
            board.updatePoints();
        }
    }

    private boolean endCondition(TextMenu textMenu, textBoardInfo boardInfo) {
        if(board.winCondition()) {
            textMenu.displayWinMessage();
            board.showHitCells();
            boardInfo.displayBoard(true);
            boardInfo.displayOpponentPoints(board.getPoints());
            textMenu.displayLowerCaseMessage();
            return true;
        } else if(board.looseCondition()){
            textMenu.displayLooseMessage();
            board.showHitCells();
            boardInfo.displayBoard(true);
            boardInfo.displayOpponentPoints(board.getPoints());
            textMenu.displayLowerCaseMessage();
            return true;
        }
        return false;
    }
}
