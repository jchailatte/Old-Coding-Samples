package game;

/**
 * a player's score
 * @author Mu-Po Rong Fang Borui
 *
 */
public class Score implements Comparable<Score> {

	private int score;
	private String name;
	
	public Score(int score, String name) {
		this.score = score;
		this.name = name;
	}
	
	public int getScore() {
		return score;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public int compareTo(Score o) {
		return o.score - score;
	}
	
	@Override
	public String toString() {
		return "Name: " + name + ",  Score: " + score;
	}
}
