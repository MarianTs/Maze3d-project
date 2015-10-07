package view;

import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.graphics.Color;

public class MyGameCharacter implements GameCharacter {


	

	
	public void paint(PaintEvent e,int x,int y, int width, int height) 
	{
		//e.gc.setForeground(new Color(null,34, 156, 34));
		//e.gc.fillOval(x, y, width, height);
		//Image image = new Image(e.display, "./resources/minion.jpg");
		e.gc.setBackground(new Color(null,255,255,51));
		e.gc.fillOval(x, y, width, height);
		//e.gc.drawImage(image, 0, 0, image.getBounds().width,image.getBounds().height,x,y ,width ,height);							
		e.gc.setBackground(new Color(null,255,255,51));
		e.gc.setBackground(new Color(null,0,0,0));
	}

}
