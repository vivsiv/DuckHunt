import java.awt.*;

import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;



@SuppressWarnings("serial")
public class DuckRange extends JPanel {
	private Duck currDuck;
	private Duck currDuck2;
	
	private ArrayList<Duck> ducks;
	private int index;
	private ArrayList<PowerUp> powerUps;
	private PowerUp currPowerUp;
	private int powerUpIndex;
	private int bullets;
	private int hits;
	private int score;
	private int interval = 35; // Milliseconds between updates.
	private Timer timer;
	private int shotTime = 10000; // 10 seconds
	private Timer shotClock;
	private Timer wait;
	private int waitTime = 2000; // 2 secs
	private Timer powerUp;
	private int powerUpTime = 15000;
	private Point shotSpot;
	private boolean drawFlyAway;
	private boolean gameOver;
	private boolean shotAnimation;
	private boolean twoDucks;

	final int RANGEWIDTH = 750;
	final int RANGEHEIGHT = 500;

	public DuckRange() {
		setPreferredSize(new Dimension(RANGEWIDTH, RANGEHEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		setCursor(new Cursor(1));
		setFocusable(true);
		ducks = new ArrayList<Duck>(10);
		powerUps = new ArrayList<PowerUp>(2);
		drawFlyAway = false;
		twoDucks = false;
		shotAnimation = false;
		gameOver = false;
		

		//Set the timer to fire tick every 35 ms
		timer = new Timer(interval, new ActionListener() {
			public void actionPerformed(ActionEvent e) { tick(); }});
		//Start the timer
		timer.start();

		shotClock = new Timer(shotTime, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				currDuck.flyAway();
				if (twoDucks){
					currDuck2.flyAway();
				}
			}
		});
		shotClock.start();


		wait = new Timer(waitTime, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pull();
			}
		});
		
		powerUp = new Timer(powerUpTime, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				releasePowerUp();
			}
		});


		addMouseListener(new MouseAdapter() {
			// Check if a shot hits the duck
			public void mouseClicked(MouseEvent e) {
				shotSpot = e.getPoint();
				if (twoDucks){
					if (hit(shotSpot,currDuck)){	
						if (currDuck.isShootable()){
							currDuck.die();
							bullets--;
							hits++;
							score += 100;
						}
					}
					else if (hit(shotSpot,currDuck2)){
						System.out.println("hi");
						if (currDuck2.isShootable()){
							currDuck2.die();
							System.out.println(currDuck2.isAlive());
							bullets--;
							hits++;
							score += 100;
						}
					}
					else if (hitPowerUp(shotSpot,currPowerUp)){
						currPowerUp.startEffect(currDuck);
						currPowerUp.startEffect(currDuck2);
						currPowerUp.shot();
						}
					else {
						if (bullets > 0){
						bullets--;
						}
					}
					if (bullets == 0){
						currDuck.flyAway();
						currDuck2.flyAway();
					}
				}
				else{
					if (currDuck.isShootable()){	
						if (hit(shotSpot,currDuck)){
							currDuck.die();
							bullets--;
							hits++;
							score += 100;
						}
						else if (hitPowerUp(shotSpot,currPowerUp)){
							currPowerUp.startEffect(currDuck);
							currPowerUp.shot();
							}
						else {
							bullets--;
						}
						if (bullets == 0){
							currDuck.flyAway();
						}
					}
				}
			}
		}); 

		/*
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseMoved(MouseEvent e){
				location = e.getPoint();
			}
		});
		 */
	}



	public void newGame(){
		hits = 0;
		score = 0;
		index = 0;
		if(twoDucks){
			for(int i = 0; i < 10/2; i++){
				ducks.add(2*i,new Duck(400,325,10,10));
				ducks.add((2*i)+1,new Duck(0,325,10,10));
			}
			currDuck = ducks.get(2*index);
			currDuck2 = ducks.get(2*index+1);
		}
		else{
			for(int i = 0; i < 10; i++){
				ducks.add(i,new Duck(400,325,10,10));
			}

			currDuck = ducks.get(index);
		}
		powerUpIndex = 0;
		for(int i = 0; i < 2; i++){
			powerUps.add(i,new SlowDown(200,0,1,1));
		}
		currPowerUp = powerUps.get(powerUpIndex);
		wait.start();
		powerUp.start();
		grabFocus();
	}

	//Release a duck
	public void pull(){
		if (!twoDucks){
			if (index < 10){
				currDuck = ducks.get(index);
				bullets = 3;
				currDuck.start();
				shotClock.restart();
				index++;
				wait.stop();
			}
			else{
				gameOver = true;
				currDuck.leaveScreen();
			}
		}

		else{
			if (index < 5){
				currDuck = ducks.get(2*index);
				currDuck2 = ducks.get((2*index)+1);
				bullets = 3;
				currDuck.start();
				currDuck2.start();
				shotClock.restart();
				index++;
				wait.stop();
			}
			else{
				gameOver = true;
				currDuck.leaveScreen();
				currDuck2.leaveScreen();
			}
		}
	}
	
	public void releasePowerUp(){
		if(powerUpIndex < 2){
			currPowerUp = powerUps.get(powerUpIndex);
			currPowerUp.start();
			powerUpIndex++;
		}
	}


	public boolean pause(){
		if(timer.isRunning()){
			timer.stop();
			shotClock.stop();
			return true;
		}
		else{
			timer.start();
			shotClock.start();
			return false;
		}
	}
		
	


	public boolean twoDucks(){
		return twoDucks;
	}

	public void setToOneDuck(){
		twoDucks = false;
	}

	public void setToTwoDucks(){
		twoDucks = true;
	}
	
	public Duck currDuck(){
		return currDuck;
	}

	public Duck currDuck2(){
		return currDuck2;
	}
	



	//has curr duck been shot
	public boolean hit(Point point, Duck duck){
		return (duck.x <= point.x && point.x <= (duck.x + 90) &&
				duck.y <= point.y && point.y <= (duck.x + 90));
	}
	
	public boolean hitPowerUp(Point point, PowerUp p){
		return (p.topLeft().x <= point.x && point.x <= (p.topLeft().x + 15) &&
				p.topLeft().y <= point.y && point.y <= (p.topLeft().y + 15));
	}

	public int getDucksLeft(){
		return (10 - index);
	}

	public int getHits(){
		return hits;
	}

	public int getScore(){
		return score;
	}

	public int getNumBullets(){
		return bullets;
	}

	public int rangeHeight(){
		return getHeight() - 100;
	}



	void tick() {
		// If duck is inPlay
		if (currDuck.inPlay()){
			//If Duck is inPlay and Alive and
			if (currDuck.isAlive()){
				currDuck.setBounds(getWidth(), getHeight());
				//If Duck is inPlay, Alive and Shootable -> Should move normally
				if (currDuck.isShootable()){
					currDuck.move();
					repaint(); // Repaint indirectly calls paintComponent.	
				}
				//If Duck is in play, alive, and NOT shootable -> Should leave screen to the top
				else {
					drawFlyAway = true;
					currDuck.moveUp();
					currDuck.setBounds(getWidth(),getHeight());
					if (currDuck.y == 0){
						drawFlyAway = false;
						currDuck.leaveScreen();
					}
					repaint();
				}
			}
			// If Duck is inPlay and Dead and
			else {
				currDuck.setBounds(getWidth(),getHeight());
				// If Duck is inPlay, Dead, and Shootable -> This shouldn't happen
				if (currDuck.isShootable()){
				}
				//If Duck is inPlay, Dead, and NOT Shootable -> Should leave screen to the bottom
				else {
					currDuck.moveDown();
					currDuck.setBounds(getWidth(),getHeight());
					if (currDuck.y == currDuck.bottomBound){
						currDuck.leaveScreen();
					}
					repaint();
				}
			}
		}
		//If duck is NOT in play
		else {
			// If Duck is NOT inPlay and Alive and
			if (currDuck.isAlive()){
				currDuck.setBounds(getWidth(), getHeight());
				//If Duck is NOT inPLay and Alive and Shootable -> Should never happen
				if (currDuck.isShootable()){
				}
				//If Duck is NOT inPlay and Alive and NOT Shootable -> Pull
				else{
					if(twoDucks){
						if (!currDuck2.inPlay()){
							wait.start();
							currDuck.setBounds(getWidth(), getHeight());
							repaint();
						}
					}
					else{
						wait.start();
						currDuck.setBounds(getWidth(), getHeight());
						repaint();
					}
				}
			}
			//If Duck is NOT inPlay and Dead and
			else{
				currDuck.setBounds(getWidth(), getHeight());
				//IF duck is NOT inPlay and Dead and Shootable -> Should never happen
				if (currDuck.isShootable()){
				}
				//If duck is NOT inPlay and Dead and Not Shootable -> Pull
				else{
					if(twoDucks){
						if (!currDuck2.inPlay()){
							wait.start();
							currDuck.setBounds(getWidth(), getHeight());
							repaint();
						}
					}
					else{
						wait.start();
						currDuck.setBounds(getWidth(), getHeight());
						repaint();
					}
				}
			}
		}
		
		if(currPowerUp.inPlay()){
			currPowerUp.setBounds(getWidth(), getHeight());
			currPowerUp.move();
			repaint();
		}
		else{
			repaint();
		}

		if(twoDucks){
			// If duck is inPlay
			if (currDuck2.inPlay()){
				//If Duck is inPlay and Alive and
				if (currDuck2.isAlive()){
					currDuck2.setBounds(getWidth(), getHeight());
					//If Duck is inPlay, Alive and Shootable -> Should move normally
					if (currDuck2.isShootable()){
						currDuck2.move();
						System.out.println(currDuck2.x);
						repaint(); // Repaint indirectly calls paintComponent.	
					}
					//If Duck is in play, alive, and NOT shootable -> Should leave screen to the top
					else {
						currDuck2.moveUp();
						currDuck2.setBounds(getWidth(),getHeight());
						if (currDuck2.y == 0){
							currDuck2.leaveScreen();
						}
						repaint();
					}
				}
				// If Duck is inPlay and Dead and
				else {
					currDuck2.setBounds(getWidth(),getHeight());
					// If Duck is inPlay, Dead, and Shootable -> This shouldn't happen
					if (currDuck2.isShootable()){
					}
					//If Duck is inPlay, Dead, and NOT Shootable -> Should leave screen to the bottom
					else {
						currDuck2.moveDown();
						currDuck2.setBounds(getWidth(),getHeight());
						if (currDuck2.y == currDuck.bottomBound){
							currDuck2.leaveScreen();
						}
						repaint();
					}
				}
			}
			//If duck is NOT in play
			else {
				// If Duck is NOT inPlay and Alive and
				if (currDuck2.isAlive()){
					currDuck2.setBounds(getWidth(), getHeight());
					//If Duck is NOT inPLay and Alive and Shootable -> Should never happen
					if (currDuck2.isShootable()){
					}
					//If Duck is NOT inPlay and Alive and NOT Shootable -> Pull
					else{
						//wait.start();
						currDuck2.setBounds(getWidth(), getHeight());
						repaint();
					}
				}
				//If Duck is NOT inPlay and Dead and
				else{
					currDuck2.setBounds(getWidth(), getHeight());
					//IF duck is NOT inPlay and Dead and Shootable -> Should never happen
					if (currDuck2.isShootable()){
					}
					//If duck is NOT inPlay and Dead and Not Shootable -> Pull
					else{
						//wait.start();
						currDuck2.setBounds(getWidth(), getHeight());
						repaint();
					}
				}
			}
		}
	}


	public void paintComponent(Graphics g){
		super.paintComponent(g);
		//Picture.draw(g,"round1.png",290,200);
		if (shotAnimation){
			g.fillRect(0, 0, RANGEWIDTH, RANGEHEIGHT);
			g.clearRect(0, 0, RANGEWIDTH,RANGEHEIGHT);
		}
		Picture.draw(g,"background2.png",0,0);
		if (drawFlyAway){
			Picture.draw(g,"flyaway.png", 290, 200);
		}
		if(currDuck.inPlay()){
			currDuck.draw(g);
		}
		if (twoDucks){
			if(currDuck2.inPlay()){
				currDuck2.draw(g);
			}
		}
		if (currPowerUp.inPlay()){
			currPowerUp.draw(g);
		}
		g.drawString("Bullets: " + getNumBullets(),10,490);
		g.drawString("Ducks Left: " + getDucksLeft(), 80,490);
		g.drawString("Hits: " + getHits(), 180, 490);
		g.drawString("Score: " + getScore(), 240, 490);
		if (gameOver){
			Picture.draw(g, "GameOver.png", 290, 200);
		}
	}


}
