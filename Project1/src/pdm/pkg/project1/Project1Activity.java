package pdm.pkg.project1;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

public class Project1Activity extends Activity {
    /** Called when the activity is first created. */
	String ipPlayer2=null;
	Boolean bln1=false;
	Boolean bln2=true;
	
	//********************* Gestione radioButton ****************************************
	public void onClientButtonClicked(){
		bln1=false;
		bln2=true;
	}
	//******************** Metodo onCreate*********************************************
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        final EditText edt = (EditText) findViewById(R.id.editText1);
        TextView tv = (TextView) findViewById(R.id.textView3);
        tv.setText(getLocalIpAddress());
        
        //RADIO BUTTON
        /*
        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup1);
        final RadioButton radioButton1 = (RadioButton) findViewById(R.id.radio0);
        final RadioButton radioButton2 = (RadioButton) findViewById(R.id.radio1);
        */
        //bln1=false;
        //bln2=true;
        
        Button btn = (Button) findViewById(R.id.button1);
        btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ipPlayer2 = edt.toString();
				Intent intent = new Intent(Project1Activity.this,GameActivity.class);
				intent.putExtra("IPPLAYER2", ipPlayer2);
				intent.putExtra("SERVER", bln1);
				intent.putExtra("CLIENT", bln2);
				startActivity(intent);
			}
		});        
    }
    
    //MY IP ADDRESS
    public String getLocalIpAddress() {
   	 try {
           for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
               NetworkInterface intf = en.nextElement();
               for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                   InetAddress inetAddress = enumIpAddr.nextElement();
                   if (!inetAddress.isLoopbackAddress()) {
                       return inetAddress.getHostAddress().toString();
                   }
               }
           }
       } catch (SocketException ex) {
           Log.e("LOG-TAG", ex.toString());
       }
       return null;
   }
}