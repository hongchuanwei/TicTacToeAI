import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Arrays;

public class Board extends JPanel {
	private static final int NUM_COL = 3;
	private static final int NUM_ROW = 3;
	private static final int NUM_POS = NUM_COL * NUM_ROW;
	private Piece lastPiece; // last piece set
	private Piece firstPiece; // first piece to be set
	private Cell[][] cells; 
	private int nPieceSet;
	// FULL: board is full; OOB: out of boundary;
    // WP: wrong piece; OK: set successful
	public enum State {FULL, OOB, WP, OK}
	public static final int BOARD_SIZE = Cell.CELL_SIZE*NUM_COL;
	// line images
	private static final String imgHLineFilename = "img/hline2.jpg";
	private static final String imgVLineFilename = "img/vline2.jpg";
	private static final int LINE_WIDTH = 10;
	private static final int LINE_HEIGHT = BOARD_SIZE;
	private static Image imgHLine;
	private static Image imgVLine;
	private static int[] winningPatterns = {
      0x1c0,   // 0b111 000 000 (row 2)
      0x038,   // 0b000 111 000 (row 1)
      0x007,   // 0b000 000 111 (row 0)
      0x124,   // 0b100 100 100 (col 2)
      0x092,   // 0b010 010 010 (col 1)
      0x049,   // 0b001 001 001 (col 0)
      0x111,   // 0b100 010 001 (diagonal)
      0x054  // 0b001 010 100 (opposite diagonal)
	};
	private int XPattern;
	private int OPattern;
	
	public Board(Piece firstPiece) {
		// Prepare the ImageIcon and Image objects for drawImage()
		ImageIcon iconHLine = null;
		ImageIcon iconVLine = null;
		URL imgURL = getClass().getClassLoader().getResource(imgHLineFilename);
		if (imgURL != null) {
			iconHLine = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imgHLineFilename);
		}
		imgHLine = iconHLine.getImage();
 
		imgURL = getClass().getClassLoader().getResource(imgVLineFilename);
		if (imgURL != null) {
			iconVLine = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imgVLineFilename);
		}
		imgVLine = iconVLine.getImage();

		cells = new Cell[NUM_ROW][NUM_COL];
		for(int i=0; i<NUM_ROW; i++) {
			for(int j=0; j<NUM_COL; j++) {
				cells[i][j] = new Cell(i,j);
			}
		}
		nPieceSet = 0;
		this.firstPiece = firstPiece;
		XPattern = 0;
		OPattern = 0;
	}

	public GameState getWinner() {
		// return the state of the game, that is who is winning
		if(lastPiece == Piece.X ) {
			for (int aWinningPattern : winningPatterns) {
				if ((aWinningPattern & XPattern) == aWinningPattern) {
					return GameState.X_WIN;
				}
			}
		} else {
			for (int aWinningPattern : winningPatterns) {
				if ((aWinningPattern & OPattern) == aWinningPattern) {
					return GameState.O_WIN;
				}
			}
		}
		// no one wins and out of step, then draw
		if(nPieceSet == NUM_ROW * NUM_COL) return GameState.DRAW;
		// otherwise, game goes on
		return GameState.PLAYER;
	}

	public void init() {
		lastPiece = null;
		for(int i=0; i<NUM_ROW; i++) {
			for(int j=0; j<NUM_COL; j++) {
				cells[i][j].init();
			}
		}
		nPieceSet = 0;
		XPattern = 0;
		OPattern = 0;
	}
	// piece getter
	public Piece getPiece(int i, int j) {
		if(i<0 || i>=NUM_ROW || j<0 || j>=NUM_COL) return Piece.INVALID;
		return cells[i][j].getPiece();
	}

	// piece setter
	public Board.State setPiece(int i, int j, Piece piece) {
		if(piece != getNextPiece() ) return Board.State.WP;
		if(i<0 || i>=NUM_ROW || j<0 || j>=NUM_COL) return Board.State.OOB;
		if(nPieceSet >= NUM_POS || !cells[i][j].isEmpty() ) return Board.State.FULL;
		
		cells[i][j].setPiece(piece);
		if(piece == Piece.X) {
			XPattern |= (1 << (i*NUM_ROW + j) );
		} else {
			OPattern |= (1 << (i*NUM_ROW + j) );
		}
		
		lastPiece = piece;
		nPieceSet++;
		return Board.State.OK;
	}

	// get next piece to be placed
	public Piece getNextPiece() {
		if(lastPiece == null) return firstPiece;
		if(lastPiece == Piece.X) return Piece.O;
		if(lastPiece == Piece.O) return Piece.X;
		return Piece.INVALID;
	}

	// method for board to draw itself
	@Override
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		setBackground(Color.WHITE);
		// Draw the cells
		for(int i=0; i<NUM_ROW; i++) {
			for(int j=0; j<NUM_COL; j++) {
				cells[i][j].paint(g);
			}
		}

		// Draw the lines
		for(int i=1; i<NUM_ROW; i++) {
			// y pos for horizion line
			int hy = Cell.CELL_SIZE*i - LINE_WIDTH/2;
			g.drawImage(imgHLine, 0, hy, LINE_HEIGHT, LINE_WIDTH, null);

			// x pos for vertical line
			int vx = Cell.CELL_SIZE*i - LINE_WIDTH/2;
			g.drawImage(imgVLine, vx, 0, LINE_WIDTH, LINE_HEIGHT, null);
		}
	}
}

