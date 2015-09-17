package boot;

import Model.Model;
import Model.MyModel;
import controller.Controller;
import controller.MyController;
import view.MyView;
import view.View;

public class Run {

	public static void main(String[] args) {
		Controller c=new MyController();
		Model m=new MyModel(c);
		View v=new MyView(c);
		c.setM(m);
		c.setV(v);
		v.start();

	}

}
