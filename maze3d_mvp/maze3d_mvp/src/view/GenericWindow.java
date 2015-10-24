package view;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
/**
 * a class that gets any type of object,show its fields in window,and after the client entered some text fill the object accordingly
 * @author Marian & Lidor
 *
 */
public class GenericWindow extends BasicWindow 
{
	Object obj;
	/**
	 * constructor using fields
	 * @param width the width of the window
	 * @param height the height of the window
	 * @param listenerCollection listener collection of the window
	 * @param obj the object to fill
	 */
	public GenericWindow( int width, int height,HashMap<String, Listener> listenerCollection,Object obj) 
	{
		super(obj.getClass().getName(), width, height,listenerCollection);
		this.obj=obj;
 		initWidgets();

	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	void initWidgets() 
	{
		
		shell.setLayout(new GridLayout(2, false));
		Method[] methods=obj.getClass().getMethods();
		ArrayList<Method> methodAL=new ArrayList<Method>();
		for(Method m:methods)
		{
			
			String methodName=m.getName();
			
			if((methodName.startsWith("set"))&&(m.getParameters().length==1))
			{
				methodAL.add(m);
				Label label=new Label(shell,SWT.NONE);
				label.setText(methodName.substring(3)+":");
				label.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
				Parameter[] paramArr=m.getParameters();
				Text t=new Text(shell, SWT.SINGLE);
				t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,1, 1));
				if(paramArr[0].getType().isAssignableFrom(int.class))
				{
					
					t.addModifyListener(new ModifyListener() {
						
						@Override
						public void modifyText(ModifyEvent arg0) {
							try {
								if(Integer.parseInt(t.getText())<=0)
								{
									showMessageBox("number must be positive");
								}
								m.invoke(obj,Integer.parseInt(t.getText()));
							} catch (IllegalAccessException e) {
								showMessageBox(e.getMessage());
							} catch (IllegalArgumentException e) {
								showMessageBox(e.getMessage());

							} catch (InvocationTargetException e) {
								showMessageBox(e.getMessage());

							}
							
						}
					});
					
					
				}
				
				
			}

		}
		Button ok=new Button(shell,SWT.PUSH);
		ok.setText("ok");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		ok.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) 
			{
				close();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {}
		});

		
		
		Button cancel=new Button(shell,SWT.PUSH);
		cancel.setText("cancel");
		cancel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		cancel.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				obj=null;
				close();
				
			}
		});
		
		shell.addListener(SWT.Close, new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				obj=null;
				close();
				
			}
		});
		
		
		shell.pack();

	}
	/**
	 * showing errors in message box
	 * @param e exception to show
	 */
	public void showMessageBox(String s)
	{
		MessageBox messageBox = new MessageBox(shell,SWT.ICON_ERROR| SWT.OK);
		messageBox.setMessage(s);
		messageBox.setText("Error");
		messageBox.open();
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() 
	{
		
		shell.dispose();

	}
	/**
	 * returning an object of the fields that was written by the client
	 * @return object with filled data members(only members that has setters)
	 */
	public Object getObj() {
		return obj;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Shell getShell() {

		return shell;
	}
	
	

}
