import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class Duck extends GamePiece {
	final static int WIDTH = 61;
	final static int HEIGHT = 72;
	private boolean isAlive;
	private boolean inPlay;
	private boolean shootable;
	
	public Duck(int x, int y, int velocityX, int velocityY){
		super(x, y, WIDTH, HEIGHT, velocityX, velocityY);
		isAlive = false;
		shootable = false;
		inPlay = false;
	}
	
	// Bounce off a wall
	public void accelerate() {
		if (x < 0)
			velocityX =  Math.abs(velocityX);
		else if ((x + WIDTH) > rightBound)
			velocityX = -Math.abs(velocityX);
		if (y < 0){
			velocityY =  Math.abs(velocityY);
			System.out.println("bounced off top");
		}
		else if ((y + HEIGHT) > bottomBound)
			velocityY = -Math.abs(velocityY);
	}

	@Override
	public void move(){
		x += velocityX;
		y += velocityY;
		accelerate();
		clip();
	}
	
	public void setVelocity(int x){
		velocityX = x;
		velocityY = x;
	}
	
	//Initialize a duck
	public void start(){
		isAlive = true;
		inPlay = true;
		shootable = true;
	}
	
	//Animation for the duck to die
	public void die(){
		isAlive = false;
		shootable = false;
	}
	
	public void leaveScreen(){
		inPlay = false;
	}
	
	//Animation for the duck to fly away if not shot
	public void flyAway(){
		shootable = false;
	}
	
	
	public void draw(Graphics g){
		if (isAlive){
			Picture.draw(g,"duck.png",x,y);
		}
		else{
			Picture.draw(g, "deadduck.png", x, y);
		}
	}
	
	public boolean isAlive(){
		return isAlive;
	}
	
	public boolean inPlay(){
		return inPlay;
	}
	
	public boolean isShootable(){
		return shootable;
	}
	
}
