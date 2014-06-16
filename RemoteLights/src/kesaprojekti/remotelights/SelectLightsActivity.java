package kesaprojekti.remotelights;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.ToggleButton;

public class SelectLightsActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	public static final String PREFS_NAME = "PrefsFile";

	//ha
    private Button         	   	exitButton;
    private Button				lightButton;
    private ToggleButton		toggleButton;
    private Spinner            	roomSpinner;
    private Spinner            	lightSpinner;
    private ArrayList<String>  	roomArray;
    private ArrayList<String>  	lightArray;
    private ArrayList<Boolean>	buttonArray;
    private LinearLayout		LL;
    private ConnectionHandler	thread;
    SharedPreferences			sharedPrefs;		
    SharedPreferences.Editor	editor;
    
    private static final int exitId	 = 50;
    private static final int lightId = 5;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lights);
       
        //Linear Layout
        LL = (LinearLayout)findViewById(R.id.LinearLayout2);
        
        //zone
        roomSpinner =     (Spinner)findViewById(R.id.roomSpinner);
        roomSpinner.setOnItemSelectedListener(this);
        
        //gets the zonenames, sets them into spinner
        try {
        	PullParserHandler	parser	= new PullParserHandler();
        	parser.parse(getAssets().open("settings.xml"), "zonename");
			roomArray = parser.getNames();
			ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, roomArray);
			roomSpinner.setAdapter(adapter);
			adapter.notifyDataSetChanged();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
      
        lightSpinner =     (Spinner)findViewById(R.id.lightSpinner);
        lightSpinner.setOnItemSelectedListener(this);
       
        //set persisted pos to spinner, calls onItemSelected for roomSpinner (lightSpinner is set in onItemSelected)
        sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
        roomSpinner.setSelection(sharedPrefs.getInt("roomspinner", 0));
        //sets persistent pos after roomspinner persisted pos has been set
        lightSpinner.setSelection(sharedPrefs.getInt("lightspinner", 0)); //depending on where I put this bit, it breaks stuff and I'm still not sure if it works
      
        //Exit Button
        Button exitButton = new Button(this);
        exitButton.setText("Exit");
        exitButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        exitButton.setId(exitId);
        exitButton.setOnClickListener(this);
        LL.addView(exitButton);
   
    }
    
    public void startThread() {
    	thread = new ConnectionHandler();
    	thread.start();
    }
 
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {
    	PullParserHandler	parser	= new PullParserHandler();

    	 switch (arg0.getId()) {
    	 case R.id.roomSpinner:
    		 //sets spinner pos to shared prefs (persisting spinner pos)
    		 sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
    		 editor		= sharedPrefs.edit();
    		 editor.putInt("roomspinner", arg2);
    		 
    		 String item =    arg0.getItemAtPosition(arg2).toString();
       	//gets targetnames, set them into spinner
	           try {
	        	parser.parse(getAssets().open("settings.xml"), item);
	   			lightArray = parser.getNames();
	   			ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lightArray);
	   			lightSpinner.setAdapter(adapter);
	   			//gets button type
	   			buttonArray = parser.getButtonType();
	   		} catch (IOException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
   
          break; 
          
    	 case R.id.lightSpinner:
    		 	  
    		 //clear old light button
    		 LL.removeView(lightButton);
    		 
    		 //check's the type of button and sets the right button
    		 if (buttonArray.get(arg2)) {
    			 
    			//LightsButton
        	     lightButton = new Button(this);
        	     lightButton.setText("Lights");
        	     lightButton.setId(lightId);
        	     lightButton.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        	     lightButton.setOnClickListener(this);
        	     LL.addView(lightButton);
        	     
    			 
    		 }
    		 else	{
    			 //other types of button
    		 }

    		 //sets spinner pos to shared prefs
    		 sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
    		 editor		= sharedPrefs.edit();
    		 editor.putInt("lightspinner", arg2);
		 
    		 break; 		        
         }
    	 editor.commit();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
       
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
       
        case lightId:
        startThread();
        break;
        	
        case exitId:
    //    finish();  
        break;
        }
       
    }
   
}