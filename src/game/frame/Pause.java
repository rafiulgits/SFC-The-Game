package game.frame;

import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.structure.Sound;
import object.structure.Square;

/**
 *
 * @author rafiul islam
 */
public class Pause extends Window{
    
    private static String[] options = {"Resume","Dashboard","Main Menu","Exit"};
    private Square[] options_pos;
    private int now;
    private Font font;
    private Sound selectionSound;
    
    public Pause(GameManager manager){
        this.manager = manager;
        
        font = Game.Fonts.getFont("TooneyNoodleNF.ttf", Font.BOLD, 50);
        selectionSound = Game.getSound("option.wav");
        options_pos = new Square[options.length];
        options_pos[0] = new Square(340,100);
        options_pos[1] = new Square(295,180);
        options_pos[2] = new Square(290,260);
        options_pos[3] = new Square(390,340);
        now = 0;
    }
    
    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setBackground(Game.Colors.darkBlue);
        graph.setFont(font);
        int posX = 100;
        int posY = 50;
        for(int i=0; i<options.length; i++){
            if(i == now){
                graph.setColor(Color.GREEN);
            }
            else{
                graph.setColor(Color.WHITE);
            }
            graph.drawString(options[i],options_pos[i].getX(),options_pos[i].getY());
        }
    }
    
    @Override
    public void resume(){
        
    }
    @Override
    public void keyPressed(int key) {
        if(key == KeyEvent.VK_UP){
            selectionSound.play();
            switch(now){
                case 0: now = 3; break;
                case 1: now = 0; break;
                case 2: now = 1; break;
                case 3: now = 2; break;
            }
        }
        if(key == KeyEvent.VK_DOWN){
            selectionSound.play();
            switch(now){
                case 0: now = 1; break;
                case 1: now = 2; break;
                case 2: now = 3; break;
                case 3: now = 0; break;
            }
        }
        if(key == KeyEvent.VK_ENTER){
            switch(now){
                case 0: manager.loadReleased();break;
                case 1: manager.loadWindow(Window.DASHBOARD);break;
                case 2: manager.loadWindow(Window.MENU);break;
                case 3: manager.gameExit();break;
            }
        }
        if(key == KeyEvent.VK_ESCAPE){
            manager.loadReleased();
        }
    }

    @Override
    public void mouseClickd(int x, int y) {
        
    }

    @Override
    protected void finalize(){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    } 
}
