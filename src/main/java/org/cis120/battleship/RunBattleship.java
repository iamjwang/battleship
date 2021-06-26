package org.cis120.battleship;

/**
 * Battleship
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 * 
 * This game adheres to a Model-View-Controller design framework. This
 * framework is very effective for turn-based games. We STRONGLY
 * recommend you review these lecture slides, starting at slide 8,
 * for more details on Model-View-Controller:
 * https://www.seas.upenn.edu/~cis120/current/files/slides/lec37.pdf
 * 
 * In a Model-View-Controller framework, Game initializes the view,
 * implements a bit of controller functionality through the reset
 * button, and then instantiates a GameBoard. The GameBoard will
 * handle the rest of the game's view and controller functionality, and
 * it will instantiate a Battleship object to serve as the game's model.
 */
public class RunBattleship implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("Battleship");
        frame.setLocation(300, 300);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel status = new JLabel("Setting up...");
        status_panel.add(status);

        // Game board
        final GameBoard board = new GameBoard(status);
        frame.add(board, BorderLayout.CENTER);

        // Reset button
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Instruction pop-up window
        final JOptionPane start_pane = new JOptionPane();
        start_pane.showMessageDialog(
                status_panel, "~ Single Player Battleship ~\nYou have 30 turns to "
                        + "guess and fire at a randomly-generated grid of 10x10 enemy ships"
                        + " by clicking on the squares!\n"
                        + "There are 3 types of ships, all with length THREE:\n"
                        + "1) Fishing boat: These guys are really good at break-dancing but "
                        + "not really anything useful for naval warfare.\n"
                        + "2) Oiltanker: Explodes everything in a 1-square radius up when sunk\n"
                        + "3) Submarine: Sinks when any part of the boat is hit\n"
                        + "\nIf you run out of turns before sinking all ships, the enemy wins."
                        + "\nThe reset button resets the game entirely."
        );

        // Note here that when we add an action listener to the reset button, we
        // define it as an anonymous inner class that is an instance of
        // ActionListener with its actionPerformed() method overridden. When the
        // button is pressed, actionPerformed() will be called.
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        // Put the frame on the screen
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}