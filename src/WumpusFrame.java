import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

public class WumpusFrame extends JFrame
{
    public WumpusFrame (String title)
    {
        super(title);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setResizable(false);

        pack();

        WumpusPanel c = new WumpusPanel();

        Insets insets = getInsets();

        int width = c.getWidth() + insets.left + insets.right;
        int height = c.getHeight() + insets.top + insets.bottom;

        setPreferredSize(new Dimension(width, height));

        setLayout(null);

        add(c);

        pack();

        setVisible(true);
    }
}
