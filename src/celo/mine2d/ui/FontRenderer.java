package celo.mine2d.ui;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;

import celo.mine2d.utils.Bounds;
import celo.mine2d.utils.Color;
import celo.mine2d.utils.Vec2;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import static java.awt.Font.MONOSPACED;
import static java.awt.Font.PLAIN;
import static java.awt.Font.TRUETYPE_FONT;

public class FontRenderer {

    private final Map<Character, Bounds> bounds;
    private int fontHeight;
    private int id;
    private Vec2 tsize;

    /**
     * Creates a default antialiased font with monospaced bounds and default
     * size 16.
     */
    public FontRenderer() {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), true);
    }

    /**
     * Creates a default font with monospaced bounds and default size 16.
     *
     * @param antiAlias Wheter the font should be antialiased or not
     */
    public FontRenderer(boolean antiAlias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, 16), antiAlias);
    }

    /**
     * Creates a default antialiased font with monospaced bounds and specified
     * size.
     *
     * @param size FontRenderer size
     */
    public FontRenderer(int size) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), true);
    }

    /**
     * Creates a default font with monospaced bounds and specified size.
     *
     * @param size FontRenderer size
     * @param antiAlias Wheter the font should be antialiased or not
     */
    public FontRenderer(int size, boolean antiAlias) {
        this(new java.awt.Font(MONOSPACED, PLAIN, size), antiAlias);
    }

    /**
     * Creates a antialiased FontRenderer from an input stream.
     *
     * @param in The input stream
     * @param size FontRenderer size
     * @throws FontFormatException if fontFile does not contain the required
     * font tables for the specified format
     * @throws IOException If font can't be read
     */
    public FontRenderer(InputStream in, int size) throws FontFormatException, IOException {
        this(in, size, true);
    }

    /**
     * Creates a FontRenderer from an input stream.
     *
     * @param in The input stream
     * @param size FontRenderer size
     * @param antiAlias Wheter the font should be antialiased or not
     * @throws FontFormatException if fontFile does not contain the required
     * font tables for the specified format
     * @throws IOException If font can't be read
     */
    public FontRenderer(InputStream in, int size, boolean antiAlias) throws FontFormatException, IOException {
        this(java.awt.Font.createFont(TRUETYPE_FONT, in).deriveFont(PLAIN, size), antiAlias);
    }

    /**
     * Creates a antialiased font from an AWT FontRenderer.
     *
     * @param font The AWT FontRenderer
     */
    public FontRenderer(java.awt.Font font) {
        this(font, true);
    }

    /**
     * Creates a font from an AWT FontRenderer.
     *
     * @param font The AWT FontRenderer
     * @param antiAlias Wheter the font should be antialiased or not
     */
    public FontRenderer(java.awt.Font font, boolean antiAlias) {
        bounds = new HashMap<Character, Bounds>();
        id = createFontTexture(font, antiAlias);
    }

    /**
     * Creates a font texture from specified AWT font.
     *
     * @param font The AWT font
     * @param antiAlias Wheter the font should be antialiased or not
     * @return FontRenderer texture
     */
    private int createFontTexture(java.awt.Font font, boolean antiAlias) {
        int imageWidth = 0;
        int imageHeight = 0;

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage ch = createCharImage(font, c, antiAlias);
            if (ch == null) {
                continue;
            }

            imageWidth += ch.getWidth();
            imageHeight = Math.max(imageHeight, ch.getHeight());
        }

        fontHeight = imageHeight;

        BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();

        int x = 0;

        for (int i = 32; i < 256; i++) {
            if (i == 127) {
                continue;
            }
            char c = (char) i;
            BufferedImage charImage = createCharImage(font, c, antiAlias);
            if (charImage == null) {
                continue;
            }

            int charWidth = charImage.getWidth();
            int charHeight = charImage.getHeight();

            Bounds ch = new Bounds(x, image.getHeight() - charHeight, charWidth, charHeight);
            g.drawImage(charImage, x, 0, null);
            x += ch.getWidth();
            bounds.put(c, ch);
        }

        int width = image.getWidth();
        int height = image.getHeight();

        int[] pixels = new int[width * height];
        image.getRGB(0, 0, width, height, pixels, 0, width);

        ByteBuffer buffer = BufferUtils.createByteBuffer(width * height * 4);
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                int pixel = pixels[i * width + j];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        tsize = new Vec2(width, height);
        buffer.flip();

        int id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);

        return id;
    }

    /**
     * Creates a char image from specified AWT font and char.
     *
     * @param font The AWT font
     * @param c The char
     * @param antiAlias Wheter the char should be antialiased or not
     * @return Char image
     */
    private BufferedImage createCharImage(java.awt.Font font, char c, boolean antiAlias) {
        BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics();
        g.dispose();

        int charWidth = metrics.charWidth(c);
        int charHeight = metrics.getHeight();

        if (charWidth == 0) {
            return null;
        }

        image = new BufferedImage(charWidth, charHeight, BufferedImage.TYPE_INT_ARGB);
        g = image.createGraphics();
        if (antiAlias) {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        }
        g.setFont(font);
        g.setPaint(java.awt.Color.WHITE);
        g.drawString(String.valueOf(c), 0, metrics.getAscent());
        g.dispose();
        return image;
    }

    /**
     * Gets the width of the specified text.
     *
     * @param text The text
     * @return Width of text
     */
    public int getWidth(CharSequence text) {
        int width = 0;
        int lineWidth = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                width = Math.max(width, lineWidth);
                lineWidth = 0;
                continue;
            }
            if (c == '\r') {
                continue;
            }
            Bounds g = bounds.get(c);
            lineWidth += g.getWidth();
        }
        width = Math.max(width, lineWidth);
        return width;
    }

    /**
     * Gets the height of the specified text.
     *
     * @param text The text
     * @return Height of text
     */
    public int getHeight(CharSequence text) {
        int height = 0;
        int lineHeight = 0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c == '\n') {
                height += lineHeight;
                lineHeight = 0;
                continue;
            }
            if (c == '\r') {
                continue;
            }
            Bounds g = bounds.get(c);
            lineHeight = Math.max(lineHeight, (int)g.getHeight());
        }
        height += lineHeight;
        return height;
    }

    /**
     * Draw text at the specified position and color.
     *
     * @param text Text to draw
     * @param pos pos x y of text
     * @param c Color to use
     */
    public void drawText(CharSequence text, Vec2 pos, Color c) {
        int textHeight = getHeight(text);

        float drawX = pos.x;
        float drawY = pos.y;
        if (textHeight > fontHeight) {
            drawY += textHeight - fontHeight;
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1f, 1f, 1f, 1f);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);
        GL11.glBegin(GL11.GL_QUADS);
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == '\n') {
                drawY += fontHeight;
                drawX = pos.x;
                continue;
            }
            if (ch == '\r') {
                continue;
            }
            Bounds g = bounds.get(ch);
            float var7 = 1F / tsize.x;
            float var8 = 1F / tsize.y;
            GL11.glColor4f(c.r, c.g, c.b, c.a);
            addVertexWithUV(new Vec2(drawX, g.getHeight() + drawY), new Vec2(g.getX() * var7, (g.getY() + g.getHeight()) * var8));
            addVertexWithUV(new Vec2(g.getWidth() + drawX, g.getHeight() + drawY), new Vec2((g.getX() + g.getWidth()) * var7, (g.getY() + g.getHeight()) * var8));
            addVertexWithUV(new Vec2(g.getWidth() + drawX, drawY), new Vec2((g.getX() + g.getWidth()) * var7, (g.getY() * var8)));
            addVertexWithUV(new Vec2(drawX, drawY), new Vec2(g.getX() * var7, g.getY() * var8));
            drawX += g.getWidth();
        }
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glPopMatrix();
    }

    public void addVertexWithUV(Vec2 vertex, Vec2 uv)
    {
        GL11.glTexCoord2f(uv.x, uv.y);
        GL11.glVertex2f(vertex.x, vertex.y);
    }

    /**
     * Draw text at the specified position.
     *
     * @param text Text to draw
     * @param pos pos X Y of text
     */
    public void drawText(CharSequence text, Vec2 pos) {
        drawText(text, pos, new Color(255,255,255));
    }

    /**
     * Disposes the font.
     */
    public void dispose() {
        GL11.glDeleteTextures(id);
    }
}