package javagame;

import java.util.LinkedList;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

/**
 * Bee-lines towards the nearest pellet it can see without care for any trails
 * Prioritizes power up pellets over point pellets
 * @author Jonathan
 *
 */
public class CompSnailGreedy extends ComputerSnail {

	public CompSnailGreedy(float initialX, float initialY, 
			Color color, Snail opponentSnail,
			LinkedList<Consumable> consumables) throws SlickException {
		super(initialX, initialY, color, opponentSnail, consumables);
	}

	@Override
	float[] getPolicy() {
		return getGreedyPolicy();
	}

}
