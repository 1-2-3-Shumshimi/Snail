package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play2 extends BasicGameState{

	public Play2(int state){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		g.drawString("Under construction!!", 50, 50);
		g.drawString("Press ENTER to return to the main menu.", 50, 65);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		Input input = gc.getInput(); //All the input
		if (input.isKeyPressed(Input.KEY_ENTER)){sbg.enterState(0);}
	}

	@Override
	public int getID() {
		return 2;
	}

}
