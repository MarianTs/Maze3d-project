package view;

import controller.Controller;

public abstract class CommonView implements View 
{
	Controller c;

	public CommonView(Controller c) {
		super();
		this.c = c;
	}

	public Controller getC() {
		return c;
	}

	public void setC(Controller c) {
		this.c = c;
	}
	
	
}
