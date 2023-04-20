import controller.TetrisGame;

/**
 * Application contains the main method that is used to run the game of Tetris.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public final class Application {
    private Application() { }

    /**
     * Main method that calls the Tetris Game to run.
     * @param theArgs Arguments for the main method (not used)
     */
    public static void main(final String[] theArgs) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            final TetrisGame game = new TetrisGame();
            game.runTetris();
        });
    }
}
