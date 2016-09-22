package javagame;

import java.util.LinkedList;
import java.util.Map;

import org.newdawn.slick.Color;
import org.newdawn.slick.SlickException;

public class CompSnailCautious extends ComputerSnail {

	public CompSnailCautious(float initialX, float initialY, Color color,
			Snail opponentSnail, LinkedList<Consumable> consumables)
			throws SlickException {
		super(initialX, initialY, color, opponentSnail, consumables);
	}

	@Override
	float[] getPolicy() {
		float[] greedyCoord = getGreedyPolicy();
		double distance = Math.sqrt(Math.pow(greedyCoord[0]-xPos, 2) + Math.pow(greedyCoord[1]-yPos, 2));
		
		float deltaX = (float) (6*(greedyCoord[0]-xPos)*speed/distance);
		float deltaY = (float) (6*(greedyCoord[1]-yPos)*speed/distance);
		float[] coord = {xPos+deltaX, yPos+deltaY};
		int lookAhead = 0;
		while (!checkCollision(coord[0], coord[1], opponentSnail.trail) && lookAhead < 15){
			coord[0] += deltaX;
			coord[1] += deltaY;
			lookAhead++;
		}
		if (lookAhead >= 15){ //No collisions detected
			return coord;
		} else { //Modify based on how far the look ahead is; the smaller it is, the larger the change
			Map.Entry<Float[], Integer> minEntry = null; //Find the minimum (e.g. oldest) entry - go towards that
			for (Map.Entry<Float[], Integer> entry : opponentSnail.trail.entrySet())
			{
			    if (minEntry == null || entry.getValue().compareTo(minEntry.getValue()) < 0)
			    {
			        minEntry = entry;
			    }
			}
			//Go towards the end of the opponent's trail
			coord[0] = (float) ((coord[0]*0.05*(lookAhead) + minEntry.getKey()[0]*0.05*(1-lookAhead))/2);
			coord[1] = (float) ((coord[1]*0.05*(lookAhead) + minEntry.getKey()[1]*0.05*(1-lookAhead))/2);

			return coord;
		}	
	}

}
