package celo.mine2d.sounds;

import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;
import paulscode.sound.SoundSystemException;
import paulscode.sound.codecs.CodecWav;
import paulscode.sound.libraries.LibraryJavaSound;
import paulscode.sound.libraries.LibraryLWJGLOpenAL;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by loucass003 on 22/12/2015.
 */

public class SoundManager {

    SoundSystem system;
    public Map<String, Sound> sounds;

    public SoundManager() {

        this.sounds = new HashMap<>();
    }

    public void init() {
        try
        {
            if(SoundSystem.libraryCompatible(LibraryLWJGLOpenAL.class))
                this.system = new SoundSystem(LibraryLWJGLOpenAL.class);
            else if(SoundSystem.libraryCompatible(LibraryJavaSound.class))
                this.system = new SoundSystem(LibraryJavaSound.class);
            else
                this.system = new SoundSystem();
            SoundSystemConfig.setSoundFilesPackage("celo/mine2d/ressources/sounds/");
            SoundSystemConfig.setCodec("wav", CodecWav.class);
        }
        catch( SoundSystemException e )
        {
            System.err.println("error linking with the plug-ins" );
        }
    }

    public void loadSounds() {
        this.addSound("sonTest", "sonTest.wav");
    }

    public void addSound(String name, String source) {
        Sound s = new Sound(name, source, system);
        s.load();
        this.sounds.put(name, s);
    }

    public void addSound(String name, String source, Sound.Type type) {
        Sound s = new Sound(name, source, system, type);
        s.load();
        this.sounds.put(name, s);
    }
}
