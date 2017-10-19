package colorChangePuzzle;
/*
 * Creates and Displays the GUI for the game.
 * If player gets all colors the same, informs 
 * them of their victory and ends the game 
 */
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.*;
import javax.swing.border.Border;


public class Board {
	
	int[][] board; //ASCII representation of board
	JFrame window; //frame to contain board
	JPanel content; //panel used to organize board layout
	CoordButton buttons[][]; //buttons for board
	JLabel labels[][]; //labels for panels
	
	ImageIcon Red;
	ImageIcon Green;
	ImageIcon Blue;
	
	/*
	 * defines an action listener for buttons
	 * calls updateBoard method to update button and its adjacent buttons
	 */
	private ActionListener buttonActions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	
            CoordButton button = (CoordButton) ae.getSource(); //find out which button initiated the action
            updateBoard(button.getX(),button.getY()); //update the board
            boolean wins = winCheck(); //check if player has won
            if(wins == true){
            	gameWon();
            	
            }
            
            
        }
    };
	
	@SuppressWarnings("serial")
	public Board(int[][]orbs){
		
		board = orbs;
		labels = new JLabel[3][3];
		
		//set up windows
		window = new JFrame("Board");
		window.setAlwaysOnTop(true);
		
		//set up panel
		content = new JPanel();
		content.setLayout(new GridLayout(3,3));
		
		
		//define images for buttons
		URL url = Board.class.getResource("blueButt.png");
		ImageIcon blue = new ImageIcon(url);
		Image bl = blue.getImage(); // transform it 
		Image newBlue = bl.getScaledInstance(170, 170,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		Blue = new ImageIcon(newBlue);  // transform it back
		
		URL url2 = Board.class.getResource("redButt.png");
		ImageIcon red = new ImageIcon(url2);
		Image rd = red.getImage(); // transform it 
		Image newRed = rd.getScaledInstance(170, 170,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		Red = new ImageIcon(newRed);  // transform it back
		
		URL url3 = Board.class.getResource("greenButt.png");
		ImageIcon green = new ImageIcon(url3);
		Image grn = green.getImage(); // transform it 
		Image newGreen = grn.getScaledInstance(170, 170,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		Green = new ImageIcon(newGreen);  // transform it back
		
		
		buttons = new CoordButton[3][3];
		
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 2); //defines border for squares
		
		//instantiate buttons and labels
		//add them to the content pane
		//start all buttons as blue
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				
				buttons[i][j] = new CoordButton(j,i);
				buttons[i][j].setIcon(Blue);
				buttons[i][j].addActionListener(buttonActions);
				buttons[i][j].setBorder(BorderFactory.createEmptyBorder());
				buttons[i][j].setContentAreaFilled(false);
				buttons[i][j].setFocusable(false);
				
				labels[i][j] = new JLabel();
				labels[i][j].setSize(50,50);
				labels[i][j].setBackground(Color.WHITE);
				labels[i][j].setBorder(border2);
				labels[i][j].setOpaque(true);
				labels[i][j].setLayout(new GridLayout(1,1));
				
				labels[i][j].add(buttons[i][j]);
				
				content.add(labels[i][j]);
			}
		}
		
		//creates button to reset the game on click
		JButton resetButt = new JButton("Reset");
		
		resetButt.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent evt) {
		          resetBoard();
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
		//set up and display window
		window.setJMenuBar(menuBar);
		window.setContentPane(content);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.toBack();
				
		window.setSize(600, 600);
		window.setLocation(100, 100);
		window.setVisible(true);
		
		buttons[2][2].setIcon(Red); //start bottom right button as red
	}
	
	/*
	 * resets the board to its initial setup
	 */
	public void resetBoard(){
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				buttons[i][j].setIcon(Blue);
				board[i][j] = 0;
			}
		}
		buttons[2][2].setIcon(Red); //start bottom right button as red
		
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				
				buttons[i][j].removeActionListener(buttonActions);
			}
		}
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				
				buttons[i][j].addActionListener(buttonActions);
			}
		}
	}
	/*
	 * method to inform player they have won,
	 * give them the password,
	 * and remove action listeners so player can't continue to play
	 * until they restart
	 */
	public void gameWon(){
		
		//inform player of their victory
		JOptionPane.showMessageDialog(null, "You Win! \n"
				+ "The Password is: Red Herring");
		
		//remove action listeners so player can't keep playing
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				
				buttons[i][j].removeActionListener(buttonActions);
			}
		}
	}
	
	/*
	 *takes in x and y value of button clicked
	 *button clicked and all adjacent buttons change color
	 *board is updated accordingly 
	 */
	public void updateBoard(int x, int y){
		
		nextColor(x,y);
		
		//checks for adjacent ordbs to the North, South, East and West
		//if adjacent is there, update it as well
		if(x - 1 >=0){ //west
			nextColor(x-1,y);
		}
		if(y - 1 >=0){ //north
			nextColor(x,y-1);
		}
		if(x + 1 <3){ //east
			nextColor(x+1,y);
		}
		if(y + 1 <3){ //south
			nextColor(x,y+1);
		}
		
	}
	
	/*
	 * takes in coordinates of a button
	 * updates ImageIcon of button
	 * if Blue becomes Red, 0 becomes 1
	 * if Red, becomes Green, 1 becomes 2
	 * if Green, becomes Blue, 2 becomes 0
	 */
	public void nextColor(int x, int y){
		
		ImageIcon temp = (ImageIcon) buttons[y][x].getIcon();
		
		if(temp == Blue){
			buttons[y][x].setIcon(Red);
			board[y][x] = 1;
		}
		if(temp == Red){
			buttons[y][x].setIcon(Green);
			board[y][x] = 2;
		}
		if(temp == Green){
			buttons[y][x].setIcon(Blue);
			board[y][x] = 0;
		}
		
	}
	
	/*
	 *checks if all orbs are now the same color
	 *if so returns true
	 *if any are different, returns false
	 */
	public boolean winCheck(){
		
		int temp = board[0][0]; //get first symbolb
		boolean returnVal =true;
		
		//if any square has a different symbol, return false
		for(int i=0;i<3;i++){
			for(int j=0;j<3;j++){
				
				if(board[i][j] != temp){
					returnVal = false;
				}
				
			}
		}
		
		return returnVal;
		
	}
	
	

}
