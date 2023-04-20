package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import javax.swing.Box;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Board;

/**
 * This class represents a JPanel that displays an "Info Area" label and has a green
 * background color. The panel is positioned at a specific location on the parent container.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 * */
public class OtherPanel extends JPanel implements PropertyChangeListener {

    /** The Property Change variable for changing the level. */
    public static final String PROPERTY_CHANGE_LEVEL = "LEVEL++";

    @Serial
    private static final long serialVersionUID = 1205221436623192212L;

    /** Next Piece width. */
    private static final int PANEL_WIDTH = 150;

    /** Next Piece height. */
    private static final int PANEL_HEIGHT = 350;

    /**
     * The number of cleared rows needed to progress to the next level.
     */
    private static final int LEVEL_SCALE = 5;

    /** Default dimension for the panel. */
    private static final Dimension SIZE = new Dimension(PANEL_WIDTH, PANEL_HEIGHT);

    /** The number of rows cleared by the player. */
    private int myRowsCounter;

    /** The current level of the player. */
    private int myLevelCount = 1;
    /** The current number of rows left to complete until level up. */
    private int myRowsLeft = LEVEL_SCALE;

    /** The number of points the player has. */
    private int myPoints;

    /**  The label for the text saying level. */
    private final JLabel myLevelLabel;

    /** The label for the level. */
    private final JLabel myLevel;

    /** The label for the text saying level. */
    private final JLabel myRowsLabel;

    /** The label for the rows. */
    private final JLabel myRows;

    /** The points label. */
    private final JLabel myPointsLabel;

    /** The points count label. */
    private final JLabel myPointsCountLabel;

    /**
     * The Property Change Support.
     */
    private final PropertyChangeSupport myPCS;

    /**
     * Constructor for the OtherInformation class that sets up the panel with
     * a green background color,
     * a specific size and position, and adds an "Info Area" label to it.
     */
    public OtherPanel() {

        super();

        myPCS = new PropertyChangeSupport(this);

        myLevelLabel = new JLabel("LEVEL");

        myRowsLabel = new JLabel("ROWS  CLEARED: ");

        myPointsLabel = new JLabel("POINTS: ");

        myLevel = new JLabel();

        myRows = new JLabel();

        myPointsCountLabel = new JLabel();

        initializePanel();
    }

    /** Initializing the Other Panel. */
    public void initializePanel() {
        this.setBackground(Color.GREEN);
        setPreferredSize(SIZE);

        this.add(myLevelLabel);
        myLevel.setText(Integer.toString(myLevelCount));
        this.add(myLevel);


        this.add(myRowsLabel);
        myRows.setText(Integer.toString(myRowsCounter));
        this.add(myRows);

        this.add(myPointsLabel);
        myPointsCountLabel.setText(Integer.toString(myPoints));
        this.add(myPointsCountLabel);

        final String[] controls = {"--- CONTROLS ---",
            "Rotate: Q/E", "Move Left: A", "Move Right: D",
            "Move Down: S", "Drop: Space", "Pause: P",
            "More Controls: F1"
        };

        final JPanel controlsPanel = new JPanel(new GridLayout(controls.length, 1));

        for (final String line : controls) {
            final JLabel lineInfo = new JLabel(line, JLabel.CENTER);
            controlsPanel.add(lineInfo);
        }

        add(Box.createVerticalGlue()); // Adds spacing at the top
        add(controlsPanel); // Adds the panel with the controls
        add(Box.createVerticalGlue()); // Adds spacing at the bottom


    }


    /**
     * Updates the labels.
     */
    private void updateLabels() {
        myLevel.setText(Integer.toString(myLevelCount));
        myRows.setText(Integer.toString(myRowsCounter));
        myPointsCountLabel.setText(Integer.toString(myPoints));
    }

    /**
     * Updates the player's statistics.
     */
    private void updateStats() {
        myLevelCount = 1 + (myRowsCounter / LEVEL_SCALE);

        myRowsLeft = myRowsCounter - (myLevelCount * LEVEL_SCALE);

        if (myRowsLeft == 4) {
            myPoints += 40 * myLevelCount;
        } else if (myRowsLeft == 3) {
            myPoints += 100 * myLevelCount;
        } else if (myRowsLeft == 2) {
            myPoints += 300 * myLevelCount;
        } else if (myRowsLeft == 1) {
            myPoints += 1200 * myLevelCount;
        }

        updateLabels();
        notifyObserversOfLevelChange();
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
     * Notifies listeners of changes to the level.
     */
    public void notifyObserversOfLevelChange() {
        if (myLevelCount > 1) {
            myPCS.firePropertyChange(PROPERTY_CHANGE_LEVEL, null, myLevelCount);
        }
    }

    /**
     * Paints the components.
     * @param theGraphics the Graphics to be painted with
     */
    @Override
    protected void paintComponent(final Graphics theGraphics) {
        super.paintComponent(theGraphics);
        updateStats();
    }

    /**
     * Handles property change events.
     * @param theEvent A PropertyChangeEvent object describing the event source
     *          and the property that has changed.
     */
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        final int pointsPerFreeze = 4;
        if (Board.PROPERTY_CHANGE_ROW.equals(theEvent.getPropertyName())) {
            myRowsCounter++;
            myRowsLeft--;
        } else if (Board.PROPERTY_CHANGE_FREEZE.equals(theEvent.getPropertyName())) {
            myPoints += pointsPerFreeze;
        }
        repaint();
    }
}
