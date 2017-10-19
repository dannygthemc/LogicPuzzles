/*
 * class defines the GUI for the game
 */
package floorTilePuzzle;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;

public class Board {
	
	private JFrame window; //frame on which board is displayed
	private JPanel content; //panel used to organize board layout
	private JLabel[] tiles; //array of labels representing board tiles
	private JLabel player; //label representing player's pawn
	private int playerLoc; //where player is on board
	private int[][] backBoard; //2D array int representation of board
	private int[][] backBoardBackup; //backup representation used for reset, never edited
	private int tot; //holds total number of tiles on board
	private int xVal; //holds number of columns
	private int yVal; //holds number of rows
	
	/*
	 * constructor for board.
	 * takes in size of board, number of blocked tiles,
	 * number of total tiles
	 * and a 2D array representing the board.
	 * builds GUI representation of board from provided info
	 * @param int x number of columns
	 * @param int y number of rows
	 * @param int[][] board ASCII repreentation of board
	 * @param int total total number of tiles
	 */
	public Board(int x, int y, int[][] board, int total){
		
		
		backBoardBackup = board;
		
		
		xVal =x;
		yVal =y;
		
		tot = total; //gets total number of tiles
		
		//creates button to reset the game on click
		JButton resetButt = new JButton("Reset");
		
		resetButt.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		          setBoard(xVal,yVal); //reset the board
		    }
		});
		
		//creates a menuBar
		//adds a file menu to the menuBar
		//adds the reset button to the file menu
		JMenuBar menuBar = new JMenuBar();
		JMenu menu = new JMenu("File");
		menu.setMnemonic(KeyEvent.VK_A);
		menu.add(resetButt);
		menuBar.add(menu);
				
		//Set up Window
		window = new JFrame("Board");
		window.setAlwaysOnTop(true);
		window.setJMenuBar(menuBar);
		
		//add keyListener to respond to arrowkey actions
		window.addKeyListener(new KeyAdapter() {
		    public void keyPressed(KeyEvent e) {
		        int key = e.getKeyCode();
		        if (key == KeyEvent.VK_LEFT) {
		        	moveLeft();
		        }
		        if (key == KeyEvent.VK_RIGHT) {
		        	moveRight();
		        }
		        if (key == KeyEvent.VK_UP) {
		        	moveUp();
		        }
		        if (key == KeyEvent.VK_DOWN) {
		        	moveDown();
		        }
				        
		       boolean winCheck = checkWin();
		        if(winCheck == true){
		        	JOptionPane.showMessageDialog(null, "Congratulations, you win! \n"
		        			+ "The Password is: 'Albatross.'");
		        }
		    }
		});
		
		setBoard(x,y); //calls method to set up the board
		
	}
	/*
	 * function to set/reset the Board
	 */
	public void setBoard(int x, int y){
		
		//define panel to organize board
		content = new JPanel();
		content.setLayout(new GridLayout(8,11));
				
		tiles = new JLabel[tot]; //create array to hold board tiles
		int tileCounter =0; //keeps track of tiles array
		
		backBoard = new int[y][x];
		
		/*
		 * copies the variables over from the backup board
		 * used to reset the backend board
		 */
		for(int i = 0; i <y; i++){
			for(int j =0; j< x; j++){
				
				backBoard[i][j] = backBoardBackup[i][j];
				
			}
		}
		
		
		//defines image for player pawn
		URL url = Board.class.getResource("pawn.png");
		ImageIcon pawn = new ImageIcon(url);
		Image pn = pawn.getImage(); // transform it 
		Image newpn = pn.getScaledInstance(41, 43,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		pawn = new ImageIcon(newpn);  // transform it back
				
		//define JLabel for player pawn
		player = new JLabel();
		player.setIcon(pawn);
		player.setHorizontalAlignment(SwingConstants.CENTER);
		player.setVerticalAlignment(SwingConstants.CENTER);
				
		playerLoc = 0;
				
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2); //defines border for squares
				
				
				
		//define tiles that correspond to board layout passed to constructor
		//add them to the panel in order
		for(int i = 0; i <y; i++){
			for(int j =0; j< x; j++){
						
				//if board has a zero at this loc
				//creat a blank tile and add it
				if(backBoard[i][j] == 0){
							
					tiles[tileCounter] = new JLabel();
					tiles[tileCounter].setSize(15,15);
					tiles[tileCounter].setBackground(Color.WHITE);
					tiles[tileCounter].setBorder(border2);
					tiles[tileCounter].setOpaque(true);
					tiles[tileCounter].setVerticalAlignment(SwingConstants.TOP);
					tiles[tileCounter].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
					content.add(tiles[tileCounter]);
					tileCounter++;
							
				}
				else{ //otherwise, create and add a blocked tile
							
					tiles[tileCounter] = new JLabel();
					tiles[tileCounter].setSize(15,15);
					tiles[tileCounter].setBackground(Color.BLACK);
					tiles[tileCounter].setBorder(border2);
					tiles[tileCounter].setOpaque(true);
					tiles[tileCounter].setVerticalAlignment(SwingConstants.TOP);
					tiles[tileCounter].setLayout(new FlowLayout(FlowLayout.LEFT, 2, 2));
					content.add(tiles[tileCounter]);
					tileCounter++;
				
				}
						
			}
		}
				
				
		//sets up the window
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.toBack();
				
		window.setSize(600, 600);
		window.setLocation(100, 100);
				
		
		makeVisible();//makes window visible
		addPlayer(); //adds player to the board
				
	}
	
	
	/*
	 * show board
	 */
	public void makeVisible(){
		window.setVisible(true);
	}
	
	public void addPlayer(){
		
		tiles[playerLoc].remove(player);
		tiles[playerLoc].revalidate();
		tiles[playerLoc].repaint();
		tiles[5].setBackground(Color.RED);
		tiles[5].add(player);
		tiles[5].revalidate();
		tiles[5].repaint();
		playerLoc = 5;
		updateBackBoard(5);
	}
	
	/*
	 * if player has a valid move to the right
	 * moves player, updates board
	 * otherwise, does nothing
	 */
	public void moveRight(){
		
		if(((playerLoc + 1) %11) != 0 && isTileFree(playerLoc + 1)){
			
			updateBoard(playerLoc,playerLoc + 1); //update GUI
			updateBackBoard(playerLoc+1); //update backend
			playerLoc = playerLoc+1;  //update playerLoc
			
		}
		
	}
	
	/*
	 * if player has a valid move to the left
	 * moves player, updates board
	 * otherwise, does nothing
	 */
	public void moveLeft(){
		
		if((playerLoc % 11) != 0 && isTileFree(playerLoc - 1)){
			
			updateBoard(playerLoc,playerLoc - 1); //update GUI
			updateBackBoard(playerLoc-1); //update backend
			playerLoc = playerLoc-1;  //update playerLoc
			
		}
		
	}
	
	/*
	 * if player has a valid move to the north
	 * moves player, updates board
	 * otherwise, does nothing
	 */
	public void moveUp(){
		
		if(playerLoc - 11 >= 0 && isTileFree(playerLoc - 11)){
			
			updateBoard(playerLoc,playerLoc - 11); //update GUI
			updateBackBoard(playerLoc-11); //update backend
			playerLoc = playerLoc-11;  //update playerLoc
			
		}
		
	}
	
	/*
	 * if player has a valid move to the south
	 * moves player, updates board
	 * otherwise, does nothing
	 */
	public void moveDown(){
		
		if(playerLoc + 11 < tot && isTileFree(playerLoc + 11)){
			
			updateBoard(playerLoc,playerLoc + 11); //update GUI
			updateBackBoard(playerLoc+11); //update backend
			playerLoc = playerLoc+11;  //update playerLoc
			
		}
		
	}
	
	/*
	 * updates the board tiles and pawn loc in response
	 * to a movement
	 */
	public void updateBoard(int oldLoc, int newLoc){
		
		//remove pawn from old space
		tiles[oldLoc].remove(player);
		tiles[oldLoc].revalidate();
		tiles[oldLoc].repaint();
		//add it to new
		tiles[newLoc].add(player);
		tiles[newLoc].revalidate();
		tiles[newLoc].repaint();
		
		//update old space
		tiles[newLoc].setBackground(Color.RED);
		
	}
	
	/*
	 * updates the symbol on the 2D array in response
	 * to a movement
	 */
	public void updateBackBoard(int tileLoc){
		
		int counter = 0; //tracks number of tiles checked
		
		//loop through all squares on the board
		for(int i =0; i<8; i++){
			for(int j =0; j<11; j++){
				
				if(counter == tileLoc){ //if on square being checked for
					
					backBoard[i][j] = 1;
					
				}
				counter++;
			}
		}
		
	}
	/*
	 * checks to see if the tile at the specified location
	 * is blocked or passed.
	 * if yes, return false.
	 * otherwise, return true.
	 * @param int x square to be checked
	 */
	public boolean isTileFree(int x){
		
		int counter = 0; //tracks number of tiles checked
		boolean val = true; //return value
		
		//loop through all squares on the board
		for(int i =0; i<8; i++){
			for(int j =0; j<11; j++){
				
				if(counter == x){ //if on square being checked for
					
					if(backBoard[i][j]== 9 ||backBoard[i][j]== 1 ){ //check if square is blocked or passed
						val = false; //if yes, return false
					}
					
				}
				counter++;
			}
		}
		
		
	return val;	
	}
	
	/*
	 * see if win condition is met.
	 * i.e. if all free squares have been passed.
	 * if so, return true
	 */
	public boolean checkWin(){
		
		boolean val = true;
		
		//loop through all squares on the board
		for(int i =0; i<8; i++){
			for(int j =0; j<11; j++){
				
				if(backBoard[i][j]== 0){ //if any square on the board is still clear
					val = false; //return false
				}
				//otherwise, game is won, return true
			}
		}
		
		return val;
	}

}
