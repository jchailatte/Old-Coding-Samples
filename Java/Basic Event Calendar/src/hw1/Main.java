//Jonathan Chai
//9272337598

package hw1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

public class Main {

	public static void main(String[] args) throws FileNotFoundException, IOException {
		
		File f;
		String filename = "";
		Scanner sc = new Scanner(System.in);
		BufferedReader br = null;
		RootObject calen = null;
		boolean change = false;
		
		boolean flag = false;
		while(flag == false)
		{
			System.out.println("Enter a JSON file name: ");
			filename = sc.nextLine();
			f = new File(filename);
			if(f.exists())
			{
				try {
					br = new BufferedReader(new FileReader(filename));
					
					//https://stackoverflow.com/questions/2517638/can-i-peek-on-a-bufferedreader
					if(!br.ready())
					{
						System.out.println("File is empty");	
					}
					else
					{
						GsonBuilder builder = new GsonBuilder();
						Gson gson = builder.setPrettyPrinting().create();
						calen = gson.fromJson(br, RootObject.class);
						flag = true;
					}
				} catch (JsonSyntaxException e) {
					System.out.println("That file is not  a well-formed JSON file.");	
				}
			}
			else
			{
				System.out.println("That file could not be found.");
			}
		}	
		
		String choice = "";
		int choice1 = 0;

		boolean breakflag = false;
		boolean check1 = false;
		
		while(true)
		{
			if(breakflag == true)
			{
				break;
			}
			
			System.out.println("1) Display User's Calendar");
			System.out.println("2) Add User");
			System.out.println("3) Remove User");
			System.out.println("4) Add Event");
			System.out.println("5) Delete Event");
			System.out.println("6) Sort Users");
			System.out.println("7) Write File");
			System.out.println("8) Exit");
			System.out.println("");
			System.out.println("What would you like to do?");
			
			choice = sc.nextLine();			
			check1 = Validator.validateChoice(choice,1,8);
			if(check1 == false)
			{
			   System.out.println("Please enter a valid number");
			}

			choice1 = Integer.parseInt(choice);
			switch(choice1){
			case 1:		
				
				if(calen.getUsers().size() == 0)
				{
					System.out.println("No Users");
					break;
				}
				
				for(int i=0; i < calen.getUsers().size(); i++)
				{
					if(calen.getUsers().get(i).getEvents().size() != 0)
					{
						Collections.sort(calen.getUsers().get(i).getEvents(), new NumberComparator());
					}
				}
				calen.print();
				System.out.println("");
				break;
				
			case 2:
				String inputname = "";	
				String firstname = "";
				String lastname = "";
				
				boolean nameflag = false;
				while(nameflag == false)
				{
					System.out.println("What is the user's name?");
					inputname = sc.nextLine();
					
					if(inputname.contains(" "))
					{
						firstname=inputname.substring(0,inputname.indexOf(" "));
						lastname = inputname.replace(firstname+" ","");
				
						nameflag = true;	
						for(int i=0; i<calen.getUsers().size(); i++)
						{
							if(calen.getUsers().get(i).getName().getLname().toLowerCase().equals(lastname.toLowerCase()))
							{
								if(calen.getUsers().get(i).getName().getFname().toLowerCase().equals(firstname.toLowerCase()))
								{
									System.out.println("User already exists");
									nameflag = false;
								}
							}
						}
					}
					else
					{
						System.out.println("Invalid, must have first and last name");
					}
				}
				
				User newuser = new User();			
				newuser.getName().setFname(firstname);
				newuser.getName().setLname(lastname);
				calen.getUsers().add(newuser);
				change = true;
				break;
			case 3:
				
				if(calen.getUsers().size() == 0)
				{
					System.out.println("There are no users to remove.");
					break;
				}
				
				boolean rmflag = false;
				String choice2 = "";
				while(rmflag == false)
				{
					System.out.println("Which User would you like to remove?");
					calen.printname();
					choice2 = sc.nextLine();
					rmflag = Validator.validateChoice(choice2, 1, calen.getUsers().size());
					if(rmflag == false)
					{
						System.out.println("Invalid Choice");
					}
				}
				
				calen.getUsers().remove(Integer.parseInt(choice2)-1);
				change = true;
				break;
				
			case 4:
				
				if(calen.getUsers().size() != 0)
				{
					calen.printname();
				}
				else
				{
					System.out.println("There are no users to add events to.");
					break;
				}
				String choice3 ="";
				boolean adevflag = false;
				
				while(adevflag == false)
				{
					System.out.println("To which user would you like to add an event?");
					choice3 = sc.nextLine();
					adevflag = Validator.validateChoice(choice3, 1, calen.getUsers().size());
					if(adevflag == false)
					{
						System.out.println("Invalid Choice");
					}
				}
				
				System.out.println("What is the title of the event?");
				String newtitle = sc.nextLine();
				System.out.println("What time is the event?");
				String newtime = sc.nextLine();
				
				String newmon = "";
				boolean evflag = false;
				while(evflag == false)
				{
					System.out.println("What month?");
					newmon = sc.nextLine();
					evflag = Validator.validateChoice(newmon, 1, 12);
					if(evflag == false)
					{
						System.out.println("Invalid month");
					}
				}
				
				String newday = "";
				while(evflag == true)
				{
					System.out.println("What day?");
					newday = sc.nextLine();
					evflag = !(Validator.validateChoice(newday, 1, 31));
					if(evflag == true)
					{
						System.out.println("Invalid day");
					}
				}
				
				String newyr = "";
				while(evflag == false)
				{
					System.out.println("What year?");
					newyr = sc.nextLine();
					evflag = Validator.verifypos(newyr);
					if(evflag == false)
					{
						System.out.println("Invalid year");
					}
				}
				
				Date newdate = new Date();
				
				HashMap<Integer, String> month = new HashMap<Integer,String>(); 
				month.put(1, "January");
				month.put(2, "February");
				month.put(3, "March");
				month.put(4, "April");
				month.put(5, "May");
				month.put(6, "June");
				month.put(7, "July");
				month.put(8, "August");
				month.put(9, "September");
				month.put(10, "October");
				month.put(11, "November");
				month.put(12, "December");
				
				newdate.setDay(Integer.parseInt(newday));
				newdate.setYear(Integer.parseInt(newyr));
				newdate.setMonth(month.get(Integer.parseInt(newmon)));
				
				Event newevent = new Event();
				newevent.setTitle(newtitle);
				newevent.setTime(newtime);
				newevent.setDate(newdate);
				
				calen.getUsers().get(Integer.parseInt(choice3)-1).getEvents().add(newevent);
				change = true;
				break;
			
			case 5:
				
				for(int i=0; i < calen.getUsers().size(); i++)
				{
					if(calen.getUsers().get(i).getEvents().size() != 0)
					{
						Collections.sort(calen.getUsers().get(i).getEvents(), new NumberComparator());
					}
				}
				
				if(calen.getUsers().size() != 0)
				{
					calen.printname();
				}
				else
				{
					System.out.println("There are no users.");
					break;
				}
				
				boolean rmeventflag = false;
				String choice4 = "";
				while(rmeventflag == false)
				{
					System.out.println("From which user would you like to delete an event?");
					choice4 = sc.nextLine();
					rmeventflag = Validator.validateChoice(choice4, 1, calen.getUsers().size());
					if(rmeventflag == false)
					{
						System.out.println("Invalid Choice");
					}
				}
				
				int fourchoice = Integer.parseInt(choice4)-1;
				int usereventsize = calen.getUsers().get(fourchoice).getEvents().size();
				
				if(usereventsize == 0)
				{
					System.out.println("Calendar is empty.");
					break;
				}
				
				String deleteevent = "";
				while(rmeventflag == true)
				{
					for(int i=0; i< usereventsize; i++)
					{
						System.out.print((i+1) + ") ");
						calen.getUsers().get(fourchoice).getEvents().get(i).printevent();	
					}
					System.out.println("Which event would you like to delete?");
					deleteevent = sc.nextLine();
					rmeventflag = !Validator.validateChoice(deleteevent, 1, calen.getUsers().get(fourchoice).getEvents().size());
					if(rmeventflag == true)
					{
						System.out.println("Invalid Choide");
					}
				}
				
				calen.getUsers().get(fourchoice).getEvents().remove(Integer.parseInt(deleteevent)-1);
				change = true;
				break;
				
			case 6:	
				boolean sortflag = false;
				String choice5 = "";
				while(sortflag == false)
				{
					System.out.println("1) Ascending (A-Z)");
					System.out.println("2) Descending (Z-A)");
					choice5 = sc.nextLine();
					sortflag = Validator.validateChoice(choice5, 1, 2);
					if(sortflag == false)
					{
						System.out.println("Invalid choice");
					}
				}
				
				if(choice5.equals("1"))
				{
					Collections.sort(calen.getUsers(), new NumberComparator1());
					calen.printname();
					System.out.println("");
				}
				else
				{
					Collections.sort(calen.getUsers(), new NumberComparator1());
					Collections.reverse(calen.getUsers());
					calen.printname();
					System.out.println("");
				}
				change = true;
				break;
				
			case 7:
				
				for(int i=0; i < calen.getUsers().size(); i++)
				{
					if(calen.getUsers().get(i).getEvents().size() != 0)
					{
						Collections.sort(calen.getUsers().get(i).getEvents(), new NumberComparator());
					}
				}
				
				GsonBuilder builder = new GsonBuilder();
				Gson gson = builder.setPrettyPrinting().create();
				String jsontostring = gson.toJson(calen);

				File file = new File(filename);
				FileWriter fileWriter = new FileWriter(file);
				fileWriter.write(jsontostring);
				fileWriter.flush();
				fileWriter.close();
				
				System.out.println("File has been saved.");
				change = false;
				break;
				
			case 8:
				if(change == true)
				{
					System.out.println("Changes have been made since the file  was last saved.");
					boolean changeflag = false;
					String choice7 = "";
					while(changeflag == false)
					{
						System.out.println("\t 1) Yes");
						System.out.println("\t 2) No");
						System.out.println("Would you like to save the file before exiting?");
						choice7 =  sc.nextLine();
						changeflag = Validator.validateChoice(choice7, 1, 2);
						if(changeflag == false)
						{
							System.out.println("Invalid Choice");
						}
					}
					
					if(choice7.equals("1"))
					{
						for(int i=0; i < calen.getUsers().size(); i++)
						{
							if(calen.getUsers().get(i).getEvents().size() != 0)
							{
								Collections.sort(calen.getUsers().get(i).getEvents(), new NumberComparator());
							}
						}
						
						builder = new GsonBuilder();
						gson = builder.setPrettyPrinting().create();
						jsontostring = gson.toJson(calen);

						file = new File(filename);
						fileWriter = new FileWriter(file);
						fileWriter.write(jsontostring);
						fileWriter.flush();
						fileWriter.close();		
						
						System.out.println("File was saved.");
					}
					else
					{
						System.out.println("File was not saved.");
					}
				}
				System.out.println("Thank you for using my program!");					
				breakflag = true;
				break;
			}
		}
		sc.close();
	}
	
}
