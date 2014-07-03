package kesaprojekti.remotelights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.SharedPreferences;
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
			         Log.v(" return ", " address: " +  in.readInt() );
			         
			       
			         
			         
			         
			         client.close();
			         
			      }catch(IOException e)
			      {
			         e.printStackTrace();
			      }
				
			}
		}).start();
		
	}
	
	
	
	

}
