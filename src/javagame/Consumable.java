package javagame;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public abstract class Consumable {
	
	protected float xPos;
	protected float yPos;
	protected float time;
	protected Image image;
	protected int id; //0 for points, 1 for poop, 2 for speed
	
	public Consumable(float xPos, float yPos, float time, Image image, int id){
		this.xPos = xPos;
		this.yPos = yPos;
		this.time = time;
		this.image = image;
		this.id = id;
	}
	
	public boolean isConsumed(Snail snail){
		return Math.abs(snail.xPos-xPos+16) < 12 && Math.abs(snail.yPos-yPos+16) < 12;
	}
	
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g){
		g.drawImage(image, xPos, yPos);
	}
	
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {
		this.time--;
	}
	
	abstract void applyEffects(Snail snail);

}
