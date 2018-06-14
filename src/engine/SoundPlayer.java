package engine;

import java.io.File;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class SoundPlayer {
	public static synchronized Clip playMusic(final String fileName)
	{
		try {
			final Clip clip = AudioSystem.getClip();
	        // Note: use .wav files
	        Thread t = new Thread(new Runnable() {
	            public void run() {
	                try {
	                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
	                    clip.open(inputStream);
	                    FloatControl gainControl = 
	                    	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
	                    gainControl.setValue(Resources.getInstance().musicVolume);
	                    clip.loop(Clip.LOOP_CONTINUOUSLY);
	                } catch (Exception e) {
	                    System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
	                }
	            }
	        });
	        t.start();
	        return clip;
        } catch (Exception e) {
	        System.out.println("Can't get system audio clip");
	    }
		return null;
    }
	
	public static synchronized void playSound(final String fileName)
	{
        // Note: use .wav files
        new Thread(new Runnable() {
            public void run() {
                try {
                	Clip clip = AudioSystem.getClip();
                    AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(fileName));
                    clip.open(inputStream);
                    FloatControl gainControl = 
                    	    (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    gainControl.setValue(Resources.getInstance().soundVolume);
                    clip.start();
                } catch (Exception e) {
                    System.out.println("play sound error: " + e.getMessage() + " for " + fileName);
                }
            }
        }).start();
    }
}
