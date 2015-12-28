package celo.mine2d.utils;

public class Color {

	public float r,g,b,a;

	public Color(float r, float g, float b, float a) {
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(int r, int g, int b) {
		this(r,g,b,255);
	}

	public Color(int r, int g, int b, int a) {
		this(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public Color(Vec3 color) {
		this(color.x,color.y,color.z,1);
	}

	public Color(Vec4 color) {
		this(color.x,color.y,color.z,color.w);
	}

	public Color(float r, float g, float b) {
		this(r, g, b, 255F);
	}

	public int toInt() {
		int r = ((int)this.r >> 16) & 0xFF;
		int g = ((int)this.g >> 8) & 0xFF;
		int b = ((int)this.b ) & 0xFF;
		int a = ((int)this.a >> 24) & 0xFF;
		return r | g | b | a;
	}

	public static Color mix(Color c1, Color c2, float ratio)
	{
		return clamp(c1.toVec4().add(c2.toVec4()).mul(ratio).toColor(), 0.0f, 1.0f);
	}

	public static Color clamp(Color c, float min, float max)
	{
		c.r = clamp(c.r, min, max);
	    c.g = clamp(c.g, min, max);
	    c.b = clamp(c.b, min, max);
	    c.a = clamp(c.a, min, max);
		return c;
	}

	public static float clamp(float v, float min, float max)
	{
		if (v < min)
			return min;
		if (v > max)
			return max;
		return v;
	}

	public Vec3 toVec3() {
		return new Vec3(r, g, b);
	}

	public Vec4 toVec4() {
		return new Vec4(r, g, b, a);
	}
}
