

import javax.swing.JButton;
import javax.swing.JLabel;

public class CoordButton extends JButton {
	
	int xVal;
	int yVal;
	boolean hasQueen; //keeps track of whether or not square is occupied
	
	public CoordButton(int x, int y){
		
		xVal =x;
		yVal =y;
		hasQueen = false;
		
	}
	
	public void addQueen(){
		hasQueen = true;
	}
	public void remQueen(){
		hasQueen = false;
	}
	public boolean hasQueen(){
		return hasQueen;
	}
	
	public int getX(){
		return xVal;
	}
	
	public int getY(){
		return yVal;
	}

}
