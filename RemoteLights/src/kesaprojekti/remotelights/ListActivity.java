package kesaprojekti.remotelights;

import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ListActivity extends Activity {
	private static final String XML_FILE	= "settings.xml";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		
		ListView lv = (ListView)findViewById(R.id.listView1);
		
		SharedPreferences sharedPrefs		= PreferenceManager.getDefaultSharedPreferences(this);
        Log.v("list activity", Integer.toString(sharedPrefs.getInt("status", 0)));
		
		
		
		PullParserHandler	parser	= new PullParserHandler();
    	
    	try {
			parser.parse(getAssets().open(XML_FILE), "zonename");
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    	ArrayList<String> allTargets	= new ArrayList<String>();
    	
    	allTargets	= parser.getAllTargets();
    	
    	ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.list_item, R.id.label, allTargets);
		
		lv.setAdapter(adapter);
    	
	}
}
