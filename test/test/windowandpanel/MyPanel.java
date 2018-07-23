package test.windowandpanel;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.TextField;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class MyPanel extends JPanel implements Runnable{
    
    private BufferedImage bi;
    private Graphics graph;
    private Thread runner;
    
    private boolean running;

    public MyPanel() {
        this.setBackground(Color.WHITE);
    }

    private void init(){
        bi = new BufferedImage(400, 450, BufferedImage.TYPE_INT_ARGB);
        graph = bi.getGraphics();
    }
    private void render(){
        TextField tf = new TextField();
        tf.setLocation(0, 0);
        tf.setSize(300, 100);
        tf.setFont(new Font("arial", Font.BOLD, 10));
        this.add(tf);
    }
    private void draw(){
        int r = (int)(Math.random()*255);
        int g = (int)(Math.random()*255);
        int b = (int)(Math.random()*255);
        
        Color c = new Color(r, g, b);
        graph.setColor(c);
        graph.fillOval(100, 100, 90, 90);
    }
    private void toScreen(){
        Graphics g = getGraphics();
        g.drawImage(bi, 400, 0, 400, 450, this);
        g.dispose();
    }
    @Override
    public void addNotify() {
        super.addNotify();
        System.out.println("add Notify called");
        if(runner == null){
            runner = new Thread(this);
            runner.start();
        }
    }
    @Override
    public void run(){
        init();
        running = true;
        while(running){
            render();
            draw();
            toScreen();
        }
    }
}
