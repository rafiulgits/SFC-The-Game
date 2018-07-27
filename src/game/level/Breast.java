package game.level;

import game.frame.Dashboard;
import game.frame.NewUser;
import game.frame.Window;
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
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import object.structure.MotionCell;
import object.structure.Sound;
import object.structure.Square;

/**
 *
 * @author rafiul islam
 */
public class Breast extends Window{
    
    private static final int TOTAL = 8;
    private static final int GAME_TIME = 35;

    private Image background,good_cell,cancer_cell_left,cancer_cell_right;
    
    private Square background_pos;
    private MotionCell[] cell_status;
    
    //sound
    private Sound backSound,clickSound,errorSound,gameOverSound;
    private Sound cellSound,therapySound,timeOutSound,surgerySound; 
    
    private Square[] tools;
    private Image cellImg,chemoImg,radiationImg,surgeryImg,pendingCell;
    private String userName;
    private final Font toolsFont, numFont;
    private int userPoint,userCash,cell,chemo,radiation,surgery; 
    private int currentPoint,currentCash,selectedTool,cellCounter;
    private boolean toolsClicked,cellClicked;
    //timer
    private long miliSec, pausedTime;
    private int counter;
    //gameOver detection 
    private boolean running,paused;
    
    public Breast(GameManager manager){
        this.manager = manager;
        running = true;
        paused = false;
        toolsClicked = cellClicked = false;
        loadBackground();
        loadCells();
        loadInfo();
        loadTools();
        loadSounds();
        currentPoint = currentCash = cellCounter = 0;
        selectedTool = -1;
        
        miliSec = System.currentTimeMillis();
        counter = 0;
        
        toolsFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD,30);
        numFont = Game.Fonts.getFont("mistv.ttf",Font.BOLD,40);
        
