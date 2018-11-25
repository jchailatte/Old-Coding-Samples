package game;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.media.Manager;
import javax.media.Player;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.Line;
import javax.sound.sampled.SourceDataLine;

/**
 * Resource manager
 * @author Mu-Po Rong Fang Borui
 *
 */
public class Res {

	private static Map<String, Image> imagesCache = new HashMap<String, Image>();
	private static List<String> cardsName = new ArrayList<String>();
	
	public static boolean musicOn = true;
	
	static class Image {
		BufferedImage parent;
		Map<Float, BufferedImage> cache;
		int x, y, w, h; // position in the big image
		Image(BufferedImage parent, int x, int y, int w, int h) {
			this.parent = parent;
			this.x = x;
			this.y = y;
			this.w = w;
			this.h = h;
			cache = new HashMap<Float, BufferedImage>();
		}
		
		/**
		 * get the small image
		 * @param scale
		 * @return
		 */
		BufferedImage getImage(float scale) {
			BufferedImage img = cache.get(scale);
			if (img == null) {
				int width = (int) (w * scale);
				int height = (int) (h * scale);
				img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
				img.getGraphics().drawImage(parent, 0, 0, width, height, x, y, x + w, y + h, null);
				cache.put(scale, img);
			}
			return img;
		}
		
		/**
		 * draw the small image
		 * @param g
		 * @param dx1
		 * @param dy1
		 * @param dx2
		 * @param dy2
		 */
		void draw(Graphics g, int dx1, int dy1, int dx2, int dy2) {
			g.drawImage(parent, dx1, dy1, dx2, dy2, x, y, x + w, y + w, null);
		}
	}
	
	static {
		// load all the images and configs
		init("res/bg.png", "res/bg.txt");
		init("res/menu.png", "res/menu.txt");
		init("res/words.png", "res/words.txt");
	}
	
	public static void init(String image, String config) {
		try {
			// load the big image
			BufferedImage bigImage = ImageIO.read(new File(image));
			
			// parse config
			BufferedReader br = new BufferedReader(new FileReader(config));
			String line = null;
			while ((line = br.readLine()) != null) {
				String[] strs = line.split(",");
				
				String name = strs[0]; // image name
				// image bounds in the big image
				int x = Integer.parseInt(strs[1]);
				int y = Integer.parseInt(strs[2]);
				int w = Integer.parseInt(strs[3]);
				int h = Integer.parseInt(strs[4]);
				imagesCache.put(name, new Image(bigImage, x, y, w, h));
				if (config.equals("res/words.txt")) {
					cardsName.add(name);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * get the small image
	 * @param name
	 * @param scale
	 * @return
	 */
	public static BufferedImage getImage(String name, float scale) {
		Image image = imagesCache.get(name);
		if (image == null) {
			return null;
		}
		return image.getImage(scale);
	}
	
	public static BufferedImage getImage(String name) {
		return getImage(name, 1.0f);
	}
	
	/**
	 * draw the small image on x, y
	 * @param g
	 * @param name
	 * @param x
	 * @param y
	 */
	public static void drawImage(Graphics g, String name, int x, int y) {
		drawImage(g, name, x, y, 0, 0);
	}
	
	public static void drawImage(Graphics g, String name, int dx1, int dy1, int dx2, int dy2) {
		Image image = imagesCache.get(name);
		if (image != null) {
			if (dx2 == 0) {
				dx2 = dx1 + image.w;
			}
			if (dy2 == 0) {
				dy2 = dy1 + image.h;
			}
			image.draw(g, dx1, dy1, dx2, dy2);
		}
	}
	
	/**
	 * get the small images' name(double)
	 * @param size
	 * @return
	 */
	public static List<String> getAllImagesDouble(int size) {
		Random rnd = new Random();
		List<String> all = new ArrayList<String>(cardsName);
		List<String> list = new ArrayList<String>(size);
		for (int i = 0; i < size / 2; i++) {
			String name = all.remove(rnd.nextInt(all.size()));
			// add twice
			list.add(name);
			list.add(name);
		}
		return list;
	}
	
	/**
	 * get letters(random in A~Z, a~z)
	 * @param size
	 * @return
	 */
	public static List<Character> getLetters(int size) {
		List<Character> all = new ArrayList<Character>(26);
		for (int i = 0; i < 26; i++) {
			all.add((char) ('A' + i));
		}
		
		Random rnd = new Random();
		List<Character> list = new ArrayList<Character>(size);
		for (int i = 0; i < size / 2; i++) {
			char c = all.remove(rnd.nextInt(all.size()));
			list.add(c);
			list.add(Character.toLowerCase(c));
		}
		return list;
	}
	
	public static void playSound(String file) {
		if (!musicOn) {
			return;
		}
		try {
			System.out.println("play " + file);
			Player player = Manager.createPlayer(new File(file).toURI().toURL());
			player.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readTxt(String file) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line).append("\n");
			}
			br.close();
			return sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static void writeTxt(String file, String content) {
		try {
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			bw.write(content);
			bw.flush();
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
