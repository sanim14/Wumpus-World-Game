import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;

public class WumpusPanel extends JPanel implements KeyListener
{
    public static final int PLAYING = 0;
    public static final int DEAD = 1;
    public static final int WON = 2;
    private int status;
    private WumpusPlayer player;
    private WumpusMap map;
    private BufferedImage floor;
    private BufferedImage arrow;
    private BufferedImage fog;
    private BufferedImage gold;
    private BufferedImage ladder;
    private BufferedImage pit;
    private BufferedImage breeze;
    private BufferedImage wumpus;
    private BufferedImage deadWumpus;
    private BufferedImage stench;
    private BufferedImage playerUp;
    private BufferedImage playerDown;
    private BufferedImage playerLeft;
    private BufferedImage playerRight;
    private BufferedImage buffer;
    private int toggle = 0;
    private boolean killWumpus = false;

    boolean dead = false;

    public WumpusPanel ()
    {
        super();
        addKeyListener(this);
        setSize(540, 640);
        buffer = new BufferedImage(540, 640, BufferedImage.TYPE_4BYTE_ABGR);
        try
        {
            arrow = ImageIO.read(new File("Images\\arrow.gif"));
            fog = ImageIO.read((new File("Images\\black.gif")));
            breeze = ImageIO.read((new File("Images\\breeze.gif")));
            deadWumpus = ImageIO.read((new File("Images\\deadwumpus.gif")));
            floor = ImageIO.read((new File("Images\\Floor.gif")));
            gold = ImageIO.read((new File("Images\\gold.gif")));
            ladder = ImageIO.read((new File("Images\\ladder.gif")));
            pit = ImageIO.read((new File("Images\\pit.gif")));
            playerDown = ImageIO.read((new File("Images\\playerDown.png")));
            playerLeft = ImageIO.read((new File("Images\\playerLeft.png")));
            playerRight = ImageIO.read((new File("Images\\playerRight.png")));
            playerUp = ImageIO.read((new File("Images\\playerUp.png")));
            stench = ImageIO.read((new File("Images\\stench.gif")));
            wumpus = ImageIO.read((new File("Images\\wumpus.gif")));

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        reset();
    }

    public void keyReleased(KeyEvent e)
    {
        //NOT USED
    }

    public void keyPressed(KeyEvent e)
    {
        //NOT USED
    }

    //In Progress
    public void keyTyped(KeyEvent e)
    {
        char dir = e.getKeyChar();

        if (dead)
        {
            if (dir == 'n')
            {
                if (status == DEAD || status == WON)
                {
                    reset();
                    repaint();
                }
            }
            return;
        }

        if (dir == 'w')
        {
            if (player.getRowPosition() - 1 >= 0)
            {
                player.setRowPosition(player.getRowPosition() - 1);
            }
            player.setDirection(0);
            repaint();
        }
        else if (dir == 's')
        {
            if (player.getRowPosition() + 1 < 10)
            {
                player.setRowPosition(player.getRowPosition() + 1);
            }
            player.setDirection(2);
            repaint();
        }
        else if (dir == 'a')
        {
            if (player.getColPosition() - 1 >= 0)
            {
                player.setColPosition(player.getColPosition() - 1);
            }
            player.setDirection(3);
            repaint();
        }
        else if (dir == 'd')
        {
            if (player.getColPosition() + 1 < 10)
            {
                player.setColPosition(player.getColPosition() + 1);
            }
            player.setDirection(1);
            repaint();
        }
        else if (dir == 'i')
        {
            if (player.getArrow())
            {
                for (int r = player.getRowPosition(); r >= 0; r--)
                {
                    if (r - 1 >= 0)
                    {
                        if (map.getSquare(player.getColPosition(), r-1).getWumpus())
                        {
                            map.getSquare(player.getColPosition(), r-1).setWumpus(false);
                            map.getSquare(player.getColPosition(), r-1).setDeadWumpus(true);
                            player.setDirection(0);
                            killWumpus = true;
                        }
                    }
                }
            }
            player.setArrow(false);
            repaint();
        }
        else if (dir == 'k')
        {
            if (player.getArrow())
            {
                for (int r = player.getRowPosition(); r < 10; r++)
                {
                    if (r + 1 < 10)
                    {
                        if (map.getSquare(player.getColPosition(), r+1).getWumpus())
                        {
                            map.getSquare(player.getColPosition(), r+1).setWumpus(false);
                            map.getSquare(player.getColPosition(), r+1).setDeadWumpus(true);
                            player.setDirection(2);
                            killWumpus = true;
                        }
                    }
                }
            }
            player.setArrow(false);
            repaint();
        }
        else if (dir == 'j')
        {
            if (player.getArrow())
            {
                for (int c = player.getColPosition(); c >= 0; c--)
                {
                    if (c - 1 >= 0)
                    {
                        if (map.getSquare(c-1, player.getRowPosition()).getWumpus())
                        {
                            map.getSquare(c-1, player.getRowPosition()).setWumpus(false);
                            map.getSquare(c-1, player.getRowPosition()).setDeadWumpus(true);
                            player.setDirection(3);
                            killWumpus = true;
                        }
                    }
                }
            }
            player.setArrow(false);
            repaint();
        }
        else if (dir == 'l')
        {
            if (player.getArrow())
            {
                for (int c = player.getColPosition(); c < 10; c++)
                {
                    if (c + 1 < 10)
                    {
                        if (map.getSquare(c + 1, player.getRowPosition()).getWumpus())
                        {
                            map.getSquare(c + 1, player.getRowPosition()).setWumpus(false);
                            map.getSquare(c + 1, player.getRowPosition()).setDeadWumpus(true);
                            player.setDirection(1);
                            killWumpus = true;
                        }
                    }
                }
            }
            player.setArrow(false);
            repaint();
        }
        else if (dir == 'c')
        {
            if (player.getGold() && map.getSquare(player.getColPosition(), player.getRowPosition()).getLadder())
            {
                status = WON;
            }
        }
        else if (dir == 'p')
        {
            if (map.getSquare(player.getColPosition(), player.getRowPosition()).getGold())
            {
                player.setGold(true);
                repaint();
            }

        }
        else if (dir == 'n')
        {
            if (status == DEAD || status == WON)
            {
                reset();
                repaint();
            }

        }
        else if (dir == '*')
        {
            if (toggle == 0)
            {
                toggle++;
                repaint();
            }
            else
            {
                toggle = 0;
                repaint();
            }
        }
    }

    public void reset()
    {
        status = PLAYING;
        map = new WumpusMap();
        player = new WumpusPlayer();
        dead = false;

        player.setColPosition(map.getLadderCol());
        player.setRowPosition(map.getLadderRow());
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.lightGray);
        g.fillRect(0, 0, getWidth(), getHeight());
        g.setColor(Color.BLACK);
        g.fillRect(0, 540, 540, 100);

        Font f =  new Font("Times New Roman" , Font.BOLD, 20);
        g.setFont(f);
        g.setColor(Color.RED);
        String s = "Inventory:";
        g.drawString(s, 15, 560);
        g.setColor(Color.lightGray);
        g.fillRect(130, 540, 5, 100);
        g.setColor(Color.RED);
        g.drawString("Messages:", 150, 560);
        if (player.getArrow())
        {
            g.drawImage(arrow, 8, 570, null);
        }

        if (player.getGold())
        {
            g.drawImage(gold, 38, 570, null);
        }

        if (killWumpus)
        {
            g.setColor(Color.CYAN);
            g.drawString("You hear a scream", 150, 620);
            killWumpus = false;
        }
        if (map.getSquare(player.getColPosition(), player.getRowPosition()).getWumpus())
        {
            g.setColor(Color.CYAN);
            g.drawString("You were eaten by a Wumpus", 150, 580);
            g.drawString("Game over. (N for a new game)", 150, 600);
            status = DEAD;
            dead = true;
        }
        if (player.getGold() && map.getSquare(player.getColPosition(), player.getRowPosition()).getLadder())
        {
            g.setColor(Color.CYAN);
            g.drawString("You won", 150, 580);
            g.setColor(Color.CYAN);
            g.drawString("Type N for a new game", 150, 600);
            status = WON;
            dead = true;
        }
        if (map.getSquare(player.getColPosition(), player.getRowPosition()).getLadder() && map.getSquare(player.getColPosition(), player.getRowPosition()).getBreeze())
        {
            if (!dead)
            {
                g.setColor(Color.CYAN);
                g.drawString("You bump into a ladder", 150, 580);

                g.setColor(Color.CYAN);
                g.drawString("You feel a breeze", 150, 600);
            }

        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getLadder() && map.getSquare(player.getColPosition(), player.getRowPosition()).getStench())
        {
            if (!dead)
            {
                g.setColor(Color.CYAN);
                g.drawString("You bump into a ladder", 150, 580);

                g.setColor(Color.CYAN);
                g.drawString("You smell a stench", 150, 600);
            }
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getGold() && map.getSquare(player.getColPosition(), player.getRowPosition()).getBreeze())
        {
            g.setColor(Color.CYAN);
            g.drawString("You see a glimmer", 150, 580);
            g.setColor(Color.CYAN);
            g.drawString("You feel a breeze", 150, 600);
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getGold() && map.getSquare(player.getColPosition(), player.getRowPosition()).getStench())
        {
            g.setColor(Color.CYAN);
            g.drawString("You see a glimmer", 150, 580);
            g.setColor(Color.CYAN);
            g.drawString("You smell a stench", 150, 600);
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getLadder())
        {
            if (!dead)
            {
                g.setColor(Color.CYAN);
                g.drawString("You bump into a ladder", 150, 580);
            }
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getStench() && map.getSquare(player.getColPosition(), player.getRowPosition()).getBreeze())
        {
            g.setColor(Color.CYAN);
            g.drawString("You smell a stench", 150, 580);
            g.drawString("You feel a breeze", 150, 600);
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getStench())
        {
            if (!dead)
            {
                g.setColor(Color.CYAN);
                g.drawString("You smell a stench", 150, 580);
            }
        }
        else if (map.getSquare(player.getColPosition(), player.getRowPosition()).getBreeze())
        {
            if (!dead)
            {
                g.setColor(Color.CYAN);
                g.drawString("You feel a breeze", 150, 580);
            }
        }
        if (map.getSquare(player.getColPosition(), player.getRowPosition()).getDeadWumpus())
        {
            g.setColor(Color.CYAN);
            g.drawString("You smell a stench", 150, 580);

            if (map.getSquare(player.getColPosition(), player.getRowPosition()).getBreeze())
            {
                g.setColor(Color.CYAN);
                g.drawString("You feel a breeze", 150, 600);
            }
        }
        if (map.getSquare(player.getColPosition(), player.getRowPosition()).getPit())
        {
            g.setColor(Color.CYAN);
            g.drawString("You fell down a pit to your death", 150, 580);
            g.setColor(Color.CYAN);
            g.drawString("Game over. (N for a new game)", 150, 600);
            status = DEAD;
            dead = true;
        }
        if (map.getSquare(player.getColPosition(), player.getRowPosition()).getGold())
        {
            g.setColor(Color.CYAN);
            g.drawString("You see a glimmer", 150, 580);
        }



        int x = 20;
        int y = 20;
        for (int r = 0; r < 10; r++)
        {
            for (int c = 0; c < 10; c++)
            {
                g.drawImage(floor, x, y, null);
                if (map.getSquare(c, r).getLadder())
                {
                    g.drawImage(ladder, x, y, null);
                    map.getSquare(c, r).setVisited(true);
                }
                if (map.getSquare(c, r).getPit())
                {
                    g.drawImage(pit, x, y, null);
                }
                if (map.getSquare(c, r).getWumpus())
                {
                    g.drawImage(wumpus, x, y, null);
                }
                if (map.getSquare(c, r).getDeadWumpus())
                {
                    g.drawImage(deadWumpus, x, y, null);
                }
                if (map.getSquare(c, r).getStench())
                {
                    g.drawImage(stench, x, y, null);
                }
                if (map.getSquare(c, r).getBreeze() && !map.getSquare(c, r).getPit())
                {
                    g.drawImage(breeze, x, y, null);
                }
                if (!player.getGold())
                {
                    if (map.getSquare(c, r).getGold())
                    {
                        g.drawImage(gold, x, y, null);
                    }
                }
                x+=50;
            }
            y+= 50;
            x = 20;
        }

        map.getSquare(player.getColPosition(), player.getRowPosition()).setVisited(true);
        x = 20;
        y = 20;

        if (toggle == 1)
        {
            for (int r = 0; r < 10; r++)
            {
                for (int c = 0; c < 10; c++)
                {
                    if (c == player.getColPosition() && r == player.getRowPosition())
                    {
                        if (player.getDirection() == 0)
                        {
                            g.drawImage(playerUp, x, y, null);
                        }
                        else if (player.getDirection() == 1)
                        {
                            g.drawImage(playerRight, x, y, null);
                        }
                        else if (player.getDirection() == 2)
                        {
                            g.drawImage(playerDown, x, y, null);
                        }
                        else if (player.getDirection() == 3)
                        {
                            g.drawImage(playerLeft, x, y, null);
                        }
                    }
                    x+=50;
                }
                y+=50;
                x = 20;
            }
        }
        else
        {
            for (int r = 0; r < 10; r++)
            {
                for (int c = 0; c < 10; c++)
                {
                    if (!map.getSquare(c, r).getVisited())
                    {
                        g.drawImage(fog, x, y, null);
                    }
                    else if (c == player.getColPosition() && r == player.getRowPosition())
                    {
                        if (player.getDirection() == 0)
                        {
                            g.drawImage(playerUp, x, y, null);
                        }
                        else if (player.getDirection() == 1)
                        {
                            g.drawImage(playerRight, x, y, null);
                        }
                        else if (player.getDirection() == 2)
                        {
                            g.drawImage(playerDown, x, y, null);
                        }
                        else if (player.getDirection() == 3)
                        {
                            g.drawImage(playerLeft, x, y, null);
                        }
                    }
                    x+=50;
                }
                y+=50;
                x = 20;
            }
        }

    }

    @Override
    public void addNotify()
    {
        super.addNotify();
        requestFocus();
    }
}