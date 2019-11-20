package Entity;

import java.awt.Graphics;
import java.awt.Rectangle;


import nuovo.GameHandler;

public abstract class Blocco {

	protected GameHandler handler;
	float x,y ;
	int width;
	int height;

	protected Rectangle bounds;
	
	public Blocco(GameHandler handler, float x, float y) {
		this.handler= handler;
		width=32;
		height=32;
		this.x=x;
		this.y=y;
		bounds = new Rectangle(0, 0, width, height);
	}
	
	public abstract void tick();
	
	public abstract void render(Graphics g);
	
	public boolean checkEntityCollisions(float xOffset, float yOffset){
		for(Blocco e : handler.getWorld().getBlocchi()){
			if(e.equals(this))
				continue;
			if(e.getCollisionBounds(0f, 0f).intersects(getCollisionBounds(xOffset, yOffset)))
				return true;
		}
		return false;
	}
	
	public Rectangle getCollisionBounds(float xOffset, float yOffset){
		return new Rectangle((int) (x + bounds.x + xOffset), (int) (y + bounds.y + yOffset), bounds.width, bounds.height);
	}

	public GameHandler getHandler() {
		return handler;
	}

	public void setHandler(GameHandler handler) {
		this.handler = handler;
	}

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		this.bounds = bounds;
	}
	
	
	
}
