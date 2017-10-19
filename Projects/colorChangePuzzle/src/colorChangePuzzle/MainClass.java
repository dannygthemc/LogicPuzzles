package colorChangePuzzle;
/*
 * A GUI game for a visual logic puzzle
 * Player needs to make all orbs the same color.
 * Clicking one orb changes its color and all orbs
 * to the North, South, East and West
 */
public class MainClass {

	public static void main(String[] args) {
		
		//2D array to represent the set of orbs
		//0 = blue, 1 = red, 2 = green
		int[][] orbs = new int[3][3];
		
		//start entire board at 0
		for(int i=0; i<3;i++){
			for(int j=0;j<3;j++){
				orbs[i][j] =0;
			}
		}
		
		orbs[2][2] = 1; //switch bottom right orb to 1;
		Board gameBoard = new Board(orbs); //create the GUI object and start the game
		

	}
	
	

}
