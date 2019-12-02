package cessoboard;


import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class Board extends JPanel implements MouseListener, MouseMotionListener {
	// Resource location constants for piece images
	private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
	private static final String RESOURCES_WROOK_PNG = "wrook.png";
	private static final String RESOURCES_BROOK_PNG = "brook.png";
	private static final String RESOURCES_WKING_PNG = "wking.png";
	private static final String RESOURCES_BKING_PNG = "bking.png";
	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_WPAWN_PNG = "blocco2.jpg";
	private static final String RESOURCES_BPAWN_PNG = "blocco2.jpg";

	// Logical and graphical representations of board
	private final Square[][] board;
	private final GameWindow g;

	// List of pieces and whether they are movable
	public final LinkedList<Piece> Bpieces;
	public final LinkedList<Piece> Wpieces;
	public List<Square> movable;

	private boolean whiteTurn;

	private Piece currPiece;
	private int currX;
	private int currY;

	private CheckmateDetector cmd;

	public Board(GameWindow g) {
		this.g = g;
		board = new Square[8][8];
		Bpieces = new LinkedList<Piece>();
		Wpieces = new LinkedList<Piece>();
		setLayout(new GridLayout(8, 8, 0, 0));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				if(x<=7 && y==7 ||x<=7 && y==0|| x==7 && y<=7||x==0 && y<=7 ) {
					board[x][y] = new Square(this, 1, y, x);
					this.add(board[x][y]);
				}else {
					board[x][y] = new Square(this, 0, y, x);
					this.add(board[x][y]); 
				}

			}
		}

		initializePieces();

		this.setPreferredSize(new Dimension(400, 400));
		this.setMaximumSize(new Dimension(400, 400));
		this.setMinimumSize(this.getPreferredSize());
		this.setSize(new Dimension(400, 400));

		whiteTurn = true;

	}

	private void initializePieces() {

			board[6][6].put(new Queen(1, board[6][6], RESOURCES_WPAWN_PNG));
			cmd = new CheckmateDetector(this, Wpieces, Bpieces);
	}

	public Square[][] getSquareArray() {
		return this.board;
	}

	public boolean getTurn() {
		return whiteTurn;
	}

	public void setCurrPiece(Piece p) {
		this.currPiece = p;
	}

	public Piece getCurrPiece() {
		return this.currPiece;
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);

		for (int x = 0; x < 8; x++) {
			for (int y = 0; y < 8; y++) {
				Square sq = board[y][x];
				sq.paintComponent(g);
			}
		}

		if (currPiece != null) {
			if ((currPiece.getColor() == 1 && whiteTurn)
					|| (currPiece.getColor() == 0 && !whiteTurn)) {
				final Image i = currPiece.getImage();
				g.drawImage(i, currX, currY, null);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currX = e.getX();
		currY = e.getY();

		Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

		if (sq.isOccupied()) {
			currPiece = sq.getOccupyingPiece();
			if (currPiece.getColor() == 0 && whiteTurn)
				return;
			if (currPiece.getColor() == 1 && !whiteTurn)
				return;
			sq.setDisplay(false);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

		if (currPiece != null) {
			if (currPiece.getColor() == 0 && whiteTurn)
				return;
			if (currPiece.getColor() == 1 && !whiteTurn)
				return;

			List<Square> legalMoves = currPiece.getLegalMoves(this);
			movable = cmd.getAllowableSquares(whiteTurn);

			if (legalMoves.contains(sq) && movable.contains(sq)
					&& cmd.testMove(currPiece, sq)) {
				sq.setDisplay(true);
				currPiece.move(sq);
				cmd.update();

				if (cmd.blackCheckMated()) {
					currPiece = null;
					repaint();
					this.removeMouseListener(this);
					this.removeMouseMotionListener(this);
					g.checkmateOccurred(0);
				} else if (cmd.whiteCheckMated()) {
					currPiece = null;
					repaint();
					this.removeMouseListener(this);
					this.removeMouseMotionListener(this);
					g.checkmateOccurred(1);
				} else {
					currPiece = null;
					movable = cmd.getAllowableSquares(whiteTurn);
				}

			} else {
				currPiece.getPosition().setDisplay(true);
				currPiece = null;
			}
		}

		repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		currX = e.getX() - 24;
		currY = e.getY() - 24;

		repaint();
	}

	// Irrelevant methods, do nothing for these mouse behaviors
	@Override
	public void mouseMoved(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

}