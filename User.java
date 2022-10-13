package menu_start;

import java.util.Scanner;

java.util.Scanner;

public class User {
	public String user_name;
	public int user_id =1;
	private String password;
	public static int count = 1;
	public static String input;
	
	public void users(String user_id, String password_id) {
		
		this.user_id = count++;
		this.user_name = user_id;
		this.password = password_id;
		count++;
		
		System.out.printf("User has been created \n",  user_id);
		System.out.printf("Enter 'login' to log in or 'register' to open another account");
		
	}
	
	public static void login(String user_id, String password_id) {
		for (int i = 1; i <= count; i++) {
			System.out.printf("Enter to 'login' to log in or 'register' to open an new account");
			
		}
	}
	
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("login");
		System.out.println("register");
		input = scanner.nextLine();
		

do 
{
	System.out.println("Enter \"Login\", \"Register\", or \"exit\"");
	input = scanner.nextLine();
	if (input.equals("login"))
	{
		// Login details
	}
	else if (input.equals("register"))
	{
		// Register details
	}
	else if (input.equals("exit"))
	{
		break; // end loop
	}
	else
	{
		// Invalid input, try again
	}
} while (true);
	

	}
}
