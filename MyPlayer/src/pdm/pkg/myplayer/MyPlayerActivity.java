package pdm.pkg.myplayer;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MyPlayerActivity extends Activity {
    /** Called when the activity is first created. */
	MediaPlayer mp;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
               
        mp = MediaPlayer.create(MyPlayerActivity.this, R.raw.dst);
        
        Button startBTN = (Button) findViewById(R.id.button1);
        Button pauseBTN = (Button) findViewById(R.id.button2);
        
        startBTN.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		mp.start();
        	}
        });
        
        pauseBTN.setOnClickListener(new OnClickListener(){
        	public void onClick(View v) {
        		mp.pause();
        	}
        });
        
     }
    
    @Override
	protected void onDestroy() {
        super.onDestroy();
        mp.release();
    }
    
}