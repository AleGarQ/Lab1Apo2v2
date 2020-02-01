package model;

public class User {
	private String docType;
	private String id;
	private String name;
	private String surname;
	private String phone;
	private String adress;
	private String turn;
	
	public User(String docType, String id, String name, String surname, String phone, String adress, String turn) {
		this.docType = docType;
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.phone = phone;
		this.adress = adress;
		this.turn = turn;
	}

	public String getDocType() {
		return docType;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAdress() {
		return adress;
	}

	public void setAdress(String adress) {
		this.adress = adress;
	}

	public String getTurn() {
		return turn;
	}

	public void setTurn(String turn) {
		this.turn = turn;
	}
	
	
}
