package game.frame;

import game.operator.Game;
import game.operator.GameManager;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.structure.Sound;
import object.structure.Square;

/**
 *
 * @author rafiul islam
 */
public class Dashboard extends Window{
    /**
     * Dashboard will generate a question box when create an object
     * of this and display it for next objective.
     * @see loadQuestion()
     */
    
    private static final String[][] QUESTIONS =
        {{"Bleeding","Pain in the bones or joints","Weight loss"},
        {"Breast pain","Nipple discharge","Unexpected flued in breast"},
        {"Pain in chest area","Change in cough","Breathing change"}};
    private static final String[] AREA = {"Blood", "Breast", "Lung"};
    private Image cellImg,chemoImg,radioImg,surgeryImg;
    private int ROW,NON_COL,OP_ROW,OP_COL;
    private Font font,playBtFont,userInfoFont,usernameFont;
    private Random random;  
    private Square playButton;
    private Square[] areasBt,toolBt;  
    private int areaSelected;
    private int userCash, userPoint;
    private int cell,radiation,chemo,surgery;
    private String userName;
    private String highScorer,highScore;

    private Sound backSound,clickSound,errorSound;
    
    public Dashboard(GameManager manager){
        this.manager = manager;
        random = new Random();
        font = Game.Fonts.getFont("courier.ttf",Font.BOLD,20);
        playBtFont = Game.Fonts.getFont("blow.ttf",Font.BOLD,37);
        userInfoFont =  Game.Fonts.getFont("courier.ttf",Font.BOLD,25);
        usernameFont = Game.Fonts.getFont("courier.ttf",Font.BOLD,40);
        backSound = Game.getSound("dashboard.wav");
        clickSound = Game.getSound("option.wav");
        errorSound = Game.getSound("error.wav");
        backSound.loop();
       
        loadImages();
        loadInformations();
        loadButtons();
        loadQuestions();
    }
    private void loadImages(){
        cellImg = Game.getImage("/image/level/cell.png");
        chemoImg = Game.getImage("/image/level/chemo.png");
        radioImg = Game.getImage("/image/level/radiation.png");
        surgeryImg = Game.getImage("/image/level/scissor.png");
    }
    private void loadInformations(){    
        String store = "";
        try {
            File file = new File("res/files/info.FILE");
            BufferedReader br = new BufferedReader(new FileReader(file));
            String temp;
            while((temp=br.readLine()) != null){
                store += temp;
            }
            br.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        StringTokenizer st = new StringTokenizer(store,":");
        userName = st.nextElement().toString();
        userPoint = Integer.parseInt(st.nextElement().toString());
        userCash = Integer.parseInt(st.nextElement().toString());
        //Cell, Chemo, Radiation, Surgery
        cell = Integer.parseInt(st.nextElement().toString());
        chemo = Integer.parseInt(st.nextElement().toString());
        radiation = Integer.parseInt(st.nextElement().toString());
        surgery = Integer.parseInt(st.nextElement().toString());
        
        //load highScore file
        store = "";
        try{
            File file = new File("res/files/high.FILE");
            BufferedReader br = new BufferedReader(new FileReader(file));
            store = br.readLine();
            br.close();
        }catch (FileNotFoundException ex){
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }catch(IOException ex){
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        st = new StringTokenizer(store,":");
        highScorer = st.nextElement().toString();
        highScore = st.nextElement().toString();
    }
    private void loadButtons(){
        areasBt = new Square[3];
        areasBt[0] = new Square(540,420,130,48);
        areasBt[1] = new Square(690,420,130,48);
        areasBt[2] = new Square(840,420,130,48);
        
        toolBt = new Square[4];
        toolBt[0] = new Square(40,220,170,45);
        toolBt[1] = new Square(240,220,150,45);
        toolBt[2] = new Square(420,220,230,45);
        toolBt[3] = new Square(680,220,195,45);
        
        areaSelected = -1;
        
        playButton = new Square(660,504,220,50);
    }
    private void loadQuestions(){
        //ROW,OP-ROW
        int[][] indices = {{0,1},{1,0},{0,2},{2,0},{1,2},{2,1}};
        int rand = random.nextInt(indices.length-1);
        ROW = indices[rand][0];
        OP_ROW = indices[rand][1];
        OP_COL = random.nextInt(2);
        NON_COL = random.nextInt(2);
    }
    private void drawUserTools(Graphics2D graph){
        graph.setColor(Color.WHITE);
        graph.fill3DRect(500,30,200,60,true);
        graph.fill3DRect(710,30,200,60,true);
        graph.fill3DRect(500,100,200,60,true);
        graph.fill3DRect(710,100,200,60, true);
        graph.drawImage(cellImg,510,40,40,40,null);
        graph.drawImage(chemoImg,720,40,40,40,null);
        graph.drawImage(radioImg,510,110,40,40,null);
        graph.drawImage(surgeryImg,720,110,40,40,null);
        
        graph.setFont(usernameFont);
        graph.setColor(Game.Colors.darkBlue);
        graph.drawString(Integer.toString(cell), 570, 70);
        graph.drawString(Integer.toString(chemo), 780, 70);
        graph.drawString(Integer.toString(radiation), 570, 140);
        graph.drawString(Integer.toString(surgery), 780, 140);
    }
    private void drawComponets(Graphics2D graph){
        //user informations
        graph.setColor(Color.WHITE);
        graph.setFont(usernameFont);
        graph.drawString(userName, 20, 50);
        graph.setFont(userInfoFont);
        graph.drawString("Points:", 20, 90);
        graph.drawString(Integer.toString(userPoint),140,90);
        graph.drawString("Credit:", 20, 135);
        graph.drawString("$"+Integer.toString(userCash),140,135);
        graph.drawString("Record:"+highScorer+"("+highScore+")",20, 180);
        
        //play button
        graph.setColor(Color.LIGHT_GRAY);
        graph.fill3DRect(playButton.getX(),playButton.getY(),playButton.getWidth(),
                playButton.getHeight(),true);
        
        graph.setColor(Color.BLACK);
        graph.setFont(playBtFont);
        graph.drawString("Lets Play!", 670, 540);
    }
    private void drawQuestionsBox(Graphics2D graph){
        //drawing rectangle box
        graph.setFont(font);
        graph.setColor(Color.WHITE);
        graph.fillRect(40, 360, 480, 30);
        graph.drawRect(40, 360, 479, 200);
        //line
        graph.drawLine(0, 190, Game.WIDTH, 190);
        graph.drawLine(0, 310, Game.WIDTH, 310);
        graph.setColor(Color.BLACK);
        graph.drawString("Symtoms",220, 380);
        
        graph.setColor(Color.WHITE);
        for(int i=0,posY=420; i<3; i++,posY+=50){
            if(i == NON_COL){
                graph.drawString(QUESTIONS[OP_ROW][OP_COL],60,posY);
                continue;
            }
            graph.drawString(QUESTIONS[ROW][i],60,posY);
        }
        
    }
    private void drawAreas(Graphics2D graph){
        for(int i=0; i<areasBt.length; i++){
            graph.setColor(Color.WHITE);
            if(i == areaSelected){
                graph.setColor(Color.GREEN);
            }
            graph.fill3DRect(areasBt[i].getX(),areasBt[i].getY(),
                    areasBt[i].getWidth(),areasBt[i].getHeight(), true);
        }
        graph.setFont(userInfoFont);
        graph.setColor(Game.Colors.darkBlue);
        graph.drawString("Blood",560,450);
        graph.drawString("Breast",707,450);
        graph.drawString("Lung",870,450);
    }
    private void drawToolShop(Graphics2D graph){
        graph.setColor(Color.WHITE);
        for(int i=0; i<toolBt.length; i++){
            graph.fill3DRect(toolBt[i].getX(),toolBt[i].getY(),toolBt[i].getWidth(),
                    toolBt[i].getHeight(), true);
        }
        graph.setColor(Color.BLACK);
        graph.setFont(userInfoFont);
        graph.drawString("Chemo $10", 50,250);
        graph.drawString("Cell $15",250,250);
        graph.drawString("Radiation $15",430,250);
        graph.drawString("Surgery $20", 690,250);
        
    }
    //override methods
    @Override
    public void update() {
        if(userPoint > Integer.parseInt(highScore)){
            highScore = Integer.toString(userPoint);
            highScorer = userName;
            saveHighScore();
        }
    }

    @Override
    public void draw(Graphics2D graph) {
        graph.setBackground(Game.Colors.darkBlue);
        drawUserTools(graph);
        drawComponets(graph);
        drawQuestionsBox(graph);
        drawAreas(graph);
        drawToolShop(graph);
        
    }
    
    @Override
    public void resume(){
        backSound.loop();
    }
    @Override
    public void keyPressed(int key) {
        switch(key){
            case KeyEvent.VK_ESCAPE:
                backSound.stop();
                manager.loadWindow(Window.PAUSE);
                break;
        }
    }

    @Override
    public void mouseClickd(int x, int y) {
        checkToolsClick(x, y);
        checkAreaClick(x, y);
        checkPlayClick(x, y);
    }

    @Override
    protected void finalize() {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    
    //button clicked checker methods
    private void checkToolsClick(int x, int y){
        for(int i=0; i<toolBt.length; i++){
            if(toolBt[i].isInside(x, y)){
                switch(i){
                    case 0: if(userCash>=10 && chemo<10)
                                {userCash-=10;chemo++;save();clickSound.play();}
                            else
                                errorSound.play();
                            break;
                    case 1: if(userCash>=15 && cell<10)
                                {userCash-=15;cell++;save();clickSound.play();}
                            else
                                errorSound.play();
                            break;
                    case 2: if(userCash>=15 && radiation<10)
                                {userCash-=15;radiation++;save();clickSound.play();}
                            else
                                errorSound.play();
                            break;
                    case 3: if(userCash>=20 && surgery<10)
                                {userCash-=20;surgery++;save();clickSound.play();}
                            else    
                                errorSound.play();
                            break;
                }
            }
        }
    }
    private void checkAreaClick(int x, int y){
        for(int i=0; i<areasBt.length; i++){
            if(areasBt[i].isInside(x, y)){
                areaSelected = i;
                clickSound.play();
            }
        }
    }
    private void checkPlayClick(int x, int y){
        if(playButton.isInside(x, y)){
            if(ROW == areaSelected){
                switch(areaSelected){
                    case 0: backSound.stop(); manager.loadWindow(BLOOD); break;
                    case 1: backSound.stop(); manager.loadWindow(BREAST);break;
                    case 2: backSound.stop(); manager.loadWindow(LUNG);break;
                }
            }
            else{
                errorSound.play();
            }
        }
    }
    private void save(){
        File file = new File("res/files/info.FILE");
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            /**  
             ** File:1 will write 
             * 1. Username
             * 2. Initial Point = 0,
             * 3. Initial User cash = 0,
             * 4. Cell
             * 5. Chemo
             * 6. Radiation
             * 7. Surgery 
             */
            bw.write(userName+":"+Integer.toString(userPoint)+":"+Integer.toString(userCash)
                    +":"+Integer.toString(cell)+":"+Integer.toString(chemo)+":"+
                    Integer.toString(radiation)+":"+Integer.toString(surgery));
            
            bw.flush();
            bw.close();
        } catch (IOException ex) {
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void saveHighScore(){
        File file = new File("res/files/high.FILE");
        try{
            BufferedWriter bw = new BufferedWriter(new FileWriter(file));
            bw.write(highScorer+":"+highScore);
            bw.flush();
            bw.close();
        } catch(FileNotFoundException ex){
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        } catch(IOException ex){
            Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
