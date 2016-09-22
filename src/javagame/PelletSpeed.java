package javagame;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class PelletSpeed extends Consumable {

	public PelletSpeed(float xPos, float yPos) throws SlickException {
		super(xPos, yPos, 5000, new Image("res/speed_pellet.png"), 2);
	}

	@Override
	void applyEffects(Snail snail) {
		snail.speed += 0.05;

	}

}
