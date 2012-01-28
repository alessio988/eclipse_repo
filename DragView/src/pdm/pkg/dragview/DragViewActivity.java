package pdm.pkg.dragview;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class DragViewActivity extends Activity {
    
	
	private View selected_item=null;
	private int offset_x=0;
	private int offset_y=0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        ImageView iv = (ImageView) findViewById(R.id.imageView1);
        iv.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getAction()==MotionEvent.ACTION_DOWN){
					offset_x=(int)event.getX();
					offset_y=(int)event.getY();
					selected_item=v;
				}
				return false;
			}
		});
        
        RelativeLayout rl = (RelativeLayout) findViewById(R.id.relativeLayout1);
        rl.setOnTouchListener(new View.OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				if(event.getActionMasked()==MotionEvent.ACTION_MOVE){
				if(selected_item==null)return false;
				//MotionEvent.ACTION_MOVE
				
						int x = (int) event.getX() - offset_x;
						int y = (int) event.getY() - offset_y;
						int w = getWindowManager().getDefaultDisplay().getWidth() -150;
						int h = getWindowManager().getDefaultDisplay().getHeight()-150;
						if (x > w)
						x = w;
						if (y > h)
						y = h;
						ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(new ViewGroup.MarginLayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT));
						((MarginLayoutParams) lp).setMargins(x,y,0,0);
						selected_item.setLayoutParams(lp);		
				}
		              	if(event.getActionMasked()==MotionEvent.ACTION_UP){
			            	selected_item=null;
			         	}
				return true;
			}
		});
	  }
}
