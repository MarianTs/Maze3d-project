package view;


import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;

import algorithms.mazeGenerators.Maze3d;
/**
 * defines the widget of the game board
 * @author Marian
 *
 */
public class MazeDisplay extends Canvas 
{
	Maze3d maze;
	/**
	 * constructor using fields
	 * @param parent parent of the widget
	 * @param style the style of the widget
	 */
	public MazeDisplay(Composite parent, int style) {
		super(parent, style);
		
		//final Color white=new Color(null, 255, 255, 255);
		//final Color black=new Color(null, 150,150,150);
	}

	
	

}
