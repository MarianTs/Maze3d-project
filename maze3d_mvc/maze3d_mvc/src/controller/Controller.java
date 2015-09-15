package controller;




import Model.Model;
import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import view.View;

public interface Controller {

	void setM(Model m);
	void setV(View v);
	
	

	
	public void passDirPath(String[] dirArray);
	public void passError(String message);
	public void passGenerate3dMaze(String message);
	public void passDisplayName(byte[] byteArr);
	public void passDisplayCrossSectionBy(int[][] crossSection);
	public void passSaveMaze(String str);
	public void passLoadMaze(String str);
	public void passMazeSize(int size);
	public void passFileSize(long length);
	public void passSolve(String message);
	public void passDisplaySolution(Solution<Position> sol);
}
