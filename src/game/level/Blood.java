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
import object.structure.Cell;
import object.structure.Sound;
import object.structure.Square;

/**
 *
 * @author rafiul islam
 */
public class Blood extends Window{

    private static final int TOTAL = 9;
    private static final int GAME_TIME = 20;
    
    private Image background,good_cell,cancer_cell_left,cancer_cell_right;
    
    private Cell background_pos;
    private Cell[] cell_status;
    
    private Sound backSound,clickSound,errorSound,gameOverSound;
    private Sound cellSound,therapySound,timeOutSound,surgerySound;
    
    private Image cellImg,chemoImg,radiationImg,surgeryImg,pendingCell;
    private int cell,chemo,radiation,surgery;
    private Square[] tools;
    private Font toolsFont,numFont;
    
    //from file
    private String userName;
    private int userPoint, userCash;
    //gameplay status
    private int selectedTool,cellCounter,currentPoint,currentCash;
    private boolean cellClicked,toolsClicked;
    //timer
    private long miliSec;
    private int counter;
    private long pausedTime;
    //game over detection
    private boolean running,paused;
    
    public Blood(GameManager manager){
        this.manager = manager;
        running = true;
        paused = false;
        cellClicked = toolsClicked = false;
        selectedTool = -1;
        cellCounter = currentPoint = currentCash = 0;
        toolsFont = Game.Fonts.getFont("Courier.ttf",Font.BOLD,30);
        numFont = Game.Fonts.getFont("mistv.ttf",Font.BOLD,40);
        
        loadBackground();
        loadCells();
        loadInfo();
        loadTools();
        loadSounds();
        
        miliSec = System.currentTimeMillis();
        counter = 0;
        
        backSound.loop();
    }
    
