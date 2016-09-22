package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PelletPoop extends Consumable {

	public PelletPoop(float xPos, float yPos) throws SlickException {
		super(xPos, yPos, 5000, new Image("/res/poop_pellet.png"), 1);
	}

	@Override
	void applyEffects(Snail snail) {
		snail.trailLength += 250;
		
	}

}
