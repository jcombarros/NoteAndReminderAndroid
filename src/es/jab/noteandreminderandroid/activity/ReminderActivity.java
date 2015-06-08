package es.jab.noteandreminderandroid.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.R.id;
import es.jab.noteandreminderandroid.R.layout;
import es.jab.noteandreminderandroid.R.menu;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Reminder;
import es.jab.noteandreminderandroid.model.Token;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class ReminderActivity extends GenericConnectionActivity {
	
	public static final int REMINDER_ACTIVITY = 006;
	
	private Gson gson;
	
	private TextView headerViewReminder;
	
	private TextView subHeaderViewReminder;
	
	private TextView descriptionViewReminder;
	
	private TextView subDescriptionViewReminder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String reminderId = extras.getString("reminderId");
		
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
		
		headerViewReminder = (TextView) findViewById(R.id.HeaderViewReminder);
		subHeaderViewReminder = (TextView) findViewById(R.id.SubHeaderViewReminder);
		descriptionViewReminder = (TextView) findViewById(R.id.DescriptionViewReminder);
		subDescriptionViewReminder = (TextView) findViewById(R.id.SubDescriptionViewReminder);
		
		headerViewReminder.setText("");		
		subHeaderViewReminder.setText("");
		descriptionViewReminder.setText("");
		subDescriptionViewReminder.setText("");
		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, RemindersActivity.METHOD, reminderId);
		}
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(this).execute(route, method, null, queryString);
		
	}

	@Override
	public void closeConnection(boolean error, String json) {
		Reminder reminder = null;
		if(!error){
			try {
				reminder = gson.fromJson(json, Reminder.class);
				
				if(reminder != null){				
					Toast.makeText(this, "Reminder retrieved", Toast.LENGTH_SHORT).show();
					
					headerViewReminder.setText(reminder.getTitle());
					headerViewReminder.setTypeface(null, Typeface.BOLD);
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					subHeaderViewReminder.setText("Created: " + df.format(reminder.getCreationDate()));
					subHeaderViewReminder.setTypeface(null, Typeface.ITALIC);
					
					descriptionViewReminder.setText("Is Completed? " + Boolean.toString(reminder.isCompleted()));
					descriptionViewReminder.setTypeface(null, Typeface.BOLD);
					
					subDescriptionViewReminder.setText("Completion date: " + df.format(reminder.getCompletionDate()));
				}
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
		}
		if(error){
			Toast.makeText(this, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}
	}
}
