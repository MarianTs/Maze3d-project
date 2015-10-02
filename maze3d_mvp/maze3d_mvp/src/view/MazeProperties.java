package view;

public class MazeProperties 
{
	int height;//x
	int width;//z
	int depth;//y
	String crossSection;
	
	public MazeProperties() {
		this.height=5;
		this.width=10;
		this.depth=10;
		this.crossSection="x";
	}

	public MazeProperties(int height, int width, int depth, String crossSection) {
		super();
		this.height = height;
		this.width = width;
		this.depth = depth;
		this.crossSection = crossSection;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public String getCrossSection() {
		return crossSection;
	}

	public void setCrossSection(String crossSection) {
		this.crossSection = crossSection;
	}
	
	
	
	
}
