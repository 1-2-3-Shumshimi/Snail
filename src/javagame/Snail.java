package javagame;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Snail {
	
	private int[] directions = {0, 1, 2, 3};//These correspond to top, left, down, right
	protected int currentDirection;
	protected float xPos;
	protected float yPos;
	protected double speed;
	protected int trailLength;
	private int trailCount;
	protected Map<Float[], Integer> trail;
	private Image imgUp;
	private Image imgLeft;
	private Image imgDown;
	private Image imgRight;
	protected Color trailColor;
	protected Snail opponentSnail;
	protected int score;
	
	public Snail(float initialX, float initialY, Color color, Snail opponentSnail) throws SlickException{
		this.currentDirection = directions[(int)(Math.random()*4)];
		this.xPos = initialX;
		this.yPos = initialY;
		this.speed = 0.30;
		this.trailLength = 1500;
		this.trailCount = 0;
		this.trail = new HashMap<Float[], Integer>();
		this.imgUp = new Image("res/snail_up.png");
		this.imgLeft = new Image("res/snail_left.png");
		this.imgDown = new Image("res/snail_down.png");
		this.imgRight = new Image("res/snail_right.png");
		this.trailColor = color;
		this.opponentSnail = opponentSnail;
		this.score = 0;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		//Drawing the snail
		if (currentDirection == 0){g.drawImage(imgUp, xPos, yPos);}
		else if (currentDirection == 1){g.drawImage(imgLeft, xPos, yPos);}
		else if (currentDirection == 2){g.drawImage(imgDown, xPos, yPos);}
		else {g.drawImage(imgRight, xPos, yPos);}
		
		//Drawing the trail
		for (Float[] p : trail.keySet()){
			g.setColor(trailColor);
			g.fillRect(p[0], p[1], 12, 12);
		}
	}
	
	/**
	 * Determines the direction (0:Up, 1:Left, 2:Down, 3:Right) of the snail based on
	 * its current x,y coordinate towards a reference x,y coordinate.
	 * Reference x,y coordinate treated with 0,0 on bottom left
	 * @param correctX
	 * @param correctY
	 * @param mouseX
	 * @param mouseY
	 * @return
	 */
	public int determineDirection(float correctX, float correctY, float mouseX, float mouseY){
		float x = mouseX - correctX;
		float y = mouseY - correctY;
		double angle = Math.atan2(y, x);
		if (angle > Math.PI/4 && angle <= Math.PI*(3.0/4)){return 0;}
		else if (angle > Math.PI*(3.0/4) || angle <= -Math.PI*(3.0/4)){return 1;}
		else if (angle < -Math.PI/4){return 2;}
		else{return 3;}
	}
	
	public void addTrail(float x, float y){
		if (trailCount%30 == 0){
			Float[] xyCoord = {x+16,y+16};
			trail.put(xyCoord, (Integer)trailLength);
		}
		trailCount++;
	}
	
	public void updateTrail(){
		//Have to use iterator if you want to remove stuff
		Iterator<Entry<Float[], Integer>> it = trail.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry<Float[], Integer> pair = (Map.Entry<Float[], Integer>)it.next();
	        pair.setValue(pair.getValue()-1);
	        if (pair.getValue() == 0){it.remove();}
	        // System.out.println(pair.getKey() + " = " + pair.getValue());
	        // avoids a ConcurrentModificationException
	    }
	}
	
	/**
	 * Checks if the snail is colliding with an opponent trail
	 * @param oppTrail
	 * @return
	 */
	public boolean checkCollision(Map<Float[], Integer> oppTrail) {
		boolean hasCollided = false;
		for (Float[] p : oppTrail.keySet()){
			if (Math.abs(xPos-p[0]+16) < 12 && Math.abs(yPos-p[1]+16) < 12){
				hasCollided = true;
				break;
			}
		}
		return hasCollided;
	}
	
	/**
	 * Checks if an input position is colliding with an opponent trail
	 * @param x
	 * @param y
	 * @param oppTrail
	 * @return
	 */
	public boolean checkCollision(float x, float y, Map<Float[], Integer> oppTrail){
		boolean hasCollided = false;
		for (Float[] p : oppTrail.keySet()){
			if (Math.abs(x-p[0]+16) < 12 && Math.abs(y-p[1]+16) < 12){
				hasCollided = true;
				break;
			}
		}
		return hasCollided;
	}
	
	abstract void update(GameContainer gc, StateBasedGame sbg, int delta);

}
