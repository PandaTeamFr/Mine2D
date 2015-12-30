package celo.mine2d.utils;


import celo.mine2d.ui.GameFrame;
import celo.mine2d.ui.texture.ITexture;
import org.lwjgl.opengl.GL11;

public class Render2D {

	private Color currentColor = new Color(255,255,255);
	public GameFrame frame;


	public Render2D(GameFrame frame) {
		this.frame = frame;
	}

	public Vec2 uv(Vec2 s){
		float X = (s.x * frame.minSize.x / frame.minSize.y) / (frame.screenSize.x / frame.screenSize.y);
		float Y = s.y * X / s.x;
		return new Vec2(X, Y);
	}

	public void translate(Vec2 pos) {
		GL11.glTranslated(pos.x, pos.y, 0);
	}

	public void sacle(Vec2 s) {
		GL11.glTranslated(s.x, s.y, 1D);
	}

	public void drawLine(Vec2 pos2) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(0, 0);
		GL11.glVertex2d(pos2.x, pos2.y);
		GL11.glEnd();
	}

	public void drawHLine(float width) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(0, 0);
		GL11.glVertex2d(width, 0);
		GL11.glEnd();
	}

	public void drawVLine(float height) {
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2d(0,0);
		GL11.glVertex2d(0,height);
		GL11.glEnd();
	}

	public void drawRect(Vec2 size) {
		drawHLine(size.x);
		drawVLine(size.y);
		GL11.glPushMatrix();
		translate(new Vec2(size.x, 0));
		drawVLine(size.y);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		translate(new Vec2(0, size.y));
		drawHLine(size.x);
		GL11.glPopMatrix();
	}

	public void fillRect(Vec2 size) {
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(0, 0);
		GL11.glVertex2d(size.x, 0);
		GL11.glVertex2d(size.x, size.y);
		GL11.glVertex2d(0, size.y);
		GL11.glEnd();
	}

	public void fillCircle(float radius) {
		GL11.glScalef(radius, radius, 1);
		GL11.glBegin(GL11.GL_TRIANGLE_FAN);
		GL11.glVertex2f(0, 0);
		double nside = 8d; // 4 minimum
		double max = Math.PI * 2.0d;
		double step = max / nside;
		for(double i = 0; i <= max; i += step)
			GL11.glVertex2d(Math.cos(i), Math.sin(i));
		GL11.glEnd();
	}

	public void setColor(Color c) {
		this.currentColor = c;
		GL11.glColor4f(c.r, c.g, c.b, c.a);
	}

	/**
	 * Draws a textured rectangle
	 * @param t Texture
	 * @param offset pos of rect
	 * @param size size of rect
	 */
	public void drawTexturedModalRect(ITexture t, Vec2 offset, Vec2 size)
	{
		float var7 = 1F / t.getSize().x;
        float var8 = 1F / t.getSize().y;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getId());
        GL11.glBegin(GL11.GL_QUADS);
        {
            addVertexWithUV(new Vec2(0, size.y), new Vec2(offset.x * var7, (offset.y + size.y) * var8));
            addVertexWithUV(new Vec2(size.x, size.y), new Vec2((offset.x + size.x) * var7, (offset.y + size.y) * var8));
            addVertexWithUV(new Vec2(size.x, 0), new Vec2((offset.x + size.x) * var7, (offset.y * var8)));
            addVertexWithUV(new Vec2(0, 0), new Vec2(offset.x * var7, offset.y * var8));
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	/**
	 * Draws a textured rectangle
	 * @param t Texture
     * @param iSize size of image
	 * @param offset pos of rect
	 * @param size size of rect
	 */
	public void drawSizedTexturedModalRect(ITexture t, Vec2 iSize, Vec2 offset, Vec2 size)
	{
		float var7 = 1F / t.getSize().x;
		float var8 = 1F / t.getSize().y;
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(1f, 1f, 1f, 1f);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, t.getId());

		float X = (iSize.x * size.x / size.y) / (size.x / size.y);
		float Y = iSize.y * X / iSize.x;

		GL11.glBegin(GL11.GL_QUADS);
		{
			addVertexWithUV(new Vec2(0, Y), new Vec2(offset.x * var7, (offset.y + size.y) * var8));
			addVertexWithUV(new Vec2(X, Y), new Vec2((offset.x + size.x) * var7, (offset.y + size.y) * var8));
			addVertexWithUV(new Vec2(X, 0), new Vec2((offset.x + size.x) * var7, (offset.y * var8)));
			addVertexWithUV(new Vec2(0, 0), new Vec2(offset.x * var7, offset.y * var8));
		}
		GL11.glEnd();
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
	}

	public void addVertexWithUV(Vec2 vertex, Vec2 uv)
	{
		GL11.glTexCoord2f(uv.x, uv.y);
		GL11.glVertex2f(vertex.x, vertex.y);
	}

}
