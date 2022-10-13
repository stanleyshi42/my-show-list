package com.cognixia.jump.myshowlist;

import java.util.Scanner;

public class ConsoleMenu {
	public ConsoleMenu() {
		welcomeMenu();
	}
	
	public void welcomeMenu() {
		System.out.println("Welcome to myShowList!");
		System.out.println("[1] Login");
		System.out.println("[2] Register");
		System.out.println("[3] Exit Program");
		
		Scanner sc = new Scanner(System.in);
		try {
			int userInput = sc.nextInt();
			switch(userInput) {
				case 1:
					loginMenu(sc);
					break;
				case 2:
					break;
				case 3:
					System.exit(0);
					break;
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private void loginMenu(Scanner sc) {
		sc.nextLine();	
		
		System.out.println("Enter your username:");
		String username=sc.nextLine();
		System.out.println(username);
		System.out.println("Enter your password:");
		String password=sc.nextLine();
		System.out.println(password);
	}

}
