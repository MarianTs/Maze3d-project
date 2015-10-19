package Model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.util.ArrayList;
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
import algorithms.mazeGenerators.Position;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import algorithms.search.Solution;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;
import presenter.Properties;
/**
 * class that defines the commands of model
 * @author Marian & Lidor
 *
 */
public class MyModel extends CommonModel 
{
	private ExecutorService threadPool;
	//the thread pool where all the threads are open in the model
	
	private HashMap<String, Maze3d> mazeCollection;
	//holds names and mazes,where start position is in it's place
	
	private HashMap<String, String> mazeToFile;
	//holds the name of the maze and the file it is located in
	
	private HashMap<Maze3d, Solution<Position>> mazeSolutions;//the solutions that the class already asked the server
	//holds maze object as a key(can be maze with start position in it's place,
	//or the start position is located somewhere in the maze) and value of solution to the current maze.
	
	private HashMap<String, Maze3d> mazeHalfCollection;
	//holds name of maze as a key and maze as value,where start position located in some place in the maze
	
	private ClientProperties clientProperties;
	//a class that holds the server IP and port of the server that our model will contact to
	//when it needs solution for mazes
	
	
	
	
	private String errorCode;//the code we enter when there is an error
	private String[] dirList;//the name of files and folder in current path
	private String generate3dmazeCode; //the pharse:maze <name> is ready
	private int[][] crossSection;//the 2d array that represents the cross section of maze
	private String saveMazeCode;//the phrase:maze has been saved
	private String loadMazeCode;//the phrase:maze has been loaded
	private int mazeSize;//the size of the maze in memory
	private long fileSize;//the size of maze in file
	private String solveMazeCode;//the phrase :solution for <maze> is ready
	private String solveHalfMazeCode;//the phrase:half solution for <maze> is ready
	private Properties properties;//a class which holds
	
