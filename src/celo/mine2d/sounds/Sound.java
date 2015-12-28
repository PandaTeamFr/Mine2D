package celo.mine2d.sounds;

import celo.mine2d.utils.Vec2;
import paulscode.sound.SoundSystem;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by loucass003 on 24/12/2015.
 */
public class Sound {

    enum State {
        PLAY,PAUSE,STOP
    }

    public String name;
    public String source;
    public SoundSystem system;
    public State state;
    public Vec2 pos;
    public Vec2 listener;

    public Sound(String name, String source, SoundSystem system) {
        this.name = name;
        this.source = source;
        this.system = system;

    }

    public Vec2 getListener() {
        return listener;
    }

    public State getState() {
        return state;
    }

    public Vec2 getPos() {
        return pos;
    }

    public void setListener(Vec2 listener) {
        this.listener = listener;
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void load() {
        system.loadSound(this.source);
    }

    public void playSound() {
        system.play(this.name);
    }

    public void unloadSound() {
        system.unloadSound(this.source);
    }
}
