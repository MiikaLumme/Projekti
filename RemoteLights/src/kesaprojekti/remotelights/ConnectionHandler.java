package kesaprojekti.remotelights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

public class ConnectionHandler extends Thread implements Runnable {
	private int	address;
	Context	context;
	
	public ConnectionHandler(Context context)	{
		this.context = context;
	}
	
<<<<<<< HEAD
	public void saveStatus(int i)	{
		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		 SharedPreferences.Editor	editor		= sharedPrefs.edit();
		 editor.putInt("status", i);
		 editor.commit();
	}
=======
>>>>>>> b7a1043ef5af317eea8d3fbf873581b209a75164
	
	public void start(int address)	{
		this.address = address;
		start();
	}

	@Override
	public void run() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				
				String serverName = "10.177.138.24";
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
<<<<<<< HEAD
			         Log.v(" return ", " Status: " +  in.readInt() );
			         
			         saveStatus(in.readInt());
			         
			         
=======
			         int i = in.readInt();
>>>>>>> b7a1043ef5af317eea8d3fbf873581b209a75164
			         
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
