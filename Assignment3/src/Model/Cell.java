package Model;

/**
 * Serves as a cell for the grid that can take the form of all the
 * possible states a cell might have during gameplay and whether it
 * has been discovered.
 */

public class Cell {
    public char cellToChar;
    public boolean found;
    public boolean fortPiece;
    public boolean hit;
    public boolean grass;
    public int row;
    public int col;

    public Cell(int row, int col) {
        hit = false;
        cellToChar = '~';
        found = false;
        fortPiece = false;
        grass = true;
        this.row = row;
        this.col = col;
    }

    public boolean isFort() {
        return fortPiece;
    }

    public boolean isGrass() {
        return grass;
    }

    public boolean isFound() {
        return found;
    }

    public boolean isHit() {
        return hit;
    }

    public void hit() {
        found = true;
        hit = true;
    }
}
