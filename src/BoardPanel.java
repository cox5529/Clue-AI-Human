import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class BoardPanel extends JPanel {
	
	private static final long serialVersionUID = -5054915648300365082L;
	
	private int[][] board;
	private int[][] players;
	private String[] roomNames;
	
	public BoardPanel(int[][] board, int[][] players, String[] roomNames) {
		this.board = board;
		this.players = players;
		this.roomNames = roomNames;
		this.setPreferredSize(new Dimension(768, 768));
		repaint();
	}
	
	public void update(int[][] players) {
		this.players = players;
		repaint();
	}
	
	@Override public void paint(Graphics g) {
		super.paint(g);
		int spaceWidth = this.getWidth() / board.length;
		int spaceHeight = this.getHeight() / board[0].length;
		for(int i = 0; i < board.length; i++) {
			for(int j = 0; j < board[0].length; j++) {
				if(board[i][j] != 0) {
					g.setColor(new Color(17, 73, 84));
				} else {
					if(i % 2 == 1) {
						if(j % 2 == 1) {
							g.setColor(new Color(70, 124, 134));
						} else {
							g.setColor(new Color(111, 154, 162));
						}
					} else {
						if(j % 2 == 0) {
							g.setColor(new Color(70, 124, 134));
						} else {
							g.setColor(new Color(111, 154, 162));
						}
					}
				}
				g.fillRect(j * spaceWidth, i * spaceHeight, spaceWidth, spaceHeight);
				if(board[i][j] != 0) {
					g.setFont(new Font("Arial", Font.PLAIN, 32));
					g.setColor(Color.BLACK);
					String s = "" + roomNames[board[i][j] - 1];
					int swidth = g.getFontMetrics().stringWidth(s);
					int f = 32;
					while(swidth > spaceWidth) {
						g.setFont(new Font("Arial", Font.PLAIN, --f));
						swidth = g.getFontMetrics().stringWidth(s);
					}
					int sheight = (int) g.getFontMetrics().getStringBounds(s, g).getHeight();
					int x = (j) * spaceWidth + spaceWidth / 2 - swidth / 2;
					int y = (i) * spaceHeight + spaceHeight / 2 + sheight / 4;
					g.drawString(s, x, y);
				}
				if(players[i][j] != 0) {
					g.setColor(Color.BLACK);
					String p = Integer.toBinaryString(players[i][j]);
					String toPrint = "";
					for(int k = p.length() - 1; k >= 0; k--) {
						if(p.charAt(k) == '1')
							toPrint += (p.length() - k - 1) + " ";
					}
					g.setFont(new Font("Arial", Font.PLAIN, 24));
					int sheight = (int) g.getFontMetrics().getStringBounds(toPrint, g).getHeight();
					int x = (j) * spaceWidth;
					int y = (i) * spaceHeight + sheight;
					g.drawString(toPrint, x, y);
				}
				g.setFont(new Font("Arial", Font.PLAIN, 10));
				g.setColor(Color.BLACK);
				int x = j * spaceWidth;
				int y = (i + 1) * spaceHeight - 2;
				g.drawString("(" + i + ", " + j + ")", x, y);
			}
		}
	}
}
