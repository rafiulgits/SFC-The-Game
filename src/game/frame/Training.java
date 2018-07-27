package game.frame;

import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
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
import object.structure.Sound;

/**
 *
 * @author rafiul islam
 */
public class Training extends Window{
    
    private final ArrayList<String> basic, area;
    private final Font labelFont,textFont;
    private boolean basicLoaded, areaLoaded;
    private Image anim,goodCell,cancerCell,pendingCell,chemo,radiation,surgery;
    private int selectY;
    private int now;
    private Sound selectionSound;
    
    public Training(GameManager manager) {
        this.manager = manager;
        
        labelFont = Game.Fonts.getFont("Courier.ttf", Font.BOLD, 30);
        textFont = Game.Fonts.getFont("Courier.ttf", Font.BOLD, 20);
        anim = Game.getImage("/animation/doctor.gif"); // doctor gif
        selectionSound = Game.getSound("option.wav");
        basicLoaded = areaLoaded = false;
        selectY = 340; now = 0;
        
        basic = new ArrayList<>();
        area = new ArrayList<>();
        
        loadFile("basic.FILE", basic);
        loadFile("area.FILE", area);
        loadImage();
    }
    
    //file loader method
    private void loadFile(String fileName, ArrayList<String> list){
        
        File file = new File("res//files//"+fileName);
        try{
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String loaderString; String temp = "";
            while((loaderString = br.readLine()) != null){
                temp += loaderString;
            }
            br.close();
            fr.close();
  
            StringTokenizer st = new StringTokenizer(temp,"{}");
            while(st.hasMoreElements()){
                list.add(st.nextElement().toString());
            }
            if(fileName == "basic.FILE"){
                basicLoaded = true;
            }
            else if(fileName == "area.FILE"){
                areaLoaded = true;
            }
        }catch(FileNotFoundException ex){
            ex.printStackTrace();
        }catch(IOException ex){
            ex.printStackTrace();
        }
    }
    private void loadImage(){
        goodCell = Game.getImage("/image/level/cell.png");
        cancerCell = Game.getImage("/image/level/cancer_left.png");
        pendingCell = Game.getImage("/image/level/cell_bw.png");
        chemo = Game.getImage("/image/level/chemo.png");
        radiation = Game.getImage("/image/level/radiation.png");
        surgery = Game.getImage("/image/level/scissor.png");
    }
    
    //components and string drawing methods
    private void drawPanel(Graphics2D graph){
        graph.setFont(labelFont);
        graph.setColor(Color.WHITE);
        graph.fillRect(0, 0, 300,Game.HEIGHT);
        graph.drawImage(anim,0,0,300,300,null);
        
        //selection rectangle
        graph.setColor(Game.Colors.darkBlue);
        graph.fillRect(0,selectY,300,50);
        
        //options
        graph.setColor(Color.red);
        graph.drawString("Basic",80, 375);
        graph.drawString("Area",80, 425);
        graph.drawString("Components",80, 475);
    }
    private void drawBasic(Graphics2D graph){
        graph.setColor(Color.WHITE);
        graph.setFont(textFont);
        int y = 50;
        for(String i:basic){
            if(i.length() < 50){
                graph.drawString(i, 310,y);
                y += 40;
            }
            else{
                graph.drawString(i.substring(0, 50),310,y);
                y+=40;
                graph.drawString(i.substring(50),310, y);
                y+=70;
            }
            
        }
    }
    private void drawArea(Graphics2D graph){
        graph.setColor(Color.WHITE);
        graph.setFont(textFont);
        int y = 40;
        for(int i=0; i<area.size(); i++){
            if(area.get(i).length() < 50){
                graph.drawString(area.get(i),310,y);
                y+=35;
            }
            else{
                graph.drawString(area.get(i).substring(0,50),310,y);
                y+=35;
                graph.drawString(area.get(i).substring(50),310,y);
                y+=35;
            }
        }
    }
    private void drawComponet(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        graph.fill3DRect(305, 5, 50, 50, true);
        graph.fill3DRect(305, 85, 50, 50, true);
        graph.fill3DRect(305, 165, 50, 50, true);
        graph.fill3DRect(305, 245, 50, 50, true);
        graph.fill3DRect(305, 325, 50, 50, true);
        graph.fill3DRect(305, 405, 50, 50, true);
        
        graph.drawImage(goodCell,310,10,40,40,null);
        graph.drawImage(cancerCell,310,90,40,40,null);
        graph.drawImage(pendingCell,310,170,40,40,null);
        graph.drawImage(chemo,310,250,40,40,null);
        graph.drawImage(radiation,310,330,40,40,null);
        graph.drawImage(surgery,310,410,40,40,null);
        
        graph.setFont(textFont);
        graph.setColor(Color.WHITE);
        graph.drawString("Normal Cell", 370, 35);
        graph.drawString("Cancer Cell", 370, 115);
        graph.drawString("Cell Under Therapy", 370, 195);
        graph.drawString("Chemotherapy", 370, 275);
        graph.drawString("Radiation Therapy", 370, 355);
        graph.drawString("Surgery", 370, 435);
    }
    //override methods
    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D graph) {
        if(basicLoaded && areaLoaded){
            drawPanel(graph);
            
            graph.setFont(labelFont);
            graph.setColor(Color.red);
            switch(now){
                case 0: drawBasic(graph); break;
                case 1: drawArea(graph); break;
                case 2: drawComponet(graph); break;
            }
        }
    }

    @Override
    public void keyPressed(int key) {
        if(key == KeyEvent.VK_UP){
            selectionSound.play();
            switch(now){
                case 0: now = 2; selectY = 440; break;
                case 1: now = 0; selectY = 340; break;
                case 2: now = 1; selectY = 390; break;
            }
        }
        else if(key == KeyEvent.VK_DOWN){
            selectionSound.play();
            switch(now){
                case 0: now = 1; selectY = 390; break;
                case 1: now = 2; selectY = 440; break;
                case 2: now = 0; selectY = 340; break;
            }
        }
        else if(key==KeyEvent.VK_ESCAPE || key==KeyEvent.VK_BACK_SPACE){
            manager.loadWindow(Window.MENU);
        }
    }

    @Override
    public void resume(){
        
    }
    @Override
    public void mouseClickd(int x, int y) {
        //options select checking 
        if(x>=0 && x <=300){
            if(y>=340 && y<390){
                selectY = 340;
                now = 0;
            }
            else if(y>=390 && y<440){
                selectY = 390;
                now = 1;
            }
            else if(y>=440 && y<490){
                selectY = 440;
                now = 2;
            }
        }
    }
    
    @Override
    protected void finalize(){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
}
