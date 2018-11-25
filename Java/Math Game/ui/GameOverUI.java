package game.ui;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import game.Game;
import game.Res;
import game.Score;
import game.UI;

/**
 * game over
 * @author Mu-Po Rong Fang Borui
 *
 */
public class GameOverUI extends UI implements ActionListener {

	private int score;
	private JTextField tfName;
	private List<Score> top5;
	
	public GameOverUI(Game owner, int score) {
		super(owner);
		this.score = score;
		top5 = new ArrayList<Score>();
	}

	@Override
	public void init() {
		initTop5Score();
		setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));
		setLayout(new GridLayout(20, 1));
		add(new JLabel("Top 5:", JLabel.CENTER));
		addTop5();
		add(new JLabel("Your score: " + score, JLabel.CENTER));
		add(new JLabel("Input your name:", JLabel.CENTER));
		tfName = new JTextField(20);
		add(tfName);
		JButton submit = new JButton("Submit Score");
		submit.addActionListener(this);
		add(submit);
	}
	
	private void initTop5Score() {
		String str = Res.readTxt("res/scores.txt");
		if (str == null) {
			return;
		}
		String[] strs = str.split("\n");
		for (String s : strs) {
			int dot = s.indexOf(',');
			if (dot != -1) {
				String name = s.substring(0, dot);
				String score = s.substring(dot + 1);
				if (top5.size() >= 5) {
					break;
				}
				top5.add(new Score(Integer.parseInt(score), name));
			}
		}
	}
	
	private void addTop5() {
		for (Score s : top5) {
			JLabel lbl = new JLabel(s.toString(), JLabel.CENTER);
			lbl.setForeground(Color.RED);
			add(lbl);
		}
	}

	@Override
	public void destroy() {
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// submit the score
		top5.add(new Score(score, tfName.getText()));
		Collections.sort(top5);
		if (top5.size() > 5) {
			top5.remove(top5.size() - 1);
		}
		// save to file
		StringBuilder sb = new StringBuilder();
		for (Score s : top5) {
			sb.append(s.getName()).append(",").append(s.getScore()).append("\n");
		}
		sb.setLength(sb.length() - 1); // delete the last '\n'
		Res.writeTxt("res/scores.txt", sb.toString());
		JOptionPane.showMessageDialog(null, "Submit success");
		
		owner.showUI(new MenuUI(owner));
	}

}
