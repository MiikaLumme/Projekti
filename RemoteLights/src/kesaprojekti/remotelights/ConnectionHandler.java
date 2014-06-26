package kesaprojekti.remotelights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.util.Log;

public class ConnectionHandler extends Thread implements Runnable {
	private int	address;
	
	
	public ConnectionHandler(int address)	{
		this.address = address;
	}

	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String serverName = "KLATL-136-02.edut.local";
			    int port = 4444;
			    try
			      {
			         Log.v("Client", "Connecting to " + serverName + " on port " + port);
			         Socket client = new Socket(serverName, port);
			         Log.v("Client", "Just connected to "
			                      + client.getRemoteSocketAddress());
			         OutputStream outToServer = client.getOutputStream();
			         DataOutputStream out =
			                       new DataOutputStream(outToServer);

			         out.writeUTF("Hello from "
			                      + client.getLocalSocketAddress());
			         InputStream inFromServer = client.getInputStream();
			         DataInputStream in =
			                        new DataInputStream(inFromServer);
			         Log.v("Client", "Server says " + in.readUTF());
			         client.close();
			         
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
				
			}
		}).start();
		
	}
	

}
