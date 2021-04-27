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

    private Ship[][] boardLeft; // Hidden computer's board
    private Ship[][] boardRight; // Player 1's board
    private boolean gameOver;
    
    /**
     * Constructor sets up game state.
     */
    public Battleship() {
        reset();
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
    public boolean playTurn(int c, int r, Ship[][] inputBoard) {
        if (gameOver) {
            return false;
        }
        
        if (inputBoard[r][c] == null) {
        	Ship empty = new EmptyShip(true, c, r, [1], 1);
        	return true;
        }
        
        int bRow = inputBoard[r][c].getBowRow();
    	int bCol = inputBoard[r][c].getBowCol();
    	int hitPos = -1; // Dummy initializer
    	
    	if (bRow == r && bCol == c) {
    		hitPos = 0;
    	} else if (bRow > r && bCol > c) {
    		System.out.println("Ship cannot be diagonal");
    		return false; // Ship cannot be diagonal
    	} else if (bRow < r && bCol < c) {
    		System.out.println("Ship can only face up or left with bow at front");
    		return false;
    	} else if (bRow > r) {
    		if (r - bRow > 3) {
    			System.out.println("Ship too long");
        		return false;
    		}
    		
    		hitPos = r - bRow;
    		
    	} else if (bCol > c) {
    		if (c - bCol > 3) {
    			System.out.println("Ship too long");
        		return false;
    		}
    		
    		hitPos = c - bCol;
    	}
    	
    	// Initialize booleans for whether there is space on each side of the ship
    	boolean oppN = false;
    	boolean oppE = false;
    	boolean oppS = false;
    	boolean oppW = false;
    	
    	switch((inputBoard[r][c].getHit())[hitPos]) {
    		case 0: inputBoard[r][c].setHit(hitPos, 2);
    				if (inputBoard[r][c].getShipType() == 0) {
    					checkWinner();
    					return true;
    				} else if (inputBoard[r][c].getShipType() == 1) {
    					// UP TO HERE!!!!!!!! Essentially you want to check the squares in a 1m radius around the ship and recursively run playTurn on all of them!
    					if (inputBoard[r][c].getHoriz()) {
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
    						
    					} else {
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
    							playTurn(c, r - 1, inputBoard);
    						}
    						
    						if (oppS) {
    							playTurn(c, r + 3, inputBoard);
    						}
    						
    						if (oppE) {
    							playTurn(c + 1, r, inputBoard);
    							playTurn(c + 1, r + 1, inputBoard);
    							playTurn(c + 1, r + 2, inputBoard);
    						}
    						
    						if (oppW) {
    							playTurn(c - 1, r, inputBoard);
    							playTurn(c - 1, r + 1, inputBoard);
    							playTurn(c - 1, r + 2, inputBoard);
    						}
    						
    						// Play the four corners too
    						if (oppN && oppE) {
    							playTurn(c + 1, r - 1, inputBoard);
    						}
    						
    						if (oppN && oppW) {
    							playTurn(c - 1, r - 1, inputBoard);
    						}
    						
    						if (oppS && oppE) {
    							playTurn(c + 1, r + 3, inputBoard);
    						}
    						
    						if (oppS && oppW) {
    							playTurn(c - 1, r + 3, inputBoard);
    						}
    					}    					
    					
    					
    					for (int m = 0; m < 3; m++) {
    						for (int n = 0; n < 3) {
    							playTurn();
    						}
    						
    					}
    					return true;
    				}
    		
    		case 1: return false;
    		
    		case 2: return false;
    	}
    	
        return true;
    }

    /**
     * checkWinner checks whether the game has reached a win condition.
     * checkWinner only looks for horizontal wins.
     *
     * @return 0 if nobody has won yet, 1 if player 1 has won, and 2 if player 2
     *         has won, 3 if tied
     */
    public int checkWinner() {
        int shipsAfloatOpp = 15;
        int shipsAfloatPlayer = 15;
    	
    	// Check horizontal, vertical and diagonal hits
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
            	if (boardLeft[i][j].checkSunkYet()) {
            		shipsAfloatOpp--;
            	} else if (boardRight[i][j].checkSunkYet()) {
            		shipsAfloatPlayer--;
            	}
            }        	
        }
        
        if (shipsAfloatOpp == 0 && shipsAfloatPlayer == 0) {
        	gameOver = true;
        	return 3;
        }
        else if (shipsAfloatOpp == 0) {
        	gameOver = true;
        	return 1;
        } else if (shipsAfloatPlayer == 0) {
        	gameOver = true;
        	return 2;
        }
        
        // Continue game if no-one has won yet
        gameOver = false;
        return 0;
    }

    /**
     * printGameState prints the current game state
     * for debugging.
     */
    public void printGameState() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j]);
                if (j < 9) {
                    System.out.print(" | ");
                }
            }
            if (i < 9) {
                System.out.println("\n-------------------------------------");
            }
        }
    }
    
    // Randomly position all of the opponent's ships upon setup
    public void positionOppAllShips() {
    	int fishBoatCounter = 3;
    	int oilCounter = 1;
    	int subCounter = 1;
    	
    	while (fishBoatCounter + oilCounter + subCounter > 0) {
    		int x = Math.random();
    		
    		if (x < 0.5 && fishBoatCounter > 0) {
    			fishBoatCounter--;
    			positionOppShip(0, boardLeft, false);
    		} else if (x >= 0.5 && x < 0.75 && oilCounter > 0) {
    			oilCounter--;
    			positionOppShip(1, boardLeft, false);
    		} else if (x >= 0.5 && x < 0.75 && subCounter > 0) {
    			subCounter--;
    			positionOppShip(2, boardLeft, false);
    		}
    	}
    }
    
 // Randomly position all of the opponent's ships upon setup
    public void positionPlayerAllShips() {
    	int fishBoatCounter = 3;
    	int oilCounter = 1;
    	int subCounter = 1;
    	
    	while (fishBoatCounter + oilCounter + subCounter > 0) {
    		int x = Math.random();
    		
    		if (x < 0.5 && fishBoatCounter > 0) {
    			fishBoatCounter--;
    			positionShip(0, boardRight, true);
    		} else if (x >= 0.5 && x < 0.75 && oilCounter > 0) {
    			oilCounter--;
    			positionShip(1, boardRight, true);
    		} else if (x >= 0.5 && x < 0.75 && subCounter > 0) {
    			subCounter--;
    			positionShip(2, boardRight, true);
    		}
    	}
    }
    
    // Randomly position one of the opponent's ships 
    public void positionShip(int shipType, Ship[][] inputBoard, boolean player) {
    	boolean notYetPlaced = true;
    	
    	while (notYetPlaced) {
    		int bowRow = (int) Math.random() * 8;
        	int bowCol = (int) Math.random() * 8;
        	    	
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
        	
        	if (inputBoard[bowRow][bowCol] != null || inputBoard[deckRow][deckCol] != null || inputBoard[sternRow][sternCol] != null) {
        		continue;
        	} 
        	
        	int[] oppHit = {0, 0, 0};
        	int[] playerHit = {3, 3, 3};
        	Ship placer = null;
        	
        	if (shipType == 0 && player) {
    			placer = new FishingBoat(paramHoriz, bowCol, bowRow, playerHit, shipType);
    		} else if (shipType == 0 && !player) {
    			placer = new FishingBoat(paramHoriz, bowCol, bowRow, oppHit, shipType);
    		} else if (shipType == 1 && player) {
    			placer = new OilTanker(paramHoriz, bowCol, bowRow, playerHit, shipType);
    		} else if (shipType == 1 && !player) {
    			placer = new OilTanker(paramHoriz, bowCol, bowRow, oppHit, shipType);
    		} else if (shipType == 2 && player) {
    			placer = new Submarine(paramHoriz, bowCol, bowRow, playerHit, shipType);
    		} else if (shipType == 2 && !player) {
    			placer = new Submarine(paramHoriz, bowCol, bowRow, oppHit, shipType);
    		}
        	
        	inputBoard[bowRow][bowCol] = placer;
			inputBoard[deckRow][deckCol] = placer;
			inputBoard[sternRow][sternCol] = placer;
    	}
    }
    
    /**
     * reset (re-)sets the game state to start a new game.
     */
    public void reset() {
        boardLeft = new int[10][10];
        boardRight = new int[10][10];
        positionOppAllShips();
        positionPlayerAllShips();
        gameOver = false;
    }

    /**
     * getCell is a getter for the contents of the cell specified by the method
     * arguments.
     *
     * @param c column to retrieve
     * @param r row to retrieve
     * @return an integer denoting the contents of the corresponding cell on the
     *         game board. 0 = empty, 1 = Player 1, 2 = Player 2
     */
    public int getCell(int c, int r) {
        return board[r][c];
    }
    
    
    public int shipsSunkUpdater(int[] board) {
    	for (int i = 0; i < 9; i++) {
        	for (int j = 0; j < 9; j++) {
        		/** 
        		 * When you iterate onto a ship on boardLeft or boardRight,
        		 * check its status to see if it has been sunk. If 
        		 */
        		boardLeft
        		
        	}
        }
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

        t.playTurn(1, 1);
        t.printGameState();

        t.playTurn(0, 0);
        t.printGameState();

        t.playTurn(0, 2);
        t.printGameState();

        t.playTurn(2, 0);
        t.printGameState();

        t.playTurn(1, 0);
        t.printGameState();

        t.playTurn(1, 2);
        t.printGameState();

        t.playTurn(0, 1);
        t.printGameState();

        t.playTurn(2, 2);
        t.printGameState();

        t.playTurn(2, 1);
        t.printGameState();
        System.out.println();
        System.out.println();
        System.out.println("Winner is: " + t.checkWinner());
    }
}
