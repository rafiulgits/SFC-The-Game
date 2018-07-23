package game.operator;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class GamePanel extends JPanel 
        implements Runnable, KeyListener,MouseListener{

    private Thread runnerThread;
    private GameManager manager;
    private BufferedImage screen;
    private Graphics2D pane;
    
    protected static boolean gameRunning;
    private boolean gameOver;
    
    public GamePanel() {
        super();
        
        setPreferredSize(new Dimension(Game.WIDTH, Game.HEIGHT));
        setBackground(Color.WHITE);
        setFocusable(true);
        requestFocus();
    }
    private void initial(){
        manager = new GameManager();
        screen = new BufferedImage(Game.WIDTH, Game.HEIGHT,BufferedImage.TYPE_INT_ARGB);
        pane = (Graphics2D )screen.getGraphics();    
        
        gameRunning = true;
        gameOver = false;
    }
    
    private void update(){
        manager.update();
    }
    private void draw(){
        manager.draw(pane);
    }
    private void render(){
        Graphics graph = this.getGraphics();
        graph.drawImage(screen, 0, 0, null);
        graph.dispose();
    }
    
    /**
     * Override methods
    */
    @Override
    public void addNotify() {
        super.addNotify(); 
        
        if(runnerThread == null){
            addKeyListener(this);
            addMouseListener(this);
            runnerThread = new Thread(this);
            runnerThread.start();
        }
    }
    
    @Override
    public void run() {
        initial();
        long before, dif, wait;
        
        while(gameRunning){
            before = System.nanoTime();
            
            //rendering
            update();
            draw();
            render();
            
            dif = System.nanoTime() - before;
            dif /= 10e6;
            wait = 1000/Game.FPS - dif;
            if(wait<0)
                wait = 5;
            try{
                Thread.sleep(wait);
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        manager.keyTyped(e.getKeyCode());
    }

    @Override
    public void keyPressed(KeyEvent e) {
        manager.keyPressed(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        manager.mouseClicked(e.getX(), e.getY());
    }
    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
       
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        
    }
    
}
