package kesaprojekti.remotelights;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements OnClickListener {
	   
	private static final String START_LBL	= "Start";
	private static final int startId		= 10;
	
    private Button        				startButton;
  
    private LinearLayout				LLayout;
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       
        LLayout		= (LinearLayout)findViewById(R.id.LinearLayout1);
        
        
        //startButton
        Helper h = new Helper();
        startButton 	=	h.makeButton(startId, START_LBL, this);
        LLayout.addView(startButton);

    }
    
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
       
        case startId:
        	ConnectionHandler thread = new ConnectionHandler(this);
        	thread.start();

            Intent i1 = new Intent (this, SelectLightsActivity.class);
            startActivity(i1);
            break;
        }
       
    }
    
    
    
}
