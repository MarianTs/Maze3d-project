package Model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel extends CommonModel 
{
	ExecutorService threadPool;
	HashMap<String, Maze3d> mazeCollection;
	HashMap<String, String> mazeToFile;
	
	String errorCode;
	String[] dirList;
	String generate3dmazeCode;
	int[][] crossSection;
	String saveMazeCode;
	String loadMazeCode;
	int mazeSize;
	long fileSize;
	

	

	public MyModel() 
	{
		threadPool = Executors.newFixedThreadPool(10);
		mazeCollection = new HashMap<String, Maze3d>();
		mazeToFile=new HashMap<String,String>();
	}
	
	public void handleDirPath(String[] path)
	{
		if(path==null)
		{
			errorCode="Invalid path";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		if (path.length != 1) // path does'nt entered
		{
			errorCode="Invalid path";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		File f = new File(path[0].toString());

		if ((f.list() != null) && (f.list().length > 0)) 
		{
			dirList=f.list();
			setChanged();
			String[] s=new String[1];
			s[0]="dir";
			notifyObservers(s);
			return;
		} 
		else if (f.list() == null) // invalid path
		{
			errorCode="Invalid path";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		} 
		else // if there is nothing in the list
		{
			errorCode="Empty folder";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}

	}
	
	public void handleGenerate3dMaze(String[] mazeParam)
	{
		if(mazeParam==null)
		{
			errorCode="Invalid parameters";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		if (mazeParam.length != 5) 
		{
			errorCode="Invalid number of parameters";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		try {
			if ((Integer.parseInt(mazeParam[1]) <= 0) || (Integer.parseInt(mazeParam[2]) <= 0)|| (Integer.parseInt(mazeParam[3]) <= 0)) 
			{// checking if the sizes of the maze passed valid
				errorCode="Invalid parametrs";
				setChanged();
				String[] s=new String[1];
				s[0]="error";
				notifyObservers(s);
				return;
			}
		} catch (NumberFormatException e) {
			errorCode="Invalid parametrs";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		
		

		Future<String> future=threadPool.submit(new Callable<String>() 
		{

			@Override
			public String call() throws Exception
			{

				Maze3dGenerator mg;
				if (mazeParam[mazeParam.length - 1].intern() == "simple") {
					mg = new SimpleMaze3dGenerator();
				} else if (mazeParam[mazeParam.length - 1].intern() == "prim") {
					mg = new MyMaze3dGenerator();
				} else {
					errorCode="Invalid algorhtim name";
					return "error";
				}

				if (mazeCollection.containsKey(mazeParam[0].toString())) 
				{
					errorCode="This name already exists,try another one";
					return "error";
				}
				Maze3d maze=mg.generate(Integer.parseInt(mazeParam[1]),Integer.parseInt(mazeParam[2]), Integer.parseInt(mazeParam[3]));
				mazeCollection.put(mazeParam[0].toString(),maze);
				// generate maze with specified algorithm ,with specified sizes.
				
				return "maze " + mazeParam[0].toString() + " is ready";
			}
		});
		
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
				try 
				{
					generate3dmazeCode=future.get();
					if(generate3dmazeCode.intern()=="error")
					{
						generate3dmazeCode=null;
						setChanged();
						String[] s=new String[1];
						s[0]="error";
						notifyObservers(s);
						return;
					}
					else
					{
						setChanged();
						String[] s=new String[1];
						s[0]="generate 3d maze";
						notifyObservers(s);
						return;
					}
					
				}
				
				catch (InterruptedException e) 
				{
					e.printStackTrace();
				} 
				catch (ExecutionException e) 
				{
					e.printStackTrace();
				}
				
				
			}
		});
		
	}
	public void handleDisplayName(String[] paramArray) 
	{
		if(paramArray==null)
		{
			errorCode="Invalid parameters";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		if (paramArray.length != 1)
		{
			errorCode="Invalid amount of parameters";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}

		
		if (!mazeCollection.containsKey(paramArray[0].toString())) {
			errorCode="maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		String[] arg=new String[2];
		arg[0]="display";
		arg[1]=paramArray[0];
		setChanged();
		notifyObservers(arg);
		
		//c.passDisplayName(mazeCollection.get(paramArray[0].toString()).toByteArray());
		// getting the maze from maze collection
		

	}
	
	
	
	public void handleDisplayCrossSectionBy(String[] paramArray)
	{
		if(paramArray==null)
		{
			errorCode="Invalid command";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		if ((paramArray.length != 4) || (paramArray[2].intern() != "for")) 
		{
			errorCode="Invalid command,or number of parameters";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
	

		if (!mazeCollection.containsKey(paramArray[3].toString())) // checking if maze is in the collection
		{
			errorCode="This maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
			
		}

		
		if (paramArray[0].intern() == "x") {
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				crossSection = maze.getCrossSectionByX(Integer.parseInt(paramArray[1]));
				String[] s=new String[1];
				s[0]="display cross section by";
				setChanged();
				notifyObservers(s);
				return;
			} catch (NumberFormatException e) 
			{
				errorCode="Invalid parameters";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			} catch (IndexOutOfBoundsException e) {
				errorCode="x coordinate out of bounds";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			}

		} else if (paramArray[0].intern() == "y") 
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try 
			{
				crossSection = maze.getCrossSectionByY(Integer.parseInt(paramArray[1]));
				String[] s=new String[1];
				s[0]="display cross section by";
				setChanged();
				notifyObservers(s);
				return;
			} 
			catch (NumberFormatException e) 
			{
				errorCode="Invalid parameters";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			} 
			catch (IndexOutOfBoundsException e) 
			{
				errorCode="y coordinate out of bounds";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			}
		} 
		else if (paramArray[0].intern() == "z") 
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				crossSection = maze.getCrossSectionByZ(Integer.parseInt(paramArray[1]));
				
				String[] s=new String[1];
				s[0]="display cross section by";
				setChanged();
				notifyObservers(s);
				return;
			} 
			catch (NumberFormatException e) 
			{
				errorCode="Invalid parameters";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			} 
			catch (IndexOutOfBoundsException e)
			{
				errorCode="z coordinate out of bounds";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			}
		} else 
		{
			errorCode="Invalid parameters";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
	}
	public void handleError(String[] paramArr)
	{

		errorCode="Invalid command";
		String[] s=new String[1];
		s[0]="error";
		setChanged();
		notifyObservers(s);
		return;
	}
	
	
	public void handleExit(String[] paramArr)
	{
		if(paramArr!=null)
		{
			errorCode="Invalid command";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		threadPool.shutdown();
		try {
			while(!threadPool.awaitTermination(10, TimeUnit.SECONDS));
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		setChanged();
		String[] s=new String[1];
		s[0]="exit";
		notifyObservers(s);
			
	}
	public void handleSaveMaze(String[] paramArray)
	{
		if(paramArray==null)
		{
			errorCode="Invalid command";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if (paramArray.length != 2) 
		{
			errorCode="Invalid amount of parameters";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}

		

		if (!(mazeCollection.containsKey(paramArray[0]))) 
		{
			errorCode="maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		//saving the name of the maze,with the file where the maze is saved,in order to use it later in file size command
		mazeToFile.put(paramArray[0], paramArray[1]);

		Maze3d maze = mazeCollection.get(paramArray[0].toString());

		int size = maze.toByteArray().length;
		
		try 
		{
			MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(paramArray[1].toString()));
			out.write(ByteBuffer.allocate(4).putInt(size).array());// first writing the size of the maze
			out.write(maze.toByteArray());
			out.close();
			
			saveMazeCode=paramArray[0].toString() + " has been saved";
			String[] s=new String[1];
			s[0]="save maze";
			setChanged();
			notifyObservers(s);
			return;
		} 
		catch (FileNotFoundException e) 
		{
			errorCode="maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		} 
		catch (IOException e) {
			e.printStackTrace();
		}
	}
	public void handleLoadMaze(String[] paramArray)
	{
		if(paramArray==null)
		{
			errorCode="Invalid parameters";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		if (paramArray.length != 2) 
		{
			errorCode="Invalid amount of parameters";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		//checking if there are already maze with the same name
		if (mazeCollection.containsKey(paramArray[1])) 
		{
			
			errorCode="Invalid name,this name is taken";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if(mazeToFile.containsKey(paramArray[1]))
		{
			if(mazeToFile.get(paramArray[1])!=paramArray[0])
			{
				errorCode="This name already exists";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			}
		}
		mazeToFile.put(paramArray[1], paramArray[0]);
			
		try 
		{	
			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(paramArray[0].toString()));

			// The ByteArrayOutputStream class stream creates a buffer in memory
			// and all the data sent to the stream is stored in the buffer.
			ByteArrayOutputStream outByte = new ByteArrayOutputStream();

			// reading 4 bytes from file,which shows the size of the maze
			outByte.write(in.read());
			outByte.write(in.read());
			outByte.write(in.read());
			outByte.write(in.read());

			// creates input stream from a buffer initialize in bracelet
			ByteArrayInputStream inByte = new ByteArrayInputStream(outByte.toByteArray());// taking out the buffer I wrote to
			DataInputStream dis = new DataInputStream(inByte);// easier to read data from stream(can read primitive types)

			// What I did:created a input stream and wrote there my 4 bytes from file.
			// than created input stream,where i put my byte array,than read from this buffer integer.

			byte[] byteArr = new byte[dis.readInt()];// construct a array of byte,in the size readen from file.
			in.read(byteArr);
			mazeCollection.put(paramArray[1], new Maze3d(byteArr));

			in.close();
			loadMazeCode=paramArray[1] + " has been loaded from file: " + paramArray[0];
			String[] s=new String[1];
			s[0]="load maze";
			setChanged();
			notifyObservers(s);
			return;

		} 
		catch (FileNotFoundException e) 
		{
			errorCode="File not found";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void handleMazeSize(String[] paramArray)
	{
		if(paramArray==null)
		{
			errorCode="Invalid parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		if (paramArray.length != 1) 
		{
			errorCode="Invalid number of parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		if (!mazeCollection.containsKey(paramArray[0])) 
		{
			errorCode="Maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}

		Maze3d maze = mazeCollection.get(paramArray[0]);
		mazeSize=maze.toByteArray().length;
		String[] s=new String[1];
		s[0]="maze size";
		setChanged();
		notifyObservers(s);
		return;
		
		
	}
	public void handleFileSize(String[] paramArray)
	{
		if(paramArray==null)
		{
			errorCode="Invalid parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		if(paramArray.length!=1)
		{
			errorCode="Invalid number of parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		//checking if there is a file that have this maze
		if(!(mazeToFile.containsKey(paramArray[0])))
		{
			errorCode="Maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		try
		{
			File file=new File(mazeToFile.get(paramArray[0]));//taking the file name from the hashmap
			
			fileSize=file.length();
			String[] s=new String[1];
			s[0]="file size";
			setChanged();
			notifyObservers(s);
			return;
		}
		catch (NullPointerException e)
		{
			errorCode="You didn't entered path";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
	}
	public void handleSolveMaze(String[] paramArray)
	{
		
	}
	
	
	public String getLoadMazeCode()
	{
		return loadMazeCode;
	}

	public String getSaveMazeCode()
	{
		return saveMazeCode;
	}

	public String getErrorCode() {
		return errorCode;
	}

	public String[] getDirList() {
		return dirList;
	}

	public String getGenerate3dmazeCode() {
		return generate3dmazeCode;
	}
	
	public byte[] getSpecificMazeFromColllection(String name)
	{
		return mazeCollection.get(name).toByteArray();
	}

	public int[][] getCrossSection() 
	{
		return crossSection;
	}
	public int getMazeSize() 
	{
		return mazeSize;
	}
	public long getFileSize() 
	{
		return fileSize;
	}
	/*public void handleCommands(String[] args)
	{
		
	}*/
}
