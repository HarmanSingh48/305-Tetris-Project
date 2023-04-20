package view;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.Serial;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Board;
import model.Rotation;
import model.TetrisPiece;

/**
 *
 * This class represents the panel that displays the next Tetris piece to the user.
 * It extends the {@link javax.swing.JPanel} class.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public class NextPiecePanel extends JPanel implements PropertyChangeListener {

    /** Default serial version UID. */
    @Serial
    private static final long serialVersionUID = -8030781040487372151L;

    /** Block Size. */
    private static final int BLOCK_SIZE = 20;

    /** Next Piece width. */
    private static final int PANEL_WIDTH = 150;

    /** Next Piece height. */
    private static final int PANEL_HEIGHT = 150;

    /** Default dimension for the panel. */
    private static final Dimension SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

    /** Object of TetrisPiece class. */
    private TetrisPiece myNextPiece;

    /**
     * Constructor to initialize the panel.
     */
    public NextPiecePanel() {
        super();
        initializePanel();
    }

    /**
     * Initializing the Panel.
     */
    public void initializePanel() {
        this.setBackground(Color.BLUE);
        setPreferredSize(SIZE);
        final JLabel label = new JLabel("Next Piece");
        label.setForeground(Color.WHITE);
        this.add(label);
    }

    /**
     * Repaints the components of this panels GUI.
     * @param theG the <code>Graphics</code> object to protect
     */
    @Override
    protected void paintComponent(final Graphics theG) {
        super.paintComponent(theG);
        final double stroke = 0.5;

        final Graphics2D g2d = (Graphics2D) theG;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2d.setStroke(new BasicStroke((float) stroke));
        g2d.setColor(Color.CYAN);


        if (myNextPiece != null) {
            final int offsetX = 2;
            final int offsetY = 1;

            for (final int[] square : myNextPiece.getPointsByRotation(Rotation.NONE)) {
                final int x = square[0];
                final int y = square[1];

                //Draw the actual Piece
                g2d.setColor(Color.magenta);
                g2d.fill3DRect((x + offsetX) * BLOCK_SIZE,
                        (y + offsetY) * BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE, true);

                //Draw the black outline
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke((float) 1));
                g2d.drawRect((x + offsetX) * BLOCK_SIZE,
                        (y + offsetY) * BLOCK_SIZE,
                        BLOCK_SIZE, BLOCK_SIZE);

            }
        }
    }

    /**
     * Handles PropertyChangeEvents.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (Board.PROPERTY_CHANGE_NEXT.equals(theEvent.getPropertyName())) {
            myNextPiece = (TetrisPiece) theEvent.getNewValue();
        } else if (MenuBar.PROPERTY_CHANGE_NEXT_COLOR.equals(theEvent.getPropertyName())) {
            final Color color = (Color) theEvent.getNewValue();
            this.setBackground(color);
        }
        repaint();
    }

}