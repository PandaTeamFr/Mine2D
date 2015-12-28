package celo.mine2d.ui;

import celo.mine2d.events.KeyBoardManager;
import celo.mine2d.events.MouseManager;
import celo.mine2d.main.Main;
import celo.mine2d.ui.texture.TextureManager;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class GameFrame {

    public static GameFrame instance;
    public GameRenderer renderer;
    public Main main;
    public Render2D render;
    public MouseManager mouseManager;
    public KeyBoardManager keyBoardManager;
    public TextureManager textureManager;

    public Vec2 maxSize;
    public Vec2 screenSize;
    public Vec2 minSize;
    private long lastFrame;
    private long lastFPS;
    private int fps;


    public GameFrame(Main main) {
        this.instance = this;
        this.main = main;
        this.minSize = new Vec2(800, 600);
        this.screenSize = minSize;
        this.textureManager = new TextureManager();
        this.mouseManager = new MouseManager();
        this.keyBoardManager = new KeyBoardManager();

    }

    public void init() {
        DisplayMode dm = Display.getDesktopDisplayMode();
        this.maxSize = new Vec2(dm.getWidth(), dm.getHeight());
        this.render = new Render2D(this);
        try {
            Display.setDisplayMode(new DisplayMode((int)screenSize.x, (int)screenSize.y));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        textureManager.loadTextures();
        this.renderer = new GameRenderer(this);
        renderer.initGl();
        lastFPS = getDelta();
        while (!Display.isCloseRequested()) {
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
            GL11.glColor4f(1F, 1F, 1F, 0F);
            int delta = getDelta();
            renderer.update(delta);
            renderer.render(render);
            mouseManager.update();
            keyBoardManager.update();
            updateFPS();
            Display.update();
            Display.sync(60);
        }

        Display.destroy();
        System.exit(0); //TODO : Faire une fonction pour netoyer la ram ---> unload des assets, sons, textures, puis fermeture propre du programe
    }

    /**
     * Set the display mode to be used 
     *
     * @param fullscreen True if we want fullscreen mode
     */
    public void setFullscreenMode(boolean fullscreen) {

        Vec2 size = fullscreen ? maxSize : minSize;

        if ((Display.getDisplayMode().getWidth() == size.x) &&
                (Display.getDisplayMode().getHeight() == size.y) &&
                (Display.isFullscreen() == fullscreen)) {
            return;
        }

        try {

            DisplayMode targetDisplayMode = null;
            if (fullscreen) {
                DisplayMode[] modes = Display.getAvailableDisplayModes();
                int freq = 0;

                for (int i=0;i<modes.length;i++) {
                    DisplayMode current = modes[i];

                    if ((current.getWidth() == size.x) && (current.getHeight() == size.y)) {
                        if ((targetDisplayMode == null) || (current.getFrequency() >= freq)) {
                            if ((targetDisplayMode == null) || (current.getBitsPerPixel() > targetDisplayMode.getBitsPerPixel())) {
                                targetDisplayMode = current;
                                freq = targetDisplayMode.getFrequency();
                            }
                        }

                        if ((current.getBitsPerPixel() == Display.getDesktopDisplayMode().getBitsPerPixel()) &&
                                (current.getFrequency() == Display.getDesktopDisplayMode().getFrequency())) {
                            targetDisplayMode = current;
                            break;
                        }
                    }
                }
            } else {
                targetDisplayMode = new DisplayMode((int)size.x,(int)size.y);
            }

            if (targetDisplayMode == null) {
                System.out.println("Failed to find value mode: "+size.x+"x"+size.y+" fullscreen="+fullscreen);
                return;
            }

            Display.setDisplayMode(targetDisplayMode);
            Display.setFullscreen(fullscreen);
            renderer.initGl();
        } catch (LWJGLException e) {
            System.out.println("Unable to setup mode "+size.x+"x"+size.y+" fullscreen="+fullscreen + " Error : " + e);
        }
    }

    /**
     * Calculate how many milliseconds have passed
     * since last frame.
     *
     * @return milliseconds passed since last frame
     */
    public int getDelta() {
        long time = getTime();
        int delta = (int) (time - lastFrame);
        lastFrame = time;

        return delta;
    }

    /**
     * Get the accurate system time
     *
     * @return The system time in milliseconds
     */
    public long getTime() {
        return (Sys.getTime() * 1000) / Sys.getTimerResolution();
    }

    /**
     * Calculate the FPS and set it in the title bar
     */
    public void updateFPS() {
        if (getTime() - lastFPS > 1000) {
            Display.setTitle("FPS: " + fps);
            fps = 0;
            lastFPS += 1000;
        }
        fps++;
    }

    public Vec2 getScreenSize() {
        return screenSize;
    }

    public int getFps() {
        return fps;
    }

    public long getLastFPS() {
        return lastFPS;
    }

    public long getLastFrame() {
        return lastFrame;
    }

    public Vec2 getMaxSize() {
        return maxSize;
    }

    public Vec2 getMinSize() {
        return minSize;
    }

    public Main getMain() {
        return main;
    }

    public GameRenderer getRenderer() {
        return renderer;
    }

    public KeyBoardManager getKeyBoardManager() {
        return keyBoardManager;
    }

    public MouseManager getMouseManager() {
        return mouseManager;
    }

    public Render2D getRender() {
        return render;
    }

    public TextureManager getTextureManager() {
        return textureManager;
    }

    public static GameFrame getInstance() {
        return instance;
    }
}
