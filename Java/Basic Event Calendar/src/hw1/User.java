//Jonathan Chai
//9272337598

package hw1;
import java.util.ArrayList;
import java.util.Comparator;

public class User
{
	User()
	{
		Name newname = new Name();
		setEvents(new ArrayList<Event>());		
		setName(newname);
	}
	
	private Name Name;

	public Name getName() { return this.Name; }

	public void setName(Name Name) { this.Name = Name; }

	private ArrayList<Event> Events;

	public ArrayList<Event> getEvents() { return this.Events; }

	public void setEvents(ArrayList<Event> Events) { this.Events = Events;}
	

}

class NumberComparator1 implements Comparator<User>  {	//fix string comparator
 	public int compare(User Item1,User Item2) {
 		
 		String ad1 = Item1.getName().getLname();
 		String ad2 = Item2.getName().getLname();
 		String fn1 = Item1.getName().getFname();
 		String fn2 = Item2.getName().getFname();

		if(ad1.compareToIgnoreCase(ad2) == 0)
		{
			return fn1.compareToIgnoreCase(fn2);
		}
		else
		{
			return ad1.compareToIgnoreCase(ad2);
		}
 	}
}


