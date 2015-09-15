package view;



import java.util.HashMap;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import controller.Command;



public interface View {
	/**
	 * starting the command line interface
	 */
	public void start();
	/**
	 * 
	 * @param dirArray
	 */
	
	public void setStringToCommand(HashMap<String, Command> stringToCommand);
	public void showDirPath(String[] dirArray);
	public void showError(String message);
	public void showHelp();
	public void showGenerate3dMaze(String message);
	public void showDisplayName(byte[] byteArr);
	public void showDisplayCrossSectionBy(int[][] crossSection);
	
	public void showSaveMaze(String str);
	public void showLoadMaze(String str);
	public void showMazeSize(int size);
	public void showFileSize(long length);
	public void showSolve(String message);
	public void showDisplaySolution(Solution<Position> sol);
}
