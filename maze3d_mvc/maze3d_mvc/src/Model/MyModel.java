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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import algorithms.mazeGenerators.Maze3d;
import algorithms.mazeGenerators.Maze3dGenerator;
import algorithms.mazeGenerators.MyMaze3dGenerator;
import algorithms.mazeGenerators.SimpleMaze3dGenerator;
import controller.Controller;
import io.MyCompressorOutputStream;
import io.MyDecompressorInputStream;

public class MyModel extends CommonModel {
	HashMap<String, Maze3d> mazeCollection;
	ExecutorService threadPool;

	/**
	 * {@inheritDoc}
	 */
	public MyModel(Controller c) {
		super(c);
		mazeCollection = new HashMap<String, Maze3d>();
		threadPool = Executors.newFixedThreadPool(10);

	}

	/**
	 * {@inheritDoc}
	 */
	public void HandleDirPath(String[] paramArray) {
		if (paramArray.length != 1) // path does'nt entered
		{
			c.passError("Invalid path");
			return;
		}
		File f = new File(paramArray[0].toString());

		if ((f.list() != null) && (f.list().length > 0)) {
			c.passDirPath(f.list());
		} else if (f.list() == null) // invalid path
		{
			c.passError("Invalid path");
			return;
		} else // if there is nothing in the list
		{
			c.passError("Empty folder");
			return;
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleGenerate3dMaze(String[] paramArray) {
		if (paramArray.length != 5) {
			c.passError("Invalid number of parameters");
			return;
		}
		try {
			if ((Integer.parseInt(paramArray[1]) <= 0) || (Integer.parseInt(paramArray[2]) <= 0)
					|| (Integer.parseInt(paramArray[3]) <= 0)) {// checking if
																// the sizes of
																// the maze
																// passed valid
				c.passError("Invalid parameters");
				return;
			}
		} catch (NumberFormatException e) {
			c.passError("Invalid parameters");
			return;
		}

		threadPool.execute(new Runnable() {
			public void run() {

				Maze3dGenerator mg;
				if (paramArray[paramArray.length - 1].intern() == "simple") {
					mg = new SimpleMaze3dGenerator();
				} else if (paramArray[paramArray.length - 1].intern() == "prim") {
					mg = new MyMaze3dGenerator();
				} else {
					c.passError("Invalid algorithm name");
					return;
				}

				// constructing the name of the maze
				/*
				 * for(int i=0;i<paramArray.length-4;i++)//running till the end
				 * of the name,4 cells before the end(where the sizes and
				 * algorithm allocated { if(i==paramArray.length-5) {
				 * name.append(paramArray[i]);//end of string,without space }
				 * else { name.append(paramArray[i]+" "); }
				 * 
				 * }
				 */

				if (mazeCollection.containsKey(paramArray[0].toString())) {
					c.passError("This name already exists,choose another one.");
					return;
				}

				mazeCollection.put(paramArray[0].toString(), mg.generate(Integer.parseInt(paramArray[1]),
						Integer.parseInt(paramArray[2]), Integer.parseInt(paramArray[3])));
				// generate maze with specified algorithm ,with specified sizes.

				c.passGenerate3dMaze("maze " + paramArray[0].toString() + " is ready");
			}
		});

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayName(String[] paramArray) {
		if (paramArray.length != 1) {
			c.passError("Invalid command");
		}

		/*
		 * StringBuilder name=new StringBuilder(); int i;
		 * for(i=0;i<paramArray.length;i++) { if(i==paramArray.length-1) {
		 * name.append(paramArray[i]); } else { name.append(paramArray[i]+" ");
		 * }
		 * 
		 * 
		 * }
		 */

		try {
			if (!mazeCollection.containsKey(paramArray[0].toString())) {
				c.passError("Maze doesn't exists");
				return;
			}

			c.passDisplayName(mazeCollection.get(paramArray[0].toString()).toByteArray());
			// getting the maze from maze collection
		} catch (IOException e) {

			e.printStackTrace();
		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void handleDisplayCrossSectionBy(String[] paramArray) {
		if ((paramArray.length != 4) || (paramArray[2].intern() != "for")) {
			c.passError("Invalid amount of parameters");
			return;
		}
		/*
		 * StringBuilder name=new StringBuilder(); int i;
		 * for(i=3;i<paramArray.length;i++) { if(i==paramArray.length-1) {
		 * name.append(paramArray[i]); } else { name.append(paramArray[i]+" ");
		 * } }
		 */

		if (!mazeCollection.containsKey(paramArray[3].toString())) // checking
																	// if maze
																	// is in the
																	// collection
		{
			c.passError("This maze doesn't exists");
			return;
		}

		// Maze3d maze=mazeCollection.get(key);
		if (paramArray[0].intern() == "x") {
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				int[][] crossSection = maze.getCrossSectionByX(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} catch (NumberFormatException e) {
				c.passError("Invalid parameters");
				return;
			} catch (IndexOutOfBoundsException e) {
				c.passError("Invalid x coordinate");
				return;
			}

		} else if (paramArray[0].intern() == "y") {
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				int[][] crossSection = maze.getCrossSectionByY(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} catch (NumberFormatException e) {
				c.passError("Invalid parameters");
				return;
			} catch (IndexOutOfBoundsException e) {
				c.passError("Invalid y coordinate");
				return;
			}
		} else if (paramArray[0].intern() == "z") {
			Maze3d maze = mazeCollection.get(paramArray[3].toString());
			try {
				int[][] crossSection = maze.getCrossSectionByZ(Integer.parseInt(paramArray[1]));
				c.passDisplayCrossSectionBy(crossSection);
				return;
			} catch (NumberFormatException e) {
				c.passError("Invalid parameters");
				return;
			} catch (IndexOutOfBoundsException e) {
				c.passError("Invalid z coordinate");
				return;
			}
		} else {
			c.passError("Invalid parameters");
			return;
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleSaveMaze(String[] paramArray) {

		if (paramArray.length != 2) {
			c.passError("Invalid amount of parameters");
			return;
		}

		try {
			
			if (!(mazeCollection.containsKey(paramArray[0].toString()))) {
				c.passError("maze doesn't exists");
				return;
			}
			
			
			Maze3d maze = mazeCollection.get(paramArray[0].toString());
			
			int size=maze.toByteArray().length;
			
			MyCompressorOutputStream out = new MyCompressorOutputStream(new FileOutputStream(paramArray[1].toString()));
			
			out.write(ByteBuffer.allocate(4).putInt(size).array());//first writing the size of the maze
			out.write(maze.toByteArray());
			c.passSaveMaze(paramArray[0].toString() + " has been saved");
			out.close();
			return;
		} catch (FileNotFoundException e) {
			c.passError("File not found");
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public void handleLoadMaze(String[] paramArray) {
		if (paramArray.length != 2) {
			c.passError("Invalid amount of parameters");
			return;
		}

		try {
			
			if (mazeCollection.containsKey(paramArray[1])) {
				c.passError("Invalid name,this name is taken");
				return;
			}
			
			MyDecompressorInputStream in = new MyDecompressorInputStream(new FileInputStream(paramArray[0].toString()));
			
			//The ByteArrayOutputStream class stream creates a buffer in memory and all the data sent to the stream is stored in the buffer.
			ByteArrayOutputStream outByte=new ByteArrayOutputStream();
			
			//reading 4 bytes from file,which shows the size of the maze
			outByte.write(in.read());
			outByte.write(in.read());
			outByte.write(in.read());
			outByte.write(in.read());
			
			//creates input stream from a buffer initialize in bracelet
			ByteArrayInputStream inByte=new ByteArrayInputStream(outByte.toByteArray());//taking out the buffer I wrote to
			DataInputStream dis=new DataInputStream(inByte);//easier to read data from stream(can read primitive types
			
			//What I did:created a input stream and wrote there my 4 bytes from file.
			//than created input stream,where i put my byte array,than read from this buffer int.
			
			byte[] byteArr=new byte[dis.readInt()];//construct a array of byte,in the size readen from file.
			in.read(byteArr);
			mazeCollection.put(paramArray[1],new Maze3d(byteArr));
			c.passLoadMaze(paramArray[1]+" has been loaded from file: "+paramArray[0]);
			in.close();
			return;
		} 
		catch (FileNotFoundException e) 
		{
			c.passError("File not found");
			return;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
