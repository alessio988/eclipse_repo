package pdm.pkg.switchimage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ToggleButton;

public class SwitchImageActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final ImageView iv = (ImageView) findViewById (R.id.imageView1);
        ToggleButton btn = (ToggleButton) findViewById (R.id.toggleButton1);
        
        // trucchetto: dopo "new" ctrl+space 
        btn.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					iv.setImageResource(R.drawable.button_pause);
				}else{
					iv.setImageResource(R.drawable.button_play);
				}
				
			}
		});
        
    }
  
}