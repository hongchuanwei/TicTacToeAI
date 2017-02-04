import java.awt.*;     // Using AWT's Graphics and Color
import java.awt.event.*;
import java.net.URL;
import javax.swing.*;  // Using Swing's components and container

public class TicTacToe extends JFrame {

	private Board board;
	private GameState gameState;
	private Piece firstPiece = Piece.X;
	private StatusBar statusBar;
	public TicTacToe() {
		gameState = GameState.PLAYER; // always let player play first
		setMinimumSize(
			new Dimension(Board.BOARD_SIZE+25, Board.BOARD_SIZE + StatusBar.HEIGHT*2));
		setMaximumSize(
			new Dimension(Board.BOARD_SIZE+25, Board.BOARD_SIZE + StatusBar.HEIGHT*2));

		Container cp = this.getContentPane(); 
		cp.setLayout(new BorderLayout());
	  
		// board
		board = new Board(firstPiece);
		board.setPreferredSize(
			new Dimension(Board.BOARD_SIZE, Board.BOARD_SIZE));
		board.setMinimumSize(
			new Dimension(Board.BOARD_SIZE, Board.BOARD_SIZE));
		cp.add(board, BorderLayout.NORTH);

		// status bar
		statusBar = new StatusBar();
		statusBar.setPreferredSize(
			new Dimension(StatusBar.WIDTH, StatusBar.HEIGHT));
		cp.add(statusBar, BorderLayout.SOUTH);
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();  // pack the components of "super" JFrame
		setTitle("Tic Tac Toe");
		setVisible(true);
		// The canvas (JPanel) fires a MouseEvent upon mouse-click
		board.addMouseListener(
			new MouseAdapter() {
				@Override
				public void mousePressed(MouseEvent e) {  // mouse-clicked handler
					if (gameState == GameState.PLAYER) {
						// Get mouse position 
						int mouseX = e.getX();
						int mouseY = e.getY();
						// Get the row and column clicked
						int rowSelected = mouseY / Cell.CELL_SIZE;
						int colSelected = mouseX / Cell.CELL_SIZE;
					
						board.setPiece(rowSelected, colSelected, board.getNextPiece()); // set piece and update internal states
						statusBar.setStatusText( board.getNextPiece() ); // update text to show who's turn
						if( board.getWinner() == GameState.X_WIN) {
							statusBar.setStatusText( GameState.X_WIN);
							gameState = GameState.X_WIN;
						} else if ( board.getWinner() == GameState.O_WIN) {
							statusBar.setStatusText( GameState.O_WIN );
							gameState = GameState.O_WIN;
						} else if ( board.getWinner() == GameState.DRAW) {
							statusBar.setStatusText( GameState.DRAW );
							gameState = GameState.DRAW;
						}
						repaint();  // Refresh the drawing canvas
					}
				}
			}
		);

		// status bar button listener event
		statusBar.button.addActionListener(new BtnPressListener());
	}
	
	// The entry main method
	public static void main(String[] args) {
		try {
            // Set System L&F
			UIManager.setLookAndFeel(
				UIManager.getSystemLookAndFeelClassName());
		} 
		catch (UnsupportedLookAndFeelException e) {
			// handle exception
		}
		catch (ClassNotFoundException e) {
			// handle exception
		}
		catch (InstantiationException e) {
			// handle exception
		}
		catch (IllegalAccessException e) {
			// handle exception
		}

		
		//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		SwingUtilities.invokeLater(
			new Runnable() {
				@Override
				public void run() {
					new TicTacToe(); // Let the constructor do the job
				}
			}
		);
	}

	/* named inner class used as ActionListener */
	private class BtnPressListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent evt) {
			board.init(); // board initialization
			gameState = GameState.PLAYER;
			statusBar.init(); // status bar initialization
			repaint();
		}
	}
}
