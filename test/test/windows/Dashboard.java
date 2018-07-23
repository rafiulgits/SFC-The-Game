package test.windows;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextArea;
import javax.swing.JPanel;

/**
 *
 * @author hp
 */
public class Dashboard extends JPanel{

    public Dashboard() {
        setBounds(200, 100, 200, 100);
        setBackground(Color.yellow);
        
        TextArea ta = new TextArea();
        ta.setBounds(0, 0, 100, 100);
        ta.setFont(new Font("Arial",0,15));
        
        add(ta);
    }
    
}
