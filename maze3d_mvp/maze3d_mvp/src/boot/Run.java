package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;

import Model.CommonModel;
import Model.MyModel;
import presenter.Presenter;
import view.CLI;
//import view.CLI;
import view.CommonView;
import view.GUI;


public class Run {

	public static void main(String[] args) {
		

		CommonModel m = new MyModel();
		String s=m.getProperties().getTypeOfUserInterfece();
		CommonView ui;
		
		if(s.intern()=="gui")
		{
			ui=new GUI();
		}
		else if(s.intern()=="cli")
		{
			ui=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		}
		else
		{
			ui=new GUI();
		}
			
		Presenter p=new Presenter(m, ui);

		ui.addObserver(p);
		m.addObserver(p);
		ui.start();

	}

}
