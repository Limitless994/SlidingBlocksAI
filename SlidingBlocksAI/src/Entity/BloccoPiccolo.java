package Entity;

import java.awt.Graphics;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import nuovo.GameHandler;

public class BloccoPiccolo extends Blocco{

	int width = 32;
	int height = 32;


	public BloccoPiccolo(GameHandler handler, float x, float y) {
		super(handler, x, y);
		bounds.x = 3;
		bounds.y = (int) (height / 2f);
		bounds.width = width - 16;
		bounds.height = (int) (height - height / 2f-14);
	}

	@Override
	public void tick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void render(Graphics g) {
		g.drawImage(gfx.Assets.bloccoGrande, (int) (x), (int) (y), (int)(width*0.8),(int) (height*0.8), null);
		
	}



}
