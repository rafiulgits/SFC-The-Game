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
 * @author rafiul islam
 */
public class Game {
    /**
     * Game class has no constructor.This class is created for carrying 
     * static elements.Game class has 2 static inner class.Color class and
     * Fonts class.Color class allow us to create Color by using RGB value and 
     * by make their static we can access these color from anywhere in this project;
     * Everything in this class are defined in public for all access.
     * 
     * @param WIDTH game frame width
     * @param HEIGHT game frame height
     * @param TITLE game frame title
     * @param FPS game panel rendering speed
     */
    
    public final static int WIDTH = 1000;
    public final static int HEIGHT = 600;
    public final static String TITLE = "SFC - The Game"; 
    public final static int FPS = 50;
    
    public static class Fonts{
        /**
         * Fonts class  is defined for access fonts from resource.This class
         * has only one method which return a font.Available fonts in res folder.
         * 
         *{@code Game.Fonts.getFonts("Courier.ttf",Font.Bold,25)};
         */
    
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
        /**
         * Color class has some defined colors with their RGB value;
         * user can access them by using {@code Game.Color.darkblue}
         */
        public static Color darkBlue = new Color(1,3,30);
        public static Color treeGreen = new Color(96, 186, 96);
        public static Color softSky = new Color(198,232,243);
        public static Color softGray = new Color(222,222,222);
    }
    
    /**
    * return an image from resource file by using a path
    * {@code Game.getImage("/image/....");}
    */
    public static Image getImage(String path){
        return new ImageIcon(Game.class.getResource(path)).getImage();
    }
    /**
     * return a sound file from resource using file name
     * {@code Game.getSound("error.wav");}
     */
    public static Sound getSound(String name){
        return new Sound(Game.class.getResourceAsStream("/sound/"+name));
    }
}
