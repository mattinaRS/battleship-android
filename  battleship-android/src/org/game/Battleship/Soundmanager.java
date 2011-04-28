package org.game.Battleship;

import java.util.HashMap;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;

public class Soundmanager 
{
	static private Soundmanager _instance;	
	private static SoundPool sPool;
	private static HashMap<Integer, Integer> sPoolmap;
	private static AudioManager sAudio;
	private static Context sContext;
	
	private Soundmanager()
	{
		
	}
	static synchronized public Soundmanager getInstance()
	{
	    if (_instance == null)
	      _instance = new Soundmanager();
	    return _instance;
	 }	

	public static void init(Context c)
	{
		sContext = c;
		sPool = new SoundPool(4, AudioManager.STREAM_MUSIC, 0);
		sPoolmap = new HashMap<Integer, Integer>();
		sAudio = (AudioManager)sContext.getSystemService(Context.AUDIO_SERVICE);		
	}

	public static void cleanup()
	{
		sPool.release();
		sPool = null;
	    sPoolmap.clear();
	    sAudio.unloadSoundEffects();
	    _instance = null;
 
	}
	
	public static void loadSounds()
	{
		sPoolmap.put(1, sPool.load(sContext, R.raw.hit, 1));
		sPoolmap.put(2, sPool.load(sContext, R.raw.miss, 1));
		sPoolmap.put(3, sPool.load(sContext, R.raw.destroy, 1));
	}	
	public static void addSound(int index, int sId)
	{
		sPoolmap.put(index, sPool.load(sContext, sId, 1));
	}
	
	public static void playSound(int index,float speed)
	{
		     float streamVolume = sAudio.getStreamVolume(AudioManager.STREAM_MUSIC);
		     streamVolume = streamVolume / sAudio.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
		     sPool.play(sPoolmap.get(index), streamVolume, streamVolume, 1, 0, speed);
	}
}