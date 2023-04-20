package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.Block;
import model.Board;
import model.MovableTetrisPiece;

/**
 * This class represents a JPanel that displays a "Board Area" label and has a red
 * background color.
 * The panel is positioned at a specific location on the parent container.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 * */
public class BoardPanel extends JPanel implements PropertyChangeListener {

    /** Default serial version UID. */
    @Serial
    private static final long serialVersionUID = -1013748546840061936L;

    /** Board width. */
    private static final int PANEL_WIDTH = 250;

    /** Board height. */
    private static final int PANEL_HEIGHT = 500;

    /** Default dimension for the panel. */
    private static final Dimension SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

    /** The width of the board in blocks. */
    private static final int BOARD_WIDTH = 10;

    /** The height of the board in blocks. */
    private static final int BOARD_HEIGHT = 20;

    /** The size of a block in pixels. */
    private static final int BLOCK_SIZE = 25;

    /** The current shape. */
    private MovableTetrisPiece myCurrShape;

    /** The board data array. */
    private List<Block[]> myBoardData;

    /** Game over boolean. */
    private boolean myGameOver;

    /** PropertyChangeSupport to manage various property changes. */
    private final PropertyChangeSupport myPCS;

    /** JLabel that shows the game over message. */
    private final JLabel myGameOverMessage = new JLabel("<html>Game<br> Over</html>"
            , SwingConstants.CENTER);

    /**
     * Constructor for the TetrisBoard class that sets up the panel with a red
     * background color,
     * a specific size and position, and adds a "Board Area" label to it.
     */
    public BoardPanel() {
        super();
        initializePanel();


        myGameOver = false;

        myPCS = new PropertyChangeSupport(this);

    }

    /**
     * Sets Up various elements of the Board GUI.
     */
    public void initializePanel() {
        final int fontSize = 25;
        final int blockWidth = 10;
        final int blockHeight = 4;
        this.setBackground(Color.ORANGE);
        setPreferredSize(SIZE);

        this.add(myGameOverMessage);
        myGameOverMessage.setVisible(false);

        myGameOverMessage.setFont(new Font(Font.SANS_SERIF, Font.BOLD, fontSize));
        myGameOverMessage.setOpaque(true);
        myGameOverMessage.setBackground(Color.LIGHT_GRAY);
        myGameOverMessage.setPreferredSize(new Dimension(blockWidth * BLOCK_SIZE,
                blockHeight * BLOCK_SIZE));

    }
    /**
     * Draws the Tetris board with gridlines and blocks.
     * @param theGraphics the Graphics object to draw on.
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;
        final int rowNum = 24;
        final double stroke = 0.25;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        if (myCurrShape != null) {
            final model.Point loc = myCurrShape.getPosition();

            final int[][] shape = myCurrShape.getTetrisPiece().getPointsByRotation(
                    myCurrShape.getRotation());


            for (final int[] square : shape) {
                final int x = square[0];
                final int y = square[1];

                //Draw the actual Piece
                g2d.setColor(Color.magenta);
                g2d.fill3DRect((x + loc.x()) * BLOCK_SIZE,
                        (BOARD_HEIGHT - (y + loc.y()) - 1) * BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE, true);

                //Draw the black outline
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke((float) 1));
                //Leave parenthesis around x + loc.x(),
                // even if there is a warning saying they are not needed.
                g2d.drawRect((x + loc.x()) * BLOCK_SIZE,
                        (BOARD_HEIGHT - (y + loc.y()) - 1) * BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE);
            }


        }

        g2d.setColor(Color.BLUE);
        if (myBoardData != null) {
            int row1 = 0;
            for (; row1 < rowNum; row1++) {
                final Block[] bArr = myBoardData.get(row1);
                int col = 0;
                for (final Block block : bArr) {
                    if (block != null) {
                        g2d.fillRect(col * BLOCK_SIZE, (BOARD_HEIGHT - row1 - 1) * BLOCK_SIZE,
                                BLOCK_SIZE, BLOCK_SIZE);
                    }
                    col++;
                }
            }
        }

        // Draw the gridlines in white
        g2d.setStroke(new BasicStroke((float) stroke));
        g2d.setColor(Color.WHITE);
        for (int row = 0; row < BOARD_HEIGHT; row++) {
            g2d.drawLine(0, BLOCK_SIZE * row, BLOCK_SIZE * BOARD_WIDTH,
                    BLOCK_SIZE * row);
        }
        for (int col = 0; col < BOARD_WIDTH; col++) {
            g2d.drawLine(col * BLOCK_SIZE, 0, col * BLOCK_SIZE,
                    BLOCK_SIZE * BOARD_HEIGHT);
        }


    }

    /**
     * Helper method that displays a label when the game is over.
     */
    private void displayMessage() {
        if (myGameOver) {
            myGameOverMessage.setVisible(true);
            this.revalidate();
        } else {
            myGameOverMessage.setVisible(false);
            this.revalidate();
        }
    }

    /**
     * Adds PropertyChangeListener to this object.
     * @param theListener  the PropertyChangeListener to be added
     *
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * Handles Property Change Events.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (Board.PROPERTY_CHANGE_CURR.equals(theEvent.getPropertyName())) {
            myCurrShape = (MovableTetrisPiece) theEvent.getNewValue();
        } else if (Board.PROPERTY_CHANGE_BOARD.equals(theEvent.getPropertyName())) {
            myBoardData = (List<Block[]>) theEvent.getNewValue();
        } else if (Board.PROPERTY_CHANGE_GAME.equals(theEvent.getPropertyName())) {
            myGameOver = (boolean) theEvent.getNewValue();
            displayMessage();
        } else if (MenuBar.PROPERTY_CHANGE_BACK_COLOR.equals(theEvent.getPropertyName())) {
            final Color color = (Color) theEvent.getNewValue();
            this.setBackground(color);
        }

        revalidate();
        repaint();
    }
}
