package boot;


import java.beans.XMLEncoder;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import presenter.Properties;
/**
 * constructing an xml file to load properties
 * @author Marian & Lidor
 *
 */
public class RunProperties {

	public static void main(String[] args) 
	{
		//properties p=new properties(10, "astar air distance", "prim");
		
		try 
		{
			XMLEncoder xmlE = new XMLEncoder(new FileOutputStream("./resources/properties.xml"));
			xmlE.writeObject(new Properties(10, "astar air distance", "prim","gui"));
			xmlE.close();
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
		
	}

}
