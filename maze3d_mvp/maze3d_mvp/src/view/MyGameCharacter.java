package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;
/**
 * defines how the character will look
 * @author Marian & Lidor
 *
 */
public class MyGameCharacter implements GameCharacter {


	
	
	/**
	 * paint the character
	 * @param PaintEvent e the paint event that invoke the painting
	 * @param x the x axis to paint the character
	 * @param y the x axis to paint the character
	 * @param width the width of the character
	 * @param height the height of the character
	 */
	public void paint(PaintEvent e,int x,int y, int width, int height) 
	{
		//e.gc.setForeground(new Color(null,34, 156, 34));
		//e.gc.fillOval(x, y, width, height);
		//Image image = new Image(e.display, "./resources/minion.jpg");
		e.gc.setBackground(new Color(null,255,165,0));
		e.gc.fillOval(x, y, width, height);
		//e.gc.drawImage(image, 0, 0, image.getBounds().width,image.getBounds().height,x,y ,width ,height);							
		e.gc.setBackground(new Color(null,255,165,0));
		e.gc.setBackground(new Color(null,0,0,0));
	}

}
