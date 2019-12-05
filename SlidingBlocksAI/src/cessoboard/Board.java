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
//	private static final String RESOURCES_WBISHOP_PNG = "wbishop.png";
//	private static final String RESOURCES_BBISHOP_PNG = "bbishop.png";
//	private static final String RESOURCES_WKNIGHT_PNG = "wknight.png";
//	private static final String RESOURCES_BKNIGHT_PNG = "bknight.png";
//	private static final String RESOURCES_WROOK_PNG = "wrook.png";
//	private static final String RESOURCES_BROOK_PNG = "brook.png";
//	private static final String RESOURCES_WKING_PNG = "wking.png";
//	private static final String RESOURCES_BKING_PNG = "bking.png";
//	private static final String RESOURCES_BQUEEN_PNG = "bqueen.png";
//	private static final String RESOURCES_WQUEEN_PNG = "wqueen.png";
	private static final String RESOURCES_BSMALL_PNG = "blocco2.jpg";
	private static final String RESOURCES_BPAWN_PNG = "bloccoGrosso.png";

	// Logical and graphical representations of board
	public static Square[][] board;
	private final GameWindow g;
	public static int righeBoard =8;
	public static int colonneBoard=8;
	// List of pieces and whether they are movable
	public final LinkedList<Blocco> Wpieces;
	public List<Square> movable;

	private boolean whiteTurn;

	private Blocco currPiece;
	private int currX;
	private int currY;

	private LogicaMosse cmd;

	public Board(GameWindow g) {
		this.g = g;
		board = new Square[righeBoard][colonneBoard];
		Wpieces = new LinkedList<Blocco>();
		setLayout(new GridLayout(righeBoard, colonneBoard, 0, 0));

		this.addMouseListener(this);
		this.addMouseMotionListener(this);

		for (int x = 0; x < righeBoard; x++) {
			for (int y = 0; y < colonneBoard; y++) {
				if(x<=righeBoard-1 && y==colonneBoard-1 ||x<=righeBoard-1 && y==0|| x==righeBoard-1 && y<=colonneBoard-1||x==0 && y<=colonneBoard-1 ) {
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
	
	   public void aggiungiBlocco(Blocco p) {
	    	//caso blocco lungo o quadrato
	    	if(p.getCurrentSquare()!=p.getFinalSquare()) {
	    		 board[p.getCurrentSquare().getYNum()][p.getCurrentSquare().getXNum()].setOccupyingPiece(p);
	    		 board[p.getFinalSquare().getYNum()][p.getFinalSquare().getXNum()].setOccupyingPiece(p);
//	    		 System.out.println("BOARD IN: " + p.getCurrentSquare().getYNum() + " e " + p.getCurrentSquare().getXNum() +" occupata");
//	    		 System.out.println("BOARD IN: " + p.getFinalSquare().getYNum() + " e " + p.getFinalSquare().getXNum() + " occupata");
	    		 p.setPosition(p.getCurrentSquare(), p.getFinalSquare());
	    		 System.out.println("S");
	    	}
	    	//caso blocco piccolo
	    	else {
	    		 board[p.getCurrentSquare().getYNum()][p.getCurrentSquare().getXNum()].setOccupyingPiece(p);
	    		 System.out.println("AGGIUNGO BLOCCO IN: " + p.getCurrentSquare().getXNum() + " " + p.getCurrentSquare().getYNum());
	    	        p.setPosition(p.getCurrentSquare(), p.getCurrentSquare());
	    	}
	    	
	    }
	   
	   public void createWinSquare(Square s) {
		   s.setColor(3);
		   cmd.setWinSquare(s);
	   }

	private void initializePieces() {
		
//		aggiungiBlocco(new BloccoPiccolo('1', board[2][1],board[2][1], RESOURCES_BSMALL_PNG));
//		aggiungiBlocco(new BloccoPiccolo('1', board[1][1],board[1][1], RESOURCES_BSMALL_PNG));
//		aggiungiBlocco(new BloccoPiccolo('1', board[3][4],board[3][4], RESOURCES_BSMALL_PNG));
		aggiungiBlocco(new BloccoLungo('2', board[5][2],board[5][3], RESOURCES_BPAWN_PNG));
//		aggiungiBlocco(new BloccoPiccolo('2', board[3][2],board[3][2], RESOURCES_BSMALL_PNG));
//		aggiungiBlocco(new BloccoPiccolo('3', board[4][4],board[4][4], RESOURCES_BSMALL_PNG));
//		
//		board[2][3].put(new BloccoPiccolo('1', board[2][3],board[2][3], RESOURCES_BSMALL_PNG));
//		board[3][3].put(new BloccoLungo('2', board[3][3],board[3][4], RESOURCES_BSMALL_PNG));
//		board[2][4].put(new BloccoPiccolo('3', board[2][4],board[2][4], RESOURCES_BSMALL_PNG));
		
		cmd = new LogicaMosse(this, Wpieces);
		createWinSquare(board[0][2]);
	}

	public Square[][] getSquareArray() {
		return this.board;
	}

	public boolean getTurn() {
		return whiteTurn;
	}

	public void setCurrPiece(Blocco p) {
		this.currPiece = p;
	}

	public Blocco getCurrPiece() {
		return this.currPiece;
	}

	@Override
	public void paintComponent(Graphics g) {
		// super.paintComponent(g);

		for (int x = 0; x < righeBoard; x++) {
			for (int y = 0; y < colonneBoard; y++) {
				Square sq = board[y][x];
				sq.paintComponent(g);
			}
		}

		if (currPiece != null) {
//			if ((currPiece.getColor() == 1 && whiteTurn)
//					|| (currPiece.getColor() == 0 && !whiteTurn)) {
				final Image i = currPiece.getImage();
				g.drawImage(i, currX, currY, null);
//			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
		currX = e.getX();
		currY = e.getY();

		Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

		if (sq.isOccupied()) {
			
				 System.out.println("BOARD IN: " + sq.getY()/50+ " e " + sq.getX()/50 +" occupata");
				
			currPiece = sq.getOccupyingPiece();
//			if (currPiece.getColor() == 0 && whiteTurn)
//				return;
//			if (currPiece.getColor() == 1 && !whiteTurn)
//				return;
			sq.setDisplay(false);
		}
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		Square sq = (Square) this.getComponentAt(new Point(e.getX(), e.getY()));

		if (currPiece != null) {
//			if (currPiece.getColor() == 0 && whiteTurn)
//				return;
//			if (currPiece.getColor() == 1 && !whiteTurn)
//				return;

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