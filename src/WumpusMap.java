public class WumpusMap
{
    //WORK IN PROGRESS
    public static final int NUM_ROWS = 10;
    public static final int NUM_COLUMNS = 10;
    public static final int NUM_PITS = 10;


    private WumpusSquare[][] grid;
    private int ladderR;
    private int ladderC;

    public WumpusMap()
    {
        createMap();
    }

    public void createMap()
    {
        int r;
        int c;
        grid = new WumpusSquare[10][10];

        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                grid[row][col] = new WumpusSquare();
            }
        }

        //LADDER
        do
        {
            r = (int) (Math.random() * (10));
            c = (int) (Math.random() * (10));
            if (!grid[r][c].getPit() && !grid[r][c].getWumpus() && !grid[r][c].getGold())
            {
                grid[r][c].setLadder(true);
            }
        }while (grid [r][c].getGold() && grid[r][c].getWumpus() && grid[r][c].getPit());

        ladderR = r;
        ladderC = c;


        //PIT
        for (int x = 0; x < NUM_PITS; x++)
        {
            do
            {
                r = (int) (Math.random() * (10));
                c = (int) (Math.random() * (10));
            }while (grid[r][c].getLadder() || grid[r][c].getPit() );

            grid[r][c].setPit(true);

            if (r + 1 < 10 && !grid[r+1][c].getPit())
            {
                grid[r+1][c].setBreeze(true);
            }
            if (r - 1 >= 0 && !grid[r-1][c].getPit())
            {
                grid[r-1][c].setBreeze(true);
            }
            if (c + 1 < 10 && !grid[r][c+1].getPit())
            {
                grid[r][c+1].setBreeze(true);
            }
            if (c - 1 >= 0 && !grid[r][c-1].getPit())
            {
                grid[r][c-1].setBreeze(true);
            }
        }

        //GOLD
        do
        {
            r = (int) (Math.random() * (10));
            c = (int) (Math.random() * (10));
            if (!grid[r][c].getPit() && !grid[r][c].getLadder())
            {
                grid[r][c].setGold(true);
            }
        }while (grid[r][c].getPit() || grid[r][c].getLadder());


        //WUMPUS
        do
        {
            r = (int) (Math.random() * (10));
            c = (int) (Math.random() * (10));
            if (!grid[r][c].getPit() && !grid[r][c].getLadder())
            {
                grid[r][c].setWumpus(true);
            }
        } while (grid[r][c].getPit() || grid[r][c].getLadder());


        //STENCH
        if (r + 1 < 10)
        {
            if (!grid[r+1][c].getGold() && !grid[r+1][c].getPit() && !grid[r+1][c].getWumpus())
            {
                grid[r+1][c].setStench(true);
            }
        }
        if (r - 1 >= 0)
        {
            if (!grid[r-1][c].getGold() && !grid[r-1][c].getPit() && !grid[r-1][c].getWumpus())
            {
                grid[r-1][c].setStench(true);
            }
        }
        if (c + 1 < 10)
        {
            if (!grid[r][c+1].getGold() && !grid[r][c+1].getPit() && !grid[r][c+1].getWumpus())
            {
                grid[r][c+1].setStench(true);
            }
        }
        if (c - 1 >= 0)
        {
            if (!grid[r][c-1].getGold() && !grid[r][c-1].getPit() && !grid[r][c-1].getWumpus())
            {
                grid[r][c-1].setStench(true);
            }
        }
    }

    public int getLadderCol()
    {
        return ladderC;
    }

    public int getLadderRow()
    {
        return ladderR;
    }

    public WumpusSquare getSquare(int col, int row)
    {
        if (row > 10 || row < 0 || col > 10 || col < 0)
        {
            return null;
        }
        return grid[row][col];
    }

    //go to the next line after each row
    public String toString()
    {
        String s = "";
        for (int r = 0; r < grid.length; r++)
        {
            for (int c = 0; c < grid[0].length; c++)
            {
                s += getSquare(c, r);
            }
        }
        return s;
    }
}
