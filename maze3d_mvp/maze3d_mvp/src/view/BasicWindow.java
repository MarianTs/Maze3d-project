package view;

import java.util.HashMap;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public abstract class BasicWindow 
{
		//org.eclipse.swt.win32.win32.x86_64_3.103.1.v20140903-1947.jar
		Display display;
		Shell shell;
		Boolean displayCanBeDisposed;
		HashMap<String, Listener> listenerCollection;
		
		
	 	public BasicWindow(String title, int width,int height,HashMap<String, Listener> listenerCollection)
	 	{
	 		if(Display.getCurrent()==null)
	 		{
	 			this.display=new Display();
	 			displayCanBeDisposed=true;
	 		}
	 		else
	 		{
	 			this.display=Display.getCurrent();
	 			displayCanBeDisposed=false;
	 		}

	 		this.shell = new Shell(display);
	 		this.shell.setSize(width,height);
	 		this.shell.setText(title);
	 		this.listenerCollection=listenerCollection;

		}
	 	
	 	
	 	
	 	public Shell getShell() {
			return shell;
		}



		abstract void initWidgets();

	 	
		public void run() 
		{
			initWidgets();
			//mainShell.pack();

			shell.open();
			// main event loop
			 while(!shell.isDisposed()){ // while window isn't closed

			    // 1. read events, put then in a queue.
			    // 2. dispatch the assigned listener
			    if(!display.readAndDispatch()){ 	// if the queue is empty
			       display.sleep(); 			// sleep until an event occurs 
			    }

			 } // shell is disposed
			 if(displayCanBeDisposed==true)
			 {
				 display.dispose();
			 }
			 
		}
		

		public abstract void close();
}
