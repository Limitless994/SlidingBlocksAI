package cessoboard;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public abstract class Blocco {
	protected final char ID;
    protected Square currentSquare;
    protected Square finalSquare;
    protected BufferedImage img;
    
    public Blocco(char ID, Square initSq,Square finalSq, String img_file) {
        this.ID = ID;
        this.currentSquare = initSq;
        this.finalSquare = finalSq;
        
        try {
            if (this.img == null) {
              this.img = ImageIO.read(getClass().getResource(img_file));
            }
          } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
          }
    }
    
    public abstract boolean move(Square fin);
    
    public Square getPosition() {
        return currentSquare;
    }
    
    public abstract void setPosition(Square sq, Square fq);
    
    
    public char getID() {
		return ID;
	}

	public Image getImage() {
        return img;
    }
	
    
    public Square getCurrentSquare() {
		return currentSquare;
	}

	public void setCurrentSquare(Square currentSquare) {
		this.currentSquare = currentSquare;
	}

	public Square getFinalSquare() {
		return finalSquare;
	}

	public void setFinalSquare(Square finalSquare) {
		this.finalSquare = finalSquare;
	}

	public abstract void draw(Graphics g);
	
    public abstract int[] getLinearOccupations(Square[][] board, int x, int y);
    
    public abstract List<Square> getDiagonalOccupations(Square[][] board, int x, int y);
    
    // No implementation, to be implemented by each subclass
    public abstract List<Square> getLegalMoves(Board b);
}