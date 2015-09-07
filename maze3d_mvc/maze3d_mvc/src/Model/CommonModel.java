package Model;

import java.io.File;

import controller.Controller;

public abstract class CommonModel implements Model 
{
	Controller c;

	public CommonModel(Controller c) {
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
