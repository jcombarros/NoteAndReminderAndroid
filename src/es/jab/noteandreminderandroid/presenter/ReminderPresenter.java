package es.jab.noteandreminderandroid.presenter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.ReminderActivity;
import es.jab.noteandreminderandroid.activity.RemindersActivity;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Reminder;
import es.jab.noteandreminderandroid.model.Token;

public class ReminderPresenter implements GenericConnectorPresenter{
	
	private ReminderActivity reminderActivity;
	
	private Gson gson;
	
	public ReminderPresenter(ReminderActivity reminderActivity){
		this.reminderActivity = reminderActivity;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create(); 
	}
	
	public void onCreate(){
		Intent intent = reminderActivity.getIntent();
		Bundle extras = intent.getExtras();
		String reminderId = extras.getString("reminderId");
		
		Token connectionToken = ((NoteAndReminderApplication) reminderActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, RemindersActivity.METHOD, reminderId);
		}
		
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(reminderActivity).execute(route, method, null, queryString);
		
	}

	@Override
	public void closeConnection(boolean error, String json) {
		Reminder reminder = null;
		if(!error){
			try {
				reminder = gson.fromJson(json, Reminder.class);
				
				if(reminder != null){				
					Toast.makeText(reminderActivity, "Reminder retrieved", Toast.LENGTH_SHORT).show();
					
					reminderActivity.getHaderViewReminder().setText(reminder.getTitle());
					reminderActivity.getHaderViewReminder().setTypeface(null, Typeface.BOLD);
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					reminderActivity.getSubHeaderViewReminder().setText("Created: " + df.format(reminder.getCreationDate()));
					reminderActivity.getSubHeaderViewReminder().setTypeface(null, Typeface.ITALIC);
					
					reminderActivity.getDescriptionViewReminder().setText("Is Completed? " + Boolean.toString(reminder.isCompleted()));
					reminderActivity.getDescriptionViewReminder().setTypeface(null, Typeface.BOLD);
					
					reminderActivity.getSubDescriptionViewReminder().setText("Completion date: " + df.format(reminder.getCompletionDate()));
				}
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
		}
		if(error){
			Toast.makeText(reminderActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}
	}
	
	@Override
	public void connectionEstablished(Token returnToken) {
		((NoteAndReminderApplication) reminderActivity.getApplication()).setToken(returnToken);
		Intent intent = reminderActivity.getIntent();
		intent.putExtra("message", "Response - connected");
		reminderActivity.setResult(Activity.RESULT_OK, intent);
		reminderActivity.finish();
		reminderActivity.onBackPressed();
	}

	@Override
	public void connectionFailed() {
		((NoteAndReminderApplication) reminderActivity.getApplication()).setToken(null);
		Intent intent = reminderActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		reminderActivity.setResult(Activity.RESULT_CANCELED, intent);
		reminderActivity.finish();
		reminderActivity.onBackPressed();
	}

	@Override
	public void connectionFinished() {
		((NoteAndReminderApplication) reminderActivity.getApplication()).setToken(null);
		Intent intent = reminderActivity.getIntent();
		intent.putExtra("message", "Response - disconnected");
		reminderActivity.setResult(Activity.RESULT_CANCELED, intent);
		reminderActivity.onResume();
	}

}