    private void loadBackground(){
        background = Game.getImage("/image/level/blood/background.jpg");
        background_pos = new Cell(0,0,Game.WIDTH,Game.HEIGHT);
    }
    private void loadCells(){
        good_cell = Game.getImage("/image/level/cell.png");
        cancer_cell_left = Game.getImage("/image/level/cancer_left.png");
        cancer_cell_right = Game.getImage("/image/level/cancer_right.png");
        pendingCell = Game.getImage("/image/level/cell_bw.png");
        
        cell_status = new Cell[TOTAL];
        
        cell_status[0] = new Cell(470,240,50,50,true);
        cell_status[1] = new Cell(530,240,50,50,true);
        cell_status[2] = new Cell(590,240,50,50,true);
        cell_status[3] = new Cell(470,320,50,50,true);
        cell_status[4] = new Cell(530,320,50,50,true);
        cell_status[5] = new Cell(590,320,50,50,true);
        cell_status[6] = new Cell(470,400,50,50,true);
        cell_status[7] = new Cell(530,400,50,50,true);
        cell_status[8] = new Cell(590,400,50,50,true);
    }
    private void loadInfo(){
        File file = new File("res/files/info.FILE");
        String store = "";
        try {
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
        cell = Integer.parseInt(st.nextElement().toString());
        chemo = Integer.parseInt(st.nextElement().toString());
        radiation = Integer.parseInt(st.nextElement().toString());
        surgery = Integer.parseInt(st.nextElement().toString());  
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
    private void loadTools(){
        cellImg = good_cell;
        chemoImg = Game.getImage("/image/level/chemo.png");
        radiationImg = Game.getImage("/image/level/radiation.png");
        surgeryImg = Game.getImage("/image/level/scissor.png");
        
        
        tools = new Square[4];
        tools[0] = new Square(250,10,120,40);
        tools[1] = new Square(400,10,120,40);
        tools[2] = new Square(550,10,120,40);
        tools[3] = new Square(700,10,120,40);
    }
    
    
    private void drawBackground(Graphics2D graph){
        drawImage(background, background_pos, graph);
    }
    private void drawCells(Graphics2D graph){   
        for(int i=0; i<TOTAL; i++){
            if(!cell_status[i].isAlive())
                continue;
            else if(cell_status[i].isInCounting()){
                drawImage(pendingCell,cell_status[i], graph);
                continue;
            }
            else if(cell_status[i].isCancer()){
                drawImage(cancer_cell_left,cell_status[i], graph);
            }
            else
                drawImage(good_cell,cell_status[i], graph);
            
        }
    }
    private void drawTools(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        for(int i=0; i<tools.length; i++){
            graph.fill3DRect(tools[i].getX(),tools[i].getY(),tools[i].getWidth(), 
                    tools[i].getHeight(), true);
        }
        graph.drawImage(cellImg,255,10,40,40,null);
        graph.drawImage(chemoImg,405,10,40,40,null);
        graph.drawImage(radiationImg,555,10,40,40,null);
        graph.drawImage(surgeryImg,715,10,40,40,null);
        
        graph.setColor(Game.Colors.darkBlue);
        graph.setFont(toolsFont);
        graph.drawString(Integer.toString(cell),310, 40);
        graph.drawString(Integer.toString(chemo),460, 40);
        graph.drawString(Integer.toString(radiation),610,40);
        graph.drawString(Integer.toString(surgery),765, 40);
        
    }
    private void drawStatus(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        graph.fill3DRect(450, 70, 200, 40, true);
        graph.setColor(Game.Colors.softSky);
        graph.fillOval(830,100, 120, 70);
        graph.fillRect(210,150,150,30);
        graph.fillOval(230,170,110,70);
        graph.setFont(toolsFont);
        graph.setColor(Game.Colors.darkBlue);
        graph.drawString("Remain",225,175);
        graph.drawString("Now:",460,100);
        graph.setFont(numFont);
        graph.setColor(Game.Colors.treeGreen);
        graph.drawString(String.format("%02d",GAME_TIME-counter),860,150);
        graph.drawString(String.format("%d",TOTAL-cellCounter),270, 220);
        graph.drawString("Points: "+Integer.toString(currentPoint),435,170);
        switch(selectedTool){
            case -1: break;
            case 0: graph.drawImage(cellImg,570,70,40,40,null);
                    break;
            case 1: graph.drawImage(chemoImg,570,70,40,40,null);
                    break;
            case 2: graph.drawImage(radiationImg,570,70,40,40,null);
                    break;
            case 3: graph.drawImage(surgeryImg,570,70,40,40,null);
                    break;
        }
    }
    //helper methods
    private void drawImage(Image img,Cell pos, Graphics2D graph){
        graph.drawImage(img,pos.getX(),pos.getY(),
                pos.getWidth(),pos.getHeight(),null);
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
    private void timeCounter(){
        counter = (int)((System.currentTimeMillis() - miliSec)/1000);
    }
   
    private void objectiveFinish(){
        if(counter==GAME_TIME || cellCounter==TOTAL){
            backSound.stop();
            gameOverSound.play();
            running = false;
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
    /**
     * Override methods
    */
    @Override
    public void update() {
        if(!running || paused) return;
        timeCounter();
        objectiveFinish();
        //cells flow
        for(int i=0; i<TOTAL; i++){
            cell_status[i].setY(cell_status[i].getY()+1);
            if(cell_status[i].getY()>500)
                cell_status[i].setY(240);
        }
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
        if(paused) return;
        
        if(key == KeyEvent.VK_ESCAPE){
            backSound.stop();
            pausedTime = System.currentTimeMillis();
            paused = true;
            manager.loadWindow(Window.PAUSE);
        }
    }

    @Override
    public void keyTyped(int key) {
        
    }

    @Override
    public void mouseClickd(int x, int y) {
        if(paused) return;
        
        checkCellClicked(x, y);
        checkToolsClick(x, y);
        toolsClicked = false;
    }
    @Override
    protected void finalize() {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    // Button events and file save 
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
        else if(selectedTool == 0 && cell>0){
            cellSound.play();
            cell-=1;
            currentPoint+=3;
            currentCash+=15;
            cellCounter++;
            cell_status[index].setCancer(false);
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
            errorSound.play();
            surgery-=1;
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
    
    private void save(){
        new Thread(new Runnable(){
            public void run(){
                userCash += currentCash;
                userPoint += currentPoint;
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
        }).start();
    }
}
