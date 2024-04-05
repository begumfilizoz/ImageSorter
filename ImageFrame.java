import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

/**
 * This program sorts the pixels of an image based on their brightness.
 * @author Begüm Öz
 * Date: 15 May 2023
 */
public class ImageFrame extends JFrame {
    private ImageSorter panel;

    public ImageFrame()
    {
        setLayout(new BorderLayout());
        panel = new ImageSorter(this);
        this.add( panel );
    }
}
