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
    final int ALIEN_WIDTH = 50;
    final int SHIP_WIDTH = 50;
    final int DELAY = 150;

    Timer timer;
    Random random;
    boolean running = false;
    Ship ship;
    ArrayList<Alien> aliens;
    int aliensDirection;
    Image bgImage;
    boolean win;

    InvadersPanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.addKeyListener(new MyKeyAdapter());
        bgImage = new ImageIcon("src/background.jpg").getImage();
        this.setFocusable(true);
        startGame();
    }

    public void startGame(){
        ship = new Ship(SCREEN_WIDTH/2, SCREEN_HEIGHT - 2*SCREEN_UNIT);
        timer = new Timer(DELAY,this);
        running = true;
        timer.start();
        aliens = new ArrayList<>();
        win = false;

        for(int j = 1;j<4;j++) {
            for (int i = 0; i < SCREEN_WIDTH / ALIEN_WIDTH - 2; i++) {
                if(j%2 == 0)
                    aliens.add(new Alien(i * 2 * SCREEN_UNIT + (SCREEN_UNIT - 10) * j , j*(SCREEN_UNIT+5)));
                else
                    aliens.add(new Alien(i * 2 * SCREEN_UNIT , j*(SCREEN_UNIT+5)));
            }
        }
        aliensDirection = 1;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){
        Graphics2D g2d = (Graphics2D)g;
        g2d.drawImage(bgImage,0,0,null);
        if(running) {
            //Draw ship
            g2d.setColor(Color.blue);
            g2d.drawImage(ship.getImg(), ship.getX(), ship.getY(), null);

            //Draw aliens
            g2d.setColor(Color.gray);
            if (aliens.size() > 0)
                for (Alien alien :aliens) {
                    g2d.drawImage(alien.getImg(), alien.getX(), alien.getY(), null);
                }

            //Draw bullets
            g2d.setColor(Color.red);
            if (ship.getBullets().size() > 0)
                for (Bullet bullet : ship.getBullets()) {
                    g2d.fillRect(bullet.getX(), bullet.getY(), Bullet.BULLET_W, Bullet.BULLET_H);
                }
        } else{
            gameOver(g);
        }

    }

    public void moveAliens(){
        for(Alien alien : aliens){
            alien.move(aliensDirection);
        }
        for(Alien alien :aliens) {
            if (aliensDirection > 0 && (alien.getX() + ALIEN_WIDTH) >= SCREEN_WIDTH) {
                for (Alien alien2 : aliens) {
                    alien2.moveDown();
                }
                aliensDirection = -1;
            } else if (aliensDirection < 0 && alien.getX() <= 0) {
                for (Alien alien2 : aliens) {
                    alien2.moveDown();
                }
                aliensDirection = 1;
            }
        }
    }

    public void moveBullets(){
        for(int i = 0; i < ship.getBullets().size(); i++){
            Bullet bullet = ship.getBullets().get(i);
            bullet.setY(bullet.getY()-20);
            if(bullet.getY() < 0)
                ship.getBullets().remove(bullet);
        }
    }

    public void checkCollisions(){

        //Checking if an alien reached the ship
        Rectangle shipRect = new Rectangle(ship.getX(),ship.getY(),ship.getImg().getWidth(null),ship.getImg().getHeight(null));
        for (Alien alien : aliens) {
            Rectangle alienRect = new Rectangle(alien.getX(),alien.getY(),alien.getImg().getWidth(null),alien.getImg().getHeight(null));
            if (alienRect.intersects(shipRect)) {
                running = false;
                break;
            }
        }

        //Checking if bullet hit an alien
        for (int i = 0; i < aliens.size();i++) {
            Alien alien = aliens.get(i);
            Rectangle alienRect = new Rectangle(alien.getX(),alien.getY(),alien.getImg().getWidth(null),alien.getImg().getHeight(null));
            for(int j = 0; j < ship.getBullets().size(); j++) {
                Bullet bullet = ship.getBullets().get(j);
                Rectangle bulletRect = new Rectangle(bullet.getX(),bullet.getY(),Bullet.BULLET_W, Bullet.BULLET_H);
                if(alienRect.intersects(bulletRect)){
                    aliens.remove(alien);
                    ship.getBullets().remove(bullet);
                    if(aliens.size() == 0){
                        running = false;
                        win = true;
                    }
                }
            }
        }

    }

    public void gameOver(Graphics g){
        if(win){
            g.setColor(Color.green);
            g.setFont(new Font("Ink Free", Font.BOLD, 80));
            FontMetrics metrics1 = g.getFontMetrics();
            g.drawString("VICTORY!", (SCREEN_WIDTH - metrics1.stringWidth("VICTORY!")) / 2, SCREEN_HEIGHT / 2);
        }else {
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 80));
            FontMetrics metrics1 = g.getFontMetrics();
            g.drawString("GAME OVER", (SCREEN_WIDTH - metrics1.stringWidth("GAME OVER")) / 2, SCREEN_HEIGHT / 2);
        }
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
                    if(ship.getX()>0)
                        ship.setX(ship.getX()-15);
                    break;
                case KeyEvent.VK_D:
                    if(ship.getX()+ SHIP_WIDTH < SCREEN_WIDTH)
                        ship.setX(ship.getX()+15);
                    break;
                case KeyEvent.VK_SPACE:
                    ship.shot();
                    break;
            }
        }
    }
}
