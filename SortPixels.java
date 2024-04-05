import javax.swing.*;
import java.awt.*;
import javax.imageio.*;

/**
 * This program sorts the pixels of an image based on their brightness.
 * @author Begüm Öz
 * Date: 15 May 2023
 */
public class SortPixels {
    public static void main(String[] args) {
        JFrame frame = new ImageFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
