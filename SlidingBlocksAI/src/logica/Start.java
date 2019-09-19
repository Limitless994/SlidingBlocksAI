package logica;

import it.unical.mat.embasp.languages.Id;
import it.unical.mat.embasp.languages.Param;

@Id("start")
public class Start { 

	@Param(0)
	int x;
	
	@Param(1)
	int y;  
	
	@Param(2)
	int direzione;
	public Start() {
		this.x = 0;
		this.y = 0;
		this.direzione = 0;
	}
	
	public Start(int x, int y) {
		this.x = x;
		this.y = y;
		this.direzione = 0;
	}
	
	public Start(int x, int y, int direzione) {
		this.x = x;
		this.y = y;
		this.direzione = direzione;
	}
	
	public Start(int x, int y, int direzione1, int direzione2) {
		this.x = x;
		this.y = y;
		this.direzione = direzione1;
	} 
	
	public int getX() { return x; }
	
	public int getY() { return y; }
	
	public int getDirezione() { return direzione; }

	public void setX(int x) { this.x = x; }
	
	public void setY(int y) { this.y = y; }
	
	public void setDirezione(int direzione) { this.direzione = direzione; }
	
	public void set(int x, int y, int direzione) {
		this.x = x;
		this.y = y;
		this.direzione = direzione;
	}
	
	public boolean isFocus(int x, int y) {    // collisione con il mouse
		x -= 110;
		y -= 70;
		int X = this.x*60;
		int Y = this.y*35;
		return x > X-15 && x < X+15 && y > Y-15 && y < Y+15;
	}
	
}
