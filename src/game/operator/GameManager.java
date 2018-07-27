package game.operator;

import game.frame.Dashboard;
import game.frame.HowToPlay;
import game.frame.Menu;
import game.frame.NewUser;
import game.frame.Opening;
import game.frame.Pause;
import game.frame.Training;
import game.frame.Window;
import game.level.Blood;
import game.level.Breast;
import game.level.Lung;
import java.awt.Graphics2D;

/**
 *
 * @author rafiul islam
 */
public class GameManager {

    /**
     * Manager of this project.This class has the control over all
     * Window extended classes and game view or game state is changed
     * by this class management.
     * 
     * @param currentIndex (integer) store the current index of Window
     * @param currentWindow (Window) indicate the current viewing page
     * @param releasedWindow (Window) indicate  the released window page
     * @param exitClicked (boolean) this will fired when user pressed on exit option
    */
    private int currentIndex;
    private Window currentWindow,releasedWindow;
    private boolean exitClicked;
    
    public GameManager() {
        exitClicked = false;
        currentIndex = -1;
        loadWindow(Window.OPENING); 
    }
    /**
     * This method load a specific Window after release the previous window
     * in releasedWindow for further use and load the currentWindow.
     * {@code manager.loadWindow(Window.DASHBOARD);}
    */
    public void loadWindow(int name){
        releaseWindow();
        switch(name){
            case Window.MENU:{
                currentWindow = new Menu(this);
                currentIndex = Window.MENU;
                break;
            }
            case Window.NEW_USER: {
                currentWindow = new NewUser(this);
                currentIndex = Window.NEW_USER;
                break;
            }
            case Window.DASHBOARD: {
                currentWindow = new Dashboard(this);
                currentIndex = Window.DASHBOARD;
                break;
            }
            case Window.TRAININHG: {
                currentWindow = new Training(this);
                currentIndex = Window.TRAININHG;
                break;
            }
            case Window.BLOOD: {
                currentWindow = new Blood(this);
                currentIndex = Window.BLOOD;
                break;
            }
            case Window.BREAST: {
                currentWindow = new Breast(this);
                currentIndex = Window.BREAST;
                break;
            }
            case Window.LUNG: {
                currentWindow = new Lung(this);
                currentIndex = Window.LUNG;
                break;
            }
            case Window.PAUSE: {
                currentWindow = new Pause(this);
                currentIndex = Window.PAUSE;
                break;
            }
            case Window.OPENING: {
                currentWindow = new Opening(this);
                currentIndex = Window.OPENING;
                break;
            }
            case Window.HOW_TO_PLAY: {
                currentWindow = new HowToPlay(this);
                currentIndex = Window.HOW_TO_PLAY;
                break;
            }
        }
    }
    /**
     * This method allow us to recycle the releasedWindow and use this 
     * as the currentWindow
    */
    public void loadReleased(){
        Window temp = currentWindow;
        currentWindow = releasedWindow;
        currentWindow.resume();
        releasedWindow = temp;
        temp = null;
        System.gc();
    }
    /**
     * this is a private method; this method always called from loadWindow(.) method
    */
    private void releaseWindow(){
        releasedWindow = currentWindow;
        System.gc();
    }
    
    public void update(){
        currentWindow.update();
    }
    public void draw(Graphics2D graph){
        /**
         * for rendering problem
        */
        graph.clearRect(0, 0, Game.WIDTH, Game.HEIGHT);
        graph.setBackground(Game.Colors.darkBlue);
        
        currentWindow.draw(graph);
    }
    public void keyPressed(int key){
        currentWindow.keyPressed(key);
    }
    public void mouseClicked(int x, int y){
        currentWindow.mouseClickd(x, y);
    }
    public void gameExit(){
        exitClicked = true;
    }
    protected boolean isExit(){
        return exitClicked;
    }
}

