//Jonathan Chai
//9272337598

package hw1;
public class Validator {

	public static boolean validateChoice(String s, int a, int b) {
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

				if (data < a) 				
					throw new Exception();	

				else if (data > b) 			
					throw new Exception();		
				isValid = true;
			}
		 
		catch (NumberFormatException e) {
			return false;
		 }						//End of catch
		catch (Exception e) {
			return false;
		 }
		} 						//End of while
		return true;
	 }	//End of validateInt()
	
	public static boolean verifynumeric(String line)
	{
		if(line.matches("[0-9.]*"))
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public static boolean verifypos(String s) {
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

				if (data < 0) 			
				{
					throw new Exception();	
				}
				else
				{
					isValid = true;
				}
			}
		 
		catch (NumberFormatException e) {
			return false;
		 }						//End of catch
		catch (Exception e) {
			return false;
		 }
		} 						//End of while
		return true;
	 }

}

