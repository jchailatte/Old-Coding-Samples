package game.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Random;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.Game;
import game.Res;
import game.UI;

/**
 * gameing ui
 * @author Mu-Po Rong Fang Borui
 *
 */
public class GameUI extends UI implements Runnable {

	private JLabel lblGameInfo;
	private JPanel pnlGame;
	
	private Card[] allCards;
	private Card[] selectedCards = new Card[2];
	private boolean canTouch = true;
	private long freezeTime;
	private boolean gameOver;
	private int score;
	private long startTime;
	
	private static final int TIME_LIMIT = 30 * 1000; // 30 second
	
	public GameUI(Game owner) {
		super(owner);
	}

	@Override
	public void init() {
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 20));
		
		lblGameInfo = new JLabel();
		add(lblGameInfo, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new GridLayout(4, 4, 10, 10));
		pnlGame = panel;
		add(panel, BorderLayout.CENTER);
		initGamePanel(panel);
		new Thread(this).start();
	}
	
	private void initGamePanel(JPanel parent) {
		parent.removeAll();
		int size = 4 * 4; // 16 grid
		Random rnd = new Random();
		List<String> images = Res.getAllImagesDouble(size);
		List<Character> letters = Res.getLetters(size);
		allCards = new Card[size];
		for (int i = 0; i < size; i++) {
			String name = images.remove(rnd.nextInt(images.size()));
			char letter = letters.remove(rnd.nextInt(letters.size()));
			Card card = new Card(name, letter, this);
			parent.add(card);
			allCards[i] = card;
		}
		startTime = System.currentTimeMillis();
	}
	
	public boolean flipCard(Card card) {
		if (!canTouch) {
			return false;
		}
		card.flip(true);
		if (selectedCards[0] == null) {
			selectedCards[0] = card;
		} else {
			if (card.equals(selectedCards[0])) {
				score += 2;
				selectedCards[0] = selectedCards[1] = null;
			} else {
				selectedCards[1] = card;
				freezeTime = System.currentTimeMillis();
				canTouch = false;
			}
		}
		return true;
	}
	
	private boolean gameFinish() {
		for (int i = 0; i < allCards.length; i++) {
			if (!allCards[i].isFlipped()) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void destroy() {
		gameOver = true;
	}

	@Override
	public void run() {
		while (!gameOver) {
			lblGameInfo.setText("<html>Score: " + score + "<br>Time:" + (TIME_LIMIT - (System.currentTimeMillis() - startTime)) / 1000 + " s</html>");
			if (!canTouch && System.currentTimeMillis() - freezeTime >= 500) {
				canTouch = true;
				selectedCards[0].flip(false);
				selectedCards[1].flip(false);
				selectedCards[0] = selectedCards[1] = null;
			}
			repaint();
			try {
				Thread.sleep(20);
			} catch (Exception e) {}
			
			if (gameFinish()) {
				initGamePanel(pnlGame);
			}
			if (System.currentTimeMillis() - startTime >= TIME_LIMIT) {
				gameOver = true;
			}
		}
		
		owner.showUI(new GameOverUI(owner, score));
	}

}
