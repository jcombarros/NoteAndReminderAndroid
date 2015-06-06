package es.jab.noteandreminderandroid.activity;

import android.app.Activity;

public abstract class GenericConnectionActivity extends Activity{

	public abstract void openConnection(String route, String method, String queryString);
	
	public abstract void closeConnection(boolean error, String json);
	
}
