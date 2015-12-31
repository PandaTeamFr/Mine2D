package celo.mine2d.ui.texture;

import celo.mine2d.utils.Vec2;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 * Created by loucass003 on 22/12/2015.
 */
public class Texture implements ITexture {

    public String path;
    public int id;
    public Vec2 size;
    private static final int BYTES_PER_PIXEL = 4;

    public Texture(String path) {
        this.path = path;
        this.size = new Vec2(0,0);
    }

    @Override
    public void load() throws IOException {
        BufferedImage image = ImageIO.read(Texture.class.getResourceAsStream("/" + path));
        this.size = new Vec2(image.getWidth(), image.getHeight());
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * BYTES_PER_PIXEL); //4 for RGBA, 3 for RGB

        for(int y = 0; y < image.getHeight(); y++){
            for(int x = 0; x < image.getWidth(); x++){
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                if(BYTES_PER_PIXEL == 4)
                    buffer.put((byte) ((pixel >> 24) & 0xFF));
            }
        }

        buffer.flip();

        this.id = GL11.glGenTextures();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, id);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);

        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, image.getWidth(), image.getHeight(), 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    }

    public int getId() {
        return id;
    }

    public String getPath() {
        return path;
    }

    @Override
    public Vec2 getSize() {
        return size;
    }
}
