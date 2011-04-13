package org.game.Battleship;
 
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.View.OnClickListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
 
public class GameBoard extends Activity implements OnClickListener {
	Button buttonInit, buttonEnd, buttonDeploy;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gameboard);
        buttonInit = (Button)findViewById(R.id.Init);
        buttonEnd = (Button)findViewById(R.id.Turn);
        buttonDeploy = (Button)findViewById(R.id.Deploy);
        buttonEnd.setOnClickListener(this);
        buttonEnd.setEnabled(false);
        buttonDeploy.setEnabled(false);
        buttonDeploy.setOnClickListener(this);
        buttonInit.setOnClickListener(this);
    }
    
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.Init:
			int dim;
	        FrameLayout main = (FrameLayout) findViewById(R.id.main_view);
	        Log.d("Activity", Integer.toString(main.getHeight()));
	        if(main.getWidth()> main.getHeight())
	        {
	        	dim = main.getHeight();
	        }
	        else
	        {
	        	dim = main.getWidth();
	        }
	        Log.d("Width", Integer.toString(dim));
	        main.addView(new Grid(this,0,0,dim,dim));
//	        main.addView(new Ship(this,50,50));
	        buttonDeploy.setEnabled(true);
			break;
			case R.id.Deploy:
	        buttonEnd.setEnabled(true);
			break;
		}		
	}
}
