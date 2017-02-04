import java.awt.*;
import java.net.URL;
import javax.swing.*;

public class Cell {
	private Piece piece;
	private int row;
	private int col;

	// Images
	private static final String imgCrossFilename = "img/cross_86_87.jpg";
	private static final String imgCircleFilename = "img/circle2.jpg";
	private static Image imgCross;   // drawImage() uses an Image object
	private static Image imgCircle;
	public static final int CELL_SIZE = 100;
	public static final int CELL_PADDING = 10;
	private static final int IMAGE_SIZE = CELL_SIZE - 2*CELL_PADDING;
	
	public Cell(int row, int col) {
		this.row = row;
		this.col = col;
		piece = Piece.E;

		// Prepare the ImageIcon and Image objects for drawImage()
		ImageIcon iconCross = null;
		ImageIcon iconCircle = null;
		URL imgURL = getClass().getClassLoader().getResource(imgCrossFilename);
		if (imgURL != null) {
			iconCross = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imgCrossFilename);
		}
		imgCross = iconCross.getImage();
 
		imgURL = getClass().getClassLoader().getResource(imgCircleFilename);
		if (imgURL != null) {
			iconCircle = new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + imgCircleFilename);
		}
		imgCircle = iconCircle.getImage();
	}

	public void init() {
		piece = Piece.E;
	}

	public Piece getPiece() {
		return piece;
	}

	public void setPiece(Piece piece) {
		this.piece = piece;
	}

	public boolean isEmpty() {
		return (piece == Piece.E);
	}

	// paint cell
	public void paint(Graphics g) {
		int x1 = col*CELL_SIZE + CELL_PADDING;
		int y1 = row*CELL_SIZE + CELL_PADDING;
		if(piece == Piece.X) {
			g.drawImage(imgCross, x1, y1, IMAGE_SIZE, IMAGE_SIZE, null);
		} else if (piece == Piece.O) {
			g.drawImage(imgCircle, x1, y1, IMAGE_SIZE, IMAGE_SIZE, null);
		}
	}
}
