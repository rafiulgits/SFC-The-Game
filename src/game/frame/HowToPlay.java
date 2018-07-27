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
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author rafiul islam
 */
public class HowToPlay extends Window{
    /**
     * Organize HowToPlay activity
     */
    
    private ArrayList<String> store;
    private Font textFont,headerFont;
    private int yPos;
    
    public HowToPlay(GameManager manager){
        this.manager = manager;
        
        store = new ArrayList<>();
        textFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD,16);
        headerFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD,30);
        loadFile();
    }
    private void loadFile(){
        String data = "";
        File file = new File("res/files/instruction.FILE");
        try{
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp=br.readLine()) != null){
                data+=temp;
            }
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HowToPlay.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HowToPlay.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringTokenizer st = new StringTokenizer(data,"#");
        while(st.hasMoreElements()){
            store.add(st.nextElement().toString());
        }
    }

    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setBackground(Game.Colors.darkBlue);
        graph.setColor(Color.red);
        graph.setFont(headerFont);
        graph.drawString("Game Concept",300, 60);
        graph.setColor(Color.WHITE);
        graph.setFont(textFont);
        yPos = 150;
        for(String s:store){
            graph.drawString(s, 50, yPos);
            yPos+=20;
        }
    }

    @Override
    public void resume() {
        
    }

    @Override
    public void keyPressed(int key) {
        if(key==KeyEvent.VK_ESCAPE || key==KeyEvent.VK_BACK_SPACE){
            manager.loadReleased();
        }
    }

    @Override
    public void mouseClickd(int x, int y) {
        
    }
    
}
