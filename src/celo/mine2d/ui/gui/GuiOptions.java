package celo.mine2d.ui.gui;

import celo.mine2d.events.ActionListner;
import celo.mine2d.ui.GuiScreen;
import celo.mine2d.ui.components.Button;
import celo.mine2d.ui.components.Component;
import celo.mine2d.ui.texture.Texture;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;
import org.lwjgl.opengl.GL11;

/**
 * Created by loucass003 on 29/12/2015.
 */
public class GuiOptions extends GuiScreen implements ActionListner {

    public Button cancel;
    public Texture t;
    @Override
    public void loadEvent() {
        this.cancel = new Button();
        this.cancel.setBounds(new Vec2(15,15), new Vec2(150,30));
        this.cancel.setText("Retour");
        this.cancel.setActionListner(this);
        this.t = (Texture) frame.getTextureManager().getTexture("test");

    }

    @Override
    public void unloadEvent() {

    }

    @Override
    public void render(Render2D r) {
        GL11.glPushMatrix();
        r.translate(new Vec2(50,50));
        r.drawRect(new Vec2(200,200));
        r.drawSizedTexturedModalRect(t, new Vec2(200,200), new Vec2(80,80), new Vec2(40,40));

        GL11.glPopMatrix();
        this.cancel.render(r);
    }

    @Override
    public void update() {

    }

    @Override
    public void actionPerformed(Component c) {
        if(c.equals(cancel))
            this.getFrame().getRenderer().setCurrentGuiScreen(new MainMenu());
    }
}
