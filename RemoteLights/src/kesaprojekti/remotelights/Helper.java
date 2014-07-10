package kesaprojekti.remotelights;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class Helper {

	public Button makeButton(int id, String label, Context context)	{
    	Button b = new Button(context);
        b.setText(label);
        b.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        b.setId(id);
        b.setOnClickListener((OnClickListener) context);
        
        return b;
    }
	
	public void saveStatus(int i, String string, Context context)	{
		 SharedPreferences sharedPrefs = PreferenceManager.getDefaultSharedPreferences(context);
		 SharedPreferences.Editor	editor		= sharedPrefs.edit();
		 editor.putInt(string, i);
		 editor.commit();	 
	}
	
}
