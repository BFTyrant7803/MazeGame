package MazeGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class StartGameGUI extends JFrame {

    private static final long serialVersionUID = 1L;

    public StartGameGUI() {
        setTitle("Maze Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel();
        JButton startButton = new JButton("Start Game");
        startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                MazeGenerator maze = new MazeGenerator();
                maze.wallfiller();
                maze.mapGenerator();
                maze.displayMap();
                dispose();
            }
        });
        panel.add(startButton);

        getContentPane().add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        StartGameGUI startGame = new StartGameGUI();
    }
}