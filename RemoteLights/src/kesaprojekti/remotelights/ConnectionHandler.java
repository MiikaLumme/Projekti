package kesaprojekti.remotelights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;


public class ConnectionHandler extends Thread implements Runnable {
	private int	address;
	Context	context;
	
	public ConnectionHandler(Context context)	{
		this.context = context;
	}
	
	
	public void start(int address)	{
		this.address = address;
		start();
	}

	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String serverName = "10.177.138.23";
			    int port = 4444;
			    try
			      {
			    	
			    	 Socket client = new Socket(serverName, port);

			         OutputStream outToServer = client.getOutputStream();
			         DataOutputStream out =
			                       new DataOutputStream(outToServer);
			         out.writeInt(address);

			         InputStream inFromServer = client.getInputStream();
			         DataInputStream in =
			                        new DataInputStream(inFromServer);
			         int i = in.readInt();
			         
			         Helper h = new Helper();
			         h.saveStatus(i, "status", context);
			         	         
			         client.close();
			         
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
				
			}
			
			
		}).start();
		
	}
	
	
	
	

}
