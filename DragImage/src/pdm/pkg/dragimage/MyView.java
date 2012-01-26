package pdm.pkg.dragimage;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.View;

public class MyView extends View {
	
	private int x=100;
	private int y=100;
	private Bitmap img=null;
	private boolean dragOn=false;

	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		BitmapFactory.Options opts = new BitmapFactory.Options();
		opts.inJustDecodeBounds = true;
		img = BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_launcher);
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawBitmap(img,  x, y, null);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event){
    	int eventaction = event.getAction();
    	int touchx = (int) event.getX();
    	int touchy = (int) event.getY();
		
    	switch (eventaction) {
        case MotionEvent.ACTION_DOWN: // touch down so check if the finger
        	if (touchx>x & touchx>x + img.getWidth() & touchy>y & touchy<y + img.getHeight()){
        		dragOn=true;
        	}
        	break;
        case MotionEvent.ACTION_MOVE: // touch drag with the ball
        	x=touchx;
        	y=touchy;
        	invalidate();
            break;
        case MotionEvent.ACTION_UP: 
        	dragOn=false;
        	break;
        }      	
    	return true;
    }
    

	
}

