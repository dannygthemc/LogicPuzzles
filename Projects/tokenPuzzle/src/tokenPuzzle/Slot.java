package tokenPuzzle;

import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Slot extends JButton {
	
	ImageIcon im; //image for Slot, empt initially, token image otherwise
	boolean selected; //keeps track of whether or not slot is selected
	boolean filled; //keeps track of whether or not slot is filled
	int slotNum; //keeps track of slot's place in the array of slots
	int heldToken; //identifies token being held
	
	/*
	 * Constructor instantiates a Slot Button
	 * sets initial image to be the blank slot image
	 * and sets selected and filled to initially be false
	 */
	public Slot(int place){
		
		URL url = Board.class.getResource("Images/BlankSlot.png");
		ImageIcon slot = new ImageIcon(url);
		Image stIm = slot.getImage(); // transform it 
		Image newSlot = stIm.getScaledInstance(80, 80,  java.awt.Image.SCALE_SMOOTH); // scale it the smooth way  
		slot = new ImageIcon(newSlot);  // transform it back
		im = slot;
		this.setIcon(im);
		this.setSize(100,100);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setFocusable(false);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setBackground(Color.GRAY);
		
		slotNum = place;
		selected = false;
		filled = false;
		heldToken = -1;
		
	}
	
	//getter and setter for the selected boolean
	public boolean getSel(){
		return selected;
	}
	
	public void setSel(boolean bool){
		selected = bool;
		updateBack();
	}
	
	public int getHeld(){
		return heldToken;
	}
	
	public void setHeld(int tokNum){
		heldToken = tokNum;
	}
	
	public void setIm(ImageIcon image){
		this.setIcon(image);
	}
	
	public void resetIm(){
		this.setIcon(im);
	}
	
	//getter and setter for the filled boolean
	public boolean getFill(){
		return filled;
	}
	
	public void setFill(boolean bool){
		filled = bool;
	}
	
	//getter for the location of the slot
	public int getLoc(){
		return slotNum;
	}
	//updates background when slot is selected
	public void updateBack(){
		
		if(selected == true){
			this.setBackground(Color.CYAN);
		}
		else{
			this.setBackground(Color.GRAY);
		}
	}
	

}
