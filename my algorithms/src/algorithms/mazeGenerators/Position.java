package algorithms.mazeGenerators;

/**
 * holds the position coordinates
 * @author Marian
 */
public class Position 
{
	private int x;
	private int y;
	private int z;
	
	/**
	 * construct Position out of x y z coordinates
	 * @param x the x coordinate
	 * @param y the y coordinate
	 * @param z the z coordinate
	 */
	public Position(int x, int y, int z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	/**
	 * constructor without parameters
	 * put 0 in each coordinate
	 */
	public Position() {
		super();
		this.x = 0;
		this.y = 0;
		this.z = 0;
	}
	
	/**
	 * set x y z coordinates in position
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 */
	public void setXYZ(int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
	}
	/**
	 * set x y z in position and return them
	 * @param x x coordinate
	 * @param y y coordinate
	 * @param z z coordinate
	 * @return position after changing
	 */
	public Position getXYZ(int x,int y,int z)
	{
		this.x=x;
		this.y=y;
		this.z=z;
		return this;
		
	}
	
	
	/**
	 * 
	 * @return x coordinate of position
	 */
	public int getX() {
		return x;
	}
	/**
	 * set x coordinate in current position
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * 
	 * @return y coordinate
	 */
	public int getY() {
		return y;
	}
	/**
	 * set y coordinate in position
	 * @param y y cordinate
	 */
	public void setY(int y) {
		this.y = y;
	}
	/**
	 * 
	 * @return z coordinate of current position
	 */
	public int getZ() {
		return z;
	}
	/**
	 * set z coordinate in current position
	 * @param z z coordinate
	 */
	public void setZ(int z) {
		this.z = z;
	}
	/**
	 * checks if current position equals to another position by its coordinates
	 */
	@Override
	public boolean equals(Object p)
	{
		if((p instanceof Position)==false)
		{
			return false;
		}
		return ((this.x==((Position)p).x)&&(this.y==((Position)p).y)&&(this.z==((Position)p).z));
	}
	/**
	 * return string with{x,y,z}
	 */
	public String toString()
	{
		String s="{"+x+","+y+","+z+"}";
		return s;
	}
	/**
	 * override hash code of object,by implementing hash code of position.
	 */
	@Override
	public int hashCode() {
		
		return this.toString().hashCode();
	}
}
