package celo.mine2d.sounds;

import celo.mine2d.utils.Vec2;
import paulscode.sound.SoundSystem;
import paulscode.sound.SoundSystemConfig;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by loucass003 on 24/12/2015.
 */
public class Sound {

    public enum Type {
        NORMAL,BACKGROUND
    }

    public enum State {
        PLAY,PAUSE,STOP
    }

    public String name;
    public String source;
    public SoundSystem system;
    public State state = State.STOP;
    public Type type = Type.NORMAL;
    public Vec2 pos;
    public Vec2 listener;
    public boolean loop = false;

    public Sound(String name, String source, SoundSystem system, Type type) {
        this.name = name;
        this.source = source;
        this.system = system;
        this.state = State.STOP;
        this.type = type;
    }

    public Sound(String name, String source, SoundSystem system) {
        this(name, source, system, Type.NORMAL);
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
        system.setListenerPosition(listener.x, listener.y, 0);
    }

    public void setPos(Vec2 pos) {
        this.pos = pos;
        system.setPosition(this.name, pos.x, pos.y, 0);
    }

    public void setState(State state) {
        this.state = state;
    }

    public SoundSystem getSystem() {
        return system;
    }

    public void load() {
        if(type.equals(Type.NORMAL))
            system.newStreamingSource(true, this.name, this.source, false, 0,0,0,SoundSystemConfig.ATTENUATION_NONE,0);
    }

    public void playSound() {
        this.setState(State.PLAY);
        if(type.equals(Type.NORMAL))
            system.play(this.name);
        else if(type.equals(Type.BACKGROUND))
            system.backgroundMusic(this.name, this.source, loop);
    }

    public void pauseSound() {
        this.setState(State.PAUSE);
        system.pause(this.name);
    }

    public void stopSound() {
        this.setState(State.STOP);
        system.stop(this.name);
    }

    public void setLoop(boolean l) {
        this.loop = l;
        this.system.setLooping(this.name, l);
    }

    public boolean getLoop() {
        return this.loop;
    }

    public boolean isPlaying() {
        return this.system.playing(this.name);
    }

    public void unloadSound() {
        system.unloadSound(this.source);
    }

    public void setType(Type type) {
        this.type = type;
    }
}
