package kesaprojekti.remotelights;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class SelectLightsActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	public static final String PREFS_NAME = "PrefsFile";

	
    private Button         	   	exitButton;
    private Spinner            	roomSpinner;
    private Spinner            	lightSpinner;
    private ArrayList<String>  	roomArray;
    private ArrayList<String>  	lightArray;        
    SharedPreferences			sharedPrefs;		
    SharedPreferences.Editor	editor;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lights);
       
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
        
        
        
        //Exit Button
        exitButton = (Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
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
    		 
    		 Log.v("lighstpinner", "works"); //testing...
    		 
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
       
        case R.id.exitButton:
        finish();  
        break;
        }
       
    }
    
    
    
    
    
    
    
}