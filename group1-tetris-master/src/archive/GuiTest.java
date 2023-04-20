package archive;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowEvent;
import java.io.Serial;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

/**
 * A file menu with all expected user options, for example: "New Game" , "Exit", "About", etc.
 * GUI Regions:
 *  Region for the board
 *      Color: Red
 *  Region for the NEXT piece
 *      Color: Blue
 *  Region for other information
 *      Smaller than the board itself
 *      Color: Green
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public class GuiTest extends JPanel {
    @Serial
    private static final long serialVersionUID = -3655189488492755197L;
    /**  The area for the main board. */
    private final JPanel myBoardArea;
    /**  The area for the next piece area. */
    private final JPanel myNextArea;
    /**  The area for game information. */
    private final JPanel myInfoArea;
    /** */
    private static final String FONT_NAME = "Lucida Sans";
    public GuiTest() {
        final int horizontalGap = -10;
        final int verticalGap = -10;
        final BorderLayout layout = new BorderLayout();
        layout.setHgap(horizontalGap); // set the horizontal gap between components
        layout.setVgap(verticalGap); // set the vertical gap between components
        myBoardArea = new JPanel();
        myNextArea = new JPanel();
        myInfoArea = new JPanel();
        createBoardArea();
        createNextArea();
        createInfoArea();
        setLayout(layout);

        add(myBoardArea, BorderLayout.CENTER);
        add(myNextArea, BorderLayout.EAST);
    }
    private void createBoardArea() {
        final int preferredWidth = 200;
        final int preferredHeight = 200;
        final String textStyle = FONT_NAME;
        final int textSize = 20;
        myBoardArea.setBackground(Color.RED);
        myBoardArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        myBoardArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // "Board Area" JLabel
        final JLabel boardLabel = new JLabel("Board Area");
        boardLabel.setFont(new Font(textStyle, Font.BOLD, textSize));
        myBoardArea.add(boardLabel);
        boardLabel.setBorder(new LineBorder(Color.BLACK)); // make it easy to see
    }
    private void createNextArea() {
        final int preferredWidth = 50;
        final int preferredHeight = 150;
        final String textStyle = FONT_NAME;
        final int textSize = 7;
        myNextArea.setBackground(Color.GREEN);
        myNextArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        myNextArea.add(myInfoArea);

        // "Next Area" JLabel
        final JLabel nextLabel = new JLabel("Next Area");
        nextLabel.setFont(new Font(textStyle, Font.BOLD, textSize));
        myNextArea.add(nextLabel);
        nextLabel.setBorder(new LineBorder(Color.BLACK)); // make it easier to see
    }

    private void createInfoArea() {
        final int preferredWidth = 50;
        final int preferredHeight = 50;
        final String textStyle = FONT_NAME;
        final int textSize = 7;
        myInfoArea.setBackground(Color.BLUE);
        myInfoArea.setPreferredSize(new Dimension(preferredWidth, preferredHeight));
        myInfoArea.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // "Info Area" JLabel
        final JLabel infoLabel = new JLabel("Info Area");
        infoLabel.setFont(new Font(textStyle, Font.BOLD, textSize));
        myInfoArea.add(infoLabel);
        infoLabel.setBorder(new LineBorder(Color.BLACK)); // make it easier to see

    }
//  private void setUpComponents() {
//        GridBagConstraints cons = new GridBagConstraints();
//        //updating constraints for the board area label
//        cons.gridx = 0;
//        cons.gridy = 0;
//        //vertical component
//        cons.gridheight = 5;
//        //horizontal component
//        cons.gridwidth = 1;
//
//        this.add(myBoardArea, cons);
//
//        //updating constraints for the next area label
//        cons.gridx = RELATIVE;
//        cons.gridy = 2;
//        cons.gridheight = 1;
//        cons.gridwidth = 1;
//        this.add(myNextArea, cons);
//
//        //updating constraints for the info area label
//        cons.gridx = 1;
//        cons.gridy = RELATIVE;
//        cons.gridheight = 1;
//        cons.gridwidth = 1;
//        this.add(myInfoArea, cons);
//
//        setBorder(BorderFactory.createEmptyBorder(
//                20, 20, 20, 20));

//  }
    private JMenuBar buildMenu(final JFrame theFrame) {
        final JMenuBar menu = new JMenuBar();

        menu.add(buildFileMenu(theFrame));

        return menu;
    }

    private static void createAndShowGUI() {
        //Create and set up the window.
        final JFrame frame = new JFrame("Tetris GUI Test Demo");

        frame.setLayout(new GridLayout(0, 1));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        final GuiTest newContentPane = new GuiTest();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);
        frame.setJMenuBar(newContentPane.buildMenu(frame));

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Builds a file menu with options and submenus.
     * @param theFrame the containing JFrame
     * @return a "File" menu with assorted options
     */
    private JMenu buildFileMenu(final JFrame theFrame) {
        final JMenu menu = new JMenu("File");

        //adding "new game" and "about" buttons
        menu.add(buildMenuItem("New Game", "New Game Started."));
        menu.add(buildMenuItem("About", """
                 Group: Group 1\s
                 Members: Harman Singh, Lucas Perry, Windie Le, Shuaib Ali.\s
                 Version: Winter 2023."""));

        //creating an exit button to add to the file menu
        final JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(theEvent -> theFrame.dispatchEvent(
                               new WindowEvent(theFrame, WindowEvent.WINDOW_CLOSING)));
        menu.add(exitItem);

        return menu;
    }

    /**
     * Helper method to build a menu item which displays a message when pressed.
     * @param theItemText the text for the button
     * @param theMessageText the message that is displayed when pressed
     * @return a menu item
     */
    private JMenuItem buildMenuItem(final String theItemText, final String theMessageText) {
        final JMenuItem item = new JMenuItem(theItemText);

        //action listener that creates a pop-up window with the messageText string
        item.addActionListener(theEvent -> JOptionPane.showMessageDialog(
                GuiTest.this, theMessageText));

        return item;
    }

    /*public static void main(final String[] theArgs) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (final UnsupportedLookAndFeelException | IllegalAccessException
                              | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        javax.swing.SwingUtilities.invokeLater(GuiTest::createAndShowGUI);

    }*/

}
