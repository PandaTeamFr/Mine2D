package celo.mine2d.ui.texture;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by loucass003 on 22/12/2015.
 */
public class TextureManager {

    public Map<String, ITexture> textures;
    private static TextureManager instance;

    public TextureManager() {
        this.instance = this;
        this.textures = new HashMap<String, ITexture>();
    }

    public void loadTextures() {
        this.addTexture("test", new Texture("celo/mine2d/ressources/textures/Idle__009.png"));
    }


    public void addTexture(String name, ITexture t) {
        if(this.textures.containsValue(t))
            return;
        this.textures.put(name, t);
        try {
            t.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ITexture getTexture(String name) {
        for (Map.Entry<String, ITexture> entry : textures.entrySet())
            if(entry.getKey().equals(name))
                return entry.getValue();
        return null;
    }

    public Map<String, ITexture> getTextures() {
        return textures;
    }

    public static TextureManager getInstnace() {
        return instance;
    }
}
