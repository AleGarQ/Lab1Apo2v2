package model;

public class User {
	private String docType;
	private String id;
	private String name;
	private String surname;
	private String phone;
	private String adress;
	private Turn turn;
	private boolean served;
	
	public User(String docType, String id, String name, String surname, String phone, String adress, boolean served) {
		this.docType = docType;
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.adress = adress;
		this.served = served;
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
	public String getTurn() {
		String auxTurn = "";
		try {
			auxTurn = turn.getCode();
		}catch(NullPointerException e) {
			auxTurn = null;
		}finally {
			return auxTurn;
		}
	}

	public void setTurn(Turn turn) {
		this.turn = turn;
	}

	public boolean wasServed() {
		return served;
	}

	public void setServed(boolean served) {
		this.served = served;
	}

	@SuppressWarnings("finally")
	@Override
	public String toString() {
		String info = null;
		try {
			info = "" + docType + "||	" + name + " " + surname + "||	" + phone + "||	" + turn.getCode();
		} catch (NullPointerException e) {
			info = "" + docType + "||	" + name + " " + surname + "||	" + phone + "||	" + null;
		}finally {
			return info;
		}	
	}
	
	
}
