package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A menu for users to select which AI snail they would like to play against:
 * 0 - random (Randy)
 * 1 - greedy (Grace)
 * 2 - cautious (Cameron)
 * 3 - aggressive (not implemented yet)
 * 4 - well-rounded (not implemented yet)
 * @author Jonathan
 *
 */
public class OpponentSelect extends BasicGameState {
	
	int currentSelection = 0;
	String[][] descriptions;
	MenuSnail snail;

	public OpponentSelect(int state){

	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		descriptions = new String[3][4];
		descriptions[0][0] = "Randy the Random Snail";
		descriptions[0][1] = "He doesn't know where he is going.";
		descriptions[0][2] = "He zigs and he zags,";
		descriptions[0][3] = "All in his own little world.";
		descriptions[1][0] = "Grace the Greedy Snail";
		descriptions[1][1] = "She just wants those pellets - ";
		descriptions[1][2] = "And will stop at nothing,";
		descriptions[1][3] = "Except maybe some snail trail.";
		descriptions[2][0] = "Cameron the Cautious Snail";
		descriptions[2][1] = "Sometimes when he's not going too fast,";
		descriptions[2][2] = "He can stop dead in his tracks,";
		descriptions[2][3] = "Or rather - spaz out!";
		snail = new MenuSnail();

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		//Pretty menu stuff
		g.setColor(Color.white);
		if (currentSelection < descriptions.length){
			for (int i = 0; i < descriptions[0].length; i++){
				g.drawString(descriptions[currentSelection][i], 50, 400+i*15);//text, x, y
			}
		}
		
		snail.render(gc, sbg, g);
		
		//Functional menu stuff
		g.setColor(Color.white);
		g.drawString("Randy", 500, 400);
		g.drawString("Grace", 500, 415);
		g.drawString("Cameron", 500, 430);
		g.drawString("Go Back", 500, 445);
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
			if (currentSelection == 3){//Go back
				sbg.enterState(0);
			} else { //Go to player 1 game mode
				Settings.getSettings().setOpponentSelected(currentSelection); //sets which AI to face
				sbg.addState(new Play1(1));
				sbg.getState(1).init(gc, sbg);
				sbg.enterState(1);
			}
		}
		//Snail
		snail.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		// TODO Auto-generated method stub
		return 3;
	}

}
