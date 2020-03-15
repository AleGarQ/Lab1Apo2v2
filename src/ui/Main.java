package ui;

import model.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.EmptyTurnsException;
import exceptions.InvalidDateException;
import exceptions.InvalidSelectionException;
import exceptions.IsNotListedException;
import exceptions.NoTurnsForAttendException;
import exceptions.TurnIsNotAttendedExcpetion;
import exceptions.UnexistingTypeException;

public class Main {
	private Controller system;
	private Scanner scan;
	
	public Main() {
		system = new Controller("A00", "A0/");
		scan = new Scanner(System.in);
		showMenu();
//		for (int i = 0; i < 2600; i++) {
//			System.out.println(system.giveTurn());
//		}
		
	}
	
	public void showMenu() {
		boolean exit = false;
		
		do {
			try {
				int selection = Menu();
				
				switch (selection) {
				case 1:
					addUser();
					break;

				case 2:
					giveTurn();
					break;
					
				case 3:
					attendTurn();
					break;
					
				case 4:
					createTurnType();
					break;	
				
				case 5:
					updateSystemDate();
					break;
					
				case 6:
					System.out.println(system.getSystemDate());
					break;
					
				case 7:
					generateReportUserTurns();
					break;
					
				case 8:
					generateReportTurnUsers();
					break;
					
				case 9:
					System.out.println("TODO");
					break;
					
				case 10:
					System.out.println("TODO");
					break;
					
				case 11:
					System.out.println("TODO");
					break;
					
				case 12:
					sortAndShow();
					break;
				
				case 13:
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					System.out.println("***************************************\n");
					System.out.println("   Thanks for use our app. Good bye!\n");
					System.out.println("***************************************");
					System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
					system.saveData();
					exit = true;
					break;
				default:
					throw new InvalidSelectionException(selection);
				}
				
			}catch(InputMismatchException e) {
				System.out.println("Please enter a valid option.");
				scan.nextLine();
			} catch (AlreadyOnListException e) {
				System.out.println("It was not possible to add the user.");
				System.out.println(e.getMessage());
			} catch (EmptyFieldException e) {
				System.out.println("It was not possible to add the user.");
				System.out.println(e.getMessage());
			} catch (InvalidSelectionException e) {
				System.out.println(e.getMessage());
				System.out.println("Please choose a valid option.\n");
			} catch (IsNotListedException e) {
				System.out.println(e.getMessage());
				System.out.println("Please add him.\n");
			} catch (NoTurnsForAttendException e) {
				System.out.println(e.getMessage());
				System.out.println("Wait for users to arrive");
			} catch (TurnIsNotAttendedExcpetion e) {
				System.out.println(e.getMessage());
				System.out.println("Please attend it and then you can give another turn to him");
			} catch (ParseException e) {
				System.out.println("You was enter a invalid format of date");
				System.out.println("Please retry.");
			} catch (UnexistingTypeException e) {
				System.out.println(e.getMessage());
				System.out.println("Please verify it name or create new");
			} catch (InvalidDateException e) {
				System.out.println(e.getMessage());
			} catch (EmptyTurnsException e) {
				System.out.println(e.getMessage());
				System.out.println("Please create turns for attend.");
			} catch (FileNotFoundException e) {
				System.out.println("Was not possible to load file");
			} catch (IOException e) {
				System.out.println("Was not posible to save your data.");
			} catch (NumberFormatException e) {
				System.out.println("Invalid selection");
				System.out.println("Please choose a valid option.");
			}
		}while(!exit);
	}

	public int Menu() throws ParseException {
		System.out.println("||||||||||||||||||||||||||||||||||||||");
		System.out.println("**************************************");
		System.out.println(system.getSystemDate());
		System.out.println("Choose an option(number):");
		System.out.println("1. Add user.");
		System.out.println("2. Give to the user a turn (Create Turn).");
		System.out.println("3. Attend turn.");
		System.out.println("4. Create new turn type.");
		System.out.println("5. Update system actual time.");
		System.out.println("6. Show system time.");
		System.out.println("7. Generate report of user's turns.");
		System.out.println("8. Generate report of turn's users.");
		System.out.println("9. Suspend user for inactivity.");
		System.out.println("10. Generate random users.");
		System.out.println("11. Generate random turns.");
		System.out.println("12. Sort and show.");
		System.out.println("13. Exit program.");
		System.out.println("**************************************");
		System.out.println("||||||||||||||||||||||||||||||||||||||");
		
		int selection = scan.nextInt();
		scan.nextLine();
		return selection;
	}
	
