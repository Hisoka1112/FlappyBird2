package FlappyBird;

import javax.swing.*;
import java.awt.*;

public class Rendrer extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        FlappyBird.flappyBird.repaint(g);
    }

}
