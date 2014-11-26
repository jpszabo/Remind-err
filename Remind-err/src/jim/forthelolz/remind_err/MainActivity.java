package jim.forthelolz.remind_err;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;

public class MainActivity extends Activity {
	
	private Timer myTimer;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		myTimer = new Timer();
		myTimer.schedule(new TimerTask() {
			
			@Override
			public void run() {
				TimerMethod();
			}
		}, 0, 5000);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void TimerMethod() {
		this.runOnUiThread(Timer_Tick);
	}
	
	private Runnable Timer_Tick = new Runnable() {
		
		public void run() {
			NotificationCompat.Builder mBuilder = 
					new NotificationCompat.Builder(getApplicationContext())
						.setSmallIcon(R.drawable.ic_launcher)
						.setContentTitle("My notification")
						.setContentText("Hello world!");
			
			Intent notif_intent = new Intent(getApplicationContext(), NotifScreen.class);
			
			TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
			stackBuilder.addParentStack(NotifScreen.class);
			stackBuilder.addNextIntent(notif_intent);
			PendingIntent resultPendingIntent = 
					stackBuilder.getPendingIntent(
							0,
							PendingIntent.FLAG_UPDATE_CURRENT
					);
			
			mBuilder.setContentIntent(resultPendingIntent);
			
			NotificationManager mNotificationManager = 
					(NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
			mNotificationManager.notify(0, mBuilder.build());
		}
	};

}
