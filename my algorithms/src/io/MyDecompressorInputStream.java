package io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Decompress the data it gets from inputStream
 * @author Marian
 *
 */
public class MyDecompressorInputStream extends InputStream {
	protected InputStream in;
	protected int currByte;
	protected int count;
	

	
	/**
	 * constructor with parameters
	 * @param in input stream source
	 */
	public MyDecompressorInputStream(InputStream in) {
		super();
		this.in = in;

	}
	/**
	 * returning the input stream source
	 * @return input stream source
	 */
	public InputStream getIn() {
		return in;
	}
	/**
	 * setting the input stream source
	 * @param in input stream source
	 */
	public void setIn(InputStream in) {
		this.in = in;
	}
	
	@Override
	public int read() throws IOException 
	{
		
		if(count<=0)
		{
			if((currByte=in.read())==-1)
			{
				return -1;
			}
			if((count=in.read())==-1)
			{
				throw (new IOException("Expected counter,invalid byte array!"));
			}
			if(count<=0)
			{
				throw (new IOException("Invalid Counter"));
			}
		}
		count--;
		return currByte;
	}
	
	
	
	/**
	 * function that return count times the number num
	 * @param num the number we return
	 * @param count the amount of times we return num
	 * @return count times number
	 */
	protected int getNumberAmountOfTimes(int num,int count)
	{
		
		if(count==1)
		{
			return num;
		}
		else
		{
			
			getNumberAmountOfTimes(num, count-1);
			return num;
		}
	}
}
