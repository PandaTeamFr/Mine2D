package celo.mine2d.ui.texture;

import celo.mine2d.utils.Vec2;

import java.io.IOException;

/**
 * Created by loucass003 on 22/12/2015.
 */
public interface ITexture {

    public abstract void load() throws IOException;

    public abstract int getId();

    public abstract Vec2 getSize();
}
