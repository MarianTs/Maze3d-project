package model;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyModel extends CommonModel 
{	
	
	
	int port;
	ServerSocket server;
	ClientHandler clinetHandler;
	int numOfClients;
	ExecutorService threadpool;
	volatile boolean stop;
	Thread mainServerThread;
	int clientsHandled=0;
	
	
	String openCode;
	String closeCode;
	
	public MyModel() 
	{
		this.clinetHandler=new MazeSolutionClientHandler();
	}
	
	public void openTheServer()
	{
		server=new ServerSocket(port);
		server.setSoTimeout(10*1000);//make  the server wait for accept for 1 minute
		threadpool=Executors.newFixedThreadPool(numOfClients);
		
		mainServerThread=new Thread(new Runnable() {			
			@Override
			public void run() {
				while(!stop)// stopping when client press stop in main..parallel
				{
					try {
						final Socket someClient=server.accept();
						//Listens for a connection to be made to this socket and accepts it.
						
						if(someClient!=null)
						{
							//we put it in a thread in order to listen a amount of clients
							threadpool.execute(new Runnable() {									
								@Override
								public void run() {
									try{	
										
										
										clientsHandled++;
										System.out.println("\thandling client "+clientsHandled);//counting the clients
										//sending the task to another class,
										//because we wan't our code to handle different tasks,and be generic
										clinetHandler.handleClient(someClient.getInputStream(), someClient.getOutputStream());
										someClient.close();
										System.out.println("\tdone handling client "+clientsHandled);										
									}catch(IOException e){
										e.printStackTrace();
									}									
								}
							});								
						}
					}
					catch (SocketTimeoutException e){
						//if minute passed and no client is connected,print:
						System.out.println("no clinet connected...");
					} 
					catch (IOException e) {
						e.printStackTrace();
					}
				}
				System.out.println("done accepting new clients.");
			} // end of the mainServerThread task
		});
		
		mainServerThread.start();
		
		
		openCode="server is opened";
		setChanged();
		notifyObservers("open the server");
	}
	
	public void closeServer()
	{
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

}
