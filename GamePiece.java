import java.awt.*;
import javax.swing.*;


public abstract class GamePiece {
	// game coordinates upper left
	int x;
	int y;
	
	int width;
	int height;
	
	//pixels to move each time move() is called
	int velocityX;
	int velocityY;
	
	//max right position, max bottom position
	int rightBound;
	int bottomBound;
	
	public GamePiece(int x, int y, int width, int height, int velocityX, int velocityY){
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	

	public void setBounds(int width, int height){
		rightBound = width - this.width;
		bottomBound = height - this.height;
	}
	
	public void setVelocity (int velocityX, int velocityY){
		this.velocityX = velocityX;
		this.velocityY = velocityY;
	}
	
	public void move(){
		x += velocityX;
		y += velocityY;
		accelerate();
		clip();
	}
	
	public void moveUp(){
		if (velocityY < 0){
			y += velocityY;
		}
		else {
			y -= velocityY;
		}
		clip();
	}

	public void moveDown(){
		if (velocityY > 0){
			y += velocityY;
		}
		else {
			y -= velocityY;
		}
		clip();
	}
	
	public void clip(){
		if (x < 0)
			x = 0;
		else if (x > rightBound)
			x = rightBound;

		if (y < 0)
			y = 0;
		else if (y > bottomBound)
			y = bottomBound;
	}
	
	/*
	public Intersection intersects(GamePiece other) {
		if (       other.x > x + width
				|| other.y > y + height
				|| other.x + other.width  < x
				|| other.y + other.height < y)
			return Intersection.NONE;

		// compute the vector from the center of this object to the center of
		// the other
		double dx = other.x + other.width /2 - (x + width /2);
		double dy = other.y + other.height/2 - (y + height/2);

		double theta = Math.atan2(dy, dx);
		double diagTheta = Math.atan2(height, width);

		if ( -diagTheta <= theta && theta <= diagTheta )
			return Intersection.RIGHT;
		if ( diagTheta <= theta && theta <= Math.PI - diagTheta )
			return Intersection.DOWN;
		if ( Math.PI - diagTheta <= theta || theta <= diagTheta - Math.PI )
			return Intersection.LEFT;
		// if ( diagTheta - Math.PI <= theta && theta <= diagTheta)
		return Intersection.UP;
	}
	*/
	

	public abstract void accelerate();
	
	public abstract void draw(Graphics g);

}
