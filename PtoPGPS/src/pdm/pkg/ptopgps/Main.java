package pdm.pkg.ptopgps;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main extends Activity {
    /** Called when the activity is first created. */
	float number1;
	float number2;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);       
        
    final EditText text1 = (EditText) findViewById(R.id.editText1);
    final EditText text2 = (EditText) findViewById(R.id.editText2);
        
    Button startButton = (Button) findViewById(R.id.button1);
    Button romeButton = (Button) findViewById(R.id.button2);
    Button parisButton = (Button) findViewById(R.id.button3);
    Button londonButton = (Button) findViewById(R.id.button4);
    Button berlinButton = (Button) findViewById(R.id.button5);
    Button madridButton = (Button) findViewById(R.id.button6);
    Button moscowButton = (Button) findViewById(R.id.button7);
    
    romeButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("41.53");
    		text2.setText("12.27");
    	}});
    parisButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("48.52");
    		text2.setText("2.20");
    	}});
    londonButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("51.30");
    		text2.setText("-0.10");
    	}});
    berlinButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("52.30");
    		text2.setText("13.25");
    	}});
    madridButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("40.24");
    		text2.setText("-3.41");
    	}});
    moscowButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
    		text1.setText("55.45");
    		text2.setText("37.35");
    	}});
        
    startButton.setOnClickListener(new OnClickListener(){
    	public void onClick(View v) {
       		Intent intent = new Intent(Main.this, Second.class);
       	   
       	
       		number1 = Float.valueOf(text1.getText().toString());
       		number2 = Float.valueOf(text2.getText().toString());
       		       		
       		intent.putExtra("latitudine",number1);
       		intent.putExtra("longitudine",number2);
       		
        	startActivity(intent);
        	}
        });  
    }
}