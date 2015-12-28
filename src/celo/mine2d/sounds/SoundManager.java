package celo.mine2d.sounds;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.util.Map;

/**
 * Created by loucass003 on 22/12/2015.
 */

public class SoundManager {

    SoundSystem system;
    public Map<String, Sound> sounds;

    public SoundManager() {
        this.system = new SoundSystem();
    }

    public void init() {
        try
        {
            SoundSystemConfig.addLibrary(LibraryLWJGLOpenAL.class);
            SoundSystemConfig.addLibrary(LibraryJavaSound.class);
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        }
        catch( SoundSystemException e )
        {
            System.err.println("error linking with the plug-ins" );
        }
    }

    public void loadSounds() {

    }

    public void addSound() {

    }
}
