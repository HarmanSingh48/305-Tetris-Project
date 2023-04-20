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
public class NextArea extends JLabel {
    /** The default width value. */
    private static final int WIDTH = 50;

    /** The default height value. */
    private static final int HEIGHT = 50;
    /** The minimum size this component should be. */
    private static final Dimension MIN_SIZE = new Dimension(WIDTH, HEIGHT);
    @Serial
    private static final long serialVersionUID = 8554307508894522324L;
    public NextArea(final Color theColor) {
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
