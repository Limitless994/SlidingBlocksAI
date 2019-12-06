package vecchio;


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

		List<Square> bMoves = getDiagonalOccupations(board, x, y);

		legalMoves.addAll(bMoves);

		return legalMoves;
	}

	@Override
	public List<Square> getDiagonalOccupations(Square[][] board, int x, int y) {
		LinkedList<Square> diagOccup = new LinkedList<Square>();

		int xNW = x - 1;
		int xSW = x - 1;
		int xNE = x + 1;
		int xSE = x + 1;
		int yNW = y - 1;
		int ySW = y + 1;
		int yNE = y - 1;
		int ySE = y + 1;

		while (xNW >= 0 && yNW >= 0) {
			if (board[yNW][xNW].isOccupied()) {
				//                if (board[yNW][xNW].getOccupyingPiece().getColor() == this.color) {
					//                    break;
					//                } else {
						diagOccup.add(board[yNW][xNW]);
						break;
						//                }
			} else {
				diagOccup.add(board[yNW][xNW]);
				yNW--;
				xNW--;
			}
		}

		while (xSW >= 0 && ySW < Board.colonneBoard) {
			if (board[ySW][xSW].isOccupied()) {
				//                if (board[ySW][xSW].getOccupyingPiece().getColor() == this.color) {
				//                    break;
				//                } else {
				diagOccup.add(board[ySW][xSW]);
				break;
				//                }
			} else {
				diagOccup.add(board[ySW][xSW]);
				ySW++;
				xSW--;
			}
		}

		while (xSE < Board.righeBoard && ySE < Board.colonneBoard) {
			if (board[ySE][xSE].isOccupied()) {
				//                if (board[ySE][xSE].getOccupyingPiece().getColor() == this.color) {
				//                    break;
				//                } else {
				diagOccup.add(board[ySE][xSE]);
				break;
				//                }
			} else {
				diagOccup.add(board[ySE][xSE]);
				ySE++;
				xSE++;
			}
		}

		while (xNE < Board.righeBoard && yNE >= 0) {
			if (board[yNE][xNE].isOccupied()) {
				//                if (board[yNE][xNE].getOccupyingPiece().getColor() == this.color) {
				//                    break;
				//                } else {
				diagOccup.add(board[yNE][xNE]);
				break;
				//                }
			} else {
				diagOccup.add(board[yNE][xNE]);
				yNE--;
				xNE++;
			}
		}

		return diagOccup;
	}

	@Override
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

//		System.out.println("Sopra: " + lastYabove + " Sotto: " + lastYbelow + "Sinistra " + lastXleft + " Destra " + lastXright);

		return occups;
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
		Blocco occup = fin.getOccupyingPiece();

		if (occup != null) {
			fin.capture(this);
		}

		currentSquare.removePiece();
		this.currentSquare = fin;
		currentSquare.put(this);
		return true;
	}

}
