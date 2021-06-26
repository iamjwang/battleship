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

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                t.playTurn(i, j);
            }
        }

        assertEquals(t.checkWinner(), 2);
    }
    
    // Tests the game state where the enemy has won
    @Test
    public void testPlayerWon() {

        Battleship t = new Battleship();

        int ctr = 0;
        
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (t.getCell(j, i) == 0) {
                    t.playTurn(j, i);
                    ctr++;
                    System.out.println("" + ctr);
                }
            }
        }
        
        assertEquals(t.checkWinner(), 1);
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
    
    // Tests whether a missed shot turns into a MissedSquare ship
    @Test
    public void testMissedSquare() {

        Battleship t = new Battleship();
        
        // Dummy target row and col initializer
        int r = -1;
        int c = -1;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (t.getCell(j, i) == 3) {
                    t.playTurn(j, i);
                    r = i; 
                    c = j;
                    break;
                }
            }
        }
        
        assertEquals(t.getCell(c, r), 1);
        assertEquals((t.getBoard())[r][c].getShipType(), 3);
    }
    
    // Tests whether untouched squares not containing ships are indeed null
    @Test
    public void testNullEmptySquare() {

        Battleship t = new Battleship();
        
        // Dummy target row and col initializer
        int r = -1;
        int c = -1;
        
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (t.getCell(j, i) == 3) {
                    r = i; 
                    c = j;
                    break;
                }
            }
        }
        
        assertNull((t.getBoard())[r][c]);
    }
}
