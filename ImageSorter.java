import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.*;

/**
 * This program sorts the pixels of an image based on their brightness.
 * @author Begüm Öz
 * Date: 15 May 2023
 */
public class ImageSorter extends JPanel
{
    public int imageW;
    public int delay;
    public int imageH;
    public boolean vSorted;
    public JLabel label;
    public boolean hSorted;
    public JFrame frame;
    public JPanel panel;
    public Timer timer;
    public BufferedImage bImage;
    TimerActionListener listener;
  

    public ImageSorter(JFrame frame)
    {
        frame.setTitle("Press Up Arrow to Speed Up, Down Arrow to Slow Down, 0 to Reset");
        listener = new TimerActionListener();
        delay = 200;
        timer = new Timer(delay, listener);
        timer.setRepeats(true);
        vSorted = false;
        hSorted = false;
        panel = this;
        this.setLayout(new BorderLayout());
        this.frame = frame;
        bImage = null;
        loadImage("/Users/begumoz/Documents/CS102/Assignments/6/image.jpg");
        startAnimatedBubbleSort();
    }

    public void loadImage(String fileName)
    {
        try {
            bImage = ImageIO.read(new File(fileName));
            imageW = bImage.getWidth();
            imageH = bImage.getHeight();
            if (imageW > 700 || imageH > 700)
            {
                int count = 2;
                while ( imageW > 700 || imageH > 700 )
                {
                    imageH = imageH / count;
                    imageW = imageW / count;
                    count++;
                }
            }
            Image image = bImage.getScaledInstance(imageW, imageH, Image.SCALE_SMOOTH);
            BufferedImage newBImage = new BufferedImage(imageW, imageH,  bImage.getType());
            Graphics2D g = newBImage.createGraphics();
            g.drawImage(image, 0, 0 , null);
            g.dispose();
            bImage = newBImage;
            this.setSize((int)(1.05*imageW), (int)(1.05*imageH));
            frame.setSize((int)(1.05*imageW), (int)(1.05*imageH));
            frame.add(this, BorderLayout.NORTH);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }

    public void diagonalStep()
    {
        displayImage();
        verticalStep();
        horizontalStep();
    }

    public void startAnimatedBubbleSort()
    {
        displayImage();
        timer.start();
        frame.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
              int keyCode = e.getKeyCode();
              if (keyCode == KeyEvent.VK_UP) {
                if (delay >= 11)
                {
                    timer.stop();
                    delay = delay - 10;
                    timer = new Timer(delay, listener);
                    timer.start();
                }
              }
              else if (keyCode == KeyEvent.VK_DOWN) {
                timer.stop();
                delay = delay + 10;
                timer = new Timer(delay, listener);
                timer.start();
              }
              else if (keyCode == KeyEvent.VK_0) {
                timer.stop();
                panel.remove(label);
                loadImage("/Users/begumoz/Documents/CS102/Assignments/6/image.jpg");
                startAnimatedBubbleSort();
              }
            }
        });
        
    }
    
    class TimerActionListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e) {
               
            panel.remove(label);
            diagonalStep();
            panel.revalidate();
            panel.repaint();
            if (!hSorted && !vSorted)
            {
                timer.stop();
            }
        }
        
    }

    public void horizontalStep()
    {
        hSorted = false;
        for (int i = 1; i <= imageH-1; i++)
        {
            for (int j = 1; j <= imageW-2; j++)
            {
                if (findBrightness(j, i) < findBrightness(j+1, i))
                {
                    int temp = bImage.getRGB(j, i);
                    bImage.setRGB(j, i, bImage.getRGB(j+1, i));
                    bImage.setRGB(j+1, i, temp);
                    hSorted = true;
                }
            }
        }
        this.revalidate();
        this.repaint(); 
    
    }

    public void verticalStep()
    {
        vSorted = false;
        for (int j = 1; j <= imageW-1; j++)
        {
            for (int i = 1; i <= imageH-2; i++)
            {
                if (findBrightness(j, i) < findBrightness(j, i+1))
                {
                    int temp = bImage.getRGB(j, i);
                    bImage.setRGB(j, i, bImage.getRGB(j, i+1));
                    bImage.setRGB(j, i+1, temp);
                    vSorted = true;
                }
            }
        }

        this.revalidate();
        this.repaint(); 
    }

    public void displayImage()
    {
        label = new JLabel(new ImageIcon(bImage));
        this.add( label, BorderLayout.NORTH);
    }

    public double findBrightness(int x, int y)
    {
        return 0.2126 * findRed(x, y) + 0.7152 * findGreen(x, y)
        + 0.0722 * findBlue(x, y);
    }

    public int findRed(int x, int y)
    {
        int RGB = bImage.getRGB(x, y);
        Color color = new Color(RGB);
        return color.getRed();
    }

    public int findGreen(int x, int y)
    {
        int RGB = bImage.getRGB(x, y);
        Color color = new Color(RGB);
        return color.getGreen();
    }

    public int findBlue(int x, int y)
    {
        int RGB = bImage.getRGB(x, y);
        Color color = new Color(RGB);
        return color.getBlue();
    }

}