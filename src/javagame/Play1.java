package javagame;

import java.util.LinkedList;
import java.util.ListIterator;

import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class Play1 extends BasicGameState{
	PlayerSnail player;
	ComputerSnail computer;
	LinkedList<Consumable> consumables;
	int consumableRespawnTimer;
	final int maxConsumables = 4;
	int gameMode;
	boolean timerOn = false;
	int endTime = 0;
	boolean pointsOn = false;
	int endPoints = 0;
	int time = 0;

	public Play1(int state){
		
	}
	
	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException {
		//Create all possible snails
		ComputerSnail[] snailAIs = {
				new CompSnailRandom(650, 300, Color.gray, null, consumables),
				new CompSnailGreedy(650, 300, Color.green, null, consumables),
				new CompSnailCautious(650, 300, Color.cyan, null, consumables)
		};
		
		//Players
		player = new PlayerSnail(null);
		computer = snailAIs[Settings.getSettings().getOpponentSelection()];
		player.opponentSnail = computer;
		computer.opponentSnail = player;
		
		//Consumables
		consumables = new LinkedList<Consumable>();
		for (int i = 0; i < 2; i++){
			addConsumable();
		}
		consumableRespawnTimer = 1;
		
		//Game mode and set up win condition
		gameMode = Settings.getSettings().getWinConditionSelection();
		
		if (gameMode < 3){ //Timer
			timerOn = true;
			if (gameMode == 0){endTime = 60;}
			else if (gameMode == 1){endTime = 120;}
			else {endTime = 300;}
		} else if (gameMode < 6){ //Points
			pointsOn = true;
			if (gameMode == 3){endPoints = 500;}
			else if (gameMode == 4){endPoints = 1000;}
			else {endPoints = 2000;}
		} else { //Special - I'M GONNA DEAL WITH THESE LATER
			if (gameMode == 6){ //Num of poop pellets
				
			} else if (gameMode == 7){ //Num of speed pellets
				
			} else { //Neverending
				//Do nothing
			}
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		computer.render(gc, sbg, g);
		g.drawString(""+computer.score, 750, 550);
		player.render(gc, sbg, g);
		g.drawString(""+player.score, 50, 550);
		
		for (int i = 0; i < consumables.size(); i++){
			Consumable c = consumables.get(i);
			g.drawImage(c.image, c.xPos, c.yPos);
		}
		
		g.setColor(Color.white);
		renderWinCondition(gc, sbg, g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		//Update snails
		player.update(gc, sbg, delta);
		computer.updateConsumables(consumables);
		computer.update(gc, sbg, delta);
		
		//Check if anything has been eaten
		ListIterator<Consumable> it = consumables.listIterator();
		while (it.hasNext()) {
	        Consumable c = it.next();
	        c.update(gc, sbg, delta);
	        if (c.isConsumed(player)){
	        	c.applyEffects(player);
	        	it.remove();
	        } else if (c.isConsumed(computer)){
	        	c.applyEffects(computer);
	        	it.remove();
	        } else if (c.time < 0){
	        	it.remove();
	        }
	    }
		
		//Respawn the pellets!
		if (consumableRespawnTimer % 1024 == 0){
			addConsumable();
			consumableRespawnTimer = 1;
		}
		consumableRespawnTimer += (int)(Math.random()*12);
		
		//Win Condition
		updateWinCondition(gc, sbg, delta);
		
	}

	@Override
	public int getID() {
		return 1;
	}
	
	public void addConsumable() throws SlickException{
		int size = consumables.size();
		if (size < maxConsumables){
			double chance = 1-0.07*(size-1);//With max size = 4, min chance is .72
			//Essentially as number of consumables increase, the less likely for a normal pellet to appear
			if (Math.random() < chance){ //Normal pellet
				consumables.add(new PelletPoint((float)(25+Math.random()*750), (float)(25+Math.random()*550)));
			} else {
				double secondChance = Math.random();
				if (secondChance > 0.5){
					consumables.add(new PelletPoop((float)(25+Math.random()*750), (float)(25+Math.random()*550)));
				} else if (secondChance > 0) {
					consumables.add(new PelletSpeed((float)(25+Math.random()*750), (float)(25+Math.random()*550)));
				}
			}
		}
	}
	
	public void updateWinCondition(GameContainer gc, StateBasedGame sbg, int delta){
		if (timerOn){
			time += delta;
			if (endTime - time/1000 <= 0){ //Game over!
				
			}
		} else if (pointsOn){
			if (player.score >= endPoints || computer.score >= endPoints){ //Game over!
				
			}
		}
	}
	
	public void renderWinCondition(GameContainer gc, StateBasedGame sbg, Graphics g){
		if (timerOn){
			g.drawString("Countdown : " + (endTime - time/1000), 325, 300);
			
		} else if (pointsOn){
			int leaderPoints;
			Color leaderColor;
			if (player.score >= computer.score){
				leaderPoints = player.score;
				leaderColor = player.trailColor;
			} else {
				leaderPoints = computer.score;
				leaderColor = computer.trailColor;
			}
			g.setColor(leaderColor);
			g.drawString("Points to goal: " + (endPoints - leaderPoints), 315, 300);
		}
	}

}
