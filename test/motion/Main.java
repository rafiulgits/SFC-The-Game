package motion;

/**
 *
 * @author hp
 */
public class Main {
    
    public static void main(String[] args) { 
        
        String s1 = "E:\\Project SFC-The Game\\Temp\\song.wav";
        String s2 = "E:\\Project SFC-The Game\\Temp\\back.wav";
        String s3 = "E:\\Project SFC-The Game\\SFC-The Game\\res\\sound\\surgery.wav";
        Sound music1 = new Sound(s1);
        Sound music2 = new Sound(s2);
        Sound music3 = new Sound(s3);
        
        music3.loop();
        try {
            Thread.sleep(4000);
        } catch (Exception e) {
        }
        music3.stop();
//        try {
//            Thread.sleep(4000);
//        } catch (Exception e) {
//        }
//        music1.pause();
//        
//        
//        try {
//            Thread.sleep(5000);
//        } catch (Exception e) {
//        }
//        
//        music1.resume();
    }
}
