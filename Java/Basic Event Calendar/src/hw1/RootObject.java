//Jonathan Chai
//9272337598

package hw1;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class RootObject {

	@SerializedName("Users")
	private List<User> Users;
	
	public List<User> getUsers() {
		return this.Users;
	}
	
	public void setUsers(List<User> Users){
		this.Users = Users;
	}
	
	public void print() {
	
		int size = Users.size();
		char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();
		
		for(int i = 0; i< size; i++)
		{
			System.out.println((i+1) +") "+Users.get(i).getName().getLname()+", "+Users.get(i).getName().getFname());
			for(int j=0; j< Users.get(i).getEvents().size(); j++)
			{
				System.out.print("\t" + alphabet[j] + ") ");
				Users.get(i).getEvents().get(j).printevent();
			}
		}
		
	}
	
	public void printname()
	{
		for(int i=0; i<Users.size(); i++)
		{
			System.out.println((i+1)+") "+Users.get(i).getName().getLname()+ ", "+Users.get(i).getName().getFname());
		}
	}

}

