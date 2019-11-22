package vecchio;

import java.awt.Font;
import java.awt.image.BufferedImage;

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
		
		bloccoPiccolo = ImageLoader.loadImage("/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG");
		bloccoGrande = ImageLoader.loadImage("/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG");
		bloccoLungo = ImageLoader.loadImage("/SlidingBlocksAI/SlidingBlocksAI/blocchi/bloccoPiccolo.PNG");
	}
}
