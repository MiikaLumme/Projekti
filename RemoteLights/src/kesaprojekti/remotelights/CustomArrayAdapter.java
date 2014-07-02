package kesaprojekti.remotelights;

import java.util.List;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.view.LayoutInflater;
import android.view.View;

public class CustomArrayAdapter extends ArrayAdapter<String>{
	
	

	public CustomArrayAdapter(Context context,
			List<String> objects) {
		super(context, R.layout.list_item, objects);
		
	}
	


}
