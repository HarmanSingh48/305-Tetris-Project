package view;

import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.io.Serial;
import java.util.HashMap;
import java.util.Map;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import model.Board;
import model.Boardable;

/**
 * This class represents a JFrame that displays a Tetris game GUI demo.
 * The frame contains a Tetris board, a Tetris piece, and other information panels.
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public class TetrisFrame extends JFrame implements PropertyChangeListener {

    /** The default delay time. */
    private static final int DEFAULT_DELAY = 1000;

    /** The default width of the Frame. */
    private static final int DEFAULT_WIDTH = 425;

    /** The default width of the Frame. */
    private static final int DEFAULT_HEIGHT = 560;

    /** Default serial version UID. */
    @Serial
    private static final long serialVersionUID = 5702522255821307328L;

    /** Title of the game JFrame. */
    private static final String WINDOW_TITLE = "Tetris";

    /** The music player. */
    private MusicPlayer myPlayer;

    /** Reference to the primary model object (Board). */
    private final Boardable myBoard;

    /**
     * Timer that steps the game, starting at the tick rate defined by DEFAULT_DELAY.
     */
    private final Timer myTimer;

    /** Keeps track of whether the current game is active. */

    private boolean myGameOver;

    /** Keeps track of whether the current game is paused. */
    private boolean myPaused;

    /** Keeps track of whether the current game is new. */
    private boolean myGameIsNew;

    /** PropertyChangeSupport to manage various property changes. */
    private final PropertyChangeSupport myPCS;

    /** The input audio. */
    private AudioInputStream myAudioInput;
    /**
     * Constructor for the TetrisFrame class that sets up the JFrame with a specific size,
     * adds a title, and sets up the layout with the Tetris board, Tetris piece, and other
     * information panels. Also sets up the menu bar.
     */

    public TetrisFrame() {
        super();
        final Dimension preferredFrameSize = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        setPreferredSize(preferredFrameSize);
        myBoard = new Board();

        //Initializing timer
        myTimer = new Timer(DEFAULT_DELAY, e -> {
            if (!myGameOver) {
                myBoard.step();
            }
        });
        //Setting Up PC Support
        myPCS = new PropertyChangeSupport(this);

        //Initializing the game state parameters to their initial values
        setUpGameParams();


        //Adding the key listener to the frame.
        addKeyListener(new ControlKeyListener());

        //Focusing this frame.
        setFocusable(true);
        requestFocus();
    }

    /**
     * Initializes, sets up, and displays elements of the GUI.
     */
    public void createAndShowGUI() {

        //Defining parameters of the JFrame object such as close behavior, focusable, etc.
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Keep this set to false as our game doesn't implement resizability yet
        this.setResizable(false);
        this.setTitle(WINDOW_TITLE);

        //Creating a container that holds the side UI elements
        // and placing it and the grid panel into the frame.
        final Box sideContainerBox = new Box(BoxLayout.PAGE_AXIS);
        final Box mainContainerBox = new Box(BoxLayout.LINE_AXIS);
        final BoardPanel boardPanel = new BoardPanel();
        final NextPiecePanel nextPiecePanel = new NextPiecePanel();
        final OtherPanel otherPanel = new OtherPanel();

        //Adding menu bar
        final MenuBar menu = new MenuBar(this);

        //Adding Listeners to the menu
        menu.addPropertyChangeListener(nextPiecePanel);
        menu.addPropertyChangeListener(boardPanel);
        menu.addPropertyChangeListener(this);

        //Adding Menu Bar to the frame
        final JMenuBar menuBar = menu.getMyMenu();
        this.setJMenuBar(menuBar);

        //Adding GUI Elements to a container and adding the container to the frame
        sideContainerBox.add(nextPiecePanel);
        sideContainerBox.add(otherPanel);
        mainContainerBox.add(boardPanel);
        mainContainerBox.add(sideContainerBox);
        this.add(mainContainerBox);

        //Adding Listeners to the board
        ((Board) myBoard).addPropertyChangeListener(boardPanel);
        ((Board) myBoard).addPropertyChangeListener(this);
        ((Board) myBoard).addPropertyChangeListener(nextPiecePanel);
        ((Board) myBoard).addPropertyChangeListener(otherPanel);

        //Adding Listeners to this frame
        this.addPropertyChangeListener(boardPanel);

        //Adding Listeners to the other information panel
        otherPanel.addPropertyChangeListener(this);


        //Setting up the music player

        try {
            setUpMusicPlayer();
        } catch (final IOException | NullPointerException | UnsupportedAudioFileException e) {
            e.printStackTrace();
        }

        myPlayer = new MusicPlayer();

        //Displaying the GUI

        //this should be the last thing done in the function
        this.pack();
        this.setVisible(true);
    }

    /**
     * Sets up parameters used for controlling the game state such as pause status.
     */
    private void setUpGameParams() {
        myPaused = false;
        myGameIsNew = true;
    }

    /**
     * Attempts to a new game if one isn't already running.
     * @return Whether the attempt to start a new game succeeded.
     */
    public boolean attemptStartOfNewGame() {
        //Only starts a new game when the current game is over
        //or no game has been started yet.
        final boolean success;
        if (myGameIsNew || myGameOver) {
            myBoard.newGame();
            myTimer.start();
            myGameIsNew = false;
            myGameOver = false;
            success = true;
        } else {
            JOptionPane.showMessageDialog(this, "Finish Current Game before restarting!");
            success = false;
        }

        return success;
    }

    /**
     * Attempts to end the current game.
     */
    public void attemptEndOfGame() {
        if (!myGameOver) {
            myTimer.stop();
            myGameOver = true;
            myPaused = true;
        }
    }

    /**
     * Helper method that up an audio input stream with the default music file path.
     * @throws NullPointerException If the object is null.
     * @throws UnsupportedAudioFileException If the file format is wrong.
     * @throws IOException If the I/O operation failed or was interrupted.
     */
    private void setUpMusicPlayer()
            throws NullPointerException, UnsupportedAudioFileException, IOException {

        myAudioInput = AudioSystem.getAudioInputStream(
                new File("support_files/ThemeMusic.wav"));

    }

    /**
     * Helper method that pauses and unpauses the game.
     */
    private void pause() {
        if (myPaused) {
            myTimer.start();
            myPaused = false;
        } else {
            myTimer.stop();
            myPaused = true;
        }
    }

    /**
     * Helper method to propertyChange to update the timer.
     * @param theLevel any level n
     */
    private void updateTimer(final int theLevel) {
        if (!myGameIsNew) {
            //For any level n, a new timer is started that is 100 * n milliseconds faster
            //than the default tick rate (1000 ms)


            final int levelScaledDelay = DEFAULT_DELAY - (theLevel * 100);
            myTimer.setDelay(levelScaledDelay);


            myTimer.restart();
        }
    }

    /**
     * Helper method to propertyChange to toggle the music when necessary.
     * @param theValue if true, then play music, else pause the music.
     */
    private void toggleMusic(final boolean theValue) {
        if (theValue) {
            myPlayer.playMusic();
        } else {
            myPlayer.pauseMusic();
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (theEvent.getPropertyName().equals(Board.PROPERTY_CHANGE_GAME)) {
            myGameOver = (boolean) theEvent.getNewValue();
        } else if (theEvent.getPropertyName().equals(OtherPanel.PROPERTY_CHANGE_LEVEL)) {
            updateTimer((int) theEvent.getNewValue());
        } else if (theEvent.getPropertyName().equals(MenuBar.PROPERTY_CHANGE_MUSIC)) {
            toggleMusic((boolean) theEvent.getNewValue());
        }
        //repaint();
    }

    /**
     * Adds a property change listener to this objects Property Change Support.
     * @param theListener  the PropertyChangeListener to be added
     *
     */
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.addPropertyChangeListener(theListener);
    }

    /**
     * Removes a property change listener to this objects Property Change Support.
     * @param theListener the PropertyChangeListener to be removed
     *
     */
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPCS.removePropertyChangeListener(theListener);
    }



    //inner classes

    /**
     * Helper inner class that facilitates keyboard inputs.
     * @author Harman Singh
     */
    private class ControlKeyListener extends KeyAdapter {
        /** New Keymap for controls. */
        private final Map<Integer, Runnable> myKeyMap;

        ControlKeyListener() {
            this.myKeyMap  = new HashMap<>();
            mapKeys();
        }

        private void mapKeys() {
            //Left
            myKeyMap.put(KeyEvent.VK_A, myBoard::left);
            myKeyMap.put(KeyEvent.VK_LEFT, myBoard::left);

            //Right
            myKeyMap.put(KeyEvent.VK_D, myBoard::right);
            myKeyMap.put(KeyEvent.VK_RIGHT, myBoard::right);

            //Down
            myKeyMap.put(KeyEvent.VK_S, myBoard::down);
            myKeyMap.put(KeyEvent.VK_DOWN, myBoard::down);

            //Drop
            myKeyMap.put(KeyEvent.VK_SPACE, myBoard::drop);

            //Rotate CW
            myKeyMap.put(KeyEvent.VK_E, myBoard::rotateCW);

            //Rotate CCW
            myKeyMap.put(KeyEvent.VK_Q, myBoard::rotateCCW);

            //Pause/UnPause
            myKeyMap.put(KeyEvent.VK_P, TetrisFrame.this::pause);

            //View Controls
            myKeyMap.put(KeyEvent.VK_F1, () -> {
                //Pause the Game
                pause();
                JOptionPane.showMessageDialog(TetrisFrame.this, """
                        Controls:
                        Left: A, a, LeftArrow
                        Right: D, d, RightArrow
                        Down: S, s, DownArrow
                        Drop: Spacebar
                        Rotate Clockwise: E, e
                        Rotate Counter Clockwise: Q, q
                        Pause/Unpause: P, p
                        Controls: F1""");

            });
        }

        /**
        * Called when a key is pressed by the user.
        * @param theEvent the event to be processed
        */
        @Override
        public void keyPressed(final KeyEvent theEvent) {
            //Avoids movement of piece if the game is paused
            if (myKeyMap.containsKey(theEvent.getKeyCode())) {

                //Allows user to unpause game when it is paused but not when the game is over
                if (myPaused && !myGameOver && theEvent.getKeyCode() == KeyEvent.VK_P) {
                    myKeyMap.get(theEvent.getKeyCode()).run();

                }  else if (myPaused && theEvent.getKeyCode() == KeyEvent.VK_F1) {
                    //Allows user to view controls even when the game is paused
                    myKeyMap.get(theEvent.getKeyCode()).run();


                } else if (!myPaused) {
                    //Otherwise, the keys will only trigger a runnable if the game is unpaused
                    myKeyMap.get(theEvent.getKeyCode()).run();
                }
            }

        }
    } //End of ControlKeyListener Inner Class

    /**
     * Helper inner class adds a music playing abilities to the program.
     * @author Harman Singh
     */
    private class MusicPlayer {
        /** The music clip. */
        private Clip myMusicClip;


        MusicPlayer() {
            try {
                myMusicClip = AudioSystem.getClip();
                myMusicClip.open(myAudioInput);
            } catch (final IOException | LineUnavailableException e) {
                e.printStackTrace();
            }
        }

        /**
         * Starts playing the music and sets it to loop continuously.
         */
        public void playMusic() {
            myMusicClip.start();
            myMusicClip.loop(Clip.LOOP_CONTINUOUSLY);
        }

        /**
         * Pauses the music, until playMusic() is run.
         */
        public void pauseMusic() {
            myMusicClip.stop();
        }
    }
} //End of Tetris Frame