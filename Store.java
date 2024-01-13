import java.util.Scanner;

public class Store {
	private static Scanner scanner;
	
	public static void main(String[] args) {
		scanner = new Scanner(System.in);
		System.out.println("Welcome to Pargol's Computer Store!");
		
		//set inventory size
		int maxComputers = 0;
		while(maxComputers < 1) { 
			System.out.print("Please enter the maximum number of computers the store can create: ");
			maxComputers = scanner.nextInt();
			if(maxComputers < 1)
				System.out.println("\nInvalid input! Please enter a value greater than 0.");
		}
		
		Computer[] inventory = new Computer[maxComputers];

		//loop until user exits
		do {
			System.out.println("\nWhat do you want to do?\n"
							 + "\t1.	Add new computers (password required)\n"
							 + "\t2.	Change information of a computer (password required)\n"
							 + "\t3.	Display all computers by a specific brand\n"
							 + "\t4.	Display all computers under a certain a price.\n"
							 + "\t5.	Quit");
			System.out.print("Please enter your choice: ");
			int menuChoice = scanner.nextInt();
			System.out.println();
			
			switch(menuChoice) {
			case 1: //enter computers
				if(verifyPassword()) {
					int numComputersToAdd;
					int slotsAvailable = slotsAvailable(inventory); //number of empty slots in inventory
					do {
						System.out.print("\nHow many computers do you want to add? ");
						numComputersToAdd = scanner.nextInt();
						
						if(numComputersToAdd > slotsAvailable) //not enough empty slots
							System.out.printf("\nError: There are only %d empty slots available. Cannot add %d computer(s)!\n", slotsAvailable, numComputersToAdd);
						else if(numComputersToAdd == 0)
							System.out.println("0 computers were added!");
						else if(numComputersToAdd > 0 && numComputersToAdd <= inventory.length){ //add computers in empty slots
							for(int i = 0, addedCounter = 0; i < numComputersToAdd; i++) {
								if(inventory[i] == null) {
									Computer c = new Computer();
									System.out.printf("Computer %d - Enter the brand: ", i + 1);
									c.setBrand(scanner.next());
									System.out.printf("Computer %d - Enter the model: ", i + 1);
									c.setModel(scanner.next());
									System.out.printf("Computer %d - Enter the price: ", i + 1);
									c.setPrice(scanner.nextDouble());
									inventory[i] = c;
									System.out.println();
								}
							}
							System.out.printf("Succesfully added %d computer(s).\n", numComputersToAdd);
						}
					} while(numComputersToAdd < 0 || numComputersToAdd > inventory.length);
				}
				break;
			case 2: //modify computer
				if(verifyPassword()) {
					int editIndex;
					do {
						System.out.printf("\nWhich computer would you like to edit? [0 - %d] or enter -1 to exit: ", inventory.length - 1);
						editIndex = scanner.nextInt();
						
						if(editIndex == -1) //quit
							break;
						else if(editIndex < 0 || editIndex >= inventory.length) //invalid entry
							System.out.println("Invalid entry! Please try again..");
						else if(inventory[editIndex] == null) //no computer in current index
							System.out.printf("No computer found for the index %d. Please try again..", editIndex);
						else { //modify computer
							boolean firstRun = true; //flag for initial message
							do {
								Computer c = inventory[editIndex];
								Computer.displayComputer(c, editIndex);
								if(firstRun)
									System.out.println("Which information would you like to change?");
								else
									System.out.println("Which information would you like to change now?");
								System.out.println("\t1. Brand\n"
										 		 + "\t2. Model\n"
										 		 + "\t3. SN\n"
										 		 + "\t4. Price\n"
										 		 + "\t5. Quit\n"
										 + "Enter your choice > ");
								menuChoice = scanner.nextInt();
								if(menuChoice == 5) //quit
									break;
								else if(menuChoice < 1 || menuChoice > 5) //invalid entry
									System.out.println("Invalid entry! Please select one of the 5 options!");
								else { //valid entry
									switch(menuChoice) {
									case 1: //edit brand
										System.out.print("Enter the new brand name: ");
										c.setBrand(scanner.next());
										break;
									case 2: //edit model
										System.out.print("Enter the new model name: ");
										c.setModel(scanner.next());
										break;
									case 3: //edit SN
										System.out.print("Enter the new Serial Number: ");
										c.setSerialNumber(scanner.nextLong());
										break;
									case 4: //edit price
										System.out.print("Enter the new Price: ");
										c.setPrice(scanner.nextDouble());
										break;
									}
									System.out.println("Edit successfully!");
									Computer.displayComputer(c, editIndex);
								}
							} while(true);
							break;
						}
					} while(true);
				}
				break;
			case 3: //display computers that match brandQuery
				System.out.print("What brand name are you searching for? ");
				String brandQuery = scanner.next();
				boolean brandFound = false;
				for(int i = 0; i < inventory.length; i++) {
					if(inventory[i] != null && brandQuery.toLowerCase().equals(inventory[i].getBrand().toLowerCase())) {
						Computer.displayComputer(inventory[i], i);
						System.out.println();
						brandFound = true;
					}
				}
				if(!brandFound)
					System.out.println("No entries matched your query!");
				break;
			case 4: //display computers under priceQuery
				System.out.print("Enter the price limit for your search: ");
				double priceQuery = scanner.nextDouble();
				boolean found = false;
				for(int i = 0; i < inventory.length; i++) {
					if(inventory[i] != null && inventory[i].getPrice() < priceQuery) {
						Computer.displayComputer(inventory[i], i);
						System.out.println();
						found = true;
					}
				}
				if(!found)
					System.out.println("No entries matched your query!");
				break;
			case 5: //exit
				System.out.println("Thank you for using pargol's Computer Inventory Manager. Goodbye!");
				scanner.close();
				System.exit(0);
				break;
			default: //invalid choice
				System.out.println("Invalid choice! Please try again..");
				break;
			}
		} while(true);
	}
	
	private static boolean verifyPassword() {
		final String password = "password"; //input must match password
		int triesLeft = 3;
		while(triesLeft > 0) {
			triesLeft--;
			System.out.print("Please enter your password: ");
			String input = scanner.next();
			if(input.equals(password))
				return true;
			else
				System.out.printf("Invalid password! You have %d tries left! ", triesLeft);
		}
		System.out.println();
		return false; //invalid password entered 3 times
	}
	
	//To see if any there's an empty slot for a new computer
	private static int slotsAvailable(Computer[] inventory) {
		int slotsAvailable = 0;
		for(Computer c : inventory) {
			if(c == null)
				slotsAvailable++;
		}
		return slotsAvailable;
	}
}