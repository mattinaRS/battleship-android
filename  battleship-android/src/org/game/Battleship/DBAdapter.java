package org.game.Battleship;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter
{
	private static final String DB_NAME = "battleshipdata";
	private static final int DB_VERSION = 2;
	private static final String DB_TABLE = "scores";
	private static final String KEY_ID = "_id";
	private static final String PLAYER_NAME = "pname";
	private static final String SCORE ="score";
	
	private static final String DB_CREATE = "create table scores (_id integer primary key autoincrement, "
			+ "pname text not null, score integer not null);";
	private final Context context; 
	    
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;
	
	public DBAdapter(Context ctx) 
	{
		this.context = ctx;
	    DBHelper = new DatabaseHelper(context);
	}
	        
	private static class DatabaseHelper extends SQLiteOpenHelper 
	    {
			DatabaseHelper(Context context)
			{
				super(context, DB_NAME, null, DB_VERSION);
			}

			@Override
			public void onCreate(SQLiteDatabase db) 
			{
				db.execSQL(DB_CREATE);				
			}

			@Override
			public void onUpgrade(SQLiteDatabase db, int oldVersion,
					int newVersion) 
			{
				db.execSQL("DROP TABLE IF EXISTS scores");
				onCreate(db);
			}											
	    }
	
	public DBAdapter open() throws SQLException
	{
		db = DBHelper.getWritableDatabase();
		return this;
	}
	
	public DBAdapter close() throws SQLException
	{
		DBHelper.close();
		return this;
	}
	
	public long insertScore(String pname, int score)
	{
		ContentValues initialvalues = new ContentValues();
		initialvalues.put(PLAYER_NAME, pname);
		initialvalues.put(SCORE, score);
		return db.insert(DB_TABLE, null, initialvalues);
	}
	/*
	public long insertName(String pname)
	{
		ContentValues initialvalues = new ContentValues();
		initialvalues.put(PLAYER_NAME, pname);
		//initialvalues.put(SCORE, score);
		return db.insert(DB_TABLE, null, initialvalues);
	}
	*/
	public boolean deleteScore(long id)
	{
		return db.delete(DB_TABLE, KEY_ID  + "=" + id, null) > 0;
	}

	public boolean deleteallScores()
	{
		return db.delete(DB_TABLE, null, null) > 0;
	}
	
	public Cursor getallScores()
	{
		return db.query(DB_TABLE, new String[]{
				KEY_ID,
				PLAYER_NAME,
				SCORE
		}, null, null, null, null, SCORE);
	}
	
	public Cursor getScore(long id) throws SQLException
	{
		Cursor mcursor = db.query(true, DB_TABLE, new String[]{
				KEY_ID,
				PLAYER_NAME,
				SCORE
		}, KEY_ID + "=" + id, null, null, null, null, null);
		
		if(mcursor != null)
		{
			mcursor.moveToFirst();
		}
		return mcursor;
	}
}