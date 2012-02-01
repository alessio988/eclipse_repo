package pdm.pkg.dragview;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;

//Togliendo gli attributi del relative layout sulle immagini si trovano una sopra l'altra
//� cmq possibile draggarle, anche se la precedenza se sovrapposte � della prima dichiarata nel codice

public class DragViewActivity extends Activity {
    /** Called when the activity is first created. */
	private View selected_item = null;
	private int offset_x = 0;
	private int offset_y = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageView iv1 = (ImageView)findViewById(R.id.imageView1);
        ImageView iv2 = (ImageView)findViewById(R.id.imageView2);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.relativeLayout1);
        
        iv1.setOnTouchListener(new OnTouchListener() {		
        	
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					offset_x = (int)event.getX();
					offset_y = (int)event.getY();
					selected_item = v;
				}
				return false;
			}
		});
        
        iv2.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					offset_x = (int)event.getX();
					offset_y = (int)event.getY();
					selected_item = v;
				}
				return false;
			}
		});
        
        rl.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (selected_item == null) {
					Log.d("TAG","Cliccata zona Vuota");
					return false;}
				else {
					int eventaction = event.getAction();
					int x = (int) event.getX() - offset_x;
					int y = (int) event.getY() - offset_y;
					int w = getWindowManager().getDefaultDisplay().getWidth() - 128;
					int h = getWindowManager().getDefaultDisplay().getHeight() - 128;
					RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
					lp.setMargins(x, y, 0, 0);
					selected_item.setLayoutParams(lp);
					if (x > w) {
						x = w;}
					if (y > h) {
						y = h;}
					switch (eventaction) {
					case MotionEvent.ACTION_MOVE:
						Log.d("TAG","ACTION MOVE");
						break;
					case MotionEvent.ACTION_UP:
						Log.d("TAG","ACTION UP");
						selected_item = null;
						break;
					}
					return true;
				}
			}
		});
    }
}