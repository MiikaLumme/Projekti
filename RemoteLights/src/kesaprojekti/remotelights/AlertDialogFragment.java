package kesaprojekti.remotelights;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class AlertDialogFragment extends DialogFragment {
	Context context;
	
	public AlertDialogFragment (Context context) {
		this.context = context;
	}
	
	 @Override
	 public Dialog onCreateDialog(Bundle savedInstanceState) {
	   
	  OnClickListener positiveClick = new OnClickListener() {   
	   @Override
	   public void onClick(DialogInterface dialog, int which) {    
		   WifiManager wifi = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
		   wifi.setWifiEnabled(true);
		   Toast.makeText(getActivity().getBaseContext(), "You are now connected to WiFi", Toast.LENGTH_LONG).show();
	   }
	  };
	   
	  OnClickListener negativeClick = new OnClickListener() {   
	   @Override
	   public void onClick(DialogInterface dialog, int which) {
	   ((Activity) context).finish();
	   }
	  };
	 
	  AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
	  builder.setTitle("Do you want to turn WiFi on?");
	  builder.setMessage("This application cannot be used without WiFi connection. Pressing No will close the application.");
	  builder.setPositiveButton("Yes", positiveClick);
	  builder.setNegativeButton("No", negativeClick);
	 
	  
	  Dialog dialog = builder.create();
	  return dialog;
	 }
	 
	}
