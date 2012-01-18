package pdm.pkg.dotoast;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class DoToastActivity extends Activity implements OnClickListener {
    /** Called when the activity is first created. */
	EditText edt;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      
        //Linear Layout
        LinearLayout ll =new LinearLayout(DoToastActivity.this);
        ll.setOrientation(LinearLayout.VERTICAL);
        ll.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));      
        
        //EditText
        edt=new EditText(this);
        edt.setText("default");
        edt.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));     
        
        //aggiungo l'edit
        ll.addView(edt);
        setContentView(edt);  
        Log.d("ADD", "Edit");
       
        //Bottone
        Button btn=new Button(this);
        btn.setText("Saluta");
        btn.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));       
       
        //aggiungo bottone
        ll.addView(btn);
        setContentView(ll);
        Log.d("ADD", "Bottom");
    }

	@Override
	public void onClick(View v) {
		String iltesto = edt.getText().toString();
		Toast.makeText(getApplicationContext(), iltesto , Toast.LENGTH_LONG).show();
		Log.d("onClick", iltesto);
	}    
}