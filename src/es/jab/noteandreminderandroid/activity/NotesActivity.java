package es.jab.noteandreminderandroid.activity;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.adapter.NoteAdapter;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.presenter.NotesPresenter;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class NotesActivity extends GenericConnectionActivity {
	
	public static final int NOTES_ACTIVITY = 003;
	
	public static final String METHOD = "Note/";
	public static final String QUERY_STRING = "userId=";
	
	private NotesPresenter notesPresenter;
		
	private ListView listView;
	
	public ListView getListView(){
		return listView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		notesPresenter = new NotesPresenter(this);
				
		listView = (ListView) findViewById(R.id.ListNotes);
		notesPresenter.onCreate();
	}

	@Override
	public void closeConnection(boolean error, String json) {
		notesPresenter.closeConnection(error, json);
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
