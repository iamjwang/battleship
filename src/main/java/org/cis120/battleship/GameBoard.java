package org.cis120.battleship;

/**
 * Battleship Game, created by John Wang
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class instantiates a Battleship Board object, which is the model for the game.
 * As the user clicks the game board, the model is updated. Whenever the model
 * is updated, the game board repaints itself and updates its status JLabel to
 * reflect the current state of the model.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, GameBoard stores the model as a field
 * and acts as both the controller (with a MouseListener) and the view (with
 * its paintComponent method and the status JLabel).
 */
@SuppressWarnings("serial")
public class GameBoard extends JPanel {

    private Battleship bship; // model for the game
    private JLabel status; // current status text
    
    // Game constants
    public static final int BOARD_WIDTH = 300;
    public static final int BOARD_HEIGHT = 300;
        
    /**
     * Initializes the game board.
     */
    public GameBoard(JLabel statusInit) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        bship = new Battleship(); // initializes model for the game
        status = statusInit; // initializes the status JLabel

        /*
         * Listens for mouseclicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Point p = e.getPoint();
                
                // updates the model given the coordinates of the mouseclick
                bship.playTurn(p.x / 30, p.y / 30);
                
                updateStatus(); // updates the status JLabel
                repaint(); // repaints the game board
            }
        });
    }
    
    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        bship.reset();
        status.setText("Fire!");
        repaint();
        
        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }
    
    /**
     * Updates the JLabel to reflect the current state of the game.
     */
    private void updateStatus() {
        int x = bship.checkWinner();
    	
        if (x == 0) {
        	status.setText(bship.getNumTurns() + " turns left!");
        } else if (x == 1) {
        	status.setText("You won! :D");
        } else if (x == 2) {
        	status.setText("Aww... Enemy won :(");
        }
    }
    
    /**
     * Draws the game board.
     * 
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw vertical lines of board grid
        g.drawLine(30, 0, 30, 300);
        g.drawLine(60, 0, 60, 300);
        g.drawLine(90, 0, 90, 300);
        g.drawLine(120, 0, 120, 300);
        g.drawLine(150, 0, 150, 300);
        g.drawLine(180, 0, 180, 300);
        g.drawLine(210, 0, 210, 300);
        g.drawLine(240, 0, 240, 300);
        g.drawLine(270, 0, 270, 300);
        g.drawLine(300, 0, 300, 300);
        
        // Draw horizontal lines of board grid
        g.drawLine(0, 30, 300, 30);
        g.drawLine(0, 60, 300, 60);
        g.drawLine(0, 90, 300, 90);
        g.drawLine(0, 120, 300, 120);
        g.drawLine(0, 150, 300, 150);
        g.drawLine(0, 180, 300, 180);
        g.drawLine(0, 210, 300, 210);
        g.drawLine(0, 240, 300, 240);
        g.drawLine(0, 270, 300, 270);
        g.drawLine(0, 300, 300, 300);
        
        // Draws X's and O's
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (bship.getBoard()[i][j] != null) {
                	int state = bship.getCell(j, i);
                    if (state == 2) {
                        g.drawOval(30 * j + 13, 30 * i + 22, 3, 3);
                        g.drawLine(30 * j + 15, 30 * i + 5, 15 + 30 * j, 19 + 30 * i);
                    } else if (state == 1) {
                        g.drawLine(30 * j, 30 * i, 30 + 30 * j, 30 + 30 * i);
                        g.drawLine(30 * j, 30 + 30 * i, 30 + 30 * j, 30 * i);
                    }
                }
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}