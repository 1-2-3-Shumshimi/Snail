package javagame;

import java.util.LinkedList;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class CompSnailRandom extends ComputerSnail {
	
	int changeDirectionTime;
	float[] oldCoord;

	public CompSnailRandom(float initialX, float initialY, 
			Color color, Snail opponentSnail, 
			LinkedList<Consumable> consumables)
			throws SlickException {
		super(initialX, initialY, color, opponentSnail, consumables);
		changeDirectionTime = 0;
		oldCoord = new float[2];
		oldCoord[0] = 0;
		oldCoord[1] = 300;
	}

	@Override
	float[] getPolicy() {
		if (changeDirectionTime % 200 == 0){
			oldCoord[0] = (float)(Math.random()*800);
			oldCoord[1] = (float)(Math.random()*600);
		}
		changeDirectionTime++;
		return oldCoord;
		
	}

}
