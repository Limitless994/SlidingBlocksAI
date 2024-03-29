package vecchio;


import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
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
	
    public int[] getLinearOccupations(Square[][] board, int x, int y) {
    	int lastYabove = 0;
		int lastXright = Board.righeBoard-1;
		int lastYbelow = Board.colonneBoard-1;
		int lastXleft = 0;

		for (int i = 0; i < y; i++) {
			if (board[i][x].isOccupied()) {
				lastYabove = i + 1;
			}
		}

		for (int i = Board.righeBoard-1; i > y; i--) {
			if (board[i][x].isOccupied()) {
				lastYbelow = i - 1;
			}
		}

		for (int i = 0; i < x; i++) {
			if (board[y][i].isOccupied()) {
				lastXleft = i + 1;
			}
		}

		for (int i = Board.colonneBoard-1; i > x; i--) {
			if (board[y][i].isOccupied()) {
				lastXright = i - 1;
			}
		}

		int[] occups = {lastYabove, lastYbelow, lastXleft, lastXright};


		return occups;
    }
    
    
    public abstract List<Square> getLegalMoves(Board b);
}