package com.kikudjiro.android.echo;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import com.kikudjiro.android.net.INetClientDelegate;
import com.kikudjiro.android.net.NetClient;
import com.kikudjiro.android.net.NetClient.State;

/**
 * Example of android TCP/IP client application.
 * 
 * @author Alexander Alexeychuk
 * @email kikudjiro@gmail.com
 */
public class EchoClientActivity extends Activity implements INetClientDelegate
{
	private TextView _lblMessages;
	private EditText _txtMessage;
	private ScrollView _scrollMessages;
	private Button _btnSend;

	private NetClient _netClient;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        _lblMessages = (TextView) findViewById(R.id.lblMessages);
        _txtMessage = (EditText) findViewById(R.id.txtMessage);
        _btnSend = (Button) findViewById(R.id.btnSend);
        _scrollMessages = (ScrollView)findViewById(R.id.scrollMessages);

        _btnSend.setText("Connect");
        
        _netClient = new NetClient(this);
        
		_btnSend.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				if (State.OFFLINE == _netClient.getState())
				{
					_open(null, 0);
					return;
				}
				if (State.ONLINE == _netClient.getState())
				{
					String text = _txtMessage.getText().toString();
					if (text.length() > 0)
					{
						_txtMessage.setText("");
						
						if (text.equals("q")) {
							_close();
							_addMessage("=== Close connection...");
							return;
						}
						
						_addMessage(text);
						
						byte[] bytes;
						try {
							bytes = text.getBytes("UTF8");
							_netClient.send(bytes);
						} catch (UnsupportedEncodingException e) {
						}
						
					}
				}
			}
		});
        
    }

    @Override
    public void onPause()
    {
        super.onPause();
        _close();
    }
    
    @Override
    public void onResume()
    {
        super.onResume();
        _open(null, 0);
    }
    
    private void _open(String host_name, int port)
    {
    	host_name = host_name == null ? "192.168.0.199" : host_name;
    	port = port == 0 ? 1234 : port;
    	
		_addMessage("=== Connecting to " + host_name + ":" + port);
		_btnSend.setVisibility(View.GONE);

		_netClient.connect(host_name, port);
    }
    private void _close()
    {
    	_netClient.disconnect();
    }
    private void _addMessage(String text)
    {
    	String messages = _lblMessages.getText().toString();
    	messages += text + "\n";
    	_lblMessages.setText(messages);
		_scrollMessages.fullScroll(View.FOCUS_DOWN);
    }
    

    // INetClientDelegate interface
    public void onConnect() {
		_btnSend.setText("Send");
		_btnSend.setVisibility(View.VISIBLE);
		_addMessage("Connected");
		_addMessage("q -- disconnect");
	}
	public void onConnectTimeout() {
		_btnSend.setText("Connect");
		_btnSend.setVisibility(View.VISIBLE);
		_addMessage("=== Can't connect: Timeout");
	}
	public void onDisconnect() {
		_btnSend.setText("Connect");
		_btnSend.setVisibility(View.VISIBLE);
		_addMessage("Disconnected" );
	}
	public void onReceive(ByteBuffer buffer) {
		_addMessage(Charset.forName("UTF8").decode(buffer).toString());
	}
	public void onError(String message) {
		_addMessage("=== " + message);
	}

}
