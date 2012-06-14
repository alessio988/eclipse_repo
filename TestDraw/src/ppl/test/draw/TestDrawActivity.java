package ppl.test.draw;

import android.app.Activity;
import android.content.res.Resources.Theme;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class TestDrawActivity extends Activity {
	private DrawView drawView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        
		drawView = new DrawView(this);

		setContentView(drawView);
		final int[] x = getResources().getIntArray(R.array.xArray);
		final int[] y = getResources().getIntArray(R.array.yArray);

		new Thread(new Runnable() {
			int ind = 0;

			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (ind < 5) {
					Message msg = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putString("NP", "NP##" + x[ind] + "##" + y[ind]);
					ind++;
					msg.setData(b);
					handler.sendMessage(msg);
					try {
						Thread.sleep(3000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					// TODO Auto-generated catch block
					
				}
			}
		}).start();

	}

	final Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			String rec = msg.getData().getString("NP");
			String[] data = rec.split("##");
			drawView.setNewPoint(Float.parseFloat(data[1]),
					Float.parseFloat(data[2]));
		};
	};

}