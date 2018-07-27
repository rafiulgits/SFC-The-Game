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
public class Lung extends Window{

    private static final short TOTAL = 18;
    private static final short GAME_TIME = 30;
    
    private Image background,cage;
    private Square background_pos,cage_pos;
    
    //Cell 
    private Image good_cell,cancer_cell_left,cancer_cell_right;
    private Cell[] cell_status;
    
    //game status
    private Image cellImg,chemoImg,radiationImg,surgeryImg,pendingCell;
    private volatile int cell,chemo,radiation,surgery;
    private Square[] tools;
    private final Font toolsFont,numFont;
    
    //sound
    private Sound backSound,clickSound,errorSound,gameOverSound;
    private Sound cellSound,therapySound,timeOutSound,surgerySound; 
    
    //from file
    private String userName;
    private volatile int userPoint, userCash;
    //gameplay status
    private volatile int selectedTool,cellCounter,currentPoint,currentCash;
    private boolean toolsClicked,cellClicked;
//timer
    private long miliSec,pausedTime;
    private int counter;
    //game over detection
    private boolean running,paused;
    
    public Lung(GameManager manager){
        this.manager = manager;
        running = true;
        paused = false;
        toolsClicked = false;
        cellClicked = false;
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
        background = Game.getImage("/image/level/lung/background.jpg");
        cage = Game.getImage("/image/level/lung/cage.png");
        
        background_pos = new Square(0, 0,Game.WIDTH,Game.HEIGHT);
        cage_pos = new Square(165,200,500,335);
    }
    private void loadCells(){
        good_cell = Game.getImage("/image/level/cell.png");
        cancer_cell_left = Game.getImage("/image/level/cancer_left.png");
        cancer_cell_right = Game.getImage("/image/level/cancer_right.png");
        pendingCell = Game.getImage("/image/level/cell_bw.png");
        
        cell_status = new Cell[TOTAL];
        
        //set positions
        cell_status[0] = new Cell(320,215,50,50,true);
        cell_status[1] = new Cell(265,275,50,50,true);
        cell_status[2] = new Cell(330,275,50,50,true);
        cell_status[3] = new Cell(220,335,50,50,true);
        cell_status[4] = new Cell(280,335,50,50,true);
        cell_status[5] = new Cell(340,335,50,50,true);
        cell_status[6] = new Cell(200,395,50,50,true);
        cell_status[7] = new Cell(260,395,50,50,true);
        cell_status[8] = new Cell(320,395,50,50,true);
        cell_status[9] = new Cell(460,215,50,50,true);
        cell_status[10] = new Cell(450,275,50,50,true);
        cell_status[11] = new Cell(515,275,50,50,true);
        cell_status[12] = new Cell(445,335,50,50,true);
        cell_status[13] = new Cell(505,335,50,50,true);
        cell_status[14] = new Cell(565,335,50,50,true);
        cell_status[15] = new Cell(460,395,50,50,true);
        cell_status[16] = new Cell(520,395,50,50,true);
        cell_status[17] = new Cell(580,395,50,50,true);
        
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
        tools[0] = new Square(50,10,120,40);
        tools[1] = new Square(200,10,120,40);
        tools[2] = new Square(350,10,120,40);
        tools[3] = new Square(500,10,120,40);
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
        drawImage(background, background_pos, graph);
        drawImage(cage, cage_pos, graph);
    }
    private void drawCells(Graphics2D graph){
        for(int i=0; i<TOTAL; i++){
            if(!cell_status[i].isAlive()){
                continue;
            }
            else if(cell_status[i].isInCounting()){
                drawImage(pendingCell,cell_status[i], graph);
                continue;
            }
            else if(cell_status[i].isCancer()){
                if(i<TOTAL/2)
                    drawImage(cancer_cell_left,cell_status[i],graph);
                else
                    drawImage(cancer_cell_right,cell_status[i], graph);
            }
            else{
                drawImage(good_cell,cell_status[i],graph);
            }
        }
    }
    private void drawTools(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        for(int i=0; i<tools.length; i++){
            graph.fill3DRect(tools[i].getX(),tools[i].getY(),tools[i].getWidth(), 
                    tools[i].getHeight(), true);
        }
        graph.drawImage(cellImg,55,10,40,40,null);
        graph.drawImage(chemoImg,205,10,40,40,null);
        graph.drawImage(radiationImg,355,10,40,40,null);
        graph.drawImage(surgeryImg,515,10,40,40,null);
        
        graph.setColor(Game.Colors.darkBlue);
        graph.setFont(toolsFont);
        graph.drawString(Integer.toString(cell),110, 40);
        graph.drawString(Integer.toString(chemo),260, 40);
        graph.drawString(Integer.toString(radiation),410,40);
        graph.drawString(Integer.toString(surgery),565, 40);
        
    }
    private void drawStatus(Graphics2D graph){
        graph.setColor(Color.LIGHT_GRAY);
        graph.fill3DRect(650,10,140,40,true);
        graph.setColor(Game.Colors.darkBlue);
        graph.setFont(toolsFont);
        graph.drawString("Now",660,40);
        switch(selectedTool){
            case -1: break;
            case 0: graph.drawImage(cellImg,730,10,40,40,null);
                    break;
            case 1: graph.drawImage(chemoImg,730,10,40,40,null);
                    break;
            case 2: graph.drawImage(radiationImg,730,10,40,40,null);
                    break;
            case 3: graph.drawImage(surgeryImg,730,10,40,40,null);
                    break;
        }
        graph.drawString("Remain", 75, 190);
        graph.setFont(numFont);
        graph.setColor(Color.RED);
        graph.drawString(String.format("%02d",GAME_TIME-counter),860,80);
        graph.drawString(String.format("%02d",TOTAL-cellCounter), 100, 155);
        graph.drawString("Points: "+Integer.toString(currentPoint),275,560);
    }
    //helper methods
    private void drawImage(Image img, Square pos, Graphics2D graph){
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

    /**
     * Override methods
    */
    @Override
    public void update() {
        if(!running || paused) return;
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
        checkToolsClick(x, y);
        checkCellClicked(x, y);
        toolsClicked = false;
        
    }

    @Override
    protected void finalize() {
        Logger.getLogger(getClass().getName()).log(Level.SEVERE,"obj released as garbage");
    }
    
    
    //Button click checker methods
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
            cell_status[index].setAlive(false);
        }
    }
    private void onAction(int index){
        if(selectedTool == -1){
            return;
        }
        else if(selectedTool == 0 && cell>0){
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
        if(cellCounter == TOTAL || counter == GAME_TIME){
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
