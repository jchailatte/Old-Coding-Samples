package game;
import game.ui.MenuUI;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.WindowAdapter;

import javax.swing.JButton;
import javax.swing.JFrame;


public class Game extends JFrame {

	private UI current; // the current display ui
	
	public Game() {
		setTitle("Game ABC");
		setSize(675,755);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
		// show menu
		showUI(new MenuUI(this));
		setVisible(true);
		addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent e) {
				System.out.println(e.getComponent().getWidth() + "," + e.getComponent().getHeight());
			}
		});
	}
	
	/**
	 * display the ui
	 * @param ui
	 */
	public void showUI(UI ui) {
		if (current != null) {
			current.destroy(); // clean up
			remove(current);
		}
		ui.init(); // init ui
		add(ui);
		current = ui;
		validate();
		repaint();
	}
	
	/**
	 * quit the game
	 */
	public void quit() {
		System.exit(0);
	}
	
}
