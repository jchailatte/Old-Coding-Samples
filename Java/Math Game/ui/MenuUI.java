package game.ui;

import game.Game;
import game.Res;
import game.UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * the menu
 * @author Mu-Po Rong Fang Borui
 *
 */
public class MenuUI extends UI implements MouseListener {

	public MenuUI(Game owner) {
		super(owner);
	}

	@Override
	public void init() {
		setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		setLayout(new BorderLayout(0, 100));
		JLabel title = new JLabel("Learn ABC", JLabel.CENTER);
		title.setForeground(new Color(0x5f9f9f));
		title.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 30));
		add(title, BorderLayout.NORTH);
		
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
		add(panel, BorderLayout.CENTER);
		addButtons(panel);
		
		repaint();
	}
	
	private void addButtons(JPanel parent) {
		BufferedImage imgStart = Res.getImage("playButtonNormal 2.png", 0.5f);
		BufferedImage imgMusic = Res.getImage("ButtonMusic.png", 0.5f);
		BufferedImage imgQuit = Res.getImage("ButtonToTitle.png", 0.5f);
		
		// start
		JLabel start = new JLabel();
		start.setToolTipText("Start the game");
		start.setIcon(new ImageIcon(imgStart));
		start.addMouseListener(this);
		parent.add(start);
		
		// music
		JLabel music = new JLabel();
		music.setToolTipText("Music on");
		music.setIcon(new ImageIcon(imgMusic));
		music.addMouseListener(this);
		parent.add(music);
		
		// quit
		JLabel quit = new JLabel();
		quit.setToolTipText("Quit");
		quit.setIcon(new ImageIcon(imgQuit));
		quit.addMouseListener(this);
		parent.add(quit);
	}
	
	@Override
	public void destroy() {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
	}

	@Override
	public void mousePressed(MouseEvent e) {
		JLabel lbl = (JLabel) e.getComponent();
		String tip = lbl.getToolTipText();
		if (tip.startsWith("Start")) {
			lbl.setIcon(new ImageIcon(Res.getImage("playButtonSelected 2.png", 0.5f)));
		} else if (tip.startsWith("Music")) {
			lbl.setIcon(new ImageIcon(Res.getImage(Res.musicOn ? "ButtonMusic_down.png" : "ButtonMusic.png", 0.5f)));
		} else if (tip.startsWith("Quit")) {
			lbl.setIcon(new ImageIcon(Res.getImage("ButtonToTitle_down.png", 0.5f)));
		}
			
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		JLabel lbl = (JLabel) e.getComponent();
		String tip = lbl.getToolTipText();
		if (tip.startsWith("Start")) {
			lbl.setIcon(new ImageIcon(Res.getImage("playButtonNormal 2.png", 0.5f)));
			owner.showUI(new GameUI(owner));
		} else if (tip.startsWith("Music")) {
			lbl.setIcon(new ImageIcon(Res.getImage(Res.musicOn ? "ButtonMusic_down.png" : "ButtonMusic.png", 0.5f)));
			Res.musicOn = !Res.musicOn;
			lbl.setToolTipText(Res.musicOn ? "Music on" : "Music off");
		} else if (tip.startsWith("Quit")) {
			lbl.setIcon(new ImageIcon(Res.getImage("ButtonToTitle.png", 0.5f)));
			owner.quit();
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}


}
