package es.jab.noteandreminderandroid.activity;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.jab.noteandreminderandroid.NoteActivity;
import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.adapter.NoteAdapter;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;
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
	
	private Gson gson;
	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_notes);
		
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, NotesActivity.METHOD, QUERY_STRING + connectionToken.getUserId());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.notes, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(this).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		List<Note> notes = null;
		if(!error){
			try {
				Type listType = new TypeToken<List<Note>>(){}.getType();
				notes = gson.fromJson(json, listType);
				
				listView = (ListView) findViewById(R.id.ListNotes);
				listView.setAdapter(new NoteAdapter(this, notes));
				
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView adapter, View view, int position, long arg) {
				    	clickNote(view, position);
				    }
				});
				
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
			if(notes != null && !notes.isEmpty()){				
				Toast.makeText(this, "Notes retrieved", Toast.LENGTH_SHORT).show();
			}

		}
		if(error){
			Toast.makeText(this, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
		}
	}
	
	private void clickNote(View view, int position){
		Note note = (Note) listView.getAdapter().getItem(position);
		
		Intent intent = new Intent(view.getContext(), NoteActivity.class);
    	intent.putExtra("message", "View note request");
    	intent.putExtra("noteId", Integer.toString(note.getId()));
    	startActivityForResult(intent, NoteActivity.NOTE_ACTIVITY);
	}
}
