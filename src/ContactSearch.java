//Sean Mann for Soliant Consulting OOP exercise
//7/20/16

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class ContactSearch {
	static List<List<Object>> contacts = new ArrayList<List<Object>>();
	static List<Object> individual = new ArrayList<Object>();
	
	public static void main(String[] args){
		String file = "soliant.txt";
		BufferedReader br = null;
		String line = "";
		String delim = ",";
		System.out.println("Loading contacts...");//This block reads in the CSV file
		try{
			br = new BufferedReader(new FileReader(file));
			while ((line=br.readLine())!=null){
				Object[] fields = line.split(delim);
				individual = Arrays.asList(fields);
				contacts.add(individual);
			}
		}
		catch (FileNotFoundException e){
			System.err.println("Error: File \"" + file + "\" not found. Exiting...");
			System.exit(0);
		}
		catch (IOException e){
			System.err.println("Error: File \"" + file + "\" cannot be read. Exiting...");
			System.exit(0);
		}
		catch (Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		//Test
		/*byLastName("g");
		byLastName("S");
		byEmail("email");
		byEmail("eMail");
		byEmail("LisaESauceda@armyspy.com");*/
		boolean open = true;
		Scanner scan = new Scanner(System.in);
		while (open==true){
			System.out.println("Do you want to search for a contact using:"
					+ "\ntheir last name (1) or an email (2)? \n(Enter 3 to quit)"
					+ "\n\nEnter your selection here: ");//Assumption: Client is fine with simple menu system. Advise that GUI will be better for future.
			while (!scan.hasNextInt()){
				scan.next();
				System.out.println("Enter 1,2, or 3, please: ");
			}
	        int i = scan.nextInt();
			System.out.println("You typed this: " + i + "\n");
			switch(i){
				case 1:	//bylastname
					boolean badletter=true;
					while (badletter==true){
						System.out.println("Enter the first letter of "
								+ "the last name you are looking for. Or enter \"back\" to return"
								+ "\nEnter here: ");
						String s = scan.next();
						if (s.equals("back")){
							badletter=false;
							System.out.println();
						}
						else if (s.length()>1 || Character.isDigit(s.charAt(0)) || s.isEmpty()==true){//Assuming that user can only type in one letter to find contact
							System.err.println("Invalid character. Try again\n");
						}
						else{
							System.out.println();
							byLastName(s);
						}
						
					}
					break;
				case 2://email
					boolean bademail=true;
					while (bademail==true){
						System.out.println("Enter the email "
								+ "you are looking for (or enter \"back\" to return)."
								+ "\nEnter here: ");
						String s = scan.next();
						if (s.equals("back")){
							bademail=false;
						}
						else {
							System.out.println();
							byEmail(s);
						}
					}
					break;
				case 3:
					open=false;
					System.out.println("Exiting...");
					break;
			}
		}
		scan.close();
		System.exit(0);
	}
	
	static void byLastName(String letter){
		List<String> tempsort = new ArrayList<String>();
		
		for (int i=0; i<contacts.size(); i++){
			letter = letter.toUpperCase();
			String name = contacts.get(i).get(1).toString();
			if (name.startsWith(letter)){
				tempsort.add(name);
			}
		}
		if (tempsort.size()==0){
			System.err.println("No contacts found\n");
		}
		else {
			Collections.sort(tempsort);//Sort alphabetically
			int count = 0;
			while (count < tempsort.size()){
				for (int i =0; i<contacts.size(); i++){
					String name = contacts.get(i).get(1).toString();
					String alphname = tempsort.get(count).toString();
					if (name.equals(alphname)) {
						print(contacts.get(i));
						count++;
						break;
					}
				}
			}
		}
	}
	
	static void byEmail(String email){
		int count=0;
		for (int i=0; i<contacts.size(); i++){
			String listemail = contacts.get(i).get(2).toString();
			if (listemail.equalsIgnoreCase(email)){//assuming that user must type exact email address to find contact
				count++;
				print(contacts.get(i));
			}
		}
		if (count==0){
			System.err.println("No contacts found");
			System.out.println();
		}
	}
	
	static void print(List<Object> contactlist){
		if (contactlist.size()!=4){
			System.err.println("Wrong # of values in contact list");//Assumption: CSV will have all fields filled out.
		}
		else {
			System.out.println("Last: " + contactlist.get(1) +
				", First: " + contactlist.get(0) + ", Phone: " +
				contactlist.get(3) + ", E-Mail: " + contactlist.get(2) + "\n");
		}
	}
}
