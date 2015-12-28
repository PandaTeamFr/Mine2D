package celo.mine2d.utils;

public class Bounds {

	public Vec2 pos;
	public Vec2 size;
	
	public Bounds() {
		this(new Vec2(0, 0), new Vec2(0, 0));
	}
	
	public Bounds(Vec2 pos, Vec2 size) {
		this.pos = pos;
		this.size = size;
	}
	
	public Bounds(int x, int y, int width, int height) {
		this.pos = new Vec2(x, y);
		this.size = new Vec2(width, height);
	}
	
	public void setBounds(Vec2 pos, Vec2 size) {
		this.pos = pos;
		this.size = size;
	}
	
	public Bounds getBounds() {
		return this;
	}
	
	public Vec2 getPos() {
		return pos;
	}
	
	public Vec2 getSize() {
		return size;
	}
	
	public void setPos(Vec2 pos) {
		this.pos = pos;
	}
	
	public void setSize(Vec2 size) {
		this.size = size;
	}
	
	public float getHeight() {
		return size.y;
	}
	
	public float getWidth() {
		return size.x;
	}
	
	public float getX() {
		return pos.x;
	}
	
	public float getY() {
		return pos.y;
	}

	public boolean isInBounds(Vec2 p) {
		if(p.x > pos.x && p.x < pos.x + size.x) {
			if (p.y > pos.y && p.y < pos.y + size.y)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		return this.hashCode() + "@[Pos=[" + this.pos.x + ", " + this.pos.y + "], Size=[" + this.size.x + ", " + this.size.y + "]]";
	}
}
