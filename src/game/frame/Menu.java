package game.frame;

import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.structure.Sound;
import object.structure.Square;

/**
 *
 * @author rafiul islam
 */
public class Menu extends Window{
    private static String[] options = {"New Game","Load Game","How To Play","Training","Exit"};
    private Square[] options_pos;
    private int now;
    private Sound selectionSound;
    private Font font;
    public Menu(GameManager manager) {
        this.manager = manager;
        
        font = Game.Fonts.getFont("TooneyNoodleNF.ttf", Font.BOLD, 50);
        selectionSound = Game.getSound("option.wav");
        options_pos = new Square[options.length];
        
        options_pos[0] = new Square(340,100);
        options_pos[1] = new Square(330,180);
        options_pos[2] = new Square(310,260);
        options_pos[3] = new Square(350,340);
        options_pos[4] = new Square(420,420);
        
        now = 0;
    }
    
    //override methods
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
            graph.drawString(options[i], options_pos[i].getX(), options_pos[i].getY());
        }
    }

    @Override
    public void resume(){
        
    }
    
    @Override
    public void keyPressed(int key) {
        if(key == KeyEvent.VK_UP){
            selectionSound.play();
            if(now == 0){
                now = 4;
            }
            else{
                now--;
            }
        }
        if(key == KeyEvent.VK_DOWN){
            selectionSound.play();
            if(now == 4){
                now = 0;
            }
            else{
                now++;
            }
        }
        if(key == KeyEvent.VK_ENTER){
            switch(now){
                case 0: manager.loadWindow(Window.NEW_USER); break;
                case 1:{
                    if(isSaved()){
                        manager.loadWindow(Window.DASHBOARD);
                    }
                    else{          
                        
                    }                  
                }break;
                case 2: manager.loadWindow(Window.HOW_TO_PLAY); break;
                case 3: manager.loadWindow(Window.TRAININHG); break;
                case 4: manager.gameExit(); break;
            }
        }
    }
    @Override
    public void mouseClickd(int x, int y) {
        
    }
    
    @Override
    protected void finalize(){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    
    private boolean isSaved(){
        File file = new File("res/files/info.FILE");
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String temp = br.readLine();
            br.close();
            fr.close();
            if(temp.substring(0,1).equals(" ")){
                return false;
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
