package celo.mine2d.ui.components;

import celo.mine2d.events.MouseListner;
import celo.mine2d.ui.FontRenderer;
import celo.mine2d.utils.Color;
import celo.mine2d.utils.Render2D;
import celo.mine2d.utils.Vec2;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Created by loucass003 on 22/12/2015.
 */
public class Button extends Component implements MouseListner {

    public String text = "";
    public boolean hover = false;
    public Color bgColor;
    public Color bgHoverColor;
    public boolean hasBgHoverColor;
    public boolean hasFgHoverColor;
    public boolean hasTexture;
    public Color fgColor;
    public Color fgHoverColor;
    public Font font;
    public FontRenderer fontRenderer;

    public Button() {
        this.bgColor = new Color(25,25,25);
        this.bgHoverColor = new Color(255,0,0);
        this.fgColor = new Color(255,255,255);
        this.fgHoverColor = new Color(255,255,255);
        this.fontRenderer = new FontRenderer();
        this.font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        this.addMouseListner(this);
    }

    @Override
    public void update() {

    }

    @Override
    public void render(Render2D r) {
        GL11.glPushMatrix();
        r.translate(bounds.getPos());
        if(isHover())
            r.setColor(bgHoverColor);
        else
            r.setColor(bgColor);
        r.fillRect(bounds.getSize());
        Color f = fgColor;
        if(isHover())
            f = fgHoverColor;
        fontRenderer.drawText(text, bounds.size.div(2).sub(fontRenderer.getWidth(text) / 2, fontRenderer.getHeight(text) / 2), f);
        GL11.glPopMatrix();
    }

    @Override
    public void mousePressed(Vec2 pos) {

    }

    @Override
    public void mouseReleased(Vec2 pos) {
        if(getBounds().isInBounds(pos))
            this.sendAction(this);
    }

    @Override
    public void mouseMoved(Vec2 pos) {
        this.setHover(false);
        if(getBounds().isInBounds(pos))
            this.setHover(true);
    }

    public Color getBgColor() {
        return bgColor;
    }

    public Color getBgHoverColor() {
        return bgHoverColor;
    }

    public Color getFgColor() {
        return fgColor;
    }

    public Color getFgHoverColor() {
        return fgHoverColor;
    }

    public String getText() {
        return text;
    }

    public boolean isHover() {
        return hover;
    }

    public void setBgColor(Color bgColor) {
        this.bgColor = bgColor;
    }

    public void setFgColor(Color fgColor) {
        this.fgColor = fgColor;
    }

    public void setBgHoverColor(Color bgHoverColor) {
        this.hasBgHoverColor = bgHoverColor != null;
        this.bgHoverColor = bgHoverColor;
    }

    public void setFgHoverColor(Color fgHoverColor) {
        this.hasFgHoverColor = fgHoverColor != null;
        this.fgHoverColor = fgHoverColor;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setHover(boolean hover) {
        this.hover = hover;
    }

    public Font getFont() {
        return font;
    }

    public void setFont(Font font) {
        this.font = font;
        this.fontRenderer = new FontRenderer(font);
    }
}
