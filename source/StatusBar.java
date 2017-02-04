import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class StatusBar extends JPanel {
	public JLabel statusText;
	public static final int WIDTH = Cell.CELL_SIZE * 3;
	public static final int HEIGHT = 50;
	public static final int ST_MIN_WIDTH = Cell.CELL_SIZE*2-Cell.CELL_PADDING;
	public static final int ST_MIN_HEIGHT = HEIGHT;
	public static final int BT_MIN_WIDTH = Cell.CELL_SIZE;
	public static final int BT_MIN_HEIGHT = HEIGHT-10;
	public JButton button = new JButton(); // use setter to set text and icon
	
	
	public StatusBar() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
		// status text
		statusText = new JLabel(Messages.WELCOME, null, SwingConstants.LEFT);
		statusText.setFont(new Font(Font.DIALOG, Font.PLAIN, 20));
		statusText.setPreferredSize(
			new Dimension(ST_MIN_WIDTH, ST_MIN_HEIGHT));
		statusText.setMinimumSize(
			new Dimension(ST_MIN_WIDTH, ST_MIN_HEIGHT));
		add(statusText);

		// button 
		button.setText("New");
		button.setPreferredSize(
			new Dimension(BT_MIN_WIDTH, BT_MIN_HEIGHT));
		add(button);
	}
	
	public void setStatusText( Piece piece ) {
		if(piece == Piece.O) statusText.setText( Messages.O_TURN );
		if(piece == Piece.X) statusText.setText( Messages.X_TURN );
	}
	public void setStatusText( GameState gameState ) {
		if(gameState == GameState.X_WIN ) statusText.setText( Messages.X_WIN );
		if(gameState == GameState.O_WIN ) statusText.setText( Messages.O_WIN );
		if(gameState == GameState.DRAW ) statusText.setText( Messages.DRAW );
	}

	public void init() {
		statusText.setText(Messages.WELCOME);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		setBackground(Color.WHITE);
	}

	public final class Messages {
		public static final String WELCOME = "Welcome!";
		public static final String X_TURN = "X's turn";
		public static final String O_TURN = "O's turn";
		public static final String X_WIN = "X has won";
		public static final String O_WIN = "O has won";
		public static final String DRAW = "It's a draw";
	}
}
