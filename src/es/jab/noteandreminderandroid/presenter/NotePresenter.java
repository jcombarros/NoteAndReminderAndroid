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
import es.jab.noteandreminderandroid.activity.NoteActivity;
import es.jab.noteandreminderandroid.activity.NotesActivity;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;

public class NotePresenter implements GenericConnector{
	
	private NoteActivity noteActivity;
	
	private Gson gson;
	
	public NoteActivity getNoteActivity(){
		return noteActivity;
	}
	
	public NotePresenter(NoteActivity noteActivity){
		this.noteActivity = noteActivity;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create(); 
	}
	
	public void onCreate(){
		Intent intent = noteActivity.getIntent();
		Bundle extras = intent.getExtras();
		String noteId = extras.getString("noteId");
		
		Token connectionToken = ((NoteAndReminderApplication) noteActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, NotesActivity.METHOD, noteId);
		}
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(noteActivity).execute(route, method, null, queryString);
	}

	
	@Override
	public void closeConnection(boolean error, String json) {
		Note note = null;
		if(!error){
			try {
				note = gson.fromJson(json, Note.class);
				
				if(note != null){				
					Toast.makeText(noteActivity, "Note retrieved", Toast.LENGTH_SHORT).show();
					
					noteActivity.getHeaderViewNote().setText(note.getTitle());
					noteActivity.getHeaderViewNote().setTypeface(null, Typeface.BOLD);
					
					DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
					noteActivity.getSubHeaderViewNote().setText("Created: " + df.format(note.getCreationDate()));
					noteActivity.getSubHeaderViewNote().setTypeface(null, Typeface.ITALIC);
					
					noteActivity.getDescriptionViewNote().setText(note.getDescription());
				}
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
		}
		if(error){
			Toast.makeText(noteActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}
	}

	public void connectionFailed() {
		((NoteAndReminderApplication) noteActivity.getApplication()).setToken(null);
		Intent intent = noteActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		noteActivity.setResult(Activity.RESULT_CANCELED, intent);
		noteActivity.finish();
		noteActivity.onBackPressed();
	}

}
