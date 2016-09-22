package javagame;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * A menu for users to select which game mode they would like to play:
 * 0 - 1 minute game
 * 1 - 2 minute game
 * 2 - 5 minute game
 * 3 - first to 500
 * 4 - first to 1000
 * 5 - first to 2000
 * 6 - good bowels (certain number of poop upgrades)
 * 7 - the flash (certain number of speed upgrades)
 * 8 - never ending
 * 
 * @author Jonathan
 *
 */
public class ModeSelect extends BasicGameState {

	int categorySelection = 0;
	int subcategorySelection = 0;
	boolean selectorOnCategory = true;
	String[][] descriptions;
	String[] modes = {
		"Timed - 1 Minute",
		"Timed - 2 Minutes",
		"Timed - 5 Minutes",
		"Point Goal - 500",
		"Point Goal - 1000",
		"Point Goal - 2000",
		"Special - Fluid Overload",
		"Special - Snail to Flash",
		"Special - Neverending"
	};
	MenuSnail snail;

	public ModeSelect(int state){

	}

	@Override
	public void init(GameContainer arg0, StateBasedGame arg1)
			throws SlickException {
		descriptions = new String[3][3];
		descriptions[0][0] = "1 Minute";
		descriptions[0][1] = "2 Minute";
		descriptions[0][2] = "5 Minute";
		descriptions[1][0] = "500 Point Game";
		descriptions[1][1] = "1000 Point Game";
		descriptions[1][2] = "2000 Point Game";
		descriptions[2][0] = "Fluid Overload";
		descriptions[2][1] = "Snail to Flash";
		descriptions[2][2] = "Infinite";
		snail = new MenuSnail();

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		//Pretty menu stuff
		snail.render(gc, sbg, g);
		g.setColor(Color.white);
		g.drawString("Current mode: " + modes[Settings.getSettings().getWinConditionSelection()], 80, 370);

		//Functional menu stuff
		g.drawString("Timed", 80, 400);
		g.drawString("Point Goal", 80, 415);
		g.drawString("Special", 80, 430);
		g.drawString("Go Back", 80, 445);
		g.drawString("Press ENTER to select!", 80, 475);

		g.setColor(Color.white);
		if (categorySelection < descriptions.length){
			for (int i = 0; i < descriptions[0].length; i++){
				g.drawString(descriptions[categorySelection][i], 500, 400+i*15);//text, x, y
			}
		}

		if (selectorOnCategory){
			g.drawOval(50, 405+categorySelection*15, 10, 10);
		} else {
			g.drawOval(470, 405+subcategorySelection*15, 10, 10);
		}

	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//Selector
		Input input = gc.getInput(); //All the input
		if (selectorOnCategory){
			if(input.isKeyPressed(Input.KEY_UP)){
				if (categorySelection == 0){
					categorySelection = 3;
				} else {
					categorySelection--;
				}
			} else if(input.isKeyPressed(Input.KEY_DOWN)){
				if (categorySelection == 3){
					categorySelection = 0;
				} else {
					categorySelection++;
				}
			} else if(input.isKeyPressed(Input.KEY_ENTER)){
				if (categorySelection == 3){//Go back
					sbg.enterState(0);
				} else {
					//Do nothing - must be on a sub category!
				}
			} else if (input.isKeyPressed(Input.KEY_RIGHT)){
				selectorOnCategory = false;
				subcategorySelection = 0;
			}
		} else {
			if(input.isKeyPressed(Input.KEY_UP)){
				if (subcategorySelection == 0){
					subcategorySelection = 2;
				} else {
					subcategorySelection--;
				}
			} else if(input.isKeyPressed(Input.KEY_DOWN)){
				if (subcategorySelection == 2){
					subcategorySelection = 0;
				} else {
					subcategorySelection++;
				}
			} else if(input.isKeyPressed(Input.KEY_ENTER)){
				//Change win condition settings
				Settings.getSettings().setWinConditionSelected(categorySelection*3+subcategorySelection);
			} else if(input.isKeyPressed(Input.KEY_LEFT)){
				selectorOnCategory = true;
			}
		}

		//Snail
		snail.update(gc, sbg, delta);
	}

	@Override
	public int getID() {
		return 4;
	}

}
