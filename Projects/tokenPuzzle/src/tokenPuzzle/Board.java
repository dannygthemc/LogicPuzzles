package tokenPuzzle;

/*
 * class is used to define a GUI for the game.
 * swing tools utilized.
 * Board functions as a view-model for the game.
 * player interacts with the Board and the Board updates alongside their interactions.
 * 
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;



public class Board {
	
	JFrame window; //frame to contain board
	JPanel content; //main panel used to organize board layout
	JPanel content2; //secondary panel to hold door image
	JLabel labels[][]; //labels used to organize main panel
	Token tokens[]; //set of token buttons to represent tokens to be place
	Slot slots[]; //set of slot buttons to represent slots to place tokens
	JLayeredPane layers; //used to place door panel overtop of main panel
	int selectedSlot; //keeps track of which slot is selected. -1 if none are selected
	
	/*
	 * defines an action listener for the SlotButtons
	 */
	private ActionListener slotActions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	
            Slot sl = (Slot) ae.getSource(); //find out which button initiated the action
            
            //if button is already selected, deselect it
            //otherwise, set it as the selected button
            //if there's already a selected button, de-select it
            if(sl.getSel() == true){
            	sl.setSel(false);
            	selectedSlot = -1;
            }
            else{
            	
            	if(selectedSlot != -1){
            		slots[selectedSlot].setSel(false);
            	}
            	
            	sl.setSel(true);
            	selectedSlot = sl.getLoc();
            }
            
        }
    };
    
    /*
     * defines an actionListener for the tokenbuttons
     */
    private ActionListener tokenActions = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent ae) {
        	
            Token tk = (Token) ae.getSource(); //find out which button initiated the action
            
            //if there's currently no slot selected, do nothing
            if(selectedSlot == -1){
            	
            }
            else{
            	
            	//if slot is already filled
            	//reset the correseponding button
            	if(slots[selectedSlot].getFill() == true){
            		
            		int tokNum = slots[selectedSlot].getHeld();
            		tokens[tokNum].setVisible(true);	
            	}
            	
            	//update the slot & remove the corresponding button
            	slots[selectedSlot].setIm(tk.getIm());
        		slots[selectedSlot].setHeld(tk.getNum());
        		slots[selectedSlot].setFill(true);
        		tokens[tk.getNum()].setVisible(false);
            	
            }
            boolean wins = winCheck(); //check if player has won
            if(wins == true){
            	gameWon();
            	
            }
            
            
        }
    };
    
    /*
     * defines a MouseListener for the Slot buttons.
     * when rick-clicked, slot buttons are reset, and so is the 
     * corresponding token button
     */
    private MouseListener rightClick = new MouseListener() {

        public void mouseClicked(MouseEvent e) {
        	
        	Slot sl = (Slot) e.getSource(); //find out which button was right clicked
        	
            if (SwingUtilities.isRightMouseButton(e) && e.getClickCount() == 1) { //if right clicked
                
            	//if filled, remove current token
            	//reset corresponding token button
            	//and return Slot to initial state
            	if (sl.getFill() != false) { 
                    int temp = sl.getHeld();
                    tokens[temp].setVisible(true);
                    sl.resetIm();
                    sl.setFill(false);
                } else { //otherwise, do nothing
                    
                }
            }
        }

		@Override
		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
    };
    
    /*
     * checks if game-won conditions have been met
     * returns true if yes, false otherwise
     */
    public boolean winCheck(){
    	
    	boolean returnVal = false;
    	
    	if(slots[0].getHeld() == 3 && slots[1].getHeld() == 5
    	&& slots[2].getHeld() == 0 && slots[3].getHeld() == 1
    	&& slots[4].getHeld() == 4 && slots[5].getHeld() == 2){
    		
    		returnVal = true;
    	}

    	return returnVal;
    }
    
    /*
     * method to initiate game-won protocols
     * informs user of their victory and removes actionListeners so game can't be played anymore
     */
    public void gameWon(){
    	
    	//deselect selected slot
    	slots[selectedSlot].setSel(false);
    	selectedSlot = -1;
		
		//inform player of their victory
		JOptionPane.showMessageDialog(null, "You Win! \n"
				+ "The Password is: Lunacycle");
		
		//remove action listeners so player can't keep playing
		for(int i=0;i<6;i++){
				slots[i].removeActionListener(slotActions);
				slots[i].removeMouseListener(rightClick);
		}
	}
    
	/*
	 * constructor instantiates the Board object,
	 * defines all the Graphical components and starts the game
	 */
	public Board(){
		
		//instantiate GUI elements
		layers = new JLayeredPane();
		window = new JFrame("Board");
		content = new JPanel();
		content.setLayout(new GridLayout(7,7));
		
		labels = new JLabel[7][7];
		selectedSlot = -1;
		
		Border border2 = BorderFactory.createLineBorder(Color.BLACK, 1); //defines border for squares
		
		
		//define 2D array of labels to represent squares on the board
		//labels used to hold buttons and facilitate board organization
		for(int i=0;i<7;i++){
			for(int j=0;j<7;j++){
				
				labels[i][j] = new JLabel();
				labels[i][j].setSize(150,150);
			    labels[i][j].setBackground(Color.GRAY);
				
				labels[i][j].setBorder(BorderFactory.createEmptyBorder());
				labels[i][j].setOpaque(true);
				labels[i][j].setLayout(new GridLayout(1,1));
				content.add(labels[i][j]);
				
			}
		}
		
		//Create a new JPanel to display the picture of the dooor
		URL url = Board.class.getResource("Images/door3.png");
		ImageIcon doorIm = new ImageIcon(url);
		Image drIm = doorIm.getImage(); // transform it 
		Image newDoorIm = drIm.getScaledInstance(350, 600,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		doorIm = new ImageIcon(newDoorIm);  // transform it back
		content2 = new JPanel();
		JLabel panelIm = new JLabel();
		panelIm.setIcon(doorIm);
		content2.add(panelIm);
		content2.setBackground(Color.GRAY);
		
		
		tokens = new Token[8];
		
		//Get Images for Tokens
		//create Token buttons and add them to the board
		
		URL url2 = Board.class.getResource("Images/CenterToken.png");
		ImageIcon token0 = new ImageIcon(url2);
		Image tk0 = token0.getImage(); // transform it 
		Image newToken0 = tk0.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token0 = new ImageIcon(newToken0);  // transform it back
		
		JLabel centerTok = new JLabel(token0); //center token is label because won't be changed
		labels[0][3].add(centerTok);
		
		URL url3 = Board.class.getResource("Images/Token1.png");
		ImageIcon token1 = new ImageIcon(url3);
		Image tk1 = token1.getImage(); // transform it 
		Image newToken1 = tk1.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token1 = new ImageIcon(newToken1);  // transform it back
		
		tokens[0] = new Token(token1, 0);
		tokens[0].addActionListener(tokenActions);
		labels[6][0].add(tokens[0]);
		
		URL url4 = Board.class.getResource("Images/Token2.png");
		ImageIcon token2 = new ImageIcon(url4);
		Image tk2 = token2.getImage(); // transform it 
		Image newToken2 = tk2.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token2 = new ImageIcon(newToken2);  // transform it back
		
		tokens[1] = new Token(token2, 1);
		tokens[1].addActionListener(tokenActions);
		labels[6][1].add(tokens[1]);
		
		URL url5 = Board.class.getResource("Images/Token3.png");
		ImageIcon token3 = new ImageIcon(url5);
		Image tk3 = token3.getImage(); // transform it 
		Image newToken3 = tk3.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token3 = new ImageIcon(newToken3);  // transform it back
		
		tokens[2] = new Token(token3, 2);
		tokens[2].addActionListener(tokenActions);
		labels[6][2].add(tokens[2]);
		
		URL url6 = Board.class.getResource("Images/Token4.png");
		ImageIcon token4 = new ImageIcon(url6);
		Image tk4 = token4.getImage(); // transform it 
		Image newToken4 = tk4.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token4 = new ImageIcon(newToken4);  // transform it back
		
		tokens[3] = new Token(token4, 3);
		tokens[3].addActionListener(tokenActions);
		labels[6][4].add(tokens[3]);
		
		URL url7 = Board.class.getResource("Images/Token5.png");
		ImageIcon token5 = new ImageIcon(url7);
		Image tk5 = token5.getImage(); // transform it 
		Image newToken5 = tk5.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token5 = new ImageIcon(newToken5);  // transform it back
		
		tokens[4] = new Token(token5, 4);
		tokens[4].addActionListener(tokenActions);
		labels[6][5].add(tokens[4]);
		
		URL url8 = Board.class.getResource("Images/Token6.png");
		ImageIcon token6 = new ImageIcon(url8);
		Image tk6 = token6.getImage(); // transform it 
		Image newToken6 = tk6.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		token6 = new ImageIcon(newToken6);  // transform it back
		
		tokens[5] = new Token(token6, 5);
		tokens[5].addActionListener(tokenActions);
		labels[6][6].add(tokens[5]);
		
		
		//instantiate slot buttons
		slots = new Slot[8];
		
		for(int i=0; i<6;i++){
			slots[i] = new Slot(i);
			slots[i].addActionListener(slotActions);
			slots[i].addMouseListener(rightClick);
		}
		
		//add Slot Buttons to the labels on the board
		//adjust alignments to organize slots in an arch formation
		
		slots[0].setHorizontalAlignment(SwingConstants.RIGHT);
		labels[2][0].add(slots[0]);
		
		slots[1].setHorizontalAlignment(SwingConstants.LEFT);
		labels[1][1].add(slots[1]);
		slots[2].setHorizontalAlignment(SwingConstants.LEFT);
		slots[2].setVerticalAlignment(SwingConstants.BOTTOM);
		labels[0][2].add(slots[2]);
		slots[3].setHorizontalAlignment(SwingConstants.RIGHT);
		slots[3].setVerticalAlignment(SwingConstants.BOTTOM);
		labels[0][4].add(slots[3]);
		
		slots[4].setHorizontalAlignment(SwingConstants.RIGHT);
		labels[1][5].add(slots[4]);
		slots[5].setHorizontalAlignment(SwingConstants.LEFT);
		labels[2][6].add(slots[5]);
		
		
		
		
		//adds JPanels to layeredpane
		//layeredPane organizes panels in relation to each other within the window
		window.add(layers, BorderLayout.CENTER);
		content.setOpaque(true);
		content2.setOpaque(true);
		content.setBounds(0, 0, 850, 850);
		content2.setBounds(244, 124, 360, 602);
		layers.add(content, new Integer(0), 0);
		layers.add(content2, new Integer(1), 0);
		
		window.pack();
		
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.toBack();			
		window.setSize(868, 898);
		window.setLocation(380, 100);
		window.setVisible(true);
		
		
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
