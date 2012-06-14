package ppl.test.draw;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class DrawView extends View {

	private Paint paint = new Paint();
	
	private ArrayList<MyPoint> myPoints = new ArrayList<MyPoint>();
	
	public DrawView(Context context) {
		super(context);
		
		// TODO Auto-generated constructor stub
		paint.setColor(Color.GREEN);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		if(myPoints.size()>2){
			for (int i = 1; i < myPoints.size(); i++) {
				MyPoint p1 = myPoints.get(i-1);
				MyPoint p2 = myPoints.get(i);
				canvas.drawLine(p1.x, p1.y, p2.x, p2.y, paint);				
			}
		}
	}

	public void setNewPoint(float x, float y) {
		MyPoint newPoint = new MyPoint();
		newPoint.x=x;
		newPoint.y=y;
		myPoints.add(newPoint);
		invalidate();
	}
	
	private static class MyPoint{
		public float x;
		public float y;
	}
	
}
