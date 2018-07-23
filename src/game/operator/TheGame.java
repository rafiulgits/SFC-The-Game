package game.operator;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author hp
 */
public class TheGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                JFrame gameFrame = new JFrame(Game.TITLE);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setContentPane(new GamePanel());
                gameFrame.setResizable(false);
                gameFrame.pack();
                gameFrame.setVisible(true);
            }
        });
    }
}
