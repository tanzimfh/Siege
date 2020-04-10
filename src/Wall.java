import java.awt.*;

public class Wall {
    private Rectangle hitbox;

    public Wall(Rectangle hitbox){
        this.hitbox=hitbox;
    }

    public Rectangle getHitbox(){
        return hitbox;
    }
}
