import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Player{
    private int x,y,hp, maxhp, v;
    private Shape hitbox;
    public static final int LEFT=0, UP=1, RIGHT=2, DOWN=3;

    public Player(int x, int y, int maxhp, int v){
        this.x=x;
        this.y=y;
        updateHitbox();
        this.maxhp=maxhp;
        hp=maxhp;
        this.v=v;
    }
    public void walk(int dir) {
        if(dir==LEFT) x-=v;
        else if(dir==RIGHT)x+=v;
        else if(dir==UP)y-=v;
        else if(dir==DOWN) y+=v;
        updateHitbox();
    }

    public void updateHitbox(){
        hitbox=new Ellipse2D.Double(x-5,y-5,10,10);
    }

    public void hit(int damage){ hp-=damage;}
    public void heal(int health){ hp+=(Math.min(health,maxhp-hp)); }

    public int getX(){ return x; }
    public int getY(){ return y; }
    public int getHP(){ return hp; }
    public int getV(){ return v; }

    public boolean collide(Rectangle wall){
        System.out.println("check");
        return hitbox.intersects(wall);
    }
}