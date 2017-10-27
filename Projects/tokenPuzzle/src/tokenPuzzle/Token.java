package tokenPuzzle;
/*
 * Class represents a token.
 * extends JLabel.
 * Tokens have an image and a placement number.
 * placement number starts at -1 and is updated when 
 * a token is placed
 */

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;

public class Token extends JButton {
	
	ImageIcon im; //image for the token
	int tokenNum; //token's number
	
	/*
	 * constructor instantiates a Token button
	 * takes in image and sets it to be the iconim
	 */
	public Token(ImageIcon image, int num){
		
		im = image;
		tokenNum = num;
		this.setIcon(im);
		this.setSize(100,100);
		this.setBorder(BorderFactory.createEmptyBorder());
		this.setFocusable(false);
		//this.setContentAreaFilled(false);
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setVerticalAlignment(SwingConstants.CENTER);
		this.setBackground(Color.GRAY);
		
	}
	
	public int getNum(){
		return tokenNum;
	}
	
	public void setNum(int newNum){
		tokenNum = newNum;
	}
	
	public ImageIcon getIm(){
		return im;
	}

}
