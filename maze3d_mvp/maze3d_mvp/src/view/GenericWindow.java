package view;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
import org.eclipse.swt.widgets.Text;

public class GenericWindow extends BasicWindow 
{
	Object obj;

	public GenericWindow( int width, int height,HashMap<String, Listener> listenerCollection,Object obj) 
	{
		super(obj.getClass().getName(), width, height,listenerCollection);
		this.obj=obj;


	}

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
				if(m.getReturnType().isPrimitive())
				{
					Text t=new Text(shell, SWT.SINGLE);
					t.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,1, 1));
					t.addModifyListener(new ModifyListener() {
						
						@Override
						public void modifyText(ModifyEvent arg0) {
							try {
								
								m.invoke(obj,Integer.parseInt(t.getText()));
							} catch (IllegalAccessException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (IllegalArgumentException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (InvocationTargetException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
					});
					
					
				}
				
			}
			
			
			
			/*if(m.getType().isEnum())
			{
				//String[] items =(String[])f.getClass().getEnumConstants();
				//Combo combo = new Combo(shell, SWT.DROP_DOWN);
				//combo.setItems(items);
			}*/
			
		}
		Button ok=new Button(shell,SWT.PUSH);
		ok.setText("ok");
		ok.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		ok.addSelectionListener(new SelectionListener() {
			
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				
				close();
				
			}
			
			@Override
			public void widgetDefaultSelected(SelectionEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		//ok.addListener(SWT.Selection, listenerCollection.get("ok"));
		
		
		Button cancel=new Button(shell,SWT.PUSH);
		cancel.setText("cancel");
		cancel.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		cancel.addListener(SWT.Selection,new Listener() {
			
			@Override
			public void handleEvent(Event arg0) {
				close();
				
			}
		});
		shell.pack();

	}

	@Override
	public boolean DisplayExitMessageBox() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void close() 
	{
		
		shell.dispose();

	}

	public Object getObj() {
		return obj;
	}
	
	

}
