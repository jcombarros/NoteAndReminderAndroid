package es.jab.noteandreminderandroid.presenter;

import java.lang.reflect.Type;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.ReminderActivity;
import es.jab.noteandreminderandroid.activity.RemindersActivity;
import es.jab.noteandreminderandroid.activity.UserActivity;
import es.jab.noteandreminderandroid.adapter.ReminderAdapter;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Reminder;
import es.jab.noteandreminderandroid.model.Token;

public class RemindersPresenter implements GenericConnector{
	
	private RemindersActivity remindersActivity;
	
	private Gson gson;
	
	public RemindersActivity getRemindersActivity(){
		return remindersActivity;
	}
	
	public RemindersPresenter(RemindersActivity remindersActivity){
		this.remindersActivity = remindersActivity;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
	}
	
	public void onCreate(){		
		Token connectionToken = ((NoteAndReminderApplication) remindersActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			this.openConnection(WSConnection.API_ROUTE, RemindersActivity.METHOD, 
					RemindersActivity.QUERY_STRING + connectionToken.getUserId());
		}
	}
	
	public void clickReminder(View view, int position){
		Reminder reminder = (Reminder) remindersActivity.getListView().getAdapter().getItem(position);
		
		Intent intent = new Intent(view.getContext(), ReminderActivity.class);
    	intent.putExtra("message", "View reminder request");
    	intent.putExtra("reminderId", Integer.toString(reminder.getId()));
    	remindersActivity.startActivityForResult(intent, ReminderActivity.REMINDER_ACTIVITY);
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(remindersActivity).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		List<Reminder> reminders = null;
		if(!error){
			try {
				Type listType = new TypeToken<List<Reminder>>(){}.getType();
				reminders = gson.fromJson(json, listType);

				remindersActivity.getListView().setAdapter(new ReminderAdapter(remindersActivity, reminders));
				remindersActivity.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView adapter, View view, int position, long arg) {
				    	clickReminder(view, position);
				    }
				});
				
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
			if(reminders != null && !reminders.isEmpty()){				
				Toast.makeText(remindersActivity, "Notes retrieved", Toast.LENGTH_SHORT).show();
			}

		}
		if(error){
			Toast.makeText(remindersActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}
	}
	
	@Override
	public void connectionEstablished(Token returnToken) {
		((NoteAndReminderApplication) remindersActivity.getApplication()).setToken(returnToken);
		Intent intent = remindersActivity.getIntent();
		intent.putExtra("message", "Response - connected");
		remindersActivity.setResult(Activity.RESULT_OK, intent);
		remindersActivity.finish();
		remindersActivity.onBackPressed();
	}

	@Override
	public void connectionFailed() {
		((NoteAndReminderApplication) remindersActivity.getApplication()).setToken(null);
		Intent intent = remindersActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		remindersActivity.setResult(Activity.RESULT_CANCELED, intent);
		remindersActivity.finish();
		remindersActivity.onBackPressed();
	}

	@Override
	public void connectionFinished() {
		((NoteAndReminderApplication) remindersActivity.getApplication()).setToken(null);
		Intent intent = remindersActivity.getIntent();
		intent.putExtra("message", "Response - disconnected");
		remindersActivity.setResult(Activity.RESULT_CANCELED, intent);
		remindersActivity.onResume();
	}

}
