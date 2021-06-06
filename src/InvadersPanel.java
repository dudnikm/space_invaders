import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class InvadersPanel extends JPanel {
    final int SCREEN_HEIGHT = 500;
    final int SCREEN_WIDTH = 500;
    final int SCREEN_UNIT = 25;

    Timer timer;
    Random random;
    boolean running = false;

    InvadersPanel(){

    }

    public void startGame(){

    }

    public void paintComponent(Graphics g){

        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
    }

}
