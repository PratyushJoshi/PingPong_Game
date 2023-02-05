import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GameFrame extends JFrame {
    //this game panel is the canvas for the components of the game
    GamePanel panel;
    GameFrame() {
        panel = new GamePanel();
        this.add(panel);
        this.setTitle("PingPong Game");
        this.setResizable(false);
        this.setBackground(Color.BLACK);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //adjust everything in the game panel accordingly
        this.pack();
        this.setVisible(true);
        //sets location to the centre of the screen
        this.setLocationRelativeTo(null);

    }
}