	public void addUser() throws AlreadyOnListException, EmptyFieldException {
		System.out.println("Diligence the following fields.");
		System.out.println("User's document type(number):");
		System.out.println("1. Citizenship Certificate");
		System.out.println("2. Identity Card");
		System.out.println("3. Civil Registration");
		System.out.println("4. Passport");
		System.out.println("5. Foreigner Certificate");
		String docSelected = scan.nextLine();
		String docType = "";
		switch (docSelected) {
		case "1":
			docType = "CC";
			break;
		case "2":
			docType = "IC";
			break;
		case "3":
			docType = "CR";
			break;
		case "4":
			docType = "P";
			break;
		case "5":
			docType = "FC";
			break;
		default:
			System.out.println("Invalid selection. The program will show you an error");
		}
		System.out.println("User's document number:");
		String id = scan.nextLine();
		System.out.println("User's name:");
		String name = scan.nextLine();
		System.out.println("User's surname:");
		String surname = scan.nextLine();
		System.out.println("User's phone:");
		String phone = scan.nextLine();
		System.out.println("User's adress:");
		String adress = scan.nextLine();
		
		long timeS = System.currentTimeMillis();
		
		system.addUser(docType, id, name, surname, phone, adress);
		System.out.println("User added successfully.\n");
		
		long timeE = System.currentTimeMillis();
		System.out.println("The method end in "+ (timeE - timeS) +" milis");
	}
	
	public void giveTurn() throws IsNotListedException, TurnIsNotAttendedExcpetion, ParseException, UnexistingTypeException {
		System.out.println("Please enter user's document type(number):");
		System.out.println("1. Citizenship Certificate");
		System.out.println("2. Identity Card");
		System.out.println("3. Civil Registration");
		System.out.println("4. Passport");
		System.out.println("5. Foreigner Certificate");
		String docSelected = scan.nextLine();
		String doc = "";
		switch (docSelected) {
		case "1":
			doc = "CC";
			break;
		case "2":
			doc = "IC";
			break;
		case "3":
			doc = "CR";
			break;
		case "4":
			doc = "P";
			break;
		case "5":
			doc = "FC";
			break;
		default:
			System.out.println("Invalid selection. The program will show you an error");
		}
		System.out.println("Please enter user's document number:");
		String id = scan.nextLine();
		System.out.println("Please enter a existing type for the turn:");
		String tp = scan.nextLine();
		
		long timeS = System.currentTimeMillis();
		
		System.out.println("The turn given is: "+ system.giveTurn(id, doc, tp.toUpperCase()));
		System.out.println("DT"+"	"+"Full  Name"+"		"+"Phone "+"		"+"Turn");
		System.out.println(system.searchUser(id, doc).toString());
		
		long timeE = System.currentTimeMillis();
		System.out.println("The method end in "+ (timeE - timeS) +" milis");
	}
	
	public void attendTurn() throws NoTurnsForAttendException, ParseException, EmptyTurnsException {
		if(system.setIfWasServed()) {
			System.out.println(system.getActualTurn());
		}else {
			System.out.println("Turns are already attended until this moment");
		}
	}
	
	public void generateUsers() {
		System.out.println("Please enter the quantity of users that you wanna generate:");
		int q = scan.nextInt();
		scan.nextLine();
		// TODO
	}
	
	private void updateSystemDate() throws ParseException, EmptyFieldException, InvalidDateException {
		System.out.println("Please enter the date with the format \"dd/MM/yyyy HH:mm:ss\"");
		String date = scan.nextLine();
		system.updateSystemDate(date);
		System.out.println("System date updated sucessfully");
	}
	
