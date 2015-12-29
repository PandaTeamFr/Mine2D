package celo.mine2d.ui.gui;

import celo.mine2d.events.ActionListner;
import celo.mine2d.ui.FontRenderer;
import celo.mine2d.ui.GuiScreen;
import celo.mine2d.ui.components.Button;
import celo.mine2d.ui.components.Component;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;

/**
 * Created by loucass003 on 22/12/2015.
 */
public class MainMenu extends GuiScreen implements ActionListner {

    public FontRenderer f;
    public Button play;
    public Button options;

    public MainMenu() {
        this.f = new FontRenderer(new java.awt.Font("Jazz LET", java.awt.Font.ITALIC, 25));
    }

    @Override
    public void loadEvent() {

        this.play = new Button();
        this.play.setBounds(screenSize.div(2).sub(150, 120), new Vec2(300,50));
        this.play.setText("Jouer !");
        this.play.setActionListner(this);

        this.options = new Button();
        this.options.setBounds(screenSize.div(2).sub(150, 50), new Vec2(300,50));
        this.options.setText("Options");
        this.options.setActionListner(this);
    }

    @Override
    public void unloadEvent() {
        this.f.dispose();
    }

    @Override
    public void render(Render2D r) {

        play.render(r);
        options.render(r);
    }

    @Override
    public void update() {

    }

    public void actionPerformed(Component c) {
        if(c.equals(options))
            this.getFrame().getRenderer().setCurrentGuiScreen(new GuiOptions());
    }
}
