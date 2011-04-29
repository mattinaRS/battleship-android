package org.game.Battleship;


import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class HighScore extends Activity{
	DBAdapter db;
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
        setContentView(R.layout.highscores);
        TableLayout tbl = (TableLayout)findViewById(R.id.RHE);
       
    	String name, score;
    	Cursor cursor;
    	db = new DBAdapter(this);
        db = db.open();  
        cursor = db.getallScores();
        cursor.moveToLast();
        while(cursor.isBeforeFirst()== false)
        {
        	name = cursor.getString(1);
        	score = cursor.getString(2);
            TableRow newRow = new TableRow(this);
            newRow.setBackgroundColor(0xFF000000);
            TableRow.LayoutParams lp = new TableRow.LayoutParams();
            lp.weight = (float) 0.5;
            lp.setMargins(2, 2, 2, 2);
            TextView cell1 = new TextView(this);
            cell1.setText(name);
            cell1.setTextColor(0xFF000000);
            cell1.setBackgroundColor(0xFF00FF00);
            newRow.addView(cell1, lp); 
            TextView cell2 = new TextView(this);
            cell2.setText(score);
            cell2.setTextColor(0xFF000000);
            cell2.setBackgroundColor(0xFF0000FF);
            newRow.addView(cell2,lp); 
            tbl.addView(newRow);
            cursor.moveToPrevious();
        }
        db.close();
    }	


}
