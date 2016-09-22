package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PelletPoint extends Consumable {
	
	public PelletPoint(float xPos, float yPos) throws SlickException {
		super(xPos, yPos, 10000, new Image("res/point_pellet.png"), 0);
		
	}

	@Override
	void applyEffects(Snail snail) {
		snail.score += 25;
	}

}
