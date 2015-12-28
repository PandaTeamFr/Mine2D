package celo.mine2d.ui.components;

import celo.mine2d.events.ActionListner;
import celo.mine2d.events.KeyListner;
import celo.mine2d.events.MouseListner;
import celo.mine2d.ui.GameFrame;
import celo.mine2d.utils.Bounds;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;

/**
 * Created by loucass003 on 22/12/2015.
 */
public abstract class Component {

    public Bounds bounds;
    public boolean isVisible;
    public boolean isEnabled;
    public ActionListner actionListner;
    public GameFrame frame;

    public Component() {
        frame = GameFrame.getInstance();
        this.bounds = new Bounds();
    }

    public abstract void update();

    public abstract void render(Render2D r);

    public void setBounds(Bounds bounds) {
        this.bounds = bounds;
    }

    public void setBounds(Vec2 pos, Vec2 size) {
        this.bounds = new Bounds(pos, size);
    }

    public Bounds getBounds() {
        return bounds;
    }

    public double getHeight() {
        return bounds.size.y;
    }

    public double getWidth() {
        return bounds.size.x;
    }

    public double getX() {
        return bounds.pos.x;
    }

    public double getY() {
        return bounds.pos.y;
    }

    public boolean isEnabled() {
        return isEnabled;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean isVisible) {
        this.isVisible = isVisible;
    }

    public void setEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public void setActionListner(ActionListner a) {
        this.actionListner = a;
    }

    public ActionListner getActionListner() {
        return actionListner;
    }

    public void sendAction(Component component) {
        if(actionListner != null)
            this.actionListner.actionPerformed(component);
    }

    public void addMouseListner(MouseListner m) {
        frame.mouseManager.addMouseListner(m);
    }

    public void addKeyListner(KeyListner k) {
        frame.keyBoardManager.addKeyListner(k);
    }
}
