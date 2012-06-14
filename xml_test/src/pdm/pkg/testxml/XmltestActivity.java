package pdm.pkg.testxml;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class XmltestActivity extends Activity {
	/** Called when the activity is first created. */

	SingleTouchEventView v1;
	Button b;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		v1 = (SingleTouchEventView) findViewById(R.id.draw1);
		b = (Button) findViewById(R.id.button1);

	}
}