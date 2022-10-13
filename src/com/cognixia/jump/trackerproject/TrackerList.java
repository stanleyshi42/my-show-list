package com.cognixia.jump.trackerproject;

import java.util.Scanner;

public class TrackerList {

	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

			int x = 0;
			System.out.println("Input index(0-4): " + "\n1 = add new show " + "\n2 = update a show " + 
								"\n3 = delete a show " + "\n4 = logout of the account ");
			Scanner input = new Scanner(System.in);
			boolean invalidInput = false;
			trySelection(x, invalidInput, input);
			
	}
			public static void trySelection(int x, boolean invalidInput, Scanner input)  {
				
//				String a = return;
//				System.out.println(a);
				x = input.nextInt();
				invalidInput = false;
		
		
	try {
				
//		while(invalidInput = false) {
			
			switch(x) {
			case 1: 
				System.out.println("You have selected option 1");
				invalidInput = true;
			break;
			case 2:
				System.out.println("You have selected option 2");
				invalidInput = true;
			break;			
			case 3: 
				System.out.println("You have selected option 3");
				invalidInput = true;
			break;
			case 4: 
				System.out.println("You have selected option 4");
				invalidInput = true;
			break;
			
			default: 
				System.out.println("Not a valid selection made: ");
				invalidInput = false;
				x = input.nextInt();
			break;
		
				}
//			}
	
		}
			catch (Exception e) {
				System.out.println("wrong input! ");
				
				
			}
				
			
		}	
		

		}
	


