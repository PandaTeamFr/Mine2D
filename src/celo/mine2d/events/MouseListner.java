package celo.mine2d.events;

import celo.mine2d.utils.Vec2;

public interface MouseListner {

	public void mousePressed(Vec2 pos);
	
	public void mouseReleased(Vec2 pos);

	public void mouseMoved(Vec2 pos);
	
}
