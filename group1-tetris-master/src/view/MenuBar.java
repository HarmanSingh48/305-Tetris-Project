package view;
import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serial;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * This class represents a custom JMenuBar that contains a File menu with New Game,
 * About, and Exit options. When a user clicks on New Game or About, a dialog box
 * appears with corresponding messages. When a user clicks on Exit, the program exits.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public class MenuBar extends JPanel {

    /** The background color. */
    public static final String PROPERTY_CHANGE_BACK_COLOR = "BACKGROUND_COLOR";

    /** The next color. */
    public static final String PROPERTY_CHANGE_NEXT_COLOR = "NEXT_COLOR";

    /** The music String. */
    public static final String PROPERTY_CHANGE_MUSIC = "MUSIC";

    @Serial
    private static final long serialVersionUID = -8340223118855585654L;

    /** Stores text for the new game menu option. */
    private static final String NEW_GAME = "New Game";

    /** Stores text for the about menu option. */
    private static final String ABOUT = "About";

    /** Stores text for the exit menu option. */
    private static final String EXIT = "Exit";

    /**
     * Stores the About info of the group.
     */
    private static final String ABOUT_INFO = """
                        Group: Group 1\s
                        Members: Harman Singh, Lucas Perry, Windie Lee, Shuaib Ali.\s
                        Version: Winter 2023.\s
                        Music:\s
                        -Tetris Theme A by Hirokazu Tanaka (1989)\s
                        -Copyrighted by The Tetris Company""";


    /** The main JMenuBar. */
    private final JMenuBar myMenu = new JMenuBar();

    /** JMenu item for the New Game option. */
    private final JMenuItem myNewGameItem = new JMenuItem(NEW_GAME);

    /**
     * JMenu item for the end game option.
     */
    private final JMenuItem myEndGameItem = new JMenuItem("End Game");

    /** JMenu item for the About option.*/
    private final JMenuItem myAboutItem = new JMenuItem(ABOUT);

    /** JMenu item for the Exit option. */
    private final JMenuItem myExitItem = new JMenuItem(EXIT);

    /** Menu option for the file sub-menu. */
    private final JMenu myFileMenu = new JMenu("File");

    /**
     * Menu option for the Settings sub-menu.
     */
    private final JMenu mySettings = new JMenu("Settings");

    /**
     * MenuItem option for changing the background of the next piece panel.
     */
    private final JMenuItem myNextPieceColor = new JMenuItem("Next Piece Color");

    /**
     * MenuItem option for changing the background of the board.
     */
    private final JMenuItem myBGColor = new JMenuItem("BackGround Color");

    /**
     * Property Change Support.
     */
    private final PropertyChangeSupport myPCS;

    /** The music check box item. */
    private final JCheckBoxMenuItem myMusic = new JCheckBoxMenuItem("Music", false);

    /**
     * Constructor for the MenuBar class that sets up the JMenuBar with a File menu
     * and options.
     * Adds action listeners to the New Game, About, and Exit options to show corresponding
     * messages or exit the program.
     * @param theFrame the Frame to attach the menu bar to.
     */
    public MenuBar(final TetrisFrame theFrame) {
        super();

        myPCS = new PropertyChangeSupport(this);

        addItemsToMenu();

        addActionListeners(theFrame);

        myMenu.add(myFileMenu);
        myMenu.add(mySettings);
    }

    /**
     * Adds all the items to the file sub-menu.
     */
    private void addItemsToMenu() {
        myFileMenu.add(myNewGameItem);
        myFileMenu.add(myEndGameItem);
        myFileMenu.add(myAboutItem);
        myFileMenu.add(myExitItem);

        mySettings.add(myBGColor);
        mySettings.add(myNextPieceColor);
        mySettings.add(myMusic);
    }

    /**
     * Adds ActionListeners to the items under file sub-menu.
     * @param theFrame the frame to that the menu bar is attached to.
     */
    private void addActionListeners(final TetrisFrame theFrame) {
        myNewGameItem.addActionListener(e -> {
            if (theFrame.attemptStartOfNewGame()) {
                myMusic.setState(true);
                JOptionPane.showMessageDialog(this, NEW_GAME,
                       NEW_GAME, JOptionPane.INFORMATION_MESSAGE);
                notifyObserverOfMusicChange();
            }
        });

        myEndGameItem.addActionListener(e -> theFrame.attemptEndOfGame());

        myAboutItem.addActionListener(e -> JOptionPane.showMessageDialog(
                MenuBar.this, ABOUT_INFO,
                /*Title of popup window      ^Info in the window*/
                ABOUT, JOptionPane.INFORMATION_MESSAGE));


        myExitItem.addActionListener(e -> System.exit(0));

        myBGColor.addActionListener(e -> notifyObserverOfBackColorChange
                (JColorChooser.showDialog(this,
                "Background Color", Color.BLACK)));

        myNextPieceColor.addActionListener(e -> notifyObserverOfNextColorChange
                (JColorChooser.showDialog(this,
                "Next Piece Panel Background Color", Color.BLACK)));

        myMusic.addActionListener(e -> notifyObserverOfMusicChange());
    }

    /**
     * Notify observers of a color change.
     * @param theColor the new color
     */
    private void notifyObserverOfBackColorChange(final Color theColor) {
        myPCS.firePropertyChange(PROPERTY_CHANGE_BACK_COLOR, null, theColor);
    }

    /**
     * Notify observers of the next color change.
     * @param theColor the new next color
     */
    private void notifyObserverOfNextColorChange(final Color theColor) {
        myPCS.firePropertyChange(PROPERTY_CHANGE_NEXT_COLOR, null, theColor);
    }

    /**
     * Notify observers of a music change.
     */
    public void notifyObserverOfMusicChange() {
        myPCS.firePropertyChange(PROPERTY_CHANGE_MUSIC, null, myMusic.getState());
    }

    /**
     * Adds propertyChangeListener.
     * @param theListener the PropertyChangeListener to be added
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes propertyChangeListener.
     * @param theListener the PropertyChangeListener to be removed
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }

    /**
     * Getter method for the JMenuBar.
     * @return the JMenuBar myMenu.
     */
    public JMenuBar getMyMenu() {
        return myMenu;
    }
}