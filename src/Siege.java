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
    private Player player1, player2;
    private Siege mainFrame;
    public static final int LEFT=0, UP=1, RIGHT=2, DOWN=3;
    ArrayList<Wall> walls;

    public GamePanel(Siege m){
        player1=new Player(490,310,67,3);
        player2= new Player(490, 380,150,2);
        walls=genWalls();
        keys = new boolean[KeyEvent.KEY_LAST+1];
        addKeyListener(this);
        mainFrame=m;
    }

    private ArrayList<Wall> genWalls(){
        ArrayList<Wall>walls=new ArrayList<Wall>();
        walls.add(new Wall(new Rectangle(875,497,11,211)));
        walls.add(new Wall(new Rectangle(875,423,11,32)));    // 455 ˅ door
        walls.add(new Wall(new Rectangle(875,187,11,151)));  // 337 ˅ soft
        walls.add(new Wall(new Rectangle(367,187,351,11))); // 718 > soft
        walls.add(new Wall(new Rectangle(779,187,112,11)));
        walls.add(new Wall(new Rectangle(367,187,11,180)));
        walls.add(new Wall(new Rectangle(367,355,56,11)));
        walls.add(new Wall(new Rectangle(411,355,11,206)));
        walls.add(new Wall(new Rectangle(366,551,56,11)));
        walls.add(new Wall(new Rectangle(366,551,11,60)));
        walls.add(new Wall(new Rectangle(366,600,56,11)));
        walls.add(new Wall(new Rectangle(411,600,11,109)));
        walls.add(new Wall(new Rectangle(411,697,476,11)));
        return walls;
    }

    public void addNotify() {
        super.addNotify();
        requestFocus();
        mainFrame.start();
    }

    public void move(){
        if(keys[KeyEvent.VK_D]) tryMove (player1, RIGHT);
        if(keys[KeyEvent.VK_A]) tryMove(player1, LEFT);
        if(keys[KeyEvent.VK_W]) tryMove(player1, UP);
        if(keys[KeyEvent.VK_S]) tryMove(player1, DOWN);

        if(keys[KeyEvent.VK_RIGHT]) tryMove (player2, RIGHT);
        if(keys[KeyEvent.VK_LEFT])  tryMove(player2, LEFT);
        if(keys[KeyEvent.VK_UP]) tryMove(player2, UP);
        if(keys[KeyEvent.VK_DOWN]) tryMove(player2, DOWN);

        /*
        Point mouse = MouseInfo.getPointerInfo().getLocation();
        Point offset = getLocationOnScreen();
        System.out.println("("+(mouse.x-offset.x)+", "+(mouse.y-offset.y)+")");
         */
    }

    public void tryMove(Player player, int dir){
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
        g.fillOval(player1.getX()-5,player1.getY()-5,10,10);
        g.setColor(Color.blue);
        g.fillOval(player2.getX()-5,player2.getY()-5,10,10);
    }

    public void keyTyped(KeyEvent e) {}
    public void keyPressed(KeyEvent e) { keys[e.getKeyCode()] = true; }
    public void keyReleased(KeyEvent e) { keys[e.getKeyCode()] = false; }
}