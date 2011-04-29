package org.game.Battleship;

import java.util.HashMap;

import android.content.Context;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

public class Sounds 
{
	private MediaPlayer mp;
	private HashMap<String, Integer> sPool = new HashMap<String, Integer>();
	private Context sContext;

	public Sounds(Context c)
	{
		sContext = c;
		sPool.put("Hit", R.raw.hit);
		sPool.put("Miss", R.raw.miss);
		sPool.put("Destroy", R.raw.destroy);
	}
	
	public void loadSound(String s)
	{
		mp =  MediaPlayer.create(sContext, sPool.get(s));
	}
	
	public void playSound()
	{
		mp.setOnCompletionListener(new OnCompletionListener()
		{

			@Override
			public void onCompletion(MediaPlayer mp) 
			{
				mp.stop();
				mp.release();
			}
			
		});
		mp.start();
		while(mp.isPlaying());
		return;
	}

	
}
