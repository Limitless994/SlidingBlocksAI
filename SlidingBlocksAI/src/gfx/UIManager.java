package gfx;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import nuovo.GameHandler;


public class UIManager {

	private GameHandler handler;
	private ArrayList<UIObject> objects;
	
	
	public UIManager(GameHandler handler) {
		this.handler=handler;
		objects = new ArrayList<UIObject>();
		
	}
	
	public GameHandler getHandler() {
		return handler;
	}

	public void setHandler(GameHandler handler) {
		this.handler = handler;
	}

	public ArrayList<UIObject> getObjects() {
		return objects;
	}

	public void setObjects(ArrayList<UIObject> objects) {
		this.objects = objects;
	}

	public void tick() {
		for(UIObject o : objects)
			o.tick();
	}
	
	public void render(Graphics g) {
		for(UIObject o : objects)
			o.render(g);
	}
	
	public void onMouseMove(MouseEvent e) {
		for(UIObject o : objects)
			o.onMouseMove(e);
	}
	
	public void onMouseRelease(MouseEvent e) {
		for(UIObject o : objects)
			o.onMouseRelease(e);
	}
	
	public void addObject(UIObject o) {
		objects.add(o);
	}
	
	public void removeObject(UIObject o) {
		objects.remove(o);
	}
	
}
