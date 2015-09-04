package algorithms.mazeGenerators;
/**
 * maze 3d generator<p>
 *
 * @author Marian
 *
 */
public interface Maze3dGenerator
{
	/**
	 * Generating maze.
	 * @param size_x the height of the maze(how many floors)
	 * @param size_y the number of rows in each floor
	 * @param size_z the number of columns in each floor
	 * @return 3D maze
	 */
	public abstract Maze3d generate(int size_x,int size_y,int size_z);
	
	/**
	 * Measure the time of building a maze(according to different algorithms).
	 * @param size_x the height of the maze(how many floors)
	 * @param size_y the number of rows in each floor
	 * @param size_z the number of columns in each floor
	 * @return string with the time in nano seconds.
	 */
	public String measureAlgorithmTime(int size_x,int size_y,int size_z);
}
