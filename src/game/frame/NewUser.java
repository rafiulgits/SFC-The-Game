package game.frame;

import game.frame.utils.FileManager;
import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.structure.Square;
import object.structure.Sound;

/**
 *
 * @author rafiul islam
 */
public class NewUser extends Window{
    private String userName;
    private final Square cancelBt,createBt;
    private final Font textFont,btFont;
    private final Sound typingSound,errorSound;
    
    public NewUser(GameManager manager){
        this.manager = manager;
        userName = "";
        
        cancelBt = new Square(250,300,200,40);
        createBt = new Square(480,300,200,40);
        
        textFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD, 30);
        btFont = Game.Fonts.getFont("TooneyNoodleNF.ttf", Font.BOLD, 25);
        
        typingSound = Game.getSound("option.wav");
        errorSound = Game.getSound("error.wav");
    }
    private void saveUser(){
        String content = userName+":"+"0:"+"400:0:0:0:0";
        String root = "res/files/info.FILE";
        FileManager.writeTheFile(root, content);
    }
    //override methods
    @Override
    public void update() {
        
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setBackground(Game.Colors.darkBlue);
        graph.setFont(textFont);
        graph.setColor(Color.WHITE);
        graph.drawString("Username",200,200);
        graph.fillRect(390,170,350,45); 
        graph.setColor(Color.BLACK);
        graph.drawString(userName+"_",400,200);
        
        graph.setColor(Color.WHITE);
        graph.fillRoundRect(cancelBt.getX(),cancelBt.getY(),cancelBt.getWidth(),
                cancelBt.getHeight(),30,150);
        graph.fillRoundRect(createBt.getX(),createBt.getY(),createBt.getWidth(),
                createBt.getHeight(),30,150);
        graph.setColor(Game.Colors.darkBlue);
        
        graph.setColor(Game.Colors.darkBlue);
        graph.drawString("Cancel",290,330);
        graph.drawString("Create",520,330); 
    }

    @Override
    public void resume(){
        
    }
    @Override
    public void keyPressed(int key) {
        if(userName.length() < 10){
            if(key>=48 && key<=57){
                typingSound.play();
                userName += (char)key;
            }
            else if(key>=65 && key<=90){
                typingSound.play();
                userName += (char)key;
            }
        }
        
        if(key == 8){
            //backspace functionality
            if(userName.length() > 0){
                typingSound.play();
                userName = userName.substring(0, userName.length()-1);
            }
            else{
                errorSound.play();
            }
                
        }
        else if(key == 10){
            //enter functionality
            if(!userName.isEmpty()){
                typingSound.play();
                saveUser();
                manager.loadWindow(Window.DASHBOARD);
            }
        }
    }

    @Override
    public void mouseClickd(int x, int y) {
        if(cancelBt.isInside(x, y)){
            typingSound.play();
            manager.loadWindow(Window.MENU);
        }
        if(createBt.isInside(x, y)){
             if(!userName.isEmpty()){
                 typingSound.play();
                 saveUser();
                 manager.loadWindow(Window.DASHBOARD);
             }
        }
    }

    @Override
    protected void finalize(){
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    
    
}
