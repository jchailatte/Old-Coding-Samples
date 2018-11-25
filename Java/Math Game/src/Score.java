// Chai, Jonathan
// CS-170-02
// Final Project
// Create a math game

import java.util.Comparator;

public class Score {

	private String name ;
	private int score ;
	
	Score(String name, int score)
	{
		this.name = name;
		this.score = score;
	}
	
	public void setName(String name) {
		   this.name = name;
	}
	  
	public String getName() {
	      return name;
	}
	
	public void setScore(int score) {
	  	   this.score = score;
    }
	
	public int getScore() {
	      return score;
	}
	
	   
	public String toString()  {
	
		String tostring= "\n" +"Name: " + name + "\t" +"  Score: " + score+ "\n";
		return  tostring;
			  
	}
	   
}  

class NumberComparator implements Comparator<Score>  {
	 	public int compare(Score Item1,Score Item2) {
	 		
	 		int ad1 = Item1.getScore();
	 		int ad2 = Item2.getScore();
				if (ad1 < ad2)  //Sorting number in natural way
			  return 1;

			 if (ad1 > ad2)
			  return -1;

	 		return 0;
	 	}
}


