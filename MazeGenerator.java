package MazeGame;
import java.util.*;
import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MazeGenerator implements KeyListener {
    private static final char WALL = (char) 178;
    private static final char PORTAL = (char) 64;
    private static final char PATH = (char) 255;
    private static final char START = (char) 83;
    private static final char EXIT = (char) 88;
    private static final char PLAYER = (char) 80;
    private int MAXROW = 21;
    private int MAXCOLUMN = 61;
    private JFrame frame;
    private char[][] mapGrid = new char[MAXROW][MAXCOLUMN];
    private Stack<Integer> cellr = new Stack<>();
    private Stack<Integer> cellc = new Stack<>();

    private boolean[] validDirections = new boolean[4];
    private int startr;
    private int startc;
    private int playerR;
    private int playerC;
    private int playerDirection = 0;

    private boolean newG;


    public void displayMap() {
        frame = new JFrame("Maze Game");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new MazePanel(mapGrid));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


        frame.addKeyListener(this);
    }

    public void pushCell(int r, int c) {
        cellr.push(r);
        cellc.push(c);
    }

    public void topCell(int[] rc) {
        if (!cellr.empty() && !cellc.empty()) {
            rc[0] = cellr.peek();
            rc[1] = cellc.peek();
        }
    }

    public void popCell() {
        if (!cellr.empty() && !cellc.empty()) {
            cellr.pop();
            cellc.pop();
        }
    }

    public int randomOddNumber(int min, int max) {
        Random rand = new Random();
        int result = 0;
        if (max < min) {
            int temp = max;
            max = min;
            min = temp;
        }
        int range = max - min + 1;
        do {
            result = rand.nextInt(range) + min;
        } while (result % 2 == 0);

        return result;
    }

    public int randomEvenNumber(int min, int max) {
        Random rand = new Random();
        int result = 0;
        if (max < min) {
            int temp = max;
            max = min;
            min = temp;
        }
        int range = max - min + 1;
        do {
            result = rand.nextInt(range) + min;
        } while (result % 2 != 0);

        return result;
    }

    public int wallfiller() {
        for (int column = 0; column < MAXCOLUMN; column++) {
            for (int row = 0; row < MAXROW; row++) {
                mapGrid[row][column] = WALL;
            }
        }
        return 0;
    }

    public int mapGenerator() {

        startr = randomOddNumber(1, MAXROW - 2);
        startc = randomOddNumber(1, MAXCOLUMN - 2);
        pushCell(startr, startc);


        mapGrid[startr][startc] = PATH;


        while (!cellr.empty()) {

            int[] current = new int[2];
            topCell(current);


            getValidDirections(current[0], current[1]);


            if (!anyValidDirections()) {
                popCell();
                continue;
            }


            int direction = randomValidDirection();
            moveDirection(current, direction);


            mapGrid[current[0]][current[1]] = PATH;
            mapGrid[playerR][playerC] = PATH;


            pushCell(playerR, playerC);
        }


        mapGrid[startr][startc] = START;
        mapGrid[MAXROW - 2][MAXCOLUMN - 2] = EXIT;


        addPortals();


        playerR = startr;
        playerC = startc;
        mapGrid[playerR][playerC] = PLAYER;

        return 0;
    }


    public void getValidDirections(int r, int c) {
        Arrays.fill(validDirections, false);

        if (r - 2 > 0 && mapGrid[r - 2][c] == WALL) {
            validDirections[0] = true;
        }


        if (c + 2 < MAXCOLUMN - 1 && mapGrid[r][c + 2] == WALL) {
            validDirections[1] = true;
        }


        if (r + 2 < MAXROW - 1 && mapGrid[r + 2][c] == WALL) {
            validDirections[2] = true;
        }


        if (c - 2 > 0 && mapGrid[r][c - 2] == WALL) {
            validDirections[3] = true;
        }
    }

    public boolean anyValidDirections() {
        for (boolean direction : validDirections) {
            if (direction) {
                return true;
            }
        }

        return false;
    }

    public int randomValidDirection() {
        ArrayList<Integer> validIndexes = new ArrayList<Integer>();
        for (int i = 0; i < validDirections.length; i++) {
            if (validDirections[i]) {
                validIndexes.add(i);
            }
        }
        Random rand = new Random();
        int index = validIndexes.get(rand.nextInt(validIndexes.size()));
        return index;
    }

    public void moveDirection(int[] current, int direction) {
        switch (direction) {
            case 0:
                mapGrid[current[0] - 2][current[1]] = PATH;
                mapGrid[current[0] - 1][current[1]] = PATH;
                playerR = current[0] - 2;
                playerC = current[1];
                break;
            case 1:
                mapGrid[current[0]][current[1] + 2] = PATH;
                mapGrid[current[0]][current[1] + 1] = PATH;
                playerR = current[0];
                playerC = current[1] + 2;
                break;
            case 2:
                mapGrid[current[0] + 2][current[1]] = PATH;
                mapGrid[current[0] + 1][current[1]] = PATH;
                playerR = current[0] + 2;
                playerC = current[1];
                break;
            case 3:
                mapGrid[current[0]][current[1] - 2] = PATH;
                mapGrid[current[0]][current[1] - 1] = PATH;
                playerR = current[0];
                playerC = current[1] - 2;
                break;
            default:
                break;
        }
    }

    public void addPortals() {
        int portal1r, portal1c, portal2r, portal2c;
        Random rand = new Random();
        portal1r = rand.nextInt(MAXROW);
        portal1c = rand.nextInt(MAXCOLUMN);

        while (mapGrid[portal1r][portal1c] != PATH) {
            portal1r = rand.nextInt(MAXROW);
            portal1c = rand.nextInt(MAXCOLUMN);
        }

        mapGrid[portal1r][portal1c] = PORTAL;

        portal2r = rand.nextInt(MAXROW);
        portal2c = rand.nextInt(MAXCOLUMN);

        while (mapGrid[portal2r][portal2c] != PATH) {
            portal2r = rand.nextInt(MAXROW);
            portal2c = rand.nextInt(MAXCOLUMN);
        }

        mapGrid[portal2r][portal2c] = PORTAL;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        switch (key) {
            case KeyEvent.VK_UP:
                if (mapGrid[playerR - 1][playerC] == EXIT) {
                    JOptionPane.showMessageDialog(frame, "Congratulations, you have won the game!");
                    System.exit(0);
                }
                movePlayer(-1, 0);
                break;
            case KeyEvent.VK_DOWN:
                if (mapGrid[playerR + 1][playerC] == EXIT) {

                    JOptionPane.showMessageDialog(frame, "Congratulations, you have won the game!");
                    System.exit(0);
                }
                movePlayer(1, 0);
                break;
            case KeyEvent.VK_LEFT:
                if (mapGrid[playerR][playerC - 1] == EXIT) {

                    JOptionPane.showMessageDialog(frame, "Congratulations, you have won the game!");
                    System.exit(0);
                }
                movePlayer(0, -1);
                break;
            case KeyEvent.VK_RIGHT:
                if (mapGrid[playerR][playerC + 1] == EXIT) {

                    JOptionPane.showMessageDialog(frame, "Congratulations, you have won the game!");
                    System.exit(0);
                }
                movePlayer(0, 1);
                break;
        }
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new MazePanel(mapGrid));
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
    private void movePlayer(int rOffset, int cOffset) {
        int newR = playerR + rOffset;
        int newC = playerC + cOffset;


        if (isValidPosition(newR, newC)) {
            mapGrid[playerR][playerC] = PATH;
            playerR = newR;
            playerC = newC;
            checkPortal();
            mapGrid[playerR][playerC] = PLAYER;
        }
    }
    private boolean isValidPosition(int r, int c) {
        char cell = mapGrid[r][c];
        return cell == PATH || cell == EXIT || cell == PORTAL;
    }

    public void checkPortal() {
        if (mapGrid[playerR][playerC] == PORTAL) {
            if (!newG) {
                newG = true;
                addPortals();
            } else {
                newG = false;
                mapGrid[playerR][playerC] = PATH;
            }
            Random rand = new Random();
            int row = rand.nextInt(MAXROW);
            int column = rand.nextInt(MAXCOLUMN);
            while (mapGrid[row][column] != PATH) {
                row = rand.nextInt(MAXROW);
                column = rand.nextInt(MAXCOLUMN);
            }
            playerR = row;
            playerC = column;
            mapGrid[playerR][playerC] = PLAYER;
            displayMap();
        }
    }
    public static void main(String[] args) {
        StartGameGUI startGame = new StartGameGUI();
    }
}
