package cessoboard;


import java.awt.Color;
import java.awt.Graphics;

import javax.swing.*;

@SuppressWarnings("serial")
public class Square extends JComponent {
    private Board b;
    
    private int color;
    private Blocco occupyingPiece;
    private boolean dispPiece;
    
    private int xNum;
    private int yNum;
    
    public Square(Board b, int c, int xNum, int yNum) {
        
        this.b = b;
        this.color = c;
        this.dispPiece = true;
        this.xNum = xNum;
        this.yNum = yNum;
        
        
        this.setBorder(BorderFactory.createEmptyBorder());
    }
    
    public int getColor() {
        return this.color;
    }
    
    public Blocco getOccupyingPiece() {
        return occupyingPiece;
    }
    
    public boolean isOccupied() {
        return (this.occupyingPiece != null);
    }
    
    public int getXNum() {
        return this.xNum;
    }
    
    public int getYNum() {
        return this.yNum;
    }
    
    
    
    public void setColor(int color) {
		this.color = color;
	}

	public int getxNum() {
		return xNum;
	}

	public void setxNum(int xNum) {
		this.xNum = xNum;
	}

	public int getyNum() {
		return yNum;
	}

	public void setyNum(int yNum) { 
		this.yNum = yNum;
	}

	public void setDisplay(boolean v) {
        this.dispPiece = v;
    }
    
//    public void put(Blocco p) {
//        this.occupyingPiece = p;
//        p.setPosition(p.getCurrentSquare(),p.getFinalSquare());
//    }
    
    public void put(Blocco p) {
        this.occupyingPiece = p;
        p.setPosition(p.getCurrentSquare(),p.getFinalSquare());
    }
 
    
    public void setOccupyingPiece(Blocco p) {
		this.occupyingPiece = p;
	}

	public Blocco removePiece() {
        Blocco p = this.occupyingPiece;
        this.occupyingPiece = null;
        return p;
    }
    
    public void capture(Blocco p) {
        Blocco k = getOccupyingPiece();
//        if (k.getColor() == 0) b.Bpieces.remove(k);
//        if (k.getColor() == 1) b.Wpieces.remove(k);
        this.occupyingPiece = p;
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        if (this.color == 1) {
            g.setColor(new Color(221,192,127));
        }
        else if(this.color == 3) {
        	  g.setColor(new Color(100,100,100));
        }
        else {
            g.setColor(new Color(101,67,33));
        }
        
        g.fillRect(this.getX(), this.getY(), this.getWidth(), this.getHeight());
        
        if(occupyingPiece != null && dispPiece) {
            occupyingPiece.draw(g);
        }
    }
    
    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + xNum;
        result = prime * result + yNum;
        return result;
    }
    
}
