
import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JFrame;

public class HumanGUI {
	
	private JFrame frame;
	private BoardPanel bp;
	private CardPanel cp;
	private MovePanel mp;
	
	public HumanGUI(int[][] board, int[][] players, Deck chars, Deck weaps, Deck rooms, int id) {
		frame = new JFrame("Clue");
		frame.setLayout(new BorderLayout());
		bp = new BoardPanel(board, players, rooms.getNames());
		cp = new CardPanel(chars, weaps, rooms);
		mp = new MovePanel(id, players);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.setLayout(new FlowLayout());
		frame.add(bp, BorderLayout.WEST);
		frame.add(cp, BorderLayout.EAST);
		frame.add(mp, BorderLayout.SOUTH);
		frame.pack();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public MovePanel getMovePanel() {
		return mp;
	}
	
	public BoardPanel getBoardPanel() {
		return bp;
	}
	
	public CardPanel getCardPanel() {
		return cp;
	}
	
}
