=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: _______
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. Incorporate the feedback you got after
  submitting your proposal.

  1. Collection: The 2D Array will store the state of the board using Ship objects

  2. Interface and sub-typing: Ships interface for the different types of ships.
  								- FishingBoat: standard ship, length 3
  								- OilTanker: Explodes all within a 1-square radius when sunk
  								- Submarine: Entire ship sinks once hit
  								- MissedSquare: filler ship for missed shots, length 1

  3. Recursion: Used in Battleship playTurn() function to explode select squares. After deep thought and 
  				going to OHQ, I concluded that the for-loop implementation would both be hard and inefficient,
  				potentially making my code 12x longer and repetitive.

  4. JUnit Testable Component: If a player misses, then the game should continue. If the player runs out of turns (30), it should be GAME OVER.
  
=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

	1. RunBattleship.java: Sets up the GUI holistically by putting all the JPanel pieces together and running the game
	2. Battleship.java: - Stores the state of the board in a 2D array of Ship objects.
						- Stores numTurns field to keep track of game progress
						- Has playTurn() function which calls the appropriate functions of each of the Ships in the squares
						  and adjusts the board state.
						- Has functions which position the ships on the board randomly
						- A bunch of getters
						- A reset() function which wipes the board and resets numTurns
	3. Ship.java: Ship interface which contains getters, and setters for the private fields which include
				  the bow row, column, shipType, orientation (horiz), and checks if the ship has been sunk yet.
	4. FishingBoat.java: standard ship, length 3, is sunk when all 3 squares are hit. Implements Ship interface functionality
	5. OilTanker.java: Explodes all within a 1-square radius when sunk, length 3. Implements Ship interface functionality
	6. Submarine.java: Entire ship sinks once hit. Implements Ship interface functionality
	7. MissedSquare.java: filler ship for missed shots, length 1

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?
Just initially when brainstorming the overall architecture. Had to pivot to 1-player to properly implement some of my 4 concepts.

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?
Yes. If I had to refactor things though, I would probably shift some of the calculation/state-manipulation from Battleship.java to the individual Ship objects.


========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
Swing Library