        backSound.loop();
    }
    
    private void loadBackground(){
        background = Game.getImage("/image/level/breast/background.jpg");
        background_pos = new Square(0,0,Game.WIDTH,Game.HEIGHT);
    }
    private void loadCells(){
        good_cell = Game.getImage("/image/level/cell.png");
        cancer_cell_left = Game.getImage("/image/level/cancer_left.png");
        cancer_cell_right = Game.getImage("/image/level/cancer_right.png");
        pendingCell = Game.getImage("/image/level/cell_bw.png");
        
        cell_status = new MotionCell[TOTAL];
        
        cell_status[0] = new MotionCell(520,520,45,44,true);
        cell_status[0].setMotion(480,400,1,2);
        cell_status[1] = new MotionCell(450,470,45,45,true);
        cell_status[1].setMotion(400, 370, 2, 3);
        cell_status[2] = new MotionCell(580,530,48,48,true);
        cell_status[2].setMotion(500,400,1,3 );
        cell_status[3] = new MotionCell(640,510,42,42,true);
        cell_status[3].setMotion(640,370,0,3);
        cell_status[4] = new MotionCell(690,460,38,38,true);
        cell_status[4].setMotion(690,300,0,4);
        cell_status[5] = new MotionCell(850,550,43,43,true);
        cell_status[5].setMotion(850, 500,0, 2);
        cell_status[6] = new MotionCell(350,520,45,45,true);
        cell_status[6].setMotion(200, 480, 3, 1);
        cell_status[7] = new MotionCell(280,540,47,47,true);
        cell_status[7].setMotion(120,480,6,1); 
        
        
    }
    private void loadInfo(){
        File file = new File("res/files/info.FILE");
        String store = "";
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            String temp;
            while((temp=br.readLine()) != null){
                store += temp;
            }
            br.close();
            fr.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex){
            Logger.getLogger(Dashboard.class.getName()).log(Level.SEVERE, null, ex);
        }
        StringTokenizer st = new StringTokenizer(store,":");
        userName = st.nextElement().toString();
        userPoint = Integer.parseInt(st.nextElement().toString());
        userCash = Integer.parseInt(st.nextElement().toString());
        cell = Integer.parseInt(st.nextElement().toString());
        chemo = Integer.parseInt(st.nextElement().toString());
        radiation = Integer.parseInt(st.nextElement().toString());
        surgery = Integer.parseInt(st.nextElement().toString());  
    }
    private void loadTools(){
        cellImg = good_cell;
        chemoImg = Game.getImage("/image/level/chemo.png");
        radiationImg = Game.getImage("/image/level/radiation.png");
        surgeryImg = Game.getImage("/image/level/scissor.png");
        
        tools = new Square[4];
        tools[0] = new Square(320,30,120,40);
        tools[1] = new Square(470,30,120,40);
        tools[2] = new Square(620,30,120,40);
        tools[3] = new Square(770,30,120,40);
    }
    private void loadSounds(){
        backSound = Game.getSound("level_background.wav");
        clickSound = Game.getSound("option.wav");
        errorSound = Game.getSound("error.wav");
        gameOverSound = Game.getSound("game_over.wav");
        cellSound = Game.getSound("cell_replace.wav");
        therapySound = Game.getSound("therapy.wav");
        timeOutSound = Game.getSound("cell_counting_over.wav");
        surgerySound = Game.getSound("surgery.wav");
    }
    private void drawBackground(Graphics2D graph){
        drawImage(background,background_pos,graph);
    }
    private void drawCells(Graphics2D graph){
        for(int i=0; i<TOTAL; i++){
            if(!cell_status[i].isAlive())
                continue;
            else if(cell_status[i].isInCounting()){
                drawImage(pendingCell,cell_status[i],graph);
            }
            else if(cell_status[i].isCancer()){
                if(i%2 == 0)
                    drawImage(cancer_cell_left,cell_status[i],graph);
                else
                    drawImage(cancer_cell_right,cell_status[i],graph);
            }
            else
                drawImage(good_cell,cell_status[i],graph);
        }
    }
    private void drawTools(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        for(int i=0; i<tools.length; i++){
            graph.fill3DRect(tools[i].getX(),tools[i].getY(),tools[i].getWidth(), 
                    tools[i].getHeight(), true);
        }
        graph.setFont(toolsFont);
        graph.setColor(Game.Colors.darkBlue);
        graph.drawString(Integer.toString(cell),380,60);
        graph.drawString(Integer.toString(chemo),530,60);
        graph.drawString(Integer.toString(radiation),680,60);
        graph.drawString(Integer.toString(surgery),830,60);
        
        graph.drawImage(cellImg,325,30,40,40,null);
        graph.drawImage(chemoImg,475,30,40,40,null);
        graph.drawImage(radiationImg,625,30,40,40,null);
        graph.drawImage(surgeryImg,775,30,40,40,null);
    }
    private void drawStatus(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        graph.fill3DRect(50,30,150,40,true);
        graph.fillOval(450,160,100,100);
        graph.fill3DRect(65,215,130,30,true);
        graph.setColor(Game.Colors.darkBlue);
        graph.setFont(toolsFont);
        graph.drawString("Remain",70,238);
        graph.drawString("Now",55,60);
        switch(selectedTool){
            case -1: break;
            case 0: graph.drawImage(cellImg,130,30,40,40,null);
                    break;
            case 1: graph.drawImage(chemoImg,130,30,40,40,null);
                    break;
            case 2: graph.drawImage(radiationImg,130,30,40,40,null);
                    break;
            case 3: graph.drawImage(surgeryImg,130,30,40,40,null);
                    break;
        }
        graph.setFont(numFont);
        graph.drawString(String.format("%02d",40-counter),470,225);
        graph.drawString(Integer.toString(TOTAL-cellCounter),110,185);
    }
    private void drawOverPage(Graphics2D graph){
        
        graph.setFont(Game.Fonts.getFont("Courier.ttf",Font.BOLD,50));
        graph.setColor(Color.WHITE);
        graph.drawString("Game Over",330,170);
        graph.setFont(toolsFont);
        graph.drawString("Earn Points:", 330, 250);
        graph.drawString(Integer.toString(currentPoint),575,250);
        graph.drawString("Earn Cash:", 330, 300);
        graph.drawString(Integer.toString(currentCash),540,300);
    }
    
    //helper methods
    private void drawImage(Image img,Square pos, Graphics2D graph){
        graph.drawImage(img,pos.getX(),pos.getY(),
                pos.getWidth(),pos.getHeight(),null);
    }
    private void timeCounter(){
        counter = (int)((System.currentTimeMillis() - miliSec)/1000);
    }
    /**
     * Override methods
    */
    @Override
    public void update() {
        if(!running || paused) return;
        for(int i=0; i<TOTAL; i++){
            cell_status[i].move();
        }
        timeCounter();
        objectiveFinish();
    }

    @Override
    public void draw(Graphics2D graph) {
        if(paused) return;
        if(running){
            drawBackground(graph);
            drawCells(graph);
            drawTools(graph);
            drawStatus(graph);
        }
        else{
            drawOverPage(graph);
        }
    }

    @Override
    public void resume(){
        paused = false;
        miliSec += (System.currentTimeMillis()-pausedTime);
        backSound.loop();
    }
    
    @Override
    public void keyPressed(int key) {
        if(key == KeyEvent.VK_ESCAPE){
            backSound.stop();
            paused = true;
            pausedTime = System.currentTimeMillis();
            manager.loadWindow(Window.PAUSE);
        }
    }

    @Override
    public void mouseClickd(int x, int y) {
        checkCellClicked(x, y);
        checkToolsClick(x, y);
    }
    @Override
    protected void finalize() {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    
    //button and tools click 
    private void checkCellClicked(int x, int y){
        cellClicked = false;
        for(int i=0; i<TOTAL; i++){
            if(!cell_status[i].isInside(x, y)){
                continue;
            }
            if(!cell_status[i].isCancer()){
                errorSound.play();
                attackReaction(i);
                cellClicked = true;
                break;
            }
            else{
                onAction(i);
                cellClicked = true;
                break;
            }
        }
        if(!cellClicked && !toolsClicked){
            errorSound.play();
            switch(selectedTool){
                case -1: break;
                case 0: if(cell>0) cell-=1; break;
                case 1: if(chemo>0) chemo-=1; break;
                case 2: if(radiation>0) radiation-=1; break;
                case 3: if(surgery>0) surgery-=1; break;
            }
        }
    }
    private void attackReaction(int index){
        if(selectedTool == -1){
            return;
        }
        else if(selectedTool == 0 && cell > 0){
            cell -= 1;
            currentPoint-=3;
        }
        else if(selectedTool == 1 && chemo > 0){
            chemo -=1;
            currentPoint-=2;
        }
        else if(selectedTool == 2 && radiation >0){
            radiation -= 1;
            currentPoint -=3;
        }
        else{
            surgery -=1;
            currentPoint -=4;
        }
    }
    private void onAction(int index){
        if(selectedTool == -1){
            return;
        }
        else if(selectedTool == 0){
            errorSound.play();
            cell-=1;
        }
        else if(selectedTool == 1 && chemo > 0){
            therapySound.play();
            chemo-=1;
            cell_status[index].setCountDown(4);
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(3900);
                    } catch (InterruptedException e) {
                    }
                    timeOutSound.play();
                    cellCounter++;
                    currentPoint +=4;
                    currentCash +=15;

                }
            }).start();
        }
        else if(selectedTool == 2 && radiation > 0){
            therapySound.play();
            radiation-=1;
            cell_status[index].setCountDown(2);
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(1900);
                    } catch (InterruptedException e) {
                    }
                    timeOutSound.play();
                    cellCounter++;
                    currentPoint += 3;
                    currentCash += 15;
                }
            }).start();
        }
        else if(selectedTool == 3 && surgery > 0){
            surgerySound.play();
            surgery-=1;
            cell_status[index].setCancer(false);
            cell_status[index].setAlive(false);
            currentPoint += 2;
            cellCounter ++;
            currentCash += 10;
        }
    }
    private void checkToolsClick(int x, int y){
        for(int i=0; i<tools.length; i++){
            if(tools[i].isInside(x, y)){
                clickSound.play();
                selectedTool = i;
                toolsClicked = true;
                break;
            }
        }
    }
    private void objectiveFinish(){
        if(cellCounter==TOTAL || counter==GAME_TIME){
            running = false;
            backSound.stop();
            gameOverSound.play();
            save();
            new Thread(new Runnable(){
                public void run(){
                    try {
                        Thread.sleep(4000);
                    } catch (InterruptedException e) {
                    }
                    gameOverSound.stop();
                    manager.loadWindow(DASHBOARD);
                }
            }).start();
        }
    }
    private void save(){
        new Thread(new Runnable(){
            public void run(){
                userCash += currentCash;
                userPoint += currentPoint;
                File file = new File("res/files/info.FILE");
                try {
                    FileWriter fw = new FileWriter(file);
                    BufferedWriter bw = new BufferedWriter(fw);
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
                    fw.flush();
                    bw.close();
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(NewUser.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }).start();
    }
}
