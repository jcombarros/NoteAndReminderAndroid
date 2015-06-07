package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.model.Token;
import android.app.Activity;
import android.content.Intent;

public abstract class GenericConnectionActivity extends Activity{

	public abstract void openConnection(String route, String method, String queryString);
	
	public abstract void closeConnection(boolean error, String json);
	
	public void connectionSuccess(Token returnToken){
		((NoteAndReminderApplication) this.getApplication()).setToken(returnToken);
		Intent intent = getIntent();
		intent.putExtra("message", "Response - connected");
    	this.setResult(Activity.RESULT_OK, intent);
		this.finish();
    	super.onBackPressed();
	}
	
	public void connectionError(){
		((NoteAndReminderApplication) this.getApplication()).setToken(null);
		Intent intent = getIntent();
		intent.putExtra("message", "Response - failed");
    	this.setResult(Activity.RESULT_CANCELED, intent);
		this.finish();
    	super.onBackPressed();
	}
	
}
