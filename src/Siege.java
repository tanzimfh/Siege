import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.Timer;

//JFrame to show JPanel in
public class Siege extends JFrame implements ActionListener{
    Timer myTimer;
    GamePanel game;

    public Siege(){
        super("Creeper");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setPreferredSize(new Dimension(1267,792));
        pack();

        myTimer = new Timer(10, this);

        game=new GamePanel(this);
        add(game);

        setResizable(false);
        setVisible(true);

    }

    public static void main (String[] args){
        new Siege();
    }

    public void start(){
        myTimer.start();
    }

    public void actionPerformed(ActionEvent e){
        game.move();
        game.repaint();
    }
}

//JPanel that shows the map and dots representing bloggers around the cursor when clicked
class GamePanel extends JPanel implements KeyListener{
    private Image map = new ImageIcon("map.jpg").getImage();
    private boolean[]keys;
    private Player player;
    private Siege mainFrame;
    public static final int LEFT=1, UP=2, RIGHT=3, DOWN=4;
    ArrayList<Wall> walls;

    public GamePanel(Siege m){
        player=new Player(490,310,100,2);
        walls=genWalls();
        keys = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        mainFrame=m;
    }

    private ArrayList<Wall> genWalls(){
        ArrayList<Wall>walls=new ArrayList<Wall>();
        walls.add(new Wall(new Rectangle(367,187,10,180)));
        return walls;
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move(){
        if(keys[KeyEvent.VK_RIGHT]){
            tryMove(RIGHT);
        }
        if(keys[KeyEvent.VK_LEFT]){
            tryMove(LEFT);
        }
        if(keys[KeyEvent.VK_UP]){
            tryMove(UP);
        }
        if(keys[KeyEvent.VK_DOWN]){
            tryMove(DOWN);
        }
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
    }

    public void tryMove(int dir){
        player.walk(dir);
        for(Wall wall:walls){
            if(player.collide(wall.getHitbox())){
                player.walk((dir+2)%4);
                return;
            }
        }
    }

    public void paintComponent(Graphics g){
        g.drawImage(map,0,0,null);
        g.setColor(Color.GREEN);
        g.fillOval(player.getX()-5,player.getY()-5,10,10);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
}