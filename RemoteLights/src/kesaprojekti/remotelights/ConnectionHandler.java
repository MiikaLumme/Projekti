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
			    	Socket client = new Socket(serverName, port);

			         OutputStream outToServer = client.getOutputStream();
			         DataOutputStream out =
			                       new DataOutputStream(outToServer);
			         out.writeUTF("Address: "
			                      + address + " ON");

			         InputStream inFromServer = client.getInputStream();
			         DataInputStream in =
			                        new DataInputStream(inFromServer);
			         client.close();
			         
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
				
			}
		}).start();
		
	}
	

}
