import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class InvadersPanel extends JPanel implements ActionListener {
    final int SCREEN_HEIGHT = 500;
    final int SCREEN_WIDTH = 500;
    final int SCREEN_UNIT = 25;
    final int DELAY = 150;

    Timer timer;
    Random random;
    boolean running = false;
    Ship ship;

    InvadersPanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        this.setFocusable(true);
        startGame();
    }

    public void startGame(){
        ship = new Ship(SCREEN_WIDTH/2, SCREEN_HEIGHT - 2*SCREEN_UNIT);
        timer = new Timer(DELAY,this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

        //Draw ship
        g2d.setColor(Color.blue);
        g2d.fillRect(ship.getX(),ship.getY(),2*SCREEN_UNIT, 2*SCREEN_UNIT);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                    ship.setX(ship.getX()-15);
                    System.out.println("X--");
                    break;
                case KeyEvent.VK_D:
                    ship.setX(ship.getX()+15);
                    System.out.println("X++");
                    break;
            }
        }
    }
}
