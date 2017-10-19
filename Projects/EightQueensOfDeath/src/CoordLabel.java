

import javax.swing.JLabel;

public class CoordLabel extends JLabel {
	
	int xVal;
	int yVal;
	public CoordLabel(int x, int y){
		
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
