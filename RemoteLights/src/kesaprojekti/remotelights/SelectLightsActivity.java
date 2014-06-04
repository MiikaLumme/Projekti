package kesaprojekti.remotelights;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class SelectLightsActivity extends Activity implements OnItemSelectedListener, OnClickListener {
	   
    private Button            exitButton;
    private Spinner            roomSpinner;
    private Spinner            lightSpinner;
    ArrayAdapter<String>      roomArray;
    ArrayAdapter<String>    lightArrayLR;        // NEEDS TO BE CHANGED
    ArrayAdapter<String>    lightArrayBR;
   
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_lights);
       
        roomSpinner =     (Spinner)findViewById(R.id.roomSpinner);
        roomSpinner.setOnItemSelectedListener(this);
        lightSpinner =     (Spinner)findViewById(R.id.lightSpinner);
        lightSpinner.setOnItemSelectedListener(this);
       
        //Rooms
        roomArray =new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        roomArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomSpinner.setAdapter(roomArray);
        roomArray.add("Living Room");
        roomArray.add("Bedroom");
        roomArray.setNotifyOnChange(true);
       
        //Lights
        lightArrayLR = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        lightArrayLR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lightArrayBR = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item);
        lightArrayBR.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        lightSpinner.setAdapter(lightArrayBR);
       
        //Exit Button
        exitButton = (Button)findViewById(R.id.exitButton);
        exitButton.setOnClickListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
            long arg3) {
       
        String item =    arg0.getItemAtPosition(arg2).toString();
        Toast.makeText(this, item, Toast.LENGTH_LONG).show();        // Test purposes only. Remove at end.
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
       
    }

   
   
   
   
   
   
    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
       
        case R.id.exitButton:
        finish();   
        }
       
    }
}