	private void createTurnType() throws InvalidSelectionException, EmptyFieldException, AlreadyOnListException {
		System.out.println("Please enter the name of the type:");
		String name = scan.nextLine();
		System.out.println("Please enter the duration of the type (x,xx mins):");
		double duration = scan.nextDouble();
		scan.nextLine();
		system.addTurnType(name.toUpperCase(), duration);
		System.out.println("Turn type added sucessfully");
	}
	
	private void generateReportUserTurns() throws NumberFormatException, InvalidSelectionException, FileNotFoundException, IsNotListedException {
		System.out.println("Choose an option(number) to generate the report:");
		System.out.println("1. Text file.");
		System.out.println("2. Console");
		String printOption = scan.nextLine();
		
		System.out.println("Please enter user's document type(number):");
		System.out.println("1. Citizenship Certificate");
		System.out.println("2. Identity Card");
		System.out.println("3. Civil Registration");
		System.out.println("4. Passport");
		System.out.println("5. Foreigner Certificate");
		String docSelected = scan.nextLine();
		String doc = "";
		switch (docSelected) {
		case "1":
			doc = "CC";
			break;
		case "2":
			doc = "IC";
			break;
		case "3":
			doc = "CR";
			break;
		case "4":
			doc = "P";
			break;
		case "5":
			doc = "FC";
			break;
		default:
			System.out.println("Invalid selection. The program will show you an error");
		}
		
		System.out.println("Please enter user's document number:");
		String id = scan.nextLine();
		
		if(printOption.equals("1")) {
			system.textFileUserTurns(doc, id);
			System.out.println("Your file is ready. You can find it in \"Data\" folder with the name \"User's Turns.txt\"");
		}else if(printOption.equals("2")){
			System.out.println(system.consoleUserTurns(doc, id));
		}else {
			throw new InvalidSelectionException(Double.parseDouble(printOption));
		}
	}
	
	private void generateReportTurnUsers() throws NumberFormatException, InvalidSelectionException, FileNotFoundException {
		System.out.println("Choose an option(number) to generate the report:");
		System.out.println("1. Text file.");
		System.out.println("2. Console");
		String printOption = scan.nextLine();
		
		System.out.println("Please enter turn's code:");
		String code = scan.nextLine();
		
		if(printOption.equals("1")) {
			system.textFileTurnUsers(code);
			System.out.println("Your file is ready. You can find it in \"Data\" folder with the name \"Turn's Users.txt\"");
		}else if(printOption.equals("2")){
			System.out.println(system.consoleTurnUsers(code));
		}else {
			throw new InvalidSelectionException(Double.parseDouble(printOption));
		}
	}
	
	private void sortAndShow() throws NumberFormatException, InvalidSelectionException {
		System.out.println("Please select an option:");
		System.out.println("1. Sort users by id type.");
		System.out.println("2. Sort users by id.");
		System.out.println("3. Sort users by name.");
		System.out.println("4. Sort users by id inverted.");
		System.out.println("5. Sort users by surname.");
		System.out.println("6. Sort turns by type.");
		System.out.println("7. Sort turns by duration.");
		System.out.println("8. Sort turns by code.");
		String sortOption = scan.nextLine();
		
		switch (sortOption) {
		case "1":
			system.selectionSortUsersByIdType();
			System.out.println(system.showUsers());
			break;
			
		case "2":
			system.sortUsersById();
			System.out.println(system.showUsers());
			break;
			
		case "3":
			system.sortUsersByName();
			System.out.println(system.showUsers());
			break;
			
		case "4":
			system.sortUsersByIdInvert();
			System.out.println(system.showUsers());
			break;
			
		case "5":
			system.sortUserBySurname();
			System.out.println(system.showUsers());
			break;
			
		case "6":
			system.sortTurnsByType();
			System.out.println(system.showTurns());
			break;
			
		case "7":
			system.sortTurnsByDuration();
			System.out.println(system.showTurns());
			break;
			
		case "8":
			system.sortTurnsByCode();
			System.out.println(system.showTurns());
			break;

		default:
			throw new InvalidSelectionException(Double.parseDouble(sortOption));
		}
	}
	
	public static void main(String[] args) {
		Main m = new Main();
	}
}
