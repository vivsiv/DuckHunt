
public class Gun {
	
	private int numBullets;
	private boolean loaded;
	
	public Gun() {
		loaded = true;
		numBullets = 3;
	}
	
	public int getNumBullets(){
		return numBullets;
	}
	
	public void reload(){
		if (loaded != true){
			loaded = true;
			numBullets = 3;
		}
		
	}
	
	public void shoot(){
		
		numBullets--;
	}

}
