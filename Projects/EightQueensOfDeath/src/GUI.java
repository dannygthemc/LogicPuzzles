import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;


public class GUI {
	
	JFrame window; //window to hold content
	JPanel content; //panel to organize content
	CoordButton[][] squares; //buttons to represent tiles on board
	
	int[][] board; //holds ASCII version of board
	JLabel[][] queens; //array of queens for each spot
	int queenCounter; //keeps track of how many queens are currently on the board
	
	
	/*
	 * defines an action listener for buttons
	 * calls updateBoard method to update button and its adjacent buttons
	 */
	private ActionListener buttonActions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	
            CoordButton button = (CoordButton) ae.getSource(); //find out which button initiated the action
            updateBoard(button.getX(),button.getY()); //update the board
            if(queenCounter == 8){ //if 8 queens on board, player wins
            	gameWon();
            }
            
            
        }
	};
	
	
	//inform user of their victory
	//remove action listeners so user can't play anymore
	public void gameWon(){
		
		//inform player of their victory
		JOptionPane.showMessageDialog(null, "You Win! \n"
				+ "The Password is: Obtuse");
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				squares[i][j].removeActionListener(buttonActions);
			}
		}
			
	}
	
	/*
	 * takes in x and y coords of a button pushed.
	 * adds or removes queen to corresponding spot.
	 * if added, checks horizontals/verticals/diagonals of current square
	 * and removes any other queens along the path.
	 */
	public void updateBoard(int x, int y){
		
		//if already has queen
		if(squares[y][x].hasQueen){
			remQueen(x,y); //remove queen from board
		}
		else{//otherwise
			addQueen(x,y); //add queen to board
			adjustBoard(x,y); //remove any queens if necessary 
		}
	}
	
	/*
	 * when a queen is added,
	 * removes all other queens within new queens's attack line
	 */
	public void adjustBoard(int x, int y){
		
		//check to the north
		for(int i=y-1; i>=0; i--){
			
			if(squares[i][x].hasQueen){
				remQueen(x,i);
			}
		}
		//check to the south
		for(int i=y+1; i<8; i++){
					
			if(squares[i][x].hasQueen){
				remQueen(x,i);
			}
		}
		//check to the east
		for(int i=x+1; i<8; i++){
			
			if(squares[y][i].hasQueen){
				remQueen(i,y);
			}
		}
		//check to the west
		for(int i=x-1; i>=0; i--){
			
			if(squares[y][i].hasQueen){
				remQueen(i,y);
			}
		}
		//check to the north-west
		int yVal = y-1;
		int xVal = x-1;
		while(yVal >=0 && xVal >=0){
			
			if(squares[yVal][xVal].hasQueen){
				remQueen(xVal,yVal);
			}
			yVal--;
			xVal--;
		}
		//check to the north-east
		int yVal2 = y-1;
		int xVal2 = x+1;
		while(yVal2 >=0 && xVal2 <8){
			
			if(squares[yVal2][xVal2].hasQueen){
				remQueen(xVal2,yVal2);
			}
			yVal2--;
			xVal2++;
		}
		//check to the south-east
		int yVal3 = y+1;
		int xVal3 = x+1;
		while(yVal3 <8 && xVal3 <8){
			
			if(squares[yVal3][xVal3].hasQueen){
				remQueen(xVal3,yVal3);
			}
			yVal3++;
			xVal3++;
		}
		//check to the south-west
		int yVal4 = y+1;
		int xVal4 = x-1;
		while(yVal4 <8 && xVal4 >=0){
			
			if(squares[yVal4][xVal4].hasQueen){
				remQueen(xVal4,yVal4);
			}
			yVal4++;
			xVal4--;
		}

	}
	
	/*
	 * adds queen to desired point on the board
	 * update counter accordingly
	 */
	public void addQueen(int x, int y){
		
		squares[y][x].add(queens[y][x]);
		squares[y][x].addQueen();
		queenCounter++;
		
	}
	
	/*
	 * removes queen from desired point on the board
	 * update counter accordingly
	 */
	public void remQueen(int x, int y){
		
		squares[y][x].remove(queens[y][x]);
		squares[y][x].remQueen();
		squares[y][x].repaint();
		squares[y][x].validate();
		queenCounter--;
		
	}
	
	/*
	 * removes all queens from the board.
	 * resets all actionListeners.
	 * and gives player a fresh start
	 */
	public void resetBoard(){
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				if(squares[i][j].hasQueen){
					remQueen(j,i);
				}
				
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				squares[i][j].removeActionListener(buttonActions);
			}
		}
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				squares[i][j].addActionListener(buttonActions);
			}
		}
	}
	
	/*
	 * initiates and displays the GUI.
	 * Starts the game.
	 * 
	 */
	public GUI(int[][] backBoard){
		
		//define window
		window = new JFrame();
		window.setAlwaysOnTop(true);
		
		//create panel with grid layout
		content = new JPanel();
		content.setLayout(new GridLayout(8,8));
		
		//defines 2D array of buttons to represent board squares
		squares = new CoordButton[8][8];
		
		//takes in ASCII board from main method
		board = backBoard;
		//defines 2D array of labels to function as  a queen to place at each tile
		queens = new JLabel[8][8];
		queenCounter =0;
		
		//define image for Queens
		URL urlq = GUI.class.getResource("Queen.png");
		ImageIcon queen = new ImageIcon(urlq);
		Image qn = queen.getImage(); // transform it 
		Image newQueen = qn.getScaledInstance(90, 90,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		queen = new ImageIcon(newQueen);  // transform it back
		
		/*
		 * instantiate a queen for each square on the board
		 */
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				queens[i][j] = new JLabel(queen);
				queens[i][j].setSize(100,100);
				queens[i][j].setBorder(BorderFactory.createEmptyBorder());
				queens[i][j].setFocusable(false);
				queens[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				queens[i][j].setVerticalAlignment(SwingConstants.CENTER);
			}
			
		}
		
		
		//define images for buttons
		URL url = GUI.class.getResource("whiteSquare2.png");
		ImageIcon white = new ImageIcon(url);
		Image wt = white.getImage(); // transform it 
		Image newWhite = wt.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		white = new ImageIcon(newWhite);  // transform it back
				
		URL url2 = GUI.class.getResource("BlackSquare.png");
		ImageIcon black = new ImageIcon(url2);
		Image bl = black.getImage(); // transform it 
		Image newBlack = bl.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		black = new ImageIcon(newBlack);  // transform it back
		
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 1); //defines border for squares
		
		//create an initial array of labels on the board
		//assigning black and white to appropriate tiles
		JLabel[][] tempLabels = new JLabel[8][8];
		
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				tempLabels[i][j] = new JLabel();
				tempLabels[i][j].setSize(150,150);
				
				if(board[i][j] == 0){
					tempLabels[i][j].setBackground(Color.WHITE);
				}
				else{
					tempLabels[i][j].setBackground(Color.BLACK);
				}
				tempLabels[i][j].setBorder(border2);
				tempLabels[i][j].setOpaque(true);
				tempLabels[i][j].setLayout(new GridLayout(1,1));
				content.add(tempLabels[i][j]);
				
			}
		}
		
		/*
		 * instantiate the buttons for the board
		 * assigns appropriate colors to appropriate buttons
		 * adds buttons to the labels already on the board
		 */
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++){
				
				squares[i][j] = new CoordButton(j,i);
				
				if(board[i][j] == 0){
					squares[i][j].setIcon(white);
				}
				else{
					squares[i][j].setIcon(black);
				}
					
				squares[i][j].addActionListener(buttonActions);
				squares[i][j].setBorder(BorderFactory.createEmptyBorder()); //no border so buttons blend into tiles
				squares[i][j].setOpaque(true);
				squares[i][j].setLayout(new GridLayout(1,1));
				squares[i][j].setContentAreaFilled(false);
				//squares[i][j].setFocusable(false);
				
				squares[i][j].setVisible(true);
				squares[i][j].validate();
				
				//content.add(squares[i][j]);
				//content.remove(tempLabels[i][j]);
				tempLabels[i][j].add(squares[i][j]);
				
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
						
		window.setSize(850, 850);
		window.setLocation(100, 100);
		window.setVisible(true);
		
		
	}

}
