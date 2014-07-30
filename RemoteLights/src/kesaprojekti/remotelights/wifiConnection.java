package kesaprojekti.remotelights;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.widget.Toast;


@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class wifiConnection {
	Context context;
	
	public wifiConnection (Context context) {
		this.context = context;
	}

	   public Boolean isWifiAvailable() {

	        try {
	            ConnectivityManager connectivityManager = (ConnectivityManager) context
	                    .getSystemService(Context.CONNECTIVITY_SERVICE);
	            NetworkInfo wifiInfo = connectivityManager
	                    .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

	            if (wifiInfo.isConnected()) {
	                return true;
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return false;
	    }
	   
	   
	   public void showStatus() {
           if (isWifiAvailable()) {
               Toast.makeText(context,
                       "You have WIFI connection", Toast.LENGTH_LONG)
                       .show();
           } else {

        	   AlertDialogFragment alert = new AlertDialogFragment(context);
        	   alert.show(((Activity) context).getFragmentManager(), "Alert Dialog");
 
           }
	   }
	
}
