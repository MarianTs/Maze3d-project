package view;

import java.util.Observable;
/**
 * abstract class that the defines the view functions and data members
 * @author Marian & Lidor
 *
 */
public abstract class CommonView extends Observable implements View {
	/**
	 * {@inheritDoc}
	 */
	public abstract void start();
	
	
}
