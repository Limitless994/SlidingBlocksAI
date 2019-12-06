package vecchio;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

public class BloccoLungo extends Blocco{
    protected BufferedImage img2;
public BloccoLungo(char ID, Square initSq, Square finalSq, String img_file,String img_file2) {
	super(ID, initSq, finalSq, img_file);
	try {
		this.img2 = ImageIO.read(getClass().getResource(img_file2));
	} catch (IOException e) {
		e.printStackTrace();
	}
}

@Override
public List<Square> getLegalMoves(Board b) {
	LinkedList<Square> legalMoves = new LinkedList<Square>();
	Square[][] board = b.getSquareArray();
	int x = this.getPosition().getXNum();
	int y = this.getPosition().getYNum();
	int[] occups = getLinearOccupations(board, x, y);
	for (int i = occups[0]; i <= occups[1]; i++) {
		if (i != y) legalMoves.add(board[i][x]);
	}
	for (int i = occups[2]; i <= occups[3]; i++) {
		if (i != x) legalMoves.add(board[y][i]);
	}
	return legalMoves;
}

@Override
public void draw(Graphics g) {
	int x = currentSquare.getX();
	int y = currentSquare.getY();
	g.drawImage(this.img, x, y, null);
	g.drawImage(this.img2, x+50, y, null);


}

@Override
public void setPosition(Square sq, Square fq) {
	this.currentSquare = sq;
	this.finalSquare = fq;
}

@Override
public boolean move(Square fin) {
	currentSquare.removePiece();
	finalSquare.removePiece();
	this.currentSquare = fin;
	fin.setxNum(fin.getyNum()+1);
	this.finalSquare = fin;
	currentSquare.put(this);
	finalSquare.put(this);
	return true;
}
}
