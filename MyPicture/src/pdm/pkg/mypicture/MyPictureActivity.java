package pdm.pkg.mypicture;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MyPictureActivity extends Activity {
    /** Called when the activity is first created. */
	
	//******************** Metodo onCreate*********************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);        
        setContentView(R.layout.secondview);
                
        ImageView buttonServer = (ImageView) findViewById(R.id.imageView2);
        ImageView buttonClient = (ImageView) findViewById(R.id.imageView3);
        ImageView buttonInfo = (ImageView) findViewById(R.id.imageView4);
        buttonServer.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyPictureActivity.this,SetInfo.class);
				intent.putExtra("ext", "server");
				startActivity(intent);
			}
		});  
        
        buttonClient.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MyPictureActivity.this,SetInfo.class);
				intent.putExtra("ext", "client");
				startActivity(intent);
			}
		});  
        
      buttonInfo.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});    
    }
}