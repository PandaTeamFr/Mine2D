package celo.mine2d.ui.gui;

import celo.mine2d.events.ActionListner;
import celo.mine2d.ui.FontRenderer;
import celo.mine2d.ui.GuiScreen;
import celo.mine2d.ui.components.Button;
import celo.mine2d.ui.components.Component;
import celo.mine2d.ui.texture.Texture;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;
import org.lwjgl.opengl.GL11;

/**
 * Created by loucass003 on 22/12/2015.
 */
public class MainMenu extends GuiScreen implements ActionListner {

    Texture test;
    public FontRenderer f;
    public Button b;

    public MainMenu() {

    }

    @Override
    public void loadEvent() {
        this.f = new FontRenderer(new java.awt.Font("Jazz LET", java.awt.Font.ITALIC, 12));
        this.test = (Texture) textureManager.getTexture("test");
        this.b = new Button();
        this.b.bounds.setBounds(new Vec2(20,20), new Vec2(200,50));
        this.b.setText("Test");
        this.b.setActionListner(this);
    }

    @Override
    public void unloadEvent() {

    }

    @Override
    public void render(Render2D r) {
        f.drawText("Test\nplop\nca va ?", new Vec2(600, 20));

        GL11.glPushMatrix();
        r.translate(new Vec2(10,50));
        r.drawTexturedModalRect(test, new Vec2(0,0), new Vec2(319,486));
        GL11.glPopMatrix();


        b.render(r);
    }

    @Override
    public void update() {

    }

    public void actionPerformed(Component c) {

    }
}
