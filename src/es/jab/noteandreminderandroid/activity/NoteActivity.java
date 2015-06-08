package es.jab.noteandreminderandroid.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class NoteActivity extends GenericConnectionActivity {
	
	public static final int NOTE_ACTIVITY = 004;
	
	private Gson gson;
	
	private TextView headerViewNote;
	
	private TextView subHeaderViewNote;
	
	private TextView descriptionViewNote;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		Intent intent = getIntent();
		Bundle extras = intent.getExtras();
		String noteId = extras.getString("noteId");
		
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
		
		headerViewNote = (TextView) findViewById(R.id.HeaderViewNote);
		subHeaderViewNote = (TextView) findViewById(R.id.SubHeaderViewNote);
		descriptionViewNote = (TextView) findViewById(R.id.DescriptionViewNote);
		
		headerViewNote.setText("");		
		subHeaderViewNote.setText("");
		descriptionViewNote.setText("");
		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, NotesActivity.METHOD, noteId);
		}
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(this).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		Note note = null;
		if(!error){
			try {
				note = gson.fromJson(json, Note.class);
				
				if(note != null){				
					Toast.makeText(this, "Note retrieved", Toast.LENGTH_SHORT).show();
					
					headerViewNote.setText(note.getTitle());
					headerViewNote.setTypeface(null, Typeface.BOLD);
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					subHeaderViewNote.setText("Created: " + df.format(note.getCreationDate()));
					subHeaderViewNote.setTypeface(null, Typeface.ITALIC);
					
					descriptionViewNote.setText(note.getDescription());
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
