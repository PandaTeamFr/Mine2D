package celo.mine2d.events;

import org.lwjgl.input.Keyboard;

import java.util.ArrayList;
import java.util.List;

public class KeyBoardManager {

	public List<KeyListner> listners = new ArrayList<KeyListner>();

	public void addKeyListner(KeyListner k) {
		if(!listners.contains(k))
			listners.add(k);
	}

	public void update() {
		Keyboard.enableRepeatEvents(true);
		while (Keyboard.next()) {
			for(KeyListner k : listners) {
				int key = Keyboard.getEventKey();
				if (Keyboard.getEventKeyState()) 
					k.keyPressed(key);
				else
					k.keyReleased(key);
			}
		}
	}

}
