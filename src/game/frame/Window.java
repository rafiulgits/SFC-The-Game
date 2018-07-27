package game.frame;

import game.operator.GameManager;
import java.awt.Graphics2D;

/**
 *
 * @author hp
 */
public abstract class Window {
    /**
     * @param manager Controller any of Window extended class
     */
    public static final int MENU = 0;
    public static final int NEW_USER = 1;
    public static final int DASHBOARD = 2;
    public static final int TRAININHG = 3;
    public static final int LUNG = 4;
    public static final int BLOOD = 5;
    public static final int BREAST = 6;
    public static final int PAUSE = 7;
    public static final int OPENING = 8;
    public static final int HOW_TO_PLAY = 9;

    protected GameManager manager;
    /**
     * Interface for extended classes
     */
    public abstract void update();
    public abstract void draw(Graphics2D graph);
    public abstract void resume();
    public abstract void keyPressed(int key);
    public abstract void mouseClickd(int x, int y);
}
