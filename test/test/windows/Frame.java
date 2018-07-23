package test.windows;

import java.awt.Button;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 *
 * @author hp
 */
public class Frame {
    JFrame frame;
    Container pane;
    int now = 0;
    
    public Frame() {
        frame = new JFrame("Game Panel");
        pane = frame.getContentPane();
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setPreferredSize(new Dimension(800, 450));
        
        pane.setLayout(null);
        
        GameLavel gl = new GameLavel();
        Dashboard da = new Dashboard(); 
        
        pane.add(gl);
        
        Button bt = new Button("Click");
        bt.setBounds(400, 350, 100, 40);
        bt.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(now == 0){
                    frame.remove(gl);
                    frame.add(da);
                    now = 1;
                }
                else{
                    frame.remove(da);
                    frame.add(gl);
                    now = 0;
                }
                
                
                frame.repaint();   
            }
        });
        
        pane.add(bt);
        
        frame.pack();
        frame.setVisible(true);
    }
    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new Frame();
            }
        });
    }
}
