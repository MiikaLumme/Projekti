package kesaprojekti.remotelights;

import java.io.IOException;
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

public class SelectLightsActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	private static final String	PREFS_NAME  = "PrefsFile";
	private static final String XML_FILE	= "settings.xml";
	private static final String	EXIT_LBL	= "Exit";
	private static final String	LIGHTS_LBL	= "Lights";
	private static final String	LIGHTS_PREF	= "lightspinner";
	private static final String ROOM_PREF	= "roomspinner";
	private static final int 	exitId		= 50;
    private static final int 	lightId		= 5;
	
    private Button         	   			exitButton;
    private Button						lightButton;
    private Spinner            			roomSpinner;
    private Spinner            			lightSpinner;
    private ArrayList<String>  			roomArray;
    private ArrayList<String>  			lightArray;
    private ArrayList<Boolean>			buttonArray;
    private ArrayList<Integer>			addressArray;
    private LinearLayout				LLLights;
    private LinearLayout				LLExit;
    private SharedPreferences			sharedPrefs;
    private ConnectionHandler			thread;
    private SharedPreferences.Editor	editor;
    private int 						persInfoTag = 0;
    private int							lightSpinnerPos;
    
    
   
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
        sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
        roomSpinner.setSelection(sharedPrefs.getInt(ROOM_PREF, 0));
        
        //Exit Button
        exitButton	=	 makeButton(exitId, EXIT_LBL);
        LLExit.addView(exitButton);
   
    }
    
 
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {

    	 switch (arg0.getId()) {
    	 case R.id.roomSpinner:
    		 //sets spinner pos to shared prefs (persisting spinner pos)
    		 savePosition(ROOM_PREF, arg2);
    		 
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
    		 
    		 //clear old light button
    		 LLLights.removeView(lightButton);
    		 
    		 //check's the type of button and sets the right button
    		 if (buttonArray.get(arg2)) {
    			 
    			//LightsButton
    			lightButton = makeButton(lightId, LIGHTS_LBL);
    			LLLights.addView(lightButton);    			 
    		 }
    		 else	{
    			 //other types of button
    		 }

    		 //sets spinner pos to shared prefs
 			 savePosition(LIGHTS_PREF, arg2);
 			 
 			 //saves lightSpinner position 
 			 lightSpinnerPos = arg2;
		 
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
	        break;
        	
        case exitId:
	        finish();  
	        break;
        }
       
    }
   
    public Button makeButton(int id, String label)	{
    	Button b = new Button(this);
        b.setText(label);
        b.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        b.setId(id);
        b.setOnClickListener(this);
        
        return b;
    }
    
    public void startThread() {
    	thread = new ConnectionHandler();
    	thread.start();
    }
    
    public void setSpinner(String item, ArrayList<String> a, Spinner s)	{
    	PullParserHandler	parser	= new PullParserHandler();
    	
    	try {
			parser.parse(getAssets().open(XML_FILE), item);
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	a = parser.getNames();
		ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, a);
		s.setAdapter(adapter);

		
		//gets button type and address, for second spinner
		if (item != "zonename")	{
			buttonArray 	= parser.getButtonType();
			addressArray	= parser.getAddress();
		}
    }

   public void savePosition(String string, int i)	{
	   	sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
		editor		= sharedPrefs.edit();
		editor.putInt(string, i);
		editor.commit();
   }
    
}