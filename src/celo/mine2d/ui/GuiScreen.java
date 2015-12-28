package celo.mine2d.ui;

import celo.mine2d.events.KeyListner;
import celo.mine2d.events.MouseListner;
import celo.mine2d.main.Main;
import celo.mine2d.ui.texture.TextureManager;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;

/**
 * Created by loucass003 on 22/12/2015.
 */
public abstract class GuiScreen {

    public TextureManager textureManager;
    public Main main;
    public GameFrame frame;
    public Vec2 screenSize;

    public GuiScreen() {
        this.main = Main.getInstance();
        this.frame = main.getFrame();
        this.screenSize = frame.getScreenSize();
        this.textureManager = frame.getTextureManager();
    }

    public void addMouseListener(MouseListner m) {
        frame.mouseManager.addMouseListner(m);
    }

    public void addKeyListener(KeyListner k) {
        frame.keyBoardManager.addKeyListner(k);
    }

    public GameFrame getFrame() {
        return frame;
    }

    public Main getMain() {
        return main;
    }

    public Vec2 getScreenSize() {
        return screenSize;
    }

    public abstract void loadEvent();

    public abstract void unloadEvent();

    public abstract void render(Render2D r);

    public abstract void update();

}
