package com.egov.smartqr.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class PreferenceUtil {

    public static final String ID = "ID";

	private static PreferenceUtil _preference;
	private SharedPreferences _sharedPreferences;
	 
	public static PreferenceUtil instance(Context context) {
		if (_preference == null)
			_preference = new PreferenceUtil(context);
		return _preference;
	}

   public PreferenceUtil(Context context)
   {
	  super();
	  _sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
	  //_sharedPreferences = context.getSharedPreferences("gallerysharp", Context.MODE_PRIVATE);// PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
   }

   public void set(String key, String value)
   {
	  SharedPreferences.Editor editor = _sharedPreferences.edit();
	  editor.putString(key, value);
	  editor.commit();
   }

   public String get(String key)
   {
	   String rtn = _sharedPreferences.getString(key, null);
	   if(rtn == null) rtn = "";
	   return rtn;
   }

   public void set(String key, boolean value)
   {
	  SharedPreferences.Editor editor = _sharedPreferences.edit();
	  editor.putBoolean(key, value);
	  editor.commit();
   }

   public boolean get(String key, boolean $default)
   {
	  return _sharedPreferences.getBoolean(key, $default);
   }

   public void set(String key, int value)
   {
	  SharedPreferences.Editor editor = _sharedPreferences.edit();
	  editor.putInt(key, value);
	  editor.commit();
   }

   public void set(String key, long value)
   {
	  SharedPreferences.Editor editor = _sharedPreferences.edit();
	  editor.putLong(key, value);
	  editor.commit();
   }

   public int get(String key, int $default)
   {
	  return _sharedPreferences.getInt(key, $default);
   }
   public long get(String key, long $default)
   {
	  return _sharedPreferences.getLong(key, $default);
   }

   public void remove(String key)
   {
	  SharedPreferences.Editor editor = _sharedPreferences.edit();
	  editor.remove(key);
	  editor.commit();
   }
}
