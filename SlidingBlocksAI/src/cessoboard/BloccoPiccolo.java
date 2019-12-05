package cessoboard;


import java.awt.Graphics;
import java.util.LinkedList;
import java.util.List;

public class BloccoPiccolo extends Blocco {

	public BloccoPiccolo(char ID, Square initSq,Square finalSq, String img_file) {
		super(ID, initSq, finalSq, img_file);
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

	}

	@Override
	public void setPosition(Square sq, Square fq) {
		this.currentSquare = sq;
		this.finalSquare = sq;
	}

	@Override
	public boolean move(Square fin) {
		currentSquare.removePiece();
		this.currentSquare = fin;
		currentSquare.put(this);
		return true;
	}

}
