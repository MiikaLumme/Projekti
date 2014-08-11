package kesaprojekti.remotelights;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;

public class SelectLightsActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	private static final String	EXIT_LBL	= "Exit";
	private static final String	LIGHTS_LBL	= "Lights";
	private static final String	STATUS_LBL	= "Light status";
	private static final String	LIGHTS_PREF	= "lightspinner";
	private static final String ROOM_PREF	= "roomspinner";
	private static final int 	exitId		= 50;
    private static final int 	lightId		= 5;
    private static final int 	listId		= 10;    
	
    private Button         	   			exitButton;
    private Button						lightButton;
    private Button						listButton;
    private Spinner            			roomSpinner;
    private Spinner            			lightSpinner;
    private ArrayList<String>  			roomArray;
    private ArrayList<String>  			lightArray;
    private ArrayList<Boolean>			buttonArray;
    private ArrayList<Integer>			addressArray;
    private LinearLayout				LLLights;
    private LinearLayout				LLExit;
    private SharedPreferences			sharedPrefs;
    private int 						persInfoTag = 0;
    private char						statusTag = 0;
    private int							lightSpinnerPos;
    private Helper 						h;
    
    
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lights);
        
        //Linear Layout for buttons (any way of doing this without the extra layouts?)
        LLLights 	= (LinearLayout)findViewById(R.id.LLLights);
        LLExit		= (LinearLayout)findViewById(R.id.LLExit);
        
        //zone
        roomSpinner =     (Spinner)findViewById(R.id.roomSpinner);
        roomSpinner.setOnItemSelectedListener(this);
        
        //gets the zonenames, sets them into spinner
        
        setSpinner("zonename", roomArray, roomSpinner);
        	
		//target
        lightSpinner =     (Spinner)findViewById(R.id.lightSpinner);
        lightSpinner.setOnItemSelectedListener(this);
       
        //set persisted pos to spinner, calls onItemSelected for roomSpinner (lightSpinner is set in onItemSelected)
        sharedPrefs		= PreferenceManager.getDefaultSharedPreferences(this);
        roomSpinner.setSelection(sharedPrefs.getInt(ROOM_PREF, 0));
        
        //List button
        h 			= new Helper();
        listButton 	=  h.makeButton(listId, STATUS_LBL, this);
        LLExit.addView(listButton);
        
        //Exit Button
        exitButton	=	 h.makeButton(exitId, EXIT_LBL, this);
        LLExit.addView(exitButton);
   
    }
    
 
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {

    	 switch (arg0.getId()) {
    	 case R.id.roomSpinner:
    		 //sets spinner pos to shared prefs (persisting spinner pos)
    		 h.saveStatus(arg2, ROOM_PREF, this);
    		 
    		 String item =    arg0.getItemAtPosition(arg2).toString();
    		
    		 //gets targetnames, set them into spinner
    		 setSpinner(item, lightArray, lightSpinner);
    		 
    		 if(persInfoTag == 0)	{
    			 //sets persistent pos after roomspinner persisted pos has been set.
    		     lightSpinner.setSelection(sharedPrefs.getInt(LIGHTS_PREF, 0));
    		     persInfoTag = 1;
    		     }
    		 	
          break; 
          
    	 case R.id.lightSpinner:
    		 
    		//saves lightSpinner position 
    		 lightSpinnerPos = arg2;
    		 
    		 //check's the type of button and sets the right button
    		 if (buttonArray.get(arg2)) {
    			 makeSwitchButton();    					 
    		 }
    		 else	{
    			 //other types of button
    		 }

    		 //sets spinner pos to shared prefs
    		 h.saveStatus(arg2, LIGHTS_PREF, this);
    		 
 			 
 			
		 
    		 break; 		        
         }
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
       
    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
       
        case lightId:
        	startThread();
        	//remove old button add new one according to status tag, change the tag
        	LLLights.removeView(lightButton);
        	if (statusTag == 1)	{
        		lightButton = h.makeButton(lightId, "Switch off", this);
        		lightButton.setBackgroundColor(Color.GREEN);
        		statusTag = 0;
        	}
        	else	{
        		lightButton = h.makeButton(lightId, "Switch on", this);
        		lightButton.setBackgroundColor(Color.RED);
        		statusTag = 1;
        	}
        	LLLights.addView(lightButton);
        	
        	break;
        	
        case exitId:
	        finish();  
	        break;
	        
        case listId:
        	Intent intent = new Intent (this, ListActivity.class);
            startActivity(intent);
        	break;
        }
    }
      
    public void startThread() {
    	int address 				= addressArray.get(lightSpinnerPos);
    	ConnectionHandler thread	= new ConnectionHandler(this);
    	thread.start(address);
    }
    
    public void makeSwitchButton()	{
    	//clear old light button
		LLLights.removeView(lightButton);
  
    	int status = sharedPrefs.getInt("status", 0);
		int mask = 1 << (addressArray.get(lightSpinnerPos) - 1);
   		if ((status & mask) != 0)	{
   			lightButton = h.makeButton(lightId, "Switch off", this);
   			lightButton.setBackgroundColor(Color.GREEN);
   			statusTag	= 0;
   		} 
   		else	{
   			lightButton = h.makeButton(lightId, "Switch on", this);
   			lightButton.setBackgroundColor(Color.RED);
   			statusTag 	= 1;
   		}
		
		//lightButton = h.makeButton(lightId, LIGHTS_LBL, this);
		
		LLLights.addView(lightButton);    	
    }
    
    public void setSpinner(String item, ArrayList<String> a, Spinner s)	{
    	PullParserHandler parser = new PullParserHandler();
    	parser = parser.getParser(item, this);
    	
    	a = parser.getNames();
		ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, a);
		s.setAdapter(adapter);
		
		//gets button type and address, for second spinner
		if (item != "zonename")	{
			buttonArray 	= parser.getButtonType();
			addressArray	= parser.getAddress();
		}
    }    
}