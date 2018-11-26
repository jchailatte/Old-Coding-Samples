//Jonathan Chai
//9272337598

package hw1;
public class Date
{
	Date()
	{
		Day = 0;
		Year = 0;
		Month = "";
	}
	
  private String Month;

  public String getMonth() { return this.Month; }

  public void setMonth(String Month) { this.Month = Month; }

  private int Day;

  public int getDay() { return this.Day; }

  public void setDay(int Day) { this.Day = Day; }

  private int Year;

  public int getYear() { return this.Year; }

  public void setYear(int Year) { this.Year = Year; }
}
