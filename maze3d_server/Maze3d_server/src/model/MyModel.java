package model;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;



public class MyModel extends CommonModel 
{	
	
	
	int port;
	ServerSocket server;
	ClientHandler clinetHandler;
	int numOfClients;
	ExecutorService threadPool;
	volatile boolean stop;
	Thread mainServerThread;
	int clientsHandled;
	
	
	
	
	String openCode;
	String closeCode;
	String messageCode;
	
	
	public MyModel() 
	{
		//deciding which client handler to work with
		this.clinetHandler=new MazeSolutionClientHandler();
		
		//taking the properties from xml file(number of clients,port number)
		File f=new File("./resources/serverProperties.xml");
		if(!f.exists())
		{
			XMLEncoder xmlE;
			try {
				xmlE = new XMLEncoder(new FileOutputStream("./resources/serverProperties.xml"));
				xmlE.writeObject(new ServerProperties(5, 5400));
				xmlE.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		XMLDecoder xmlD;
		ServerProperties serverProperties;
		try 
		{
			xmlD = new XMLDecoder(new FileInputStream("./resources/serverProperties.xml"));
			serverProperties=(ServerProperties)xmlD.readObject();
			xmlD.close();
			
			this.threadPool = Executors.newFixedThreadPool(serverProperties.getNumberOfClients());
			this.port=serverProperties.getPort();
			this.server=new ServerSocket(this.port);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		
		
		this.clientsHandled=0;
		this.stop=false;
		
		
	}
	
	public void openTheServer()
	{
		
		try 
		{

			server.setSoTimeout(10*1000);//make  the server wait for accept for 1 minute
		}
		catch (SocketException e1) 
		{
			e1.printStackTrace();
		}
		
		
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop)// stopping when client press stop in main..parallel
				{
					try {
						final Socket someClient=server.accept();
						
						//Listens for a connection to be made to this socket and accepts it.
						System.out.println(someClient);
						if(someClient!=null)
						{
							//we put it in a thread in order to listen a amount of clients
							threadPool.execute(new Runnable() {									
								@Override
								public void run() {
									try{	

										clientsHandled++;
										
										messageCode="\thandling client "+clientsHandled;//counting the clients
										setChanged();
										notifyObservers("pass messages");
										

										//sending the task to another class,
										//because we wan't our code to handle different tasks,and be generic
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										
										someClient.close();
										
										messageCode="\tdone handling client "+clientsHandled;
										setChanged();
										notifyObservers("pass messages");
										
										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						//if minute passed and no client is connected,print:
						
						messageCode="no clinet connected...";
						setChanged();
						notifyObservers("pass messages");
						
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				messageCode="done accepting new clients.";
				setChanged();
				notifyObservers("pass messages");
				
				
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
		openCode="server is opened";
		setChanged();
		notifyObservers("open the server");
		
		
	}
	
	public void closeServer()
	{
		stop=true;
		
		clinetHandler.close();
		try 
		{
			// do not execute jobs in queue, continue to execute running threads
			messageCode="shutting down";
			setChanged();
			notifyObservers("pass messages");
			
			
			
			threadPool.shutdown();
			// wait 10 seconds over and over again until all running jobs have finished
			boolean allTasksCompleted=false;
			while(!(allTasksCompleted=threadPool.awaitTermination(10, TimeUnit.SECONDS)));
			//not busy waiting,because not asking each time again and again
			
			messageCode="all the tasks have finished";
			setChanged();
			notifyObservers("pass messages");
			
			
			//if we stack on the minute of the accept,and we already on stop=true,if we do close to the server,
			//this is a problem,so we need to close the thread
			if(mainServerThread!=null)
			{
				mainServerThread.join();		
				
				messageCode="main server thread is done";
				setChanged();
				notifyObservers("pass messages");
				
			}
			
			
			server.close();
			
			messageCode="server is safely closed";
			setChanged();
			notifyObservers("pass messages");
			
		} 
		catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		
		
		
		
		
		
		
		closeCode="server shut down has finished";
		setChanged();
		notifyObservers("close the server");
	}

	public String getCloseCode() {
		return closeCode;
	}

	public String getOpenCode() 
	{
		return openCode;
	}
	public String getMessageCode() {
		return messageCode;
	}


}
