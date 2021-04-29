package org.cis120.battleship;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * You can use this file (and others) to test your
 * implementation.
 */

public class GameTest {

	// Tests the game state where the enemy has won
    @Test
    public void testOppWon() {
        
    	Battleship t = new Battleship();
    	
    	for (int i = 0; i < 6; i++) {
    		for (int j = 0; j < 5; j++) {
    			t.playTurn(i, j);
    		}
    	}
    	            	
        assertEquals(t.checkWinner(), 2);
    }
    
    // Tests the game state with playTurn() and checkWinner() functions
    @Test
    public void testGameInProgress() {
        
    	Battleship t = new Battleship();
    	
    	for (int i = 0; i < 3; i++) {
    		for (int j = 0; j < 2; j++) {
    			t.playTurn(i, j);
    		}
    	}
    	            	
        assertEquals(t.checkWinner(), 0);
    }
}
