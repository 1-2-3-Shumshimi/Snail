package javagame;

import java.util.Map;

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MenuSnail extends Snail {

	public MenuSnail() throws SlickException{
		super(750, 50, Color.magenta, null);
	}
	
	@Override
	void update(GameContainer gc, StateBasedGame sbg, int delta) {
		
		//Trail blazin'
		addTrail(xPos, yPos);
		updateTrail();
		
		//Snail moving
		float mouseX = Mouse.getX();
		float mouseY = Mouse.getY(); //point 0,0 is at the bottom left (unlike graphics)
		float correctX = xPos;
		float correctY = 600-yPos;
		currentDirection = determineDirection(correctX, correctY, mouseX, mouseY);
		float deltaX = correctX-mouseX;
		float deltaY = correctY-mouseY;
		double distance = Math.sqrt(Math.pow(deltaX, 2)+Math.pow(deltaY, 2));
		if (distance > 1.5){
			xPos += deltaX/distance * -speed;
			yPos -= deltaY/distance * -speed;
		}
		
	}

}
