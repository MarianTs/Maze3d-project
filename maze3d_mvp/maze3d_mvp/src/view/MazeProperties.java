package view;
/**
 * holds the properties of maze
 * @author Marian & Lidor
 *
 */
public class MazeProperties 
{
	int height;//x
	int width;//z
	int depth;//y
	//String crossSection;
	/**
	 * constructor that define default properties
	 * height=5
	 * width=10
	 * depth=10
	 */
	public MazeProperties() {
		this.height=5;
		this.width=10;
		this.depth=10;
		//this.crossSection="x";
	}
	/**
	 * Constructor using fields
	 * @param height
	 * @param width
	 * @param depth
	 */
	public MazeProperties(int height, int width, int depth) 
	{
		super();
		this.height = height;
		this.width = width;
		this.depth = depth;
		//this.crossSection = crossSection;
	}
	/**
	 * returning the height of the maze
	 * @return height height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * setting the height of the maze
	 * @param height height
	 */
	public void setHeight(int height) {
		this.height = height;
	}
	/**
	 * returning the width of the maze
	 * @return width width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * setting the width of the maze
	 * @param width width
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * returning the depth of the maze
	 * @return depth depth
	 */
	public int getDepth() {
		return depth;
	}
	/**
	 * setting the depth of the maze
	 * @param depth depth
	 */
	public void setDepth(int depth) {
		this.depth = depth;
	}

	/*public String getCrossSection() {
		return crossSection;
	}

	public void setCrossSection(String crossSection) {
		this.crossSection = crossSection;
	}*/
	
	
	
	
}
