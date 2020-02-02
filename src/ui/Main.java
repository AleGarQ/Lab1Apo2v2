package ui;

import model.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import exceptions.AlreadyOnListException;
import exceptions.EmptyFieldException;
import exceptions.InvalidSelectionException;
import exceptions.IsNotListedException;
import exceptions.UserWithoutTurn;

public class Main {
	private Controller system;
	private Scanner scan;
	
	public Main() {
		system = new Controller();
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
					
					system.addUser(docType, id, name, surname, phone, adress);
					System.out.println("User added successfully.\n");
					break;

				case 2:
					System.out.println("Please enter user's document number:");
					id = scan.nextLine();
					try {
						System.out.println("DT"+"	"+"Full  Name"+"		"+"Phone "+"		"+"Turn");
						System.out.println(system.getUserTurn(id));
					}catch(UserWithoutTurn e) {
						System.out.println(system.searchUser(id).toString());
						System.out.println("At this moment the user does not have turn.");
						System.out.println("The turn given is: "+ system.giveTurn(id));
					}
					break;
					
				case 3:
					
					break;
					
				case 4:
					
					break;
				default:
					throw new InvalidSelectionException(selection);
				}
				
			}catch(InputMismatchException e) {
				System.out.println("Por favor digite una opcion valida");
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
				
			}
		}while(!exit);
	}
	
	public int Menu() {
		System.out.println("||||||||||||||||||||||||||||||||||||||");
		System.out.println("**************************************");
		System.out.println("Choose an option(number):");
		System.out.println("1. Add user.");
		System.out.println("2. Give to the user a turn.");
		System.out.println("3. Attend turn.");
		System.out.println("4. Exit program.");
		System.out.println("**************************************");
		System.out.println("||||||||||||||||||||||||||||||||||||||");
		
		int selection = scan.nextInt();
		scan.nextLine();
		return selection;
	}
	
	public static void main(String[] args) {
		Main m = new Main();
	}
}
