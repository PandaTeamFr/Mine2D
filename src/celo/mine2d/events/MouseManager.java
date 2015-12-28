package celo.mine2d.events;

import celo.mine2d.utils.Vec2;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;

public class MouseManager {

	public List<MouseListner> listners = new ArrayList<MouseListner>();

	public void addMouseListner(MouseListner m) {
		if(!listners.contains(m))
			listners.add(m);
	}

	public void update() {
		while (Mouse.next()) {
			float mousey = Display.getHeight() - Mouse.getY();
			Vec2 pos = new Vec2(Mouse.getX(), mousey);
			for(MouseListner m : listners) {
				m.mouseMoved(pos);
				if (Mouse.getEventButtonState()) {
					if (Mouse.getEventButton() == 0) {
						m.mousePressed(pos);
					}
				}else {
					if (Mouse.getEventButton() == 0) {
						m.mouseReleased(pos);
					}
				}
			}
		}
	}
}
