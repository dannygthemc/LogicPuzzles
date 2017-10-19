/*
 * represents a square on the board
 * has x,y value representing coordinate on board
 * can return and adjust these values
 */
package floorTilePuzzle;

public class Square {
	
	public int x; //holds x coordinate
	public int y; //holds y coordinate
	
	public Square(int xVal, int yVal){
		
		x = xVal;
		y = yVal;
		
	}
	
	public int getX(){
		return x;
	}
	
	public int getY(){
		return y;
	}

}
