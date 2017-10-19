/*
 * GUI program for a logic-based puzzle game.
 * Player must navigate 
 */
package floorTilePuzzle;

public class MainClass {

	public static void main(String[] args) {
		
		//2D array used to represent board
		//0 = clear space, 1 = used space, 9 = blocked space 
		int board[][] = new int[8][11]; 
		
		int blocked[] = {1,2,16,20,22,29,35,46,50,55,59,65,74,83,0}; //array of ints indentifying which squares should be blocked
		
		int counter = 0; //counter for squares on board
		int blockedCounter =0; //counter for array of squares to be blocked
		
		//instantiates array
		//initializes values to zero, unless square number matches next value in blocked array
		//in which case value is initiated to 9
		for(int i =0; i <8; i++){
			
			for(int j =0; j <11; j++){
				
				if(counter == blocked[blockedCounter]){ //blocked space found
					board[i][j] = 9; //set board value to 9
					blockedCounter++; //increment blockedCounter
				}
				else{ //not blocked space
					board[i][j] = 0; //set board value to zero
				}
				counter++; //increment board Counter
				
			}
		}
		/*
		//print out ASCII representation of board
		for(int i =0; i <8; i++){
			
			for(int j =0; j <11; j++){
				
				System.out.print(board[i][j]);
				
			}
			System.out.println();
		}*/
		
		Board mainBoard = new Board(11,8,board,counter);
		
		

	}

}

