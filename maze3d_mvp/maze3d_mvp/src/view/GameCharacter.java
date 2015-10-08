package view;

import org.eclipse.swt.events.PaintEvent;
/**
 * defines the game character picture
 * @author Marian & Lidor
 *
 */
public interface GameCharacter 
{
	/**
	 * drawing the game character picture
	 * @param e event that draw the picture
	 * @param x x axis in the left up corner
	 * @param y y axis in the left up corner
	 * @param width picture width
	 * @param height picture height
	 */
	public void paint(PaintEvent e,int x,int y, int width, int height);
}
