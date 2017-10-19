package colorChangePuzzle;

import javax.swing.JButton;

public class CoordButton extends JButton {
	
	int xVal;
	int yVal;
	public CoordButton(int x, int y){
		
		xVal =x;
		yVal =y;
		
	}
	
	public int getX(){
		return xVal;
	}
	
	public int getY(){
		return yVal;
	}

}
