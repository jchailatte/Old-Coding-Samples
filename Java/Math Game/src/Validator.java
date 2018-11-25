// Chai, Jonathan
// CS-170-02
// Final Project
// Create a math game

public class Validator {

	public static boolean validateInt(String s) {
		boolean isValid = false;
		int data = 0;

		 while(!isValid) {
		  try {
				if(verifynumeric(s) == false)
				{
					throw new NumberFormatException();
				}
				else
				{
					int sc = Integer.parseInt(s);
					data = sc;
				}

				isValid = true;
			}
		 
		catch (NumberFormatException e) {
			return false;
		 }						//End of catch
		catch (Exception e) {
			System.out.println("\nInput data out of the range error. Please check the data and try again...");
			return false;
		 }
		} 						//End of while
		return true;
	 }	//End of validateInt()

	
public static boolean verifynumeric(String line)
{
	if(line.matches("^[+-]?[0-9.]*"))
	{
		return true;
	}
	else
	{
		return false;
	}
}

}

