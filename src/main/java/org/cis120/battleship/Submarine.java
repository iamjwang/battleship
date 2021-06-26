package org.cis120.battleship;

/**
 * Battleship
 */

/**
 * This class is an implementation of Ship. If it gets hit once, the whole thing
 * sinks.
 */
public class Submarine implements Ship {

    private boolean horiz; // Does the ship only occupy one row?
    private int bowCol;
    private int bowRow;
    private final int shipType = 2; // 2 if Submarine

    /**
     * Since we are considering index 0 to be the bow, and for the ships to only be
     * facing up or the left,
     * the other ship sections are in rows or cols (depending on orientation) that
     * are of a higher number.
     * Status:
     * 0 (hidden) then the square does contain a ship and has not been explored
     * 1 (miss) then the square does not contain a ship and has been explored
     * 2 (hit) then the square does contain a ship and has been explored
     * 
     * Note: If the whole Ship object is null, this indicates the square does NOT
     * contain the ship, and has not been explored
     */
    private int[] hit;
    // Note that all ships are of length 3

    // Constructor
    public Submarine(boolean horizontal, int bowCol, int bowRow, int[] hit) {
        if (bowRow > 10 || bowRow < 0 || bowCol > 10 || bowCol < 0) {
            throw new IllegalArgumentException("Invalid ship location");
        } else {
            this.bowRow = bowRow;
            this.bowCol = bowCol;
        }

        this.horiz = horizontal;
        this.hit = hit;
    }

    /**
     * Getter for bowRow
     */
    public int getBowRow() {
        return this.bowRow;
    }

    /**
     * Getter for bowCol
     */
    public int getBowCol() {
        return this.bowCol;
    }

    // Setter for bowRow
    public void setBowRow(int newR) {
        this.bowRow = newR;
    }

    // Setter for bowCol
    public void setBowCol(int newC) {
        this.bowCol = newC;
    }

    /**
     * Getter for horizontality/orientation
     */
    public boolean getHoriz() {
        return this.horiz;
    }

    // Setter for horizontality
    public void setHoriz(boolean horizontality) {
        this.horiz = horizontality;
    }

    /**
     * Getter for hit int array
     */
    public int[] getHit() {
        return this.hit;
    }

    /**
     * Setter for hit int array
     */
    public void setHit(int pos, int status) {
        this.hit[pos] = status;

        if (pos == 0) {
            this.hit[1] = 2;
            this.hit[2] = 2;
        } else if (pos == 1) {
            this.hit[0] = 2;
            this.hit[2] = 2;
        } else if (pos == 2) {
            this.hit[0] = 2;
            this.hit[1] = 2;
        }
    }

    /**
     * Getter for shipType
     * 0: Fishing boat, normal and 3-long
     * 1: Oiltanker, ship explodes when hit in a 1-square radius from all angles
     * (3-long)
     * 2: Submarine, Once hit, the entire ship sinks immediately (3-long)
     * 3: MissedSquare, An empty square that has been fired at
     */
    public int getShipType() {
        return this.shipType;
    }

    /**
     * checkSunkStatus checks whether the ship object has been sunk
     *
     * @return false if ship has not been sunk, true if ship has been sunk
     */
    public boolean checkSunkYet() {
        int sunkCtr = 3;

        for (int i = 0; i < 3; i++) {
            if (hit[i] == 2) {
                sunkCtr--;
            }
        }

        if (sunkCtr == 0) {
            return true;
        }

        return false;
    }
}
