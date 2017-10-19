/*
 * This project creates a GUI implementation
 * of the logic-based, 8 Queens of Death puzzle.
 * The main method defines as ASCII representation of a chess board
 * and passes this to the GUI class it creates.
 * The GUI class in turn creates the GUI for the game and initiates gameplay.
 */
public class MainClass {
	
	public static void main(String[] args) {
		
		
		//2D array to represent the chess board
		//0 = white, 1 = black
		int[][] board = new int[8][8];
		
		//keeps track of whether square should be black or not
		int black = 0; 
		
		
		//instantiate squares of board
		//sets whether square is black or white
		for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				
				if(black == 1){
					board[i][j]=1;
				}
				else{
					board[i][j]=0;
				}
				
				black = (black +1) %2; //alternates between 0 and 1
				
			}
			black = (black +1) %2; //new line starts on same color as last line ended on
		}
		
		//prints out board
		/*for(int i=0; i<8;i++){
			for(int j=0;j<8;j++){
				
				System.out.print(board[i][j]);
				
			}
			System.out.println();
		}*/
		
		GUI newGUI = new GUI(board);
	}

}
