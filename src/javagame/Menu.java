package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.lwjgl.input.Mouse;

public class Menu extends BasicGameState{
	
	int currentSelection = 0; //0 for 1-p version, 1 for 2-p version, and 2 for exit game
	String[] snailsLyrics = {
			"Snails see the benefits",
			"The beauty in every inch",
			"Oh why, why, why, why",
			"Are you quick to kiss?",
			"Baby, maybe I spoke to soon",
			"I’ll touch you once you make the first move",
			"Snails see the benefits",
			"The beauty in every inch"};
	Image imgSnailCover;
	MenuSnail snail;

	public Menu(int state){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		imgSnailCover = new Image("res/snails_cover.png");
		snail = new MenuSnail();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		//Pretty menu stuff
		g.setColor(Color.white);
		for (int i = 0; i < snailsLyrics.length; i++){
			g.drawString(snailsLyrics[i], 50, 50+i*15);//text, x, y
		}
		g.drawImage(imgSnailCover, 45, 70+(snailsLyrics.length)*15);
		
		//Pretty moving snail menu stuff
		snail.render(gc, sbg, g);
		
		//Functional menu stuff
		g.setColor(Color.white);
		g.drawString("1 Player", 500, 400);
		g.drawString("2 Player", 500, 415);
		g.drawString("Settings", 500, 430);
		g.drawString("Exit", 500, 445);
		g.drawOval(470, 405+currentSelection*15, 10, 10);
		g.drawString("Press ENTER to start!", 500, 475);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		
		//Selector
		Input input = gc.getInput(); //All the input
		if(input.isKeyPressed(Input.KEY_UP)){
			if (currentSelection == 0){
				currentSelection = 3;
			} else {
				currentSelection--;
			}
		} else if(input.isKeyPressed(Input.KEY_DOWN)){
			if (currentSelection == 3){
				currentSelection = 0;
			} else {
				currentSelection++;
			}
		} else if(input.isKeyPressed(Input.KEY_ENTER)){
			if (currentSelection == 0){//Start player 1 mode
				sbg.enterState(3);
			} else if (currentSelection == 1){//Start player 2 mode
				sbg.enterState(2);
			} else if (currentSelection == 2){
				sbg.enterState(4);
			} else { //Exit
				gc.exit();
			}
		}
		
		//Snail
		snail.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		return 0;
	}

}
