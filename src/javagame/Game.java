package javagame;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Game extends StateBasedGame{

	public static final String gameName = "Snail";
	public static final int menu = 0;
	public static final int play1 = 1;
	public static final int play2 = 2;
	public static final int opponentSelect = 3;
	public static final int modeSelect = 4;
	
	public Game (String gameName){
		super(gameName); //Adds the title at the top of the screen
		this.addState(new Menu(menu));
//		this.addState(new Play1(play1));
		this.addState(new Play2(play2));
		this.addState(new OpponentSelect(opponentSelect));
		this.addState(new ModeSelect(modeSelect));
	}
	
	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		//Initialize the states
		this.getState(menu).init(gc, this);
//		this.getState(play1).init(gc, this);
		this.getState(play2).init(gc, this);
		this.getState(opponentSelect).init(gc, this);
		this.getState(modeSelect).init(gc, this);
		this.enterState(menu); //Which screen to show user first
	}
	
	public static void main(String[] args) {
		AppGameContainer appgc; //The window of the game
		try {
			appgc = new AppGameContainer(new Game(gameName));
			appgc.setDisplayMode(800, 600, false);//First two args window size, third arg fullscreen
			appgc.start();
		} catch(SlickException e){
			e.printStackTrace();
		}
	}

}
