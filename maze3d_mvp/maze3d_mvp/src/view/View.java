package view;

import algorithms.mazeGenerators.Position;
import algorithms.search.Solution;
import presenter.Properties;
/**
 * interface that determine the functionality of the user interface
 * @author Marian & Lidor
 *
 */
public interface View {
	/**
	 * starting the user interface
	 */
	public void start();
	/**
	 * returning the command that the user entered
	 * @return string with the command that user entered
	 */
	public String getUserCommand();
	/**
	 * display the error in the commands that the client wrote
	 * @param message string telling what is the error
	 */
	public void showError(String error);
	/**
	 * display the outcome of command:dir <path>
	 * displaying the files and directories of the specified path
	 * @param dirArray string's array with the names of files and directories in the specified path
	 */
	public void showDirPath(String[] list);
	/**
	 * display the message that the maze is ready
	 * @param message string with the massege:maze is ready
	 */
	public void showGenerate3dMaze(String message);
	/**
	 * displaying the specified maze
	 * @param byteArr byte array representing the maze
	 */
	public void showDisplayName(byte[] byteArr);
	/**
	 * displaying the cross section which the client asked for
	 * @param crossSection 2d array with the cross section asked
	 */
	public void showDisplayCrossSectionBy(int[][] crossSection);
	/**
	 * displaying the string:the maze has been saved
	 * @param str string with the word:maze has been saved
	 */
	public void showSaveMaze(String message);
	/**
	 * displaying the string:the maze has been loaded
	 * @param str string with the word:maze has been loaded
	 */
	public void showLoadMaze(String message);
	/**
	 * display the maze size in memory(bytes)
	 * @param size the size of the maze in bytes
	 */
	public void showMazeSize(int size);
	/**
	 * display the maze size in file(bytes)
	 * @param length the size of the maze in file
	 */
	public void showFileSize(long size);
	/**
	 * displaying the string:solution for maze is ready
	 * @param message string with the words:solution for maze is ready
	 */
	public void showSolveMaze(String message);
	/**
	 * displaying the solution of the specified maze
	 * @param sol the solution of the maze
	 */
	public void showDisplaySolution(Solution<Position> solution);
	/**
	 * a command that only gui has:solve from <name> <algorithm> <x> <y> <z>
	 * showing the outcome of this command,in other wards display the solution from specific point in the maze
	 * @param message message that the solution is ready
	 */
	public void showSolveFrom(String message);
	/**
	 * a command that only gui has:display half solution <name>
	 * displaying a solution for the specified maze
	 * @param solution  solution that was calculated in the model
	 */
	public void showDisplayHalfSolution(Solution<Position> solution);
	/**
	 * display a message about some error
	 */
	public void showExit();
	/**
	 * displaying help,which shows the commands the client can write
	 */
	public void showHelp();
	/**
	 * a command that only gui have: load xml
	 * @param p properties object that was loaded by the xml file
	 */
	public void showLoadXML(Properties p);
}
