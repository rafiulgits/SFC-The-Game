package object.structure;

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
    public Sound(String location){
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
        clip.setFramePosition(0);
        clip.start();
    }
    public void pause(){
        clip.stop();
    }
    public void resume(){
        clip.start();
    }
    public void stop(){
        clip.stop();
    }
    public synchronized void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public boolean isResumed(){
        if(clip.getMicrosecondPosition()>0)
            return true;
        return false;
    }
}
