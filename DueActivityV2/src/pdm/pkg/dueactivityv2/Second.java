package pdm.pkg.dueactivityv2;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class Second extends Activity {
	
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        
        TextView label = (TextView) findViewById(R.id.textView1);
        String iltestoricevuto = getIntent().getExtras().getString("il testo nel box");
        label.setText(iltestoricevuto);
	}
}
