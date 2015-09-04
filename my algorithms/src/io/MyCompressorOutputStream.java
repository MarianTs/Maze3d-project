package io;

import java.io.IOException;
import java.io.OutputStream;

/**
 * compress given data and write it to the output stream source
 * @author Marian
 */
public class MyCompressorOutputStream extends OutputStream 
{
	protected OutputStream out;
	protected int previousByte;
	protected int count;
	

	
	/**
	 * constructor using fields
	 * @param out output stream source
	 * 
	 */
	public MyCompressorOutputStream(OutputStream out) {
		super();
		this.out = out;
		this.count=0;

	}
	/**
	 * returning output stream source
	 * @return output stream source
	 */
	public OutputStream getOut() {
		return out;
	}
	/**
	 * setting output stream source
	 * @param out output stream source
	 */
	public void setOut(OutputStream out) {
		this.out = out;
	}
	
	
	
	
	/**
	 * writing one integer to data source
	 */
	@Override
	public void write(int num) throws IOException 
	{
		if(count==0)
		{
			this.previousByte=num;
			this.count=1;
			return;
		}
		
		
		if(num==this.previousByte)
		{
			count++;
		}
		else
		{
			out.write(previousByte);
			out.write(count);
			this.previousByte=num;
			this.count=1;
		}

	}
	
	@Override
	public void write(byte[] byteArr) throws IOException 
	{
		super.write(byteArr);
		if(count==0)
		{
			out.write((byte)previousByte);
			out.write((byte)count);
		}
		
		
		count=0;
		previousByte=0;
	}
	
}