	/**
	 * constructor using fields
	 * @param path path of xml file,where all the properties are held
	 */
	public MyModel(String[] path) 
	{
		
		handleLoadXML(path);//loading xml file
		//number of threads loaded here
		threadPool = Executors.newFixedThreadPool(properties.getNumberOfThreads());

		mazeCollection = new HashMap<String, Maze3d>();
		mazeHalfCollection=new HashMap<String,Maze3d>();
		mazeToFile=new HashMap<String,String>();
		mazeSolutions=new HashMap<Maze3d,Solution<Position>>();
		
		//loading the port and ip of the server
		try
		{
			File f=new File("./resources/client properties.xml");
			if(!f.exists())
			{
				XMLEncoder xmlE = new XMLEncoder(new FileOutputStream("./resources/client properties.xml"));
				xmlE.writeObject(new ClientProperties("127.0.0.1",5400));
				xmlE.close();
			}
			XMLDecoder xmlD= new XMLDecoder(new FileInputStream("./resources/client properties.xml"));
			clientProperties=(ClientProperties)xmlD.readObject();
			
			
			xmlD.close();
			
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleDirPath(String[] path)
	{
		//client doesn't enter path
		if(path==null)
		{
			errorCode="Invalid path";
			setChanged();
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		
		//if the path has spaces
		StringBuilder sb=new StringBuilder();
		for(int i=0;i<path.length;i++)
		{
			if(i==path.length-1)
			{
				sb.append(path[i]);
			}
			else
			{
				sb.append(path[i]+" ");
			}
			
		}
		String whole_path=sb.toString();

		File f = new File(whole_path);

		//if i got a valid path
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
	/**
	 * {@inheritDoc}
	 */
	public void handleGenerate3dMaze(String[] mazeParam)
	{
		
		if(mazeParam==null)//didn't get any prameters
		{
			errorCode="Invalid parameters";//entering error code
			setChanged();//Notifying the observers to fetch the error code and show it in view
			String[] s=new String[1];
			s[0]="error";
			notifyObservers(s);
			return;
		}
		
		if (mazeParam.length != 5) 
		{
			if(mazeParam.length!=4)
			{
				errorCode="Invalid number of parameters";
				setChanged();
				String[] s=new String[1];
				s[0]="error";
				notifyObservers(s);
				return;
			}
			
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
				if(mazeParam.length==4)//if client entered command without the algorithm
				{//take the algorithm from the xml file of properties
					if(properties.getAlgorithmToGenerateMaze().intern()=="simple")
					{
						mg = new SimpleMaze3dGenerator();
					}
					else if(properties.getAlgorithmToGenerateMaze().intern()=="prim")
					{
						mg = new MyMaze3dGenerator();
					}
					else
					{
						errorCode="Invalid algorithm";
						return "error";
					}
				}
				else if(mazeParam.length==5)//if algorithm entered
				{
					if (mazeParam[mazeParam.length - 1].intern() == "simple")
					{
						mg = new SimpleMaze3dGenerator();
					} 
					else if (mazeParam[mazeParam.length - 1].intern() == "prim") {
						mg = new MyMaze3dGenerator();
					} 
					else 
					{
						errorCode="Invalid algorithm";
						
						return "error";
					}
				}
				else
				{
					errorCode="Invalid algorithm";
					return "error";
				}
				
				

				mazeCollection.remove(mazeParam[0].toString());//removes a maze with the same name,if exists

				//generating a maze with the specified sizes
				Maze3d maze=mg.generate(Integer.parseInt(mazeParam[1]),Integer.parseInt(mazeParam[2]), Integer.parseInt(mazeParam[3]));
				//entering the maze into the collection,in order to take it out when necessary
				mazeCollection.put(mazeParam[0].toString(),maze);
				
				//System.out.println("in future "+maze);
				generate3dmazeCode="maze " + mazeParam[0].toString() + " is ready";
				
				
				return "generate 3d maze";//returning string into Future object
				
				
			}
		});
		
		//future take time to calculate thats why we put future.get into thread
		threadPool.execute(new Runnable() {
			
			@Override
			public void run() {
			
				try 
				{
					
					String message=future.get();
					if(message.intern()=="error")
					{

						setChanged();
						String[] s=new String[1];
						s[0]="error";
						notifyObservers(s);
						
						return;
					}
					else
					{
						//System.out.println("in get of furure ");
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
	/**
	 * {@inheritDoc}
	 */
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

	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayCrossSectionBy(String[] paramArray)
	{
		//validation check
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

		//if cross section of x
		if (paramArray[0].intern() == "x") 
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try 
			{
				//putting the last cross section in its variable
				crossSection = maze.getCrossSectionByX(Integer.parseInt(paramArray[1]));
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
			catch (IndexOutOfBoundsException e) {
				errorCode="x coordinate out of bounds";
				String[] s=new String[1];
				s[0]="error";
				setChanged();
				notifyObservers(s);
				return;
			}

		} 
		else if (paramArray[0].intern() == "y") //y cross section
		{
			Maze3d maze = mazeCollection.get(paramArray[3].toString());//taking the maze of the collection by its name
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
	/**
	 * {@inheritDoc}
	 */
	public void handleError(String[] paramArr)
	{

		errorCode="Invalid command";
		String[] s=new String[1];
		s[0]="error";
		setChanged();
		notifyObservers(s);
		return;
	}
	
	/**
	 * {@inheritDoc}
	 */
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
		
		//shutting down the threads
		threadPool.shutdown();
		try 
		{
			int i=0;
			while(!threadPool.awaitTermination(10, TimeUnit.SECONDS))
			{
				System.out.println(i++);
			}
			//waits for the threads to finish and shutting down the thread pool
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		setChanged();
		String[] s=new String[1];
		s[0]="exit";
		notifyObservers(s);
			
	}
	/**
	 * {@inheritDoc}
	 */
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
			//writing the maze shrinked
			MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(paramArray[1].toString()));
			out.write(ByteBuffer.allocate(4).putInt(size).array());// first writing the size of the maze
			out.write(maze.toByteArray());
			out.close();
			
			//Notifying that the maze has been saved to file
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
	/**
	 * {@inheritDoc}
	 */
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
			in.close();
			mazeCollection.put(paramArray[1], new Maze3d(byteArr));
			//if we succeded in loading maze,put it to the hash map which maps between file names and mazes
			mazeToFile.put(paramArray[1], paramArray[0]);
			
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
	
	
	/**
	 * {@inheritDoc}
	 */
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
		mazeSize=maze.toByteArray().length;//getting the number of bytes of maze
		String[] s=new String[1];
		s[0]="maze size";
		setChanged();
		notifyObservers(s);
		return;
		
		
	}
	/**
	 * {@inheritDoc}
	 */
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
			
			fileSize=file.length();//checking the file size containing the maze
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
	/**
	 * {@inheritDoc}
	 */
	public void handleSolveMaze(String[] paramArray)
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
		
		
		
		if(paramArray.length!=4)
		{
			if(paramArray.length!=2)
			{
				if(paramArray.length!=1)
				{
					errorCode="Invalid amount of parameters";
					String[] s=new String[1];
					s[0]="error";
					setChanged();
					notifyObservers(s);
					return;
				}
			}
		}
		//check if i generated maze with this name
		if(!(mazeCollection.containsKey(paramArray[0])))
		{
			errorCode="Maze doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}

		
		//thread which generates solution for the maze
		Future<String> future=threadPool.submit(new Callable<String>()
		{

			@Override
			public String call() throws Exception
			{
				
				StringBuilder algo=new StringBuilder();
				if(paramArray.length==1)//only maze name without the algorithm
				{
					algo.append(properties.getAlgorithmToSearch());//take the algorithm from properties file
				}
				else//client entered algorithm name
				{
					for(int i=1;i<paramArray.length;i++)//constructing the algorithm name(because there are algorithms with more than one word)
					{
						if(i==paramArray.length-1)
						{
							algo.append(paramArray[i]);
						}
						else
						{
							algo.append(paramArray[i]+" ");
						}
					}
				}
				//if the algorithm is invalid
				if((algo.toString().intern()!="bfs")&&(algo.toString().intern()!="astar manhatten distance")&&(algo.toString().intern()!="astar air distance"))
				{
					errorCode="Invalid algorithm";
					return "error";
				}
				
				
				//asking the server for solution
				Solution<Position> sol=askingTheServerForSolution(algo.toString(),mazeCollection.get(paramArray[0]));
				
				//if there was some problem with connection returning null,and showing error
				if(sol==null)
				{
					errorCode="problem with server,try to open the server";
					return "error";
				}
				//put the maze in the collection,in order to take it out when display command written
				mazeSolutions.put(mazeCollection.get(paramArray[0]),sol );
				
				solveMazeCode="Solution for "+paramArray[0]+ " is ready";
				return "solve";

			}
		});
		
		//putting the future.get in thread ,not waiting for response for a long time
		threadPool.execute(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				try 
				{
					String message=future.get();
					if(message.intern()=="error")
					{

						//we already put the error in errorCode
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
						s[0]="solve";
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
	/**
	 * {@inheritDoc}
	 */
	public void handleDisplaySolution(String[] paramArray)
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
			errorCode="Invalid amount of parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if(!(mazeCollection.containsKey(paramArray[0])))
		{
			errorCode="Maze with this name doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if(mazeSolutions.containsKey(mazeCollection.get(paramArray[0])))
		{
			String[] s=new String[2];
			s[0]="display solution";
			s[1]=paramArray[0].toString();
			//passing the name of the maze that his solution is ready,that presenter will know what to take out

			setChanged();
			notifyObservers(s);
			return;
		}
		else
		{
			errorCode="Solution doesn't exists(use solve command first)";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleSolveFrom(String[] paramArray)
	{
		//solving mazes that there start point is now in the start position
		if(paramArray==null)
		{
			errorCode="Invalid parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		
		//checking number of parameters
		if(paramArray.length!=5)
		{
			if(paramArray.length!=7)
			{
				if(paramArray.length!=4)
				{
					errorCode="Invalid amount of parameters";
					String[] s=new String[1];
					s[0]="error";
					setChanged();
					notifyObservers(s);
					return;
				}

			}
		}

		
		//thread which generates solution for the maze
		Future<String> future=threadPool.submit(new Callable<String>()
		{

			@Override
			public String call() throws Exception
			{
				
				//constructing the name of the algorithm to solve the maze
				StringBuilder algo=new StringBuilder();
				if(paramArray.length==4)
				{
					algo.append(properties.getAlgorithmToSearch());
				}
				else
				{
					int length;
					if(paramArray.length==5)
					{
						length=2;
					}
					else
					{
						length=4;
					}
					for(int i=1;i<length;i++)
					{
						if(i==length-1)
						{
							algo.append(paramArray[i]);
						}
						else
						{
							algo.append(paramArray[i]+" ");
						}
					}
				}
				
				
				//taking out the maze from collection
				Maze3d maze=new Maze3d(mazeCollection.get(paramArray[0]).toByteArray());
				//generating a new place in memory,in order not to change the maze in mazSolutions
				int x=Integer.parseInt(paramArray[paramArray.length-3]);
				int y=Integer.parseInt(paramArray[paramArray.length-2]);
				int z=Integer.parseInt(paramArray[paramArray.length-1]);
				maze.setStartPosition(new Position(x,y,z));//local changing
				//changing the start position of the maze,to solve the maze from another point in the maze
				
				
				Solution<Position> sol=askingTheServerForSolution(algo.toString(), maze);
				//asking the server for solution
				
				if(sol==null)//if there was a problem with the connection
				{
					errorCode="problem with server,try openning the server";
					return "error";
				}
				
				//now the maze entered to the hash map is a maze with other start position,
				//it is different from the same maze with the valid start position
				//thats why it doesn't override the previous one(good hash code)
				mazeSolutions.put(maze, sol);
				
				//this maze has now new place,for half solution mazes
				mazeHalfCollection.put(paramArray[0].toString(),maze);
				solveHalfMazeCode="Half solution for "+paramArray[0]+ " is ready";
				return "solve from";
	
			}
		});
		
		threadPool.execute(new Runnable() 
		{
			
			@Override
			public void run() 
			{
				try 
				{
					String message=future.get();
					if(message.intern()=="error")
					{
						//we already put the error in errorCode
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
						s[0]="solve from";
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
	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayHalfSolution(String[] paramArray)
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
			errorCode="Invalid amount of parametrs";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if(!(mazeHalfCollection.containsKey(paramArray[0].toString())))
		{
			errorCode="Maze with this name doesn't exists";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
		
		if(mazeSolutions.containsKey(mazeHalfCollection.get(paramArray[0])))
		{
			String[] s=new String[2];
			s[0]="display half solution";
			s[1]=paramArray[0].toString();

			setChanged();
			notifyObservers(s);
			return;
		}
		else
		{
			errorCode="Solution doesn't exists(use solve command first)";
			String[] s=new String[1];
			s[0]="error";
			setChanged();
			notifyObservers(s);
			return;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleLoadXML(String[] path)
	{
		//building the path,if it has spaces
		StringBuilder sb=new StringBuilder();
		
		if(path==null)
		{
			sb.append("./resources/properties.xml");
		}
		if(path.length==0)
		{
			sb.append("./resources/properties.xml");
		}
		else if(path[0].intern()=="null")
		{
			sb.append("./resources/properties.xml");
		}
		else
		{
			for(int i=0;i<path.length;i++)
			{
				if(i==path.length-1)
				{
					sb.append(path[i]);
				}
				else
				{
					sb.append(path[i]+" ");
				}
			}
		}

		try
		{
			File f=new File(sb.toString());
			//if file doesn't exists
			if(!f.exists())
			{
				//load it
				XMLEncoder xmlE = new XMLEncoder(new FileOutputStream(sb.toString()));
				xmlE.writeObject(new Properties(10, "astar air distance", "prim","gui"));
				xmlE.close();
			}
			//now it exists,take the properties from the file
			XMLDecoder xmlD= new XMLDecoder(new FileInputStream(sb.toString()));
			properties=(Properties)xmlD.readObject();

			xmlD.close();
			
			
			String[] s=new String[1];
			s[0]="load xml";
			setChanged();
			notifyObservers(s);
			return;
			
		} 
		catch (FileNotFoundException e) 
		{
			
			e.printStackTrace();
		}
		
	}


	/**
	 * asking the server to generate solution
	 * @param algo the algoritm to search
	 * @param maze the maze that need solution
	 * @return solution for the specified maze
	 */
	private Solution<Position> askingTheServerForSolution(String algo,Maze3d maze)
	{
		Socket theServer=null;
		PrintWriter outToServer=null;
		BufferedReader inFromServer=null;
		
		ObjectOutputStream mazeToServer=null;
		ObjectInputStream solutionFromServer=null;
		
		try {
			//defines the socket and the input and output sources
			theServer = new Socket(clientProperties.getServerIp().toString(),clientProperties.getServerPort());
			
			//now we connected to the server
			//taking out of socket his output and input streams
			outToServer = new PrintWriter(theServer.getOutputStream());
			inFromServer=new BufferedReader(new InputStreamReader(theServer.getInputStream()));

			//writing hello to the server
			outToServer.println("hello\n");
			outToServer.flush();
			
			//reading ok
			inFromServer.readLine();
			
			ArrayList<Object> packetToServer=new ArrayList<Object>();
			packetToServer.add(algo);
			packetToServer.add(maze.toByteArray());
			
			//sending array list to the server,containing the algorithm that solve the maze and the maze
			mazeToServer=new ObjectOutputStream(theServer.getOutputStream());
			mazeToServer.writeObject(packetToServer);
			mazeToServer.flush();
			
			
			solutionFromServer=new ObjectInputStream(theServer.getInputStream());
			//reading the solution
			Solution<Position> sol=(Solution<Position>)solutionFromServer.readObject();

			mazeToServer.close();
			solutionFromServer.close();
			outToServer.close();
			inFromServer.close();
			
			theServer.close();
			
			return sol;
		}//in any other problem with server we return null
		catch (IOException e) 
		{
			//e.printStackTrace();
			return null;
		}
		catch (ClassNotFoundException e) 
		{
			//e.printStackTrace();
			try {
				theServer.close();
			} catch (IOException e1) {
				return null;
				//e1.printStackTrace();
			}
			return null;
		}

		
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	public Properties getProperties() {
		return properties;
	}
	/**
	 * {@inheritDoc}
	 */
	public String getSolveHalfMazeCode() {
		return solveHalfMazeCode;
	}
	/**
	 * {@inheritDoc}
	 */
	public Solution<Position> getSpecificSolution(String name)
	{
		return mazeSolutions.get(mazeCollection.get(name));
	}
	/**
	 * {@inheritDoc}
	 */
	public Solution<Position> getSpecificHalfSolution(String name)
	{
		return mazeSolutions.get(mazeHalfCollection.get(name));
	} 
	/**
	 * {@inheritDoc}
	 */
	public String getSolveMazeCode() 
	{
		return solveMazeCode;
	}
	/**
	 * {@inheritDoc}
	 */
	public String getLoadMazeCode()
	{
		return loadMazeCode;
	}
	/**
	 * {@inheritDoc}
	 */
	public String getSaveMazeCode()
	{
		return saveMazeCode;
	}
	/**
	 * {@inheritDoc}
	 */
	public String getErrorCode() {
		return errorCode;
	}
	/**
	 * {@inheritDoc}
	 */
	public String[] getDirList() {
		return dirList;
	}
	/**
	 * {@inheritDoc}
	 */
	public String getGenerate3dmazeCode() {
		return generate3dmazeCode;
	}
	/**
	 * {@inheritDoc}
	 */	
	public byte[] getSpecificMazeFromColllection(String name)
	{
		return mazeCollection.get(name).toByteArray();
	}
	/**
	 * {@inheritDoc}
	 */
	public int[][] getCrossSection() 
	{
		return crossSection;
	}
	/**
	 * {@inheritDoc}
	 */
	public int getMazeSize() 
	{
		return mazeSize;
	}
	/**
	 * {@inheritDoc}
	 */
	public long getFileSize() 
	{
		return fileSize;
	}
	

	
}
