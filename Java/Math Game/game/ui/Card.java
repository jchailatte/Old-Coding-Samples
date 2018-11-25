package game.ui;

import game.Res;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JComponent;

public class Card extends JComponent implements MouseListener {

	private char letter; // a~z A~Z
	private String name;
	private boolean flipped;
	private GameUI owner;
	
	public Card(String name, char letter, GameUI owner) {
		this.name = name;
		this.letter = letter;
		this.owner = owner;
		addMouseListener(this);
	}
	
	public void flip(boolean flipped) {
		this.flipped = flipped;
	}
	
	public boolean isFlipped() {
		return flipped;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (flipped) {
			Res.drawImage(g, "front-bg.png", 0, 0);
			g.setColor(new Color(0x00009c));
			g.setFont(new Font(null, Font.BOLD, 20));
			Res.drawImage(g, name, 15, 15, 15 + 113, 15 + 113);
			g.drawString(letter + "", 10, 20);
		} else {
			Res.drawImage(g, "back-img-bg.png", 0, 0);
		}
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Card) {
			return ((Card) o).name.equals(name);
		}
		return false;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (flipped || owner.flipCard(this)) {
			Res.playSound("res/" + Character.toLowerCase(letter) + ".mp3");
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}
}
