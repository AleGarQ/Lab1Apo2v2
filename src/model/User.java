package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable, Comparable<User> {
	private static final long serialVersionUID = 1L;
	private String docType;
	private String id;
	private String name;
	private String surname;
	private String phone;
	private String adress;
	private ArrayList<Turn> turn;

	public User(String docType, String id, String name, String surname, String phone, String adress) {
		this.docType = docType;
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.adress = adress;
		turn = new ArrayList<Turn>();
	}

	public String getDocType() {
		return docType;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getSurname() {
		return surname;
	}

	public String getPhone() {
		return phone;
	}

	public String getAdress() {
		return adress;
	}

	@SuppressWarnings("finally")
	public Turn getTurn() {
		Turn auxTurn = null;
		try {
			auxTurn = turn.get(turn.size() - 1);
		} catch (NullPointerException e) {
			auxTurn = null;
		} finally {
			return auxTurn;
		}
	}

	public boolean binarySearchTurn(String code) {
		bubbleSortTurnsByCode();

		boolean found = false;

		for (int i = 0; i < turn.size(); i++) {
			int start = 0;
			int end = turn.size() - 1;

			while (start <= end && !found) {
				int mid = (start + end) / 2;

				if (turn.get(mid).getCode().compareTo(code) == 0) {
					found = true;
				} else if (turn.get(mid).getCode().compareTo(code) > 0) {
					end = mid - 1;
				} else {
					start = mid + 1;
				}
			}
		}

		return found;
	}

	private void bubbleSortTurnsByCode() {
		Turn aux = null;
		
		for (int i = 1; i < turn.size(); i++) {
			for (int j = 0; j < turn.size() - i; j++) {
				if (turn.get(j).getCode().compareTo(turn.get(j + 1).getCode()) > 0) {
					aux = turn.get(j);
					turn.set(j, turn.get(j + 1));
					turn.set(j + 1, aux);
				}
			}
		}
	}

	public void setTurn(Turn turn) {
		this.turn.add(turn);
	}

	public boolean wasServed() {
		return turn.get(turn.size() - 1).isServed();
	}

	public void setServed(boolean served) {
		turn.get(turn.size() - 1).setServed(served);
	}

	public void setWasAway(boolean away) {
		turn.get(turn.size() - 1).setUserAway(away);
	}

	@SuppressWarnings("finally")
	@Override
	public String toString() {
		String info = null;
		try {
			info = "" + docType + "||	" + name + " " + surname + "||	" + phone + "||	"
					+ turn.get(turn.size() - 1).getCode();
		} catch (NullPointerException e) {
			info = "" + docType + "||	" + name + " " + surname + "||	" + phone + "||	" + null;
		} finally {
			return info;
		}
	}
	
	public String info() {
		return "["+docType + ", " + name + ", " + surname + ", " + phone + ", "+ adress + "]";
	}

	public String infoToPrintTurns() {
		String info = "";

		for (int i = 0; i < turn.size(); i++) {
			Turn current = turn.get(i);
			info += "" + current.getCode() + "||" + current.isUserAway() + "    ||" + current.isServed() + "\n";
		}

		return info;
	}

	@Override
	public int compareTo(User o) {
		int result;
		
		if(o.getName().equalsIgnoreCase(name)) {
			result = 0;
		}else {
			result = name.compareTo(o.getName());
		}
		
		return result;
	}
}
