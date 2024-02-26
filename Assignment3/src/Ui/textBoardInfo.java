package Ui;

/**
 *  Class to display various information relating to the game
 *  board and various states of the board (how many forts are
 *  damaged...etc.)
 */

import Model.Board;
import Model.Cell;

import java.util.Scanner;

public class textBoardInfo {
    private Board board;

    public textBoardInfo(Board board) {
        this.board = board;
    }

    public void displayBoard(boolean cheating) {
        System.out.print("Game Board:\n        ");
        for(int i=1; i<=10; i++)
            System.out.print(i + "  ");
        System.out.println();
        int row=0;
        for (char letter = 'A'; letter <= 'J'; letter++) {
            System.out.print("    " + letter + "   ");
            for(int col=0; col<10; col++) {
               displayCell(board.getCell(row, col), cheating);
               System.out.print("  ");
            }
            System.out.println();
            row++;
        }
    }

    public String getMoveInput() {
        Scanner scanner = new Scanner(System.in);
        String ret = "";
        while(true) {
            System.out.print("Enter your move: ");
            ret = scanner.nextLine();
            if(validInput(ret)) {
                return ret;
            }
        }
    }

    public void displayOpponentFortPoints(Board board) {
        for(int i=0; i<board.numOfForts; i++) {
            int points = board.getFortPoints(i);
            int oppNum = i+1;
            if(points > 0)
                System.out.println("Opponent #" + oppNum + " of " + board.numOfForts + " shot you for " + points + " points!");
        }
        System.out.println();
    }

    public void displayOpponentPoints(int points) {
        System.out.println("Opponents points: " + points + " / 2500.");
    }

    private boolean validInput(String ret) {
        if(ret.length() > 3 || ret.isEmpty())
            return false;
        char row = ret.charAt(0);
        char col = ret.charAt(1);
        char third;
        if(ret.length() > 2) {
            third = ret.charAt(2);
            if(third != '0')
                return false;
        }
        if(!Character.isAlphabetic(row)) {
            return false;
        }
        if(!Character.isDigit(col)) {
            return false;
        }
        int roww = 0;
        if(Character.isLowerCase(row))
           roww = row - 'a' + 1;
       else if(Character.isUpperCase(row))
           roww = row - 'A' + 1;
       int coll = col - '0';
       if(roww <= 10 && roww >= 0 && coll < 10 && coll >= 0 ) {
           return true;
       }
       return false;
    }

    private void displayCell(Cell cell, boolean cheating) {
        if(cheating && cell.isFort()) {
            System.out.print(cell.cellToChar);
            return;
        }
        if(cheating && cell.isGrass()) {
            System.out.print(".");
            return;
        }
        if(!cell.isFound()) {
            System.out.print("~");
        }
        else if(cell.isHit()){
            if(cell.isFort())
                System.out.print("X");
            else
                System.out.print(" ");
        }

    }
}
