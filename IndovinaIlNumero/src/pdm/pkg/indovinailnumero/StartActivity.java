package pdm.pkg.indovinailnumero;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends Activity {
    /** Called when the activity is first created. */
	String nome_proprio;
	String nome_avversario;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
        
        //inizializzazione
        Button startButton = (Button) findViewById(R.id.button1);
        final EditText campo1 = (EditText) findViewById(R.id.editText1);
        final EditText campo2 = (EditText) findViewById(R.id.editText2);
        
        //intent al click        
        startButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(StartActivity.this,Main.class);
				
				//salvataggio variabili per intent
		        nome_proprio = campo1.getText().toString();
		        nome_avversario = campo2.getText().toString();
		        
				intent.putExtra("nome proprio", nome_proprio);
				intent.putExtra("nome avversario", nome_avversario);
				startActivity(intent);
			}
        	
        });
        
    }
}