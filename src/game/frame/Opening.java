package game.frame;

import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import object.structure.Sound;

/**
 *
 * @author rafiul islam
 */
public class Opening extends Window{
    /**
     * Opening class is stand for manage game opening view functionality
     */

    private static final int TIME = 4;
    private Image anim;
    private Font titleFont,textFont;
    private volatile Sound background;
    
    public Opening(GameManager manager){
        this.manager = manager;

        anim = Game.getImage("/animation/opening.gif");
        titleFont = Game.Fonts.getFont("AGENTRED.TTF",Font.BOLD,40);
        textFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD,40);
        
        background = Game.getSound("opening_page.wav");
        counter();
        
    }
    /**
     * This method is called from constructor after constructor loaded its 
     * parameters; this method start a thread and make a count down for closing 
     * this opening page and call game menu
     */
    private void counter(){
        background.play();
        new Thread(new Runnable(){
            public void run(){
                try{
                    Thread.sleep(TIME*1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }
                manager.loadWindow(Window.MENU);
            }
        }).start();
    }
    @Override
    public void update() {
        
    }
    
    @Override
    public void draw(Graphics2D graph) {
        graph.setBackground(Game.Colors.softGray);
        graph.drawImage(anim,0,0,Game.WIDTH,Game.HEIGHT, null);
        graph.setColor(Color.BLACK);
        graph.setFont(titleFont);
        graph.drawString("SFC - The Game",200,520); 
        graph.setFont(textFont);
        graph.drawString("Save From Cancer", 260, 570);
    }
    
    @Override
    public void resume(){
        
    }
    @Override
    public void keyPressed(int key) {
        
    }

    @Override
    public void mouseClickd(int x, int y) {
        System.out.println();
    }
    
}
