package org.game.Battleship;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class losegame extends Activity implements OnClickListener {
	
	Button buttonQuit;
	TextView testText;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        buttonQuit = (Button)findViewById(R.id.quit);
        testText = (TextView)findViewById(R.id.testText);
        
        buttonQuit.setOnClickListener(this);
    }

	@Override
	public void onClick(View src) {
		switch(src.getId()){
		case R.id.quit:
			testText.setText("You Pressed Quit");
			break;
		}		
	}
}