package game.operator;

import javax.swing.JFrame;

/**
 *
 * @author rafiul islam
 */
public class TheGame {
    /**
     * TheGame class is stand for create a frame for game and invoke a main thread 
     * for run the game.
     * @param gameFrame JFrame in protected mode for accessing in this package
    */
    static JFrame gameFrame;
    private TheGame(){
        gameFrame = new JFrame(Game.TITLE);
        gameFrame.setIconImage(Game.getImage("/image/util/frame_icon.png"));
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setContentPane(new GamePanel());
        gameFrame.setResizable(false);
        gameFrame.pack();
        gameFrame.setVisible(true);
    }
    /**
     * invoke this frame by calling main thread
     */
    public static void main(String[] args) {
        new TheGame();
    }
}
