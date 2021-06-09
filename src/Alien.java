import javax.swing.*;
import java.awt.*;

public class Alien {

    private int x;
    private int y;
    private Image img;

    public Alien(int x, int y){
        this.x = x;
        this.y = y;
        img = new ImageIcon("src/alien.png").getImage();
    }

    public void move(int dir){
        this.x += dir*5;
    }

    public void moveDown(){
        this.y += 20;
    }

    public int getX() {
        return x;
    }


    public int getY() {
        return y;
    }

    public Image getImg() {
        return img;
    }
}
