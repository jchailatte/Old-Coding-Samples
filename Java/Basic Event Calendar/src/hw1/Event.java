//Jonathan Chai
//9272337598

package hw1;
import java.util.Comparator;
import java.util.HashMap;

public class Event
{
	Event()
	{
		Title = "";
		Time = "";		
	}
  private String Title;

  public String getTitle() { return this.Title; }

  public void setTitle(String Title) { this.Title = Title; }

  private String Time;

  public String getTime() { return this.Time; }

  public void setTime(String Time) { this.Time = Time; }

  private Date Date;

  public Date getDate() { return this.Date; }

  public void setDate(Date Date) { this.Date = Date; }

  public void printevent()
  {
		System.out.println(getTitle() + ", " + getTime() + ", " + getDate().getMonth() + " " +
				getDate().getDay() + ", " + getDate().getYear());
  }

}


class NumberComparator implements Comparator<Event>  {
 	public int compare(Event Item1,Event Item2) {
 		
 		int ad1 = Item1.getDate().getYear();
 		int ad2 = Item2.getDate().getYear();

		if(ad1>ad2)
		{
			return 1;
		}
		else if(ad1<ad2)
		{
			return -1;
		}
		else
		{		
			HashMap<String, Integer> month = new HashMap<String, Integer>(); 
			month.put("", 0);
			month.put("January", 1);
			month.put("February", 2);
			month.put("March",3);
			month.put("April",4);
			month.put("May",5);
			month.put("June", 6);
			month.put("July",7);
			month.put("August",8);
			month.put("September",9);
			month.put("October",10);
			month.put("November",11);
			month.put("December",12);
		
			String da1 = Item1.getDate().getMonth();
			String da2 = Item2.getDate().getMonth();
		
			int dad1 = month.get(da1);
			int dad2 = month.get(da2);	
		
			if(dad1>dad2)
			{
				return 1;
			}
			else if(dad1<dad2)
			{
				return -1;
			}
			else
			{
				if(Item1.getDate().getDay() > Item2.getDate().getDay())
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			
		}
 	}
}

	



