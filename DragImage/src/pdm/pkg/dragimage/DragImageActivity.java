package pdm.pkg.dragimage;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;

public class DragImageActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //impostare la seconda view passandogli per riferimento la view attuale
        setContentView(new MyView(this));
        
    }

}