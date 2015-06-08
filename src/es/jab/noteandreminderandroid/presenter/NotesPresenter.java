package es.jab.noteandreminderandroid.presenter;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;
import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.NoteActivity;
import es.jab.noteandreminderandroid.activity.NotesActivity;
import es.jab.noteandreminderandroid.adapter.NoteAdapter;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;

public class NotesPresenter implements GenericConnector{
	
	private NotesActivity notesActivity;
	
	private Gson gson;
	
	public NotesPresenter(NotesActivity notesActivity){
		this.notesActivity = notesActivity;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
	}
	
	public void onCreate(){
		Token connectionToken = ((NoteAndReminderApplication) notesActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, NotesActivity.METHOD, 
					NotesActivity.QUERY_STRING + connectionToken.getUserId());
		}
	}
	
	private void clickNote(View view, int position){
		Note note = (Note) notesActivity.getListView().getAdapter().getItem(position);
		
		Intent intent = new Intent(view.getContext(), NoteActivity.class);
    	intent.putExtra("message", "View note request");
    	intent.putExtra("noteId", Integer.toString(note.getId()));
    	notesActivity.startActivityForResult(intent, NoteActivity.NOTE_ACTIVITY);
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(notesActivity).execute(route, method, null, queryString);
	}
	
	@Override
	public void closeConnection(boolean error, String json) {
		List<Note> notes = null;
		if(!error){
			try {
				Type listType = new TypeToken<List<Note>>(){}.getType();
				notes = gson.fromJson(json, listType);

				notesActivity.getListView().setAdapter(new NoteAdapter(notesActivity, notes));
				notesActivity.getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
				Toast.makeText(notesActivity, "Notes retrieved", Toast.LENGTH_SHORT).show();
			}

		}
		if(error){
			Toast.makeText(notesActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}
	}
	
	@Override
	public void connectionEstablished(Token returnToken) {
		((NoteAndReminderApplication) notesActivity.getApplication()).setToken(returnToken);
		Intent intent = notesActivity.getIntent();
		intent.putExtra("message", "Response - connected");
		notesActivity.setResult(Activity.RESULT_OK, intent);
		notesActivity.finish();
		notesActivity.onBackPressed();
	}

	@Override
	public void connectionFailed() {
		((NoteAndReminderApplication) notesActivity.getApplication()).setToken(null);
		Intent intent = notesActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		notesActivity.setResult(Activity.RESULT_CANCELED, intent);
		notesActivity.finish();
		notesActivity.onBackPressed();
	}

	@Override
	public void connectionFinished() {
		((NoteAndReminderApplication) notesActivity.getApplication()).setToken(null);
		Intent intent = notesActivity.getIntent();
		intent.putExtra("message", "Response - disconnected");
		notesActivity.setResult(Activity.RESULT_CANCELED, intent);
		notesActivity.onResume();
	}

}
