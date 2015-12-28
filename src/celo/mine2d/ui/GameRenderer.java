package celo.mine2d.ui;

import celo.mine2d.main.Main;
import celo.mine2d.ui.gui.MainMenu;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;

import java.util.logging.Logger;

/**
 * Created by loucass003 on 21/12/2015.
 */
public class GameRenderer {

    public Logger logger = Main.getInstance().getLogger();
    public GameFrame frame;
    public GuiScreen currentGuiScreen;


    public GameRenderer(GameFrame frame) {
        this.frame = frame;
        setCurrentGuiScreen(null);
    }

    public void initGl() {
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0, Display.getWidth(), Display.getHeight(), 0, -1, 1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glViewport(0,0, Display.getWidth(), Display.getHeight());
    }

    public void update(int delta) {
        this.currentGuiScreen.update();
        frame.updateFPS();
        checkGLError("Update");
    }

    public void render(Render2D r) {
        this.currentGuiScreen.render(r);
        checkGLError("Render");
    }

    public void setCurrentGuiScreen(GuiScreen currentGuiScreen) {
        if(currentGuiScreen == null)
            currentGuiScreen = new MainMenu();
        else
            currentGuiScreen.unloadEvent();
        this.currentGuiScreen = currentGuiScreen;
        this.currentGuiScreen.loadEvent();
    }

    /**
     * Checks for an OpenGL error. If there is one, prints the error ID and error string.
     */
    private void checkGLError(String message)
    {
        if (frame.main.debug)
        {
            int var2 = GL11.glGetError();

            if (var2 != 0)
            {
                String var3 = GLU.gluErrorString(var2);
                logger.severe("########## GL ERROR ##########");
                logger.severe("@ " + message);
                logger.severe(var2 + ": " + var3);
            }
        }
    }

}
