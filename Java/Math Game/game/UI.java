package game;

import javax.swing.JPanel;


public abstract class UI extends JPanel {

	protected Game owner;
	
	public UI(Game owner) {
		this.owner = owner;
	}
	
	/**
	 * init the ui when opened
	 */
	public abstract void init();
	
	/**
	 * clear resources
	 */
	public abstract void destroy();
	
}
