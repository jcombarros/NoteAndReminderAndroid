package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.NotesPresenter;
import android.os.Bundle;
import android.widget.ListView;

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
