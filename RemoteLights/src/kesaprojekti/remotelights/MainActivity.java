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
	private static final String CONNECT_LBL	= "Connect";
	private static final int startId		= 10;
	private static final int connectId		= 20;
	
    private Button        				startButton;
  
    private LinearLayout				LLayout;
  

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
       
        LLayout		= (LinearLayout)findViewById(R.id.LinearLayout1);
        
        
        //startButton
        startButton 	=	makeButton(startId, START_LBL);
        LLayout.addView(startButton);

    }
    
    public Button makeButton(int id, String label)	{
    	Button b = new Button(this);
        b.setText(label);
        b.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        b.setId(id);
        b.setOnClickListener(this);
        
        return b;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
       
        case startId:
        	Thread thread = new ConnectionHandler(0);
        	thread.start();

            Intent i1 = new Intent (this, SelectLightsActivity.class);
            startActivity(i1);
            break;
  

        }
       
    }
    
    
    
}
