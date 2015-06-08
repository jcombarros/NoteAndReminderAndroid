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
import es.jab.noteandreminderandroid.presenter.ReminderPresenter;
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
	
	private ReminderPresenter reminderPresenter;
	
	private TextView headerViewReminder;
	private TextView subHeaderViewReminder;
	private TextView descriptionViewReminder;
	private TextView subDescriptionViewReminder;
	
	public TextView getHaderViewReminder(){
		return headerViewReminder;
	}
	
	public TextView getSubHeaderViewReminder(){
		return subHeaderViewReminder;
	}

	
	public TextView getDescriptionViewReminder(){
		return descriptionViewReminder;
	}

	public TextView getSubDescriptionViewReminder(){
		return subDescriptionViewReminder;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		
		reminderPresenter = new ReminderPresenter(this);
				
		headerViewReminder = (TextView) findViewById(R.id.HeaderViewReminder);
		subHeaderViewReminder = (TextView) findViewById(R.id.SubHeaderViewReminder);
		descriptionViewReminder = (TextView) findViewById(R.id.DescriptionViewReminder);
		subDescriptionViewReminder = (TextView) findViewById(R.id.SubDescriptionViewReminder);
		
		headerViewReminder.setText("");		
		subHeaderViewReminder.setText("");
		descriptionViewReminder.setText("");
		subDescriptionViewReminder.setText("");
		
		reminderPresenter.onCreate();
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		reminderPresenter.openConnection(route, method, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		reminderPresenter.closeConnection(error, json);
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
}
