package kesaprojekti.remotelights;

import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

public class ListActivity extends Activity implements OnItemClickListener, OnClickListener {	
	
	private ArrayList<Integer>		activeAddr;	
	private ArrayList<String>		activeTargets;
	private ArrayAdapter<String>	adapter;
	private	Button					exitButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		exitButton = (Button)findViewById(R.id.exitButton);
		exitButton.setOnClickListener(this);
		
		ListView lv = (ListView)findViewById(R.id.listView1);

		SharedPreferences sharedPrefs		= PreferenceManager.getDefaultSharedPreferences(this);
        int status = sharedPrefs.getInt("status", 0);
		
        PullParserHandler parser = new PullParserHandler();
    	parser = parser.getParser("zonename", this);
    	
    	// get all targets and addresses
    	ArrayList<String>	allTargets		= parser.getAllTargets();
    	ArrayList<Integer> 	allAddr			= parser.getAllAddr();
    	
    	activeTargets	= new ArrayList<String>();
    	activeAddr		= new ArrayList<Integer>();
    	
    	int targetCount = allTargets.size();
    	
    	//go through each element in allTargets and check if it's status is on
    	//if target is on add addresses and target names for into respective arrays 
    	for (int i = 0; i < allTargets.size(); i++)	{
    		int mask = 1 << (targetCount - 1);
    		if ((status & mask) != 0)	{
    			activeTargets.add(allTargets.get(i));
    			activeAddr.add(allAddr.get(i));
    		}
    		targetCount--;
    	}

    	adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, activeTargets);
		
		lv.setAdapter(adapter);
		
		lv.setOnItemClickListener(this);

	}
	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		//turn off target on click
		
		String item = (String) parent.getItemAtPosition(position);
		int address = activeAddr.get(position);
    	ConnectionHandler thread = new ConnectionHandler(this);
    	thread.start(address);
		
		Toast.makeText(getApplicationContext(), item + " has been switched off.", Toast.LENGTH_SHORT).show();
		//remove item from display
		activeTargets.remove(position);
		activeAddr.remove(position);
		adapter.notifyDataSetChanged();
		
	}


	@Override
	public void onClick(View v) {
		switch (v.getId())
		{
		case R.id.exitButton:
			finish();
			break;
			
		}
		
	}
}
