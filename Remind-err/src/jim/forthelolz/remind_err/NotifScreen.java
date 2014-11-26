package jim.forthelolz.remind_err;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class NotifScreen extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		TextView hello_world = (TextView)findViewById(R.id.TextView1);
		hello_world.setText("Hello notification!");
	}
	
	
}
