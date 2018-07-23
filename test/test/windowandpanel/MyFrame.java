package test.windowandpanel;

import java.awt.Container;
import java.awt.Dimension;
import javax.swing.JFrame;

/**
 *
 * @author hp
 */
public class MyFrame {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Container pane = frame.getContentPane();
        
        frame.setPreferredSize(new Dimension(800, 480));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        pane.add(new MyPanel());
        frame.pack();
        frame.setVisible(true);
    }
}
