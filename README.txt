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

  1. Collection: The 2D Array will store the state of the board

  2. Interface and sub-typing: Ships interface for the different types of ships

  3. Novel linked/recursive data structure: Array List for storing the location and length of the ships

  4. JUnit Testable Component: If a player misses, then the state of the board should not be 
  	 different. If a player’s ships have all been destroyed, it should be GAME OVER
  
=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

RunBattleship.java: Sets up the GUI holistically by putting all the JPanel pieces together and running the game


- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?


- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?



========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.
