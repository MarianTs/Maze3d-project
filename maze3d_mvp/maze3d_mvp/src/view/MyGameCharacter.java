package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

public class MyGameCharacter implements GameCharacter {


	public MyGameCharacter() {
		super();
	}

	public void paint(PaintEvent e,int x,int y, int width, int height) 
	{
		e.gc.setForeground(new Color(null, 255, 0, 0));
		e.gc.drawOval(x, y, width, height);
	}

}
