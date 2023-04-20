
package controller;


import view.TetrisFrame;

/**
 * Tetris Game class holds a reference to the JFrame that will contain the game of Tetris.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 *
 */

public class TetrisGame {

    /**
     * The JFrame containing the GUI.
     */
    private final TetrisFrame myFrame;


    public TetrisGame(final TetrisFrame theFrame) {
        myFrame = theFrame;
    }

    public TetrisGame() {
        this(new TetrisFrame());
    }

    /**
     * Runs the game of Tetris.
     */
    public void runTetris() {
        myFrame.createAndShowGUI();
    }

}
