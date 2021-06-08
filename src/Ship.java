import java.awt.*;
import java.util.ArrayList;

public class Ship {

    private int x;
    private int y;
    private Image img;
    private ArrayList<Bullet> bullets;

    public Ship(int x, int y) {
        this.x = x;
        this.y = y;
        bullets = new ArrayList<>();
    }

    public void shot(){
        bullets.add(new Bullet(this.x+25,this.y));
    }

    public ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Image getImg() {
        return img;
    }

    public void setImg(Image img) {
        this.img = img;
    }
}
