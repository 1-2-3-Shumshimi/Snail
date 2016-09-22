package javagame;

import java.util.LinkedList;
import java.util.Map;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class ComputerSnail extends Snail{

	int scoreDeductCounter = 0;
	int collisionCounter = 0;
	LinkedList<Consumable> consumables;
	int changeDirectionTime;
	float[] oldCoord;

	public ComputerSnail(float initialX, float initialY, 
			Color color, Snail opponentSnail, 
			LinkedList<Consumable> consumables)
					throws SlickException {
		super(initialX, initialY, color, opponentSnail);
		this.consumables = consumables;
		changeDirectionTime = 0;
		oldCoord = new float[2];
		oldCoord[0] = 0;
		oldCoord[1] = 300;
	}

	@Override
	void update(GameContainer gc, StateBasedGame sbg, int delta) {

		//Update outside if statement or things can get stuck!
		updateTrail();

		if (!checkCollision(opponentSnail.trail)){
			//Trail blazin'
			addTrail(xPos, yPos);

			//Snail moving
			float[] coord = getPolicy(); //Different from player and different between different AIs
			float dirX = coord[0];
			float dirY = coord[1];
			currentDirection = determineDirection(xPos, yPos, dirX, dirY);
			float deltaX = xPos-dirX;
			float deltaY = yPos-dirY;
			double distance = Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2));
			if (distance > 0.5){
				xPos += deltaX/distance * -speed;
				yPos += deltaY/distance * -speed;
			}
			collisionCounter = 0;
		} else {
			if (scoreDeductCounter % 50 == 0){
				score--;
				scoreDeductCounter = 1;
			}
			scoreDeductCounter++;
			collisionCounter++;
			if (collisionCounter == 4000){
				xPos = (float)(Math.random()*750+25);
				yPos = (float)(Math.random()*550+25);
			}
		}
	}
	
	public void updateConsumables(LinkedList<Consumable> consumables){
		this.consumables = consumables;
	}

	/**
	 * Given a linked list of consumbles, returns the closest one to the snail
	 * @param consumables
	 * @return
	 */
	public Consumable getNearestConsumable(LinkedList<Consumable> consumables){
		if (consumables.isEmpty()){//Empty list
			return null;
		} else if (consumables.size() == 1){
			return consumables.get(0);
		} else {
			Consumable nearest = consumables.get(0);
			double nearestDistance = Math.pow(nearest.xPos-xPos, 2) + Math.pow(nearest.yPos-yPos, 2);
			for (int i = 1; i < consumables.size(); i++){
				Consumable c = consumables.get(1);
				double cDistance = Math.pow(c.xPos-xPos, 2) + Math.pow(c.yPos-yPos, 2);
				if (cDistance < nearestDistance){
					nearestDistance = cDistance;
					nearest = c;
				}
			}
			return nearest;
		}
	}
	
	/**
	 * Similar to the greedy policy from the greedy snail.
	 * @return
	 */
	public float[] getGreedyPolicy(){

		//Greedy policy
		LinkedList<Consumable> powerPellets = new LinkedList<Consumable>();
		LinkedList<Consumable> normalPellets = new LinkedList<Consumable>();
		for (int i = 0; i < consumables.size(); i++){
			Consumable c = consumables.get(i);
			if (c.id != 0){powerPellets.add(c);}
			else {normalPellets.add(c);}
		}
		if (!powerPellets.isEmpty()){//there are power pellets
			Consumable c = getNearestConsumable(powerPellets);
			float[] coord = {c.xPos-8, c.yPos-8};
			return coord;
		} else if (!normalPellets.isEmpty()){
			Consumable c = getNearestConsumable(normalPellets);
			float[] coord = {c.xPos-8, c.yPos-8};
			return coord;
		} else {//Both empty?? - just be random
			if (changeDirectionTime % 200 == 0){
				oldCoord[0] = (float)(Math.random()*800);
				oldCoord[1] = (float)(Math.random()*600);
			}
			changeDirectionTime++;
			return oldCoord;
		}
	}

	/**
	 * Return a coordinate in the form of a float array that acts like an
	 * artificial mouse location. This method essentially chooses which direction
	 * the computer player will go.
	 * @return
	 */
	abstract float[] getPolicy();

}
