package Model;



public interface Model {
	/**
	 * Handling the command :dir <path>
	 * @param args Array of strings,containing one string with path
	 */
	public void HandleDirPath(String[] paramArray);
	/**
	 * Handling the command:generate 3d maze <name> <x> <y> <z> <algorithm>
	 * name-name of the maze,x-amount of floors in maze,y-amount of rows,z-amount of columns,
	 * algorithm-prim/simple (generating algorithms)
	 * @param paramArray Array of strings with the parameters i mentioned above
	 */
	public void handleGenerate3dMaze(String[] paramArray);
	/**
	 * Handling the command:display <name>(name of the maze)
	 * @param paramArray Array with one string,the name of the maze that needs to be displayed
	 */
	public void handleDisplayName(String[] paramArray);
	/**
	 * Handling the command:display cross section by {x,y,z} <index> for <name>
	 * @param paramArray Array of strings containing the parameters above
	 */
	public void handleDisplayCrossSectionBy(String[] paramArray);
	/**
	 * Handling the command:save maze <name> <file name>
	 * name-maze name generated before,file name-the name of the file to save maze to
	 * @param paramArray array of strings with the parameters above
	 */
	public void handleSaveMaze(String[] paramArray);
	/**
	 * handling command:load maze <file name> <name>
	 * @param paramArray array of strings with file name and maze name
	 */
	public void handleLoadMaze(String[] paramArray);
}
