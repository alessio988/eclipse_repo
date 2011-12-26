package pdm.pkg.dueactivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Main extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        Button startButton = (Button) findViewById (R.id.startButton);
        startButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		startActivity(new Intent(Main.this, Second.class));
        	}
        });
    }
}