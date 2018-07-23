package motion;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Rafiul
 */
public class Sound {
    
    private volatile Clip clip;
    private volatile boolean running,looping;
    public Sound(String location){
        looping = running = false;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(location)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Sound.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void play() {
        running = true;
        clip.setFramePosition(0);
        new Thread(new Runnable(){
            public void run(){
                clip.start();
                while(true){
                    if(clip.getMicrosecondPosition() == clip.getMicrosecondLength())
                        break;
                    if(!running)
                        break;
                }
            }
        }).start();
    }
    public void pause(){
        running = false;
        clip.stop();
    }
    public void resume(){
        running = true;
        new Thread(new Runnable(){
            public void run(){
                clip.start();
                while(true){
                    if(clip.getMicrosecondPosition() == clip.getMicrosecondLength())
                        break;
                    if(!running)
                        break;
                }
            }
        }).start();
    }
    public void stop(){
        clip.stop();
        running = false;
        looping = false;
        clip.setFramePosition(0);
    }
    public void loop(){
        looping = true;
        new Thread(new Runnable(){
            public void run(){
                clip.start();
                while(looping){
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                }
            }
        }).start();
    }
    public boolean isResumed(){
        if(clip.getMicrosecondPosition()>0)
            return true;
        return false;
    }
}
