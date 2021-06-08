import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

public class InvadersPanel extends JPanel implements ActionListener {
    final int SCREEN_HEIGHT = 500;
    final int SCREEN_WIDTH = 500;
    final int SCREEN_UNIT = 25;
    final int DELAY = 150;
    final int DEF_ALIENS_NUMBER = 8;

    Timer timer;
    Random random;
    boolean running = false;
    Ship ship;
    ArrayList<Alien> aliens;
    int aliensDirection;

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
        running = true;
        timer.start();
        aliens = new ArrayList<>();
        for(int i = 0; i < DEF_ALIENS_NUMBER ; i++){
            aliens.add(new Alien(i*2*SCREEN_UNIT + SCREEN_UNIT, SCREEN_UNIT));
        }
        aliensDirection = 1;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;

            //Draw ship
            g2d.setColor(Color.blue);
            g2d.fillRect(ship.getX(), ship.getY(), 2 * SCREEN_UNIT, 2 * SCREEN_UNIT);

            //Draw aliens
            g2d.setColor(Color.gray);
            if(aliens.size()>0)
                for (int i = 0; i < aliens.size(); i++) {
                    g2d.fillOval(aliens.get(i).getX(), aliens.get(i).getY(), 2 * SCREEN_UNIT, 2 * SCREEN_UNIT);
                }

            //Draw bullets
            g2d.setColor(Color.green);
            if(ship.getBullets().size() > 0)
                for (int i = 0; i < ship.getBullets().size(); i++) {
                    g2d.fillRect(ship.getBullets().get(i).getX(), ship.getBullets().get(i).getY(), 5, 15);
                }

    }

    public void moveAliens(){
        for(int i = 0; i< aliens.size(); i++){
            aliens.get(i).move(aliensDirection);
        }
        if(aliensDirection > 0 && (aliens.get(aliens.size()-1).getX() + 2*SCREEN_UNIT) >= SCREEN_WIDTH){
            for(int i = 0; i< aliens.size(); i++){
                aliens.get(i).moveDown();
            }
            aliensDirection = -1;
        }else if(aliensDirection < 0 && aliens.get(0).getX() <= 0){
            for(int i = 0; i< aliens.size(); i++){
                aliens.get(i).moveDown();
            }
            aliensDirection = 1;
        }
    }

    public void moveBullets(){
        for(int i = 0; i < ship.getBullets().size(); i++){
            Bullet bullet = ship.getBullets().get(i);
            bullet.setY(bullet.getY()-15);
            if(bullet.getY() < 0)
                ship.getBullets().remove(bullet);
        }
    }

    public void checkCollisions(){
        if(aliensDirection > 0) {
            for (int i = 0; i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                if (alien.getX() + SCREEN_UNIT == ship.getX() && alien.getY() + (ship.getY() - (alien.getY() + 2 * SCREEN_UNIT)) < 2*SCREEN_UNIT) {
                    gameOver();
                }
            }
        } else{
            for (int i = 0; i < aliens.size(); i++) {
                Alien alien = aliens.get(i);
                if (alien.getX() + SCREEN_UNIT == ship.getX()+2*SCREEN_UNIT && (ship.getY() - (alien.getY() + 2 * SCREEN_UNIT)) < 2*SCREEN_UNIT) {
                    gameOver();
                }
            }
        }
        for (int i = 0; i < aliens.size(); i++) {
            Alien alien = aliens.get(i);
            for(int j = 0; j < ship.getBullets().size(); j++) {
                Bullet bullet = ship.getBullets().get(j);
                if(Math.abs(alien.getX() - bullet.getX()) <= 2*SCREEN_UNIT && Math.abs(alien.getY() - bullet.getY()) <= 2*SCREEN_UNIT){
                    aliens.remove(alien);
                    ship.getBullets().remove(bullet);
                }
            }
        }

    }

    public void gameOver(){
        running = false;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            moveAliens();
            moveBullets();
            checkCollisions();
        }
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
                case KeyEvent.VK_SPACE:
                    ship.shot();
                    break;
            }
        }
    }
}
