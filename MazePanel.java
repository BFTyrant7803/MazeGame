package MazeGame;

import java.awt.*;
import javax.swing.*;

public class MazePanel extends JPanel {
    private static final char WALL = (char) 178;
    private static final char PORTAL = (char) 64;
    private static final char PATH = (char) 255;
    private static final char START = (char) 83;
    private static final char EXIT = (char) 88;
    private static final char PLAYER = (char) 80;
    private static final int MAXROW = 21;
    private static final int MAXCOLUMN = 61;

    private char[][] mapGrid;

    public MazePanel(char[][] mapGrid) {
        this.mapGrid = mapGrid;
        setPreferredSize(new Dimension(MAXCOLUMN * 10, MAXROW * 10));
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        for (int row = 0; row < MAXROW; row++) {
            for (int column = 0; column < MAXCOLUMN; column++) {
                char cell = mapGrid[row][column];
                switch (cell) {
                    case WALL:
                        g.setColor(Color.BLACK);
                        break;
                    case PATH:
                        g.setColor(Color.WHITE);
                        break;
                    case START:
                        g.setColor(Color.GREEN);
                        break;
                    case EXIT:
                        g.setColor(Color.RED);
                        break;
                    case PORTAL:
                        g.setColor(Color.MAGENTA);
                        break;
                    case PLAYER:
                        g.setColor(Color.BLUE);
                        break;
                }
                g.fillRect(column * 10, row * 10, 10, 10);
            }
        }
    }
}
