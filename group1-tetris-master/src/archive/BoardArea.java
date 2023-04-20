package archive;

import java.awt.Color;
import java.awt.Dimension;
import java.io.Serial;
import javax.swing.BorderFactory;
import javax.swing.JLabel;

/**
 *
 *
 * @author Harman Singh
 *         Lucas Perry
 *         Windie Le
 *         Shuaib Ali
 * @version Winter 2023
 */
public class BoardArea extends JLabel {

    /** The default width value. */
    private static final int WIDTH = 100;

    /** The default height value. */
    private static final int HEIGHT = 500;
    /** The minimum size this component should be. */
    private static final Dimension MIN_SIZE = new Dimension(WIDTH, HEIGHT);
    @Serial
    private static final long serialVersionUID = -5447062896311513925L;

    public BoardArea(final Color theColor) {
        super();
        setBackground(theColor);
        setOpaque(true);
        setBorder(BorderFactory.createLineBorder(Color.black));
    }

    @Override
    public Dimension getMinimumSize() {
        return MIN_SIZE; }

    @Override
    public Dimension getPreferredSize() {
        return  MIN_SIZE; }
}
