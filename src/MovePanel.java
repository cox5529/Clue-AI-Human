
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MovePanel extends JPanel {
	
	private static final long serialVersionUID = 4047071600067623750L;
	
	private JLabel idView;
	private JLabel rangeView;
	private JLabel disproveView;
	private JTextField guessField;
	private JTextField moveField;
	private JTextField disproveField;
	private JButton disprove;
	private JButton move;
	private JButton guess;
	private JButton accuse;
	
	private int[][] players;
	private int id;
	
	public MovePanel(int id, int[][] players) {
		this.id = id;
		this.players = players;
		this.setLayout(new GridLayout(5, 2));
		idView = new JLabel("You are: " + id);
		guessField = new JTextField();
		guess = new JButton("Guess");
		guess.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent arg0) {
				if(guessField.getText().length() != 0) {
					System.out.println("Guess " + guessField.getText());
					guessField.setText("");
				}
			}
		});
		accuse = new JButton("Accuse");
		accuse.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				if(guessField.getText().length() != 0) {
					System.out.println("Accusation " + guessField.getText());
					guessField.setText("");
				}
			}
		});
		moveField = new JTextField();
		move = new JButton("Move");
		move.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				String move = moveField.getText();
				String guess = guessField.getText();
				if(guess.length() != 0 && move.length() != 0) {
					System.out.println("Move Guess " + move + " " + guess);
				} else if(move.length() != 0) {
					System.out.println("Move " + move);
				}
				if(move.length() != 0) {
					String[] data = move.split(" ");
					int y = Integer.parseInt(data[0]);
					int x = Integer.parseInt(data[1]);
					doMove(y, x);
				}
				moveField.setText("");
				guessField.setText("");
			}
		});
		disproveField = new JTextField();
		disprove = new JButton("Disprove");
		disprove.addActionListener(new ActionListener() {
			@Override public void actionPerformed(ActionEvent e) {
				String text = disproveField.getText();
				if(text.length() != 0) {
					System.out.println(text);
					disproveField.setText("");
				}
			}
		});
		disproveView = new JLabel("");
		rangeView = new JLabel("Range = 0");
		this.add(idView);
		this.add(accuse);
		this.add(guessField);
		this.add(guess);
		this.add(moveField);
		this.add(move);
		this.add(disproveField);
		this.add(disprove);
		this.add(rangeView);
		this.add(disproveView);
	}
	
	private void doMove(int y, int x) {
		int[] cur = Human.getCurrentPos(id, players);
		players[cur[0]][cur[1]] -= Math.pow(2, id);
		players[y][x] += Math.pow(2, id);
	}
	
	public void setRange(int range) {
		rangeView.setText("Range = " + range);
	}
	
	public void setDisproveview(String data) {
		disproveView.setText(data);
	}
}
