package org.cis120.battleship;

/**
 * Battleship
 */

/**
 * This class is a model for Battleship.
 * 
 * This game adheres to a Model-View-Controller design framework.
 * This framework is very effective for turn-based games. We
 * STRONGLY recommend you review these lecture slides, starting at
 * slide 8, for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec36.pdf
 * 
 * This model is completely independent of the view and controller.
 * This is in keeping with the concept of modularity! We can play
 * the whole game from start to finish without ever drawing anything
 * on a screen or instantiating a Java Swing object.
 * 
 * Run this file to see the main method play a game of Battleship,
 * visualized with Strings printed to the console.
 */
public class Battleship {

    private Ship[][] board; // Opponent's board 
    private int numTurns = 29;
    private boolean gameOver;
    
    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
    }
    
    /**
     * Getter for number of turns
     * 
     * @return int for the number of Turns left
     */
    public int getNumTurns() {
    	return this.numTurns;
    }
    
    /**
     * Getter for board
     * 
     * @return int 2D array for board
     */
    public Ship[][] getBoard() {
    	return this.board;
    }
    
    /**
     * playTurn allows players to play a turn. Returns true if the move is
     * successful and false if a player tries to play in a location that is
     * taken or after the game has ended. If the turn is successful and the game
     * has not ended, the player is changed. If the turn is unsuccessful or the
     * game has ended, the player is not changed.
     *
     * @param c column to play in
     * @param r row to play in
     * @return whether the turn was successful
     */
    public boolean playTurn(int c, int r) {    	
    	if (gameOver) {
            return false;
        }
        
        if (board[r][c] == null) {
        	Ship empty = new MissedSquare(c, r);
        	numTurns--;
        	board[r][c] = empty;
        	return true;
        }
        
        int firePos = getHitPos(c, r);
    	
        int bRow = board[r][c].getBowRow();
    	int bCol = board[r][c].getBowCol();
        
    	// Initialize booleans for whether there is space on each side of the ship
    	boolean oppN = false;
    	boolean oppE = false;
    	boolean oppS = false;
    	boolean oppW = false;
    	
    	int explosionCtr = 0; // Will be added to boost numTurns if player successfully sinks oilTanker
    	
    	switch((board[r][c].getHit())[firePos]) {
    		case 0: board[r][c].setHit(firePos, 2);    				
    				if (board[r][c].getShipType() == 1) {
    					// If the OilTanker has not been sunk yet, don't explode
    					if (board[r][c].checkSunkYet()) {
    						// If the ship is an OilTanker, explode everything around it within a 1-square radius
        					if (board[r][c].getHoriz()) {
        						// Check the sides for horizontal ship
        						if (bCol >= 1 && bCol <= 6) {
        							oppE = true;
        							oppW = true;
        						} else if (bCol < 1) {
        							oppE = true;
        						} else if (bCol > 6) {
        							oppW = true;
        						}
        						
        						if (bRow >= 1 && bRow <= 8) {
        							oppN = true;
        							oppS = true;
        						} else if (bRow < 1) {
        							oppS = true;
        						} else if (bRow > 8) {
        							oppN = true;
        						}
        						
        						// Play the sides if valid
        						if (oppE) {
        							explosionCtr++;
        							playTurn(bCol + 3, bRow);
        						}
        						
        						if (oppW) {
        							explosionCtr++;
        							playTurn(bCol - 1, bRow);
        						}
        						
        						if (oppS) {
        							explosionCtr++;
        							explosionCtr++;
        							explosionCtr++;
        							playTurn(bCol, bRow + 1);
        							playTurn(bCol + 1, bRow + 1);
        							playTurn(bCol + 2, bRow + 1);
        						}
        						
        						if (oppN) {
        							explosionCtr++;
        							explosionCtr++;
        							explosionCtr++;
        							playTurn(bCol, bRow - 1);
        							playTurn(bCol + 1, bRow - 1);
        							playTurn(bCol + 2, bRow - 1);
        						}
        						
        						// Play the four corners too
        						if (oppN && oppE) {
        							explosionCtr++;
        							playTurn(bCol + 3, bRow - 1);
        						}
        						
        						if (oppN && oppW) {
        							explosionCtr++;
        							playTurn(bCol - 1, bRow - 1);
        						}
        						
        						if (oppS && oppE) {
        							explosionCtr++;
        							playTurn(bCol + 3, bRow + 1);
        						}
        						
        						if (oppS && oppW) {
        							explosionCtr++;
        							playTurn(bCol - 1, bRow + 1);
        						}
        						
        						// Added to boost numTurns as player successfully sunk oilTanker
        						numTurns += explosionCtr + 2;
        					} else {
        						// Check the sides for vertical ship
        						if (bCol >= 1 && bCol <= 8) {
        							oppE = true;
        							oppW = true;
        						} else if (bCol < 1) {
        							oppE = true;
        						} else if (bCol > 8) {
        							oppW = true;
        						}
        						
        						if (bRow >= 1 && bRow <= 6) {
        							oppN = true;
        							oppS = true;
        						} else if (bRow < 1) {
        							oppS = true;
        						} else if (bRow > 6) {
        							oppN = true;
        						}
        						
        						// Play the sides if valid
        						if (oppN) {
        							explosionCtr++;
        							playTurn(bCol, bRow - 1);
        						}
        						
        						if (oppS) {
        							explosionCtr++;
        							playTurn(bCol, bRow + 3);
        						}
        						
        						if (oppE) {
        							explosionCtr++;
        							explosionCtr++;
        							explosionCtr++;
        							playTurn(bCol + 1, bRow);
        							playTurn(bCol + 1, bRow + 1);
        							playTurn(bCol + 1, bRow + 2);
        						}
        						
        						if (oppW) {
        							explosionCtr++;
        							explosionCtr++;
        							explosionCtr++;
        							playTurn(bCol - 1, bRow);
        							playTurn(bCol - 1, bRow + 1);
        							playTurn(bCol - 1, bRow + 2);
        						}
        						
        						// Play the four corners too
        						if (oppN && oppE) {
        							explosionCtr++;
        							playTurn(bCol + 1, bRow - 1);
        						}
        						
        						if (oppN && oppW) {
        							explosionCtr++;
        							playTurn(bCol - 1, bRow - 1);
        						}
        						
        						if (oppS && oppE) {
        							explosionCtr++;
        							playTurn(bCol + 1, bRow + 3);
        						}
        						
        						if (oppS && oppW) {
        							explosionCtr++;
        							playTurn(bCol - 1, bRow + 3);
        						}
        						
        						// Added to boost numTurns as player successfully sunk oilTanker
        						numTurns += explosionCtr + 2;
        					}
    					}    					
    				} else if (board[r][c].getShipType() == 2) {
    					if (firePos == 0) {
    						board[r][c].setHit(1, 2);
    						board[r][c].setHit(2, 2);
    					} else if (firePos == 1) {
    						board[r][c].setHit(0, 2);
    						board[r][c].setHit(2, 2);
    					} else if (firePos == 2) {
    						board[r][c].setHit(0, 2);
    						board[r][c].setHit(1, 2);
    					}				
    				}
    				
    				numTurns--;
					checkWinner();
					return true;
    		
    		case 1: return false;
    		
    		case 2: return false;
    	}
    	
    	checkWinner();
    	return true;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player has won, and 2 if opp
     *         has won
     * 
     * NOTE: Game cannot be tied
     */
    public int checkWinner() {
        int shipsAfloatOpp = 15;
    	
    	// Check horizontal, vertical and diagonal hits
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            	if (board[i][j] == null) {
            		continue;
            	}
            	
            	if (board[i][j].checkSunkYet()) {
            		shipsAfloatOpp--;
            	}
            }        	
        }
        
        if (numTurns < 1) {
        	gameOver = true;
        	return 2;
        } else if (shipsAfloatOpp == 0) {
        	gameOver = true;
        	return 1;
        }
        
        // Continue game if no-one has won yet
        gameOver = false;
        return 0;
    }

    /** 
     * Gets the ship and position of specific square, and returns the position in relation to the ship
     * 0: Bow
     * 1: Deck
     * 2: Stern
     * 
     * Note: Returns -1 if ship is of invalid length/direction
     * 
     * Ship can only face up or left, with the bow at the front of the ship.
     * @param c
     * @param r
     * @return hitPos (an int)
     */
    public int getHitPos(int c, int r) {   	
    	if (board[r][c] == null) {
    		return 0;
    	}
    	
    	int bRow = board[r][c].getBowRow();
    	int bCol = board[r][c].getBowCol();
    	int hitPos = -1; // Dummy initializer
    	
    	// Compare/check Ship location to inputted row and column
    	if (bRow == r && bCol == c) {
    		hitPos = 0;
    	} else if (bRow > r && bCol > c) {
    		System.out.println("Ship cannot be diagonal");
    		return -1; // Ship cannot be diagonal
    	} else if (bRow > r || bCol > c) {
    		System.out.println("Ship can only face up or left with bow at front");
    		return -1;
    	} else if (bRow < r) {
    		if (r - bRow > 3) {
    			System.out.println("Ship too long");
        		return -1;
    		}
    		
    		hitPos = r - bRow;
    	} else if (bCol < c) {
    		if (c - bCol > 3) {
    			System.out.println("Ship too long");
        		return -1;
    		}
    		
    		hitPos = c - bCol;
    	}
    	    	
    	return hitPos;
    }
    
    /**
     * Since we are considering index 0 to be the bow, and for the ships to only be facing up or the left, 
	 * the other ship sections are in rows or cols (depending on orientation) that are of a higher number.
	 * Status:
	 * 0 (hidden) then the square does contain a ship and has not been explored
	 * 1 (miss) then the square does not contain a ship and has been explored
	 * 2 (hit) then the square does contain a ship and has been explored
	 * 3 (null) then the square does not contain a ship and has not been explored
	 * 
	 * Note: If the whole Ship object is null, this indicates the square does NOT
	 * contain the ship, and has not been explored
     * 
     * @param col
     * @param row
     * @return
     */
    public int getCell(int c, int r) {
    	if (c > 9 || r > 9 || c < 0 || r < 0) {
    		throw new IllegalArgumentException("Invalid grid co-ords");
    	}
    	
    	if (board[r][c] == null) {
    		return 3;
    	}
    	
    	int hitPos = getHitPos(c, r);
    	
    	return (board[r][c].getHit())[hitPos];
    }
    
    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {	
    	System.out.println("\n\n" + numTurns + " Turns Left:\n");
    	
    	for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            	String symbol = "";
            	
            	if (board[i][j] == null) {
            		symbol = "~";
            	} else {
            		int hitStatus = getCell(j, i);
            		
            		if (hitStatus == 0) {
                		symbol = "~";
                	} else if (hitStatus == 1) {
                		symbol = "X";
                	} else if (hitStatus == 2) {
                		symbol = "!";
                	}
            	}
           	
            	System.out.print(symbol);
                if (j < 9) {
                    System.out.print(" | ");
                }                
            }
            if (i < 9) {
                System.out.println("\n-------------------------------------");
            } else if (i == 9) {
            	System.out.println("\n=====================================");
            }
        }
    }
    
    // Randomly position all of the opponent's ships upon setup
    public void positionAllShips() {
    	int fishBoatCounter = 3;
    	int oilCounter = 1;
    	int subCounter = 1;
    	
    	while (fishBoatCounter + oilCounter + subCounter > 0) {
    		double x = Math.random();
    		
    		if (x < 0.5 && fishBoatCounter > 0) {
    			fishBoatCounter--;
    			positionShip(0, board);
    		} else if (x >= 0.5 && x < 0.75 && oilCounter > 0) {
    			oilCounter--;
    			positionShip(1, board);
    		} else if (x >= 0.75 && x < 1 && subCounter > 0) {
    			subCounter--;
    			positionShip(2, board);
    		}
    	}
    }
    
    // Randomly position one of the opponent's ships 
    private void positionShip(int shipType, Ship[][] board) {
    	boolean notYetPlaced = true;
    	
    	while (notYetPlaced) {
    		int bowRow = (int) (Math.random() * 8);
        	int bowCol = (int) (Math.random() * 8);
        	    	
        	boolean paramHoriz = false;
        	
        	int deckRow = -1;
        	int deckCol = -1;
        	int sternRow = -1;
        	int sternCol = -1;
        	
        	if (Math.random() >= 0.5) {
            	paramHoriz = true;
            	deckRow = bowRow;
            	deckCol = bowCol + 1;
            	sternRow = bowRow;
            	sternCol = bowCol + 2;
            } else {
            	deckCol = bowCol;
            	deckRow = bowRow + 1;
            	sternCol = bowCol;
            	sternRow = bowRow + 2;
            }
        	
        	if (board[bowRow][bowCol] != null || board[deckRow][deckCol] != null || board[sternRow][sternCol] != null) {
        		continue;
        	} 
        	
        	int[] oppHit = {0, 0, 0};
        	Ship placer = null;
        	
        	if (shipType == 0) {
    			placer = new FishingBoat(paramHoriz, bowCol, bowRow, oppHit);
    		} else if (shipType == 1) {
    			placer = new OilTanker(paramHoriz, bowCol, bowRow, oppHit);
    		} else if (shipType == 2) {
    			placer = new Submarine(paramHoriz, bowCol, bowRow, oppHit);
    		}
        	
        	notYetPlaced = false;
        	
        	board[bowRow][bowCol] = placer;
			board[deckRow][deckCol] = placer;
			board[sternRow][sternCol] = placer;
    	}
    }
    
    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        board = new Ship[10][10];
        board = new Ship[10][10];
        positionAllShips();
        numTurns = 30;
        gameOver = false;
    }
    
    /**
     * This main method illustrates how the model is completely independent of
     * the view and controller. We can play the game from start to finish
     * without ever creating a Java Swing object.
     *
     * This is modularity in action, and modularity is the bedrock of the
     * Model-View-Controller design framework.
     *
     * Run this file to see the output of this method in your console.
     */
    public static void main(String[] args) {
        Battleship t = new Battleship();

        t.playTurn(1, 2);
        t.printGameState();
        
        System.out.println();
        System.out.println();
        
        int x = t.checkWinner();
    	
        if (x == 0) {
        	System.out.println("Keep going! " + t.getNumTurns() + " turns left!");
        } else if (x == 1) {
        	System.out.println("You won! :D");
        } else if (x == 2) {
        	System.out.println("Aww... Robot won :(");
        }
    }
}
