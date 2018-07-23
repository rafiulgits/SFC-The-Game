package game.operator;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import object.structure.Sound;

/**
 *
 * @author hp
 */
public class Game {
    public final static int WIDTH = 1000;
    public final static int HEIGHT = 600;
    public final static String TITLE = "SFC - The Game";
    
    public final static int FPS = 50;
    public final static Color DEFAULT_BACKGROUND = Color.WHITE;
    public final static Color DEFAULT_FOREGROUND = Color.BLACK;
    
    public static class Fonts{
        
        public static Font getFont(String name, int style, int size){
            InputStream is = Game.Fonts.class.getResourceAsStream("/font/"+name);
            try {
                Font font = Font.createFont(Font.TRUETYPE_FONT, is);
                font = font.deriveFont(style, size);
                return font;
            } catch (FontFormatException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
            }
            return null;
        }
    }
    public static class Colors{
        public static Color darkBlue = new Color(1,3,30);
        public static Color treeGreen = new Color(96, 186, 96);
        public static Color softSky = new Color(198,232,243);
        public static Color softGray = new Color(222,222,222);
    }
    public static Image getImage(String path){
        return new ImageIcon(Game.class.getResource(path)).getImage();
    }
    public static Sound getSound(String name){
        return new Sound("res\\sound\\"+name);
        
    }
}
