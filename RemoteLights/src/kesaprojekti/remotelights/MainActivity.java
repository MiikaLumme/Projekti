package kesaprojekti.remotelights;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {
	   
    private Button        startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        startButton    = (Button)findViewById(R.id.startButton);
        startButton.setOnClickListener(this);
       
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
       
        case R.id.startButton:
            Intent i1 = new Intent (this, SelectLightsActivity.class);
            startActivity(i1);
            break;

        }
       
    }
}
