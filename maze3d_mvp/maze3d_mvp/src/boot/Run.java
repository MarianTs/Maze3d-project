package boot;

//import java.io.BufferedReader;
//import java.io.InputStreamReader;
//import java.io.PrintWriter;

import Model.CommonModel;
import Model.MyModel;
import presenter.Presenter;
//import view.CLI;
import view.CommonView;
import view.GUI;


public class Run {

	public static void main(String[] args) {
		/*Controller c=new MyController();
		Model m=new MyModel(c);
		View v=new MyView(c);
		c.setM(m);
		c.setV(v);
		v.start();*/
		//CommonView ui = new CLI(new BufferedReader(new InputStreamReader(System.in)),new PrintWriter(System.out));
		CommonView ui=new GUI();
		CommonModel m = new MyModel();
		Presenter p=new Presenter(m, ui);

		ui.addObserver(p);
		m.addObserver(p);
		ui.start();

	}

}
