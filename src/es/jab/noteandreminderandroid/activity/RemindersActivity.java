package es.jab.noteandreminderandroid.activity;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.adapter.ReminderAdapter;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Reminder;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.presenter.RemindersPresenter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RemindersActivity extends GenericConnectionActivity {
	
	public static final int REMINDERS_ACTIVITY = 005;
	
	public static final String METHOD = "Reminder/";
	public static final String QUERY_STRING = "userId=";
	
	private RemindersPresenter remindersPresenter;
	
	private Gson gson;
	
	private ListView listView;
	
	public ListView getListView(){
		return listView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);
		
		remindersPresenter = new RemindersPresenter(this);
		
		listView = (ListView) findViewById(R.id.ListReminders);
		remindersPresenter.onCreate();
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		remindersPresenter.openConnection(route, method, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		remindersPresenter.closeConnection(error, json);
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
