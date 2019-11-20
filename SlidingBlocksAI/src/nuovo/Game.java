package nuovo;

import java.awt.Graphics;
import java.util.ArrayList;

import gfx.Display;
import gfx.MouseManager;





public class Game {

	private Display display;
	private int width, height;
	public String title;
	private boolean running = false;
	private Thread thread;
	
	private Graphics g;
	
	private MouseManager mouseManager;
	
	//Handler
		private GameHandler handler;
		
		public Game(String title, int width, int height) {
			this.width=width;
			this.height=height;
			this.title=title;
			mouseManager=new MouseManager();
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

		public MouseManager getMouseManager() {
			return mouseManager;
		}

		public void setMouseManager(MouseManager mouseManager) {
			this.mouseManager = mouseManager;
		}

		public GameHandler getHandler() {
			return handler;
		}

		public void setHandler(GameHandler handler) {
			this.handler = handler;
		}


		
}
