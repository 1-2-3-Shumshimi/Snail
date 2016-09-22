package javagame;

import java.util.Map;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class PlayerSnail extends Snail {

	int scoreDeductCounter = 1;
	int collisionCounter = 0;
	
	public PlayerSnail(Snail opponentSnail) throws SlickException {
		super(150, 300, Color.red, opponentSnail);

	}

	@Override
	void update(GameContainer gc, StateBasedGame sbg, int delta) {

		//Update outside if statement or things can get stuck!
		updateTrail();
		
		if (!checkCollision(opponentSnail.trail)){
			//Trail blazin'
			addTrail(xPos, yPos);

			//Snail moving
			float mouseX = Mouse.getX();
			float mouseY = Mouse.getY(); //point 0,0 is at the bottom left (unlike graphics)
			float correctX = xPos;
			float correctY = 600-yPos;
			currentDirection = determineDirection(correctX, correctY, mouseX, mouseY);
			float deltaX = correctX-mouseX;
			float deltaY = correctY-mouseY;
			double distance = Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2));
			if (distance > 0.5){
				xPos += deltaX/distance * -speed;
				yPos -= deltaY/distance * -speed;
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

}
