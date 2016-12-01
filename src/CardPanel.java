
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CardPanel extends JPanel {
	
	private static final long serialVersionUID = -490153446226422343L;
	
	private Deck chars;
	private Deck weaps;
	private Deck rooms;
	
	private JTable charData;
	private JTable weapData;
	private JTable roomData;
	
	private String[][] charList;
	private String[][] weapList;
	private String[][] roomList;
	
	public CardPanel(Deck chars, Deck weaps, Deck rooms) {
		this.chars = chars;
		this.weaps = weaps;
		this.rooms = rooms;
		this.setPreferredSize(new Dimension(512, 768));
		this.setLayout(new GridLayout(3, 1));
	}
	
	public void initialize() {
		String[] cNames = chars.getNames();
		charList = new String[cNames.length][3];
		for(int i = 0; i < charList.length; i++) {
			charList[i][0] = i + "";
			charList[i][1] = cNames[i];
		}
		charData = new JTable(charList, new String[] { "ID", "Character", "Known" });
		charData.setFillsViewportHeight(true);
		charData.setPreferredScrollableViewportSize(new Dimension(this.getWidth(), this.getHeight() / 3));
		
		String[] wNames = weaps.getNames();
		weapList = new String[wNames.length][3];
		for(int i = 0; i < weapList.length; i++) {
			weapList[i][0] = i + "";
			weapList[i][1] = wNames[i];
		}
		weapData = new JTable(weapList, new String[] { "ID", "Weapon", "Known" });
		weapData.setFillsViewportHeight(true);
		weapData.setPreferredScrollableViewportSize(new Dimension(this.getWidth(), this.getHeight() / 3));
		
		String[] rNames = rooms.getNames();
		roomList = new String[rNames.length][3];
		for(int i = 0; i < roomList.length; i++) {
			roomList[i][0] = i + "";
			roomList[i][1] = rNames[i];
		}
		roomData = new JTable(roomList, new String[] { "ID", "Room", "Known" });
		roomData.setFillsViewportHeight(true);
		roomData.setPreferredScrollableViewportSize(new Dimension(this.getWidth(), this.getHeight() / 3));// TODO fix jtables
		
		this.add(new JScrollPane(roomData), 2, 0);
		this.add(new JScrollPane(weapData), 1, 0);
		this.add(new JScrollPane(charData), 0, 0);
		
	}
	
	public void updateData(Deck p, Deck w, Deck r) {
		chars = p;
		weaps = w;
		rooms = r;
		ArrayList<Integer> known = chars.getKnown();
		for(int i = 0; i < known.size(); i++) {
			int val = known.get(i);
			if(chars.getGuessable().contains(val)) {
				charData.getModel().setValueAt("X", known.get(i), 2);
			} else {
				charData.getModel().setValueAt("x", known.get(i), 2);
			}
		}
		known = weaps.getKnown();
		for(int i = 0; i < known.size(); i++) {
			int val = known.get(i);
			if(weaps.getGuessable().contains(val)) {
				weapData.getModel().setValueAt("X", known.get(i), 2);
			} else {
				weapData.getModel().setValueAt("x", known.get(i), 2);
			}
		}
		known = rooms.getKnown();
		for(int i = 0; i < known.size(); i++) {
			int val = known.get(i);
			if(rooms.getGuessable().contains(val)) {
				roomData.getModel().setValueAt("X", known.get(i), 2);
			} else {
				roomData.getModel().setValueAt("x", known.get(i), 2);
			}
		}
		
		charData.repaint();
		weapData.repaint();
		roomData.repaint();
	}
}
