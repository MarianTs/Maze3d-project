package controller;

import java.util.HashMap;

import Model.Model;

import view.View;

public interface Controller {

	void setM(Model m);
	void setV(View v);
	
	
	public HashMap<String, Command> getStringToCommand();
	
	public void passDirPath(String[] dirArray);
	public void passError(String message);
	public void passGenerate3dMaze(String message);
	public void passDisplayName(byte[] byteArr);
	public void passDisplayCrossSectionBy(int[][] crossSection);
	public void passSaveMaze(String str);
	public void passLoadMaze(String str);
}
