package vecchio;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class Assets {

	private static final int width=32, height =32;

	public static Font font28;
	
	public static int getWidth() {
		return width;
	}

	public static int getHeight() {
		return height;
	}
	
	public static BufferedImage bloccoPiccolo, bloccoGrande,bloccoLungo;
	
	public static void init() {
		
		bloccoPiccolo = ImageLoader.loadImage("C:\\Users\\ricky\\git\\SlidingBlocksAI\\SlidingBlocksAI\\blocchi\\bloccoPiccolo.PNG");
		bloccoGrande = ImageLoader.loadImage("/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG");
		bloccoLungo = ImageLoader.loadImage("/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG");
					
	}
}
