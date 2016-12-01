
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

import javax.swing.SwingUtilities;

public class Human {
	
	private int[][] rooms;
	private int[][] players;
	private int id;
	private String name;
	private int state;
	private int range;
	private HumanGUI gui;
	
	public Human(String name) {
		this.name = name;
		state = 0;
	}
	
	public void play() {
		Scanner s = new Scanner(System.in);
		int count = 0;
		int roomCount = 0;
		String[] roomNames = new String[0];
		String[] charNames = null;
		String[] weaponNames = null;
		Deck knownChars = new Deck(charNames);
		Deck knownWeaps = new Deck(weaponNames);
		Deck knownRooms = new Deck(roomNames);
		while(s.hasNextLine()) {
			if(gui != null)
				gui.getBoardPanel().repaint();
			String in = s.nextLine();
			String[] data = in.split(" ");
			if(data[0].equals("ID")) {
				id = Integer.parseInt(data[1]);
			} else if(data[0].equals("Board")) {
				int h = Integer.parseInt(data[1]);
				int w = Integer.parseInt(data[2]);
				rooms = new int[h][w];
				int x = 0;
				int y = 0;
				for(int i = 0; i < h; i++) {
					for(int j = 0; j < w; j++) {
						rooms[i][j] = s.nextInt();
						if(rooms[i][j] == 1) {
							x = j;
							y = i;
						}
						if(rooms[i][j] != 0)
							roomCount++;
					}
				}
				roomNames = new String[roomCount];
				knownRooms.setNames(roomNames);
				players = new int[h][w];
				players[y][x] = (int) (Math.pow(2, count) - 1);
			} else if(data[0].equals("Name")) {
				System.out.println(name);
			} else if(data[0].equals("Playercount")) {
				count = Integer.parseInt(data[1]);
			} else if(data[0].equals("Roomname")) {
				int idx = Integer.parseInt(data[1]);
				String name = "";
				for(int i = 2; i < data.length; i++)
					name += data[i] + (i == data.length - 1 ? "": " ");
				roomNames[idx] = name;
			} else if(data[0].equals("Personname")) {
				int len = Integer.parseInt(data[1]);
				if(charNames == null) {
					charNames = new String[len];
					knownChars.setNames(charNames);
				}
				int idx = Integer.parseInt(data[2]);
				String name = "";
				for(int i = 3; i < data.length; i++)
					name += data[i] + (i == data.length - 1 ? "": " ");
				charNames[idx] = name;
			} else if(data[0].equals("Weaponname")) {
				int len = Integer.parseInt(data[1]);
				if(weaponNames == null) {
					weaponNames = new String[len];
					knownWeaps.setNames(weaponNames);
				}
				int idx = Integer.parseInt(data[2]);
				String name = "";
				for(int i = 3; i < data.length; i++)
					name += data[i] + (i == data.length - 1 ? "": " ");
				weaponNames[idx] = name;
			} else if(data[0].equals("Card")) {
				System.err.println(in);
				if(!data[1].equals("From")) {
					int type = Integer.parseInt(data[1]);
					int val = Integer.parseInt(data[2]);
					if(type == 0)
						knownChars.addKnown(val, true);
					else if(type == 1)
						knownWeaps.addKnown(val, true);
					else
						knownRooms.addKnown(val, true);
				} else {
					int type = Integer.parseInt(data[1]);
					int val = Integer.parseInt(data[2]);
					if(type == 0)
						knownChars.addKnown(val, false);
					else if(type == 1)
						knownWeaps.addKnown(val, false);
					else
						knownRooms.addKnown(val, false);
				}
			} else if(data[0].equals("Move")) {
				long time = Long.parseLong(data[1]);
				range = Integer.parseInt(data[2]);
				if(gui != null)
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							gui.getMovePanel().setRange(range);
						}
					});
			} else if(data[0].equals("Opponent")) {
				int id = Integer.parseInt(data[1]);
				int y = Integer.parseInt(data[2]);
				int x = Integer.parseInt(data[3]);
				int[] cur = getCurrentPos(id, players);
				players[cur[0]][cur[1]] -= Math.pow(2, id);
				players[y][x] += Math.pow(2, id);
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						gui.getBoardPanel().update(players);
					}
				});
			} else if(data[0].equals("Disprove")) {
				gui.getMovePanel().setDisproveview(data[1] + " " + data[2] + " " + data[3]);
			}
			
			if(state == 0 && rooms != null && players != null && roomNames[roomNames.length - 1] != null) {
				state++;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						gui = new HumanGUI(rooms, players, knownChars, knownWeaps, knownRooms, id);
						gui.getMovePanel().setRange(range);
					}
				});
			} else if(state == 1) {
				state++;
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						gui.getCardPanel().initialize();
					}
				});
			}
			if(data[0].equals("Card")) {
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						gui.getCardPanel().updateData(knownChars, knownWeaps, knownRooms);
					}
				});
			}
		}
		s.close();
	}
	
	public static void main(String[] args) {
		try {
			System.setErr(new PrintStream("error.txt"));
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
		Human h = new Human(args[0]);
		h.play();
	}
	
	public static int[] getCurrentPos(int id, int[][] pLocs) {
		int curX = 0;
		int curY = 0;
		for(int i = 0; i < pLocs.length; i++) {
			for(int j = 0; j < pLocs.length; j++) {
				String b = Integer.toBinaryString(pLocs[i][j]);
				if(b.length() > id && b.charAt(b.length() - id - 1) == '1') {
					curX = j;
					curY = i;
					break;
				}
			}
		}
		return new int[] { curY, curX };
	}
	
}
