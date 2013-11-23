import java.awt.Graphics;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;


public class SlowDown extends GamePiece implements PowerUp {
	
	final static int WIDTH = 15;
	final static int HEIGHT = 15;
	private boolean inPlay;
	private boolean active;
	private boolean shootable;
	private Timer in;
	//private Timer effect;
	//private int inEffect = 5000;
	private int play = 6000;
	
	
	public SlowDown(int x, int y, int velocityX, int velocityY){
		super(x, y, WIDTH, HEIGHT, velocityX, velocityY);
		inPlay = false;
		active = false;
		shootable = false;
		
		/*
		effect = new Timer(inEffect, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		*/
		
		in = new Timer(play, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				inPlay = false;
				active = false;
				shootable = false;
			}
		});
	}
		
	@Override
	public void setBounds(int width, int height){
		rightBound = width - this.width;
		bottomBound = height - this.height;
	}

	@Override
	public void startEffect(Duck duck){
		duck.setVelocity(5);
	}
	
	public void endEffect(Duck duck){
		duck.setVelocity(10);
		
	}

	@Override
	public boolean inPlay() { 
		return inPlay;
	}

	@Override
	public boolean isActive() {
		// TODO Auto-generated method stub
		return active;
	}

	@Override
	public boolean shootable() {
		// TODO Auto-generated method stub
		return shootable;
	}
	
	@Override
	public void start(){
		in.start();
		inPlay = true;
		active =  false;
		shootable = true;
	}

	@Override
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
	public Point topLeft(){
		return (new Point(x,y));
	}
	
	@Override
	public void shot(){
		in.stop();
		inPlay = false;
		shootable = false;
		active = true;
	}
		

	@Override
	public void draw(Graphics g) {
		g.fillOval(x, y, WIDTH, HEIGHT);
		
	}

}
