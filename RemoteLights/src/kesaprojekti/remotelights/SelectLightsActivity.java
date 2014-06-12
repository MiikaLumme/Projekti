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
    SharedPreferences			sharedPrefs;		
    SharedPreferences.Editor	editor;
    
    private static final int exitId = 50;
   
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
			roomArray = parser.parse(getAssets().open("settings.xml"), "zonename");
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
  
//        //Toggle Button
//        ToggleButton toggleButton = new ToggleButton(this);
//        toggleButton.setTextOn("ON");
//        toggleButton.setTextOff("OFF");
//        toggleButton.setChecked(true);
//        toggleButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
//        LL.addView(toggleButton);
        
        //Exit Button
        Button exitButton = new Button(this);
        exitButton.setText("Exit");
        exitButton.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        exitButton.setId(exitId);
        exitButton.setOnClickListener(this);
        LL.addView(exitButton);
    }
 
    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {

    	 switch (arg0.getId()) {
    	 case R.id.roomSpinner:
    		 //sets spinner pos to shared prefs (persisting spinner pos)
    		 sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
    		 editor		= sharedPrefs.edit();
    		 editor.putInt("roomspinner", arg2);
    		 
    		 String item =    arg0.getItemAtPosition(arg2).toString();
//           Toast.makeText(this, item, Toast.LENGTH_LONG).show();        // Test purposes only. Remove at end.
       	Log.v("onSelected", item); 		//for testing
       	
       	//gets targetnames, set them into spinner
           try {
           	PullParserHandler	parser	= new PullParserHandler();
   			lightArray = parser.parse(getAssets().open("settings.xml"), item);
   			ArrayAdapter<String> adapter	= new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, lightArray);
   			lightSpinner.setAdapter(adapter);
   		} catch (IOException e) {
   			// TODO Auto-generated catch block
   			e.printStackTrace();
   		}
   
          break; 
          
    	 case R.id.lightSpinner:
    		 	  
    		PullParserHandler	parser	= new PullParserHandler();
    	  	LL.removeView(lightButton);  
    	  	buttonArray = parser.returnButtonType();

    		 Log.v("lighstpinner", "works"); //testing...
    		 
    		 //sets spinner pos to shared prefs
    		 sharedPrefs	= getSharedPreferences(PREFS_NAME, 0);
    		 editor		= sharedPrefs.edit();
    		 editor.putInt("lightspinner", arg2);
    		 //LightsButton
    	        lightButton = new Button(this);
    	        lightButton.setText("Lights");
    	        lightButton.setId(5);
    	        lightButton.setLayoutParams(new LayoutParams (LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
    	        LL.addView(lightButton);
    		 
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
       
        case exitId:
        finish();  
        break;
        }
       
    }
   
}