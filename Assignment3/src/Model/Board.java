package Model;

/**
 * Handle the underlying board for the user to play the game on.
 * Purpose is to generate the correct grid for the selected number of
 * forts and update this grid to account for various game states.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.*;
import static java.lang.Thread.sleep;

public class Board {
    public int opponentPoints;
    private List<List<Cell>> forts;
    private Cell[][] grid;
    public int numOfForts;

    public Board(int numOfForts) {
        grid = new Cell[10][10];
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        forts = new ArrayList<>();
        generateGameBoard(numOfForts);
        this.numOfForts = numOfForts;
    }

    public int getFortPoints(int fortIndex) {
        List<Cell> fort = forts.get(fortIndex);
        int undamaged = fort.size();
        for(Cell cell : fort ) {
            if(cell.isHit() && cell.isFort())
                undamaged--;
        }
        switch (undamaged) {
            case 1 -> {return 1;}
            case 2 -> {return 2;}
            case 3 -> {return 5;}
            case 4, 5 -> {return 20;}
            default -> {return 0;}
        }
    }

   public void updatePoints() {
       int totalPoints = 0;
       for(List<Cell> fort : forts) {
           int undamaged = fort.size();
           for(Cell cell : fort) {
               if(cell.isHit())
                   undamaged--;
           }
           switch (undamaged) {
               case 1 -> totalPoints+=1;
               case 2 -> totalPoints+=2;
               case 3 -> totalPoints+=5;
               case 4, 5 -> totalPoints+=20;
               default -> totalPoints +=0;
           }
       }
       opponentPoints += totalPoints;
   }

    public int getPoints(){
        return opponentPoints;
    }

    public Cell getCell(int row, int col) {
        if(row < grid.length && row >= 0
                && col < grid[0].length && col >= 0) {
            return grid[row][col];
        }
        return null;
    }

    public boolean winCondition() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j].isFort() && !grid[i][j].isHit())
                    return false;
            }
        }
        return true;
    }

    public boolean looseCondition() {
        return opponentPoints >= 2500;
    }

    public void showHitCells() {
        for(int i=0; i<grid.length; i++) {
            for(int j=0; j<grid[i].length; j++){
                if(grid[i][j].isFort() && grid[i][j].isHit())
                    grid[i][j].cellToChar = Character.toLowerCase(grid[i][j].cellToChar);
            }
        }
    }

    private void generateGameBoard(int numOfForts) {
        Random random = new Random();
        char marker = 'A';
        int i=0;
        while(i<numOfForts) {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            Cell cell = grid[row][col];
            if (!cell.grass)
                continue;
            boolean success =  placeFort(row, col, random, marker++);
            if(!success){
                marker = 'A';
                i=0;
                clearGrid();
                placeFort(row, col, random, marker++);
                success = true;
            }
            i++;
        }
    }

    private boolean placeFort(int row, int col, Random random, char marker) {
        List<Cell> fort= new ArrayList<>();
        Cell start = grid[row][col];
        start.cellToChar = marker;
        start.grass = false;
        start.fortPiece = true;
        fort.add(start);
        int placed = 0;
        int attempts = 0;
        while(placed < 4 && attempts <= 100) {
            attempts++;
            int rand = random.nextInt(fort.size());
            Cell lastPos = fort.get(rand);
            int i = lastPos.row;
            int j = lastPos.col;
            int direction =  random.nextInt(4);
            if(direction == 0) j++;
            else if(direction == 1) i++;
            else if(direction == 2) i--;
            else j--;
            if(validPosition(i, j)) {
                Cell newFortPiece = grid[i][j];
                if(newFortPiece.cellToChar == '~') {
                    newFortPiece.cellToChar = marker;
                    newFortPiece.fortPiece = true;
                    newFortPiece.grass = false;
                    fort.add(newFortPiece);
                    placed++;
                }
            }
        }
        forts.add(fort);
        return attempts < 100;
    }

    private void clearGrid() {
        for(int i=0; i<10; i++) {
            for(int j=0; j<10; j++) {
                grid[i][j] = new Cell(i, j);
            }
        }
        forts = new ArrayList<>();
    }

    private boolean validPosition(int row, int col) {
        return row < grid.length && row >= 0 && col < grid[0].length && col >= 0;
    }
}
