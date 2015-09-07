package Model;

import java.io.File;

import controller.Controller;

public class MyModel extends CommonModel {

	public MyModel(Controller c) {
		super(c);
		
	}
	public void HandleDirPath(String[] args)
	{
		File f=new File(args[0].toString());
		if(f.list()!=null)
		{
			c.passDirPath(f.list());
		}
		else
		{
			c.passError("Invalid path");
		}
		
	}
}
