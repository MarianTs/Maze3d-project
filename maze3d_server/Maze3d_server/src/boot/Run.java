package boot;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import model.CommonModel;
import model.MyModel;
import presenter.Presenter;
import view.CLI;
import view.CommonView;
/**
 * starting the server MVP
 * @author Marian & Lidor
 *
 */
public class Run {

	public static void main(String[] args) 
	{
		CommonModel m=new MyModel();
		CommonView ui=new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		Presenter p=new Presenter(m, ui);
		
		ui.addObserver(p);
		m.addObserver(p);
		ui.start();
		
		

	}

}
