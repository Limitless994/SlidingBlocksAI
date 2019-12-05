package cessoboard;


import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * Component of the Chess game that detects check mates in the game.
 * 
 * @author Jussi Lundstedt
 *
 */
public class LogicaMosse {
	private LinkedList<Blocco> Pieces;
	private LinkedList<Square> movableSquares;
	private final LinkedList<Square> squares;
	private HashMap<Square,List<Blocco>> wMoves;
    private int righeBoard =Board.righeBoard;
    private int colonneBoard=Board.colonneBoard;

	/**
	 * Constructs a new instance of CheckmateDetector on a given board. By
	 * convention should be called when the board is in its initial state.
	 * 
	 * @param b The board which the detector monitors
	 * @param wPieces White pieces on the board.
	 * @param bPieces Black pieces on the board.
	 * @param wk Piece object representing the white king
	 * @param bk Piece object representing the black king
	 */
	public LogicaMosse(Board b, LinkedList<Blocco> wPieces) {
		this.Pieces = wPieces;

		// Initialize other fields
		squares = new LinkedList<Square>();
		movableSquares = new LinkedList<Square>();
		wMoves = new HashMap<Square,List<Blocco>>();


		Square[][] brd = b.getSquareArray();

		// add all squares to squares list and as hashmap keys
		for (int x = 1; x < righeBoard-1; x++) {
			for (int y = 1; y < colonneBoard-1; y++) {
				squares.add(brd[y][x]);
				wMoves.put(brd[y][x], new LinkedList<Blocco>());

			}
		}

		// update situation
		update();
	}
	
	public void setWinSquare(Square q) {
		squares.add(q);
		wMoves.put(q, new LinkedList<Blocco>());
		update();
	}

	/**
	 * Updates the object with the current situation of the game.
	 */
	public void update() {
		// empty moves and movable squares at each update
		for (List<Blocco> pieces : wMoves.values()) {
			pieces.removeAll(pieces);
		}

		movableSquares.removeAll(movableSquares);


	}

	/**
	 * Checks if the black king is threatened
	 * @return boolean representing whether the black king is in check.
	 */
	public boolean blackInCheck() {
		update();
		movableSquares.addAll(squares);
		return false;

	}

	/**
	 * Checks if the white king is threatened
	 * @return boolean representing whether the white king is in check.
	 */
	public boolean whiteInCheck() {
		update();
		movableSquares.addAll(squares);
		return false;
	}

	/**
	 * Checks whether black is in checkmate.
	 * @return boolean representing if black player is checkmated.
	 */
	public boolean blackCheckMated() {
		return false;
	}

	/**
	 * Checks whether white is in checkmate.
	 * @return boolean representing if white player is checkmated.
	 */
	public boolean whiteCheckMated() {
		return false;
	}

	/*
	 * Helper method to determine if the king can evade the check.
	 * Gives a false positive if the king can capture the checking piece.
	 */

	/**
	 * Method to get a list of allowable squares that the player can move.
	 * Defaults to all squares, but limits available squares if player is in
	 * check.
	 * @param b boolean representing whether it's white player's turn (if yes,
	 * true)
	 * @return List of squares that the player can move into.
	 */
	public List<Square> getAllowableSquares(boolean b) {
		movableSquares.removeAll(movableSquares);
		if (whiteInCheck()) {
			whiteCheckMated();
		} else if (blackInCheck()) {
			blackCheckMated();
		}
		return movableSquares;
	}

	/**
	 * Tests a move a player is about to make to prevent making an illegal move
	 * that puts the player in check.
	 * @param p Piece moved
	 * @param sq Square to which p is about to move
	 * @return false if move would cause a check
	 */
	public boolean testMove(Blocco p, Square sq) {
		Blocco c = sq.getOccupyingPiece();

		boolean movetest = true;
		Square init = p.getPosition();

		p.move(sq);
		update();

//		if (p.getColor() == 0 && blackInCheck()) movetest = false;
//		else if (p.getColor() == 1 && whiteInCheck()) movetest = false;

		p.move(init);
		if (c != null) sq.put(c);

		update();

		movableSquares.addAll(squares);
		return movetest;
	}

}
