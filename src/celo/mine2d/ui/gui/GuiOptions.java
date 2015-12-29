package celo.mine2d.ui.gui;

import celo.mine2d.events.ActionListner;
import celo.mine2d.ui.GuiScreen;
import celo.mine2d.ui.components.Button;
import celo.mine2d.ui.components.Component;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;

/**
 * Created by loucass003 on 29/12/2015.
 */
public class GuiOptions extends GuiScreen implements ActionListner {

    public Button cancel;

    @Override
    public void loadEvent() {
        this.cancel = new Button();
        this.cancel.setBounds(new Vec2(15,15), new Vec2(150,30));
        this.cancel.setText("Retour");
        this.cancel.setActionListner(this);
    }

    @Override
    public void unloadEvent() {

    }

    @Override
    public void render(Render2D r) {
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
