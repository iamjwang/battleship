package org.cis120.battleship;

/**
 * Battleship
 */

/**
 * This class is an interface for Ships
 */
public interface Ship {
	
    /**
     * Getter for bowRow
     */
    public int getBowRow();
    
    /**
     * Getter for bowCol
     */
    public int getBowCol();
    
    // Setter for bowRow
    public void setBowRow(int newR);

    // Setter for bowCol
    public void setBowCol(int newC);
    
    /**
     * Getter for horizontality/orientation
     */
    public boolean getHoriz();
    
    // Setter for horizontality
    public void setHoriz(boolean horizontality);
    
    /**
     * Getter for hit int array
     */
    public int[] getHit();
    
    // Setter for hit int array
    public void setHit(int pos, int status);
    
    /**
     * Getter for shipType
     * 0: Fishing boat, normal and 3-long
     * 1: Oiltanker ship explodes when hit in a 1-square radius from all angles (3-long)
     * 2: Submarine, Once hit, the entire ship sinks immediately (3-long)
     * 3: MissedSquare, An empty square that has been fired at
     */
    public int getShipType();
    
    /**
     * checkSunkStatus checks whether the ship object has been sunk
     *
     * @return false if ship has not been sunk, true if ship has been sunk
     */
    public boolean checkSunkYet();
}
