import java.awt.*;


public interface PowerUp {
	
	
	public void startEffect(Duck duck);
	
	public void endEffect(Duck duck);
	
	public void start();
	
	public void shot();
	
	public void move();
	
	public void setBounds(int x, int y);
	
	public boolean inPlay();
	
	public boolean isActive();
	
	public boolean shootable();
	
	public Point topLeft();
	
	public void draw(Graphics g);
	
	
}
