package es.jab.noteandreminderandroid.activity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.R.id;
import es.jab.noteandreminderandroid.R.layout;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Note;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.model.User;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivity extends GenericConnectionActivity {
	
	public static final int USER_ACTIVITY = 000;

	public static final String METHOD = "User/";
	
	private Gson gson;
	
	private EditText nameEditView;
	
	private EditText surnameEditView;
	
	private EditText emailEditView;
	
	private EditText passwordEditView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
				
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
		
		nameEditView = (EditText) findViewById(R.id.NameInputUser);
		surnameEditView = (EditText) findViewById(R.id.SurnameInputUser);
		emailEditView = (EditText) findViewById(R.id.EmailInputUser);
		passwordEditView = (EditText) findViewById(R.id.PasswordInputUser);
		
		nameEditView.setText("");
		surnameEditView.setText("");	
		emailEditView.setText("");	
		passwordEditView.setText("");	

		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, UserActivity.METHOD, 
					Integer.toString(connectionToken.getUserId()));
		}
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(this).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		User user = null;
		if(!error){
			try {
				user = gson.fromJson(json, User.class);
				
				if(user != null){				
					Toast.makeText(this, "User retrieved", Toast.LENGTH_SHORT).show();
					
					nameEditView.setText(user.getName());
					
					surnameEditView.setText(user.getSurname());
					
					emailEditView.setText(user.getEmail());
					
					passwordEditView.setText(user.getName());
					passwordEditView.setTypeface(null, Typeface.BOLD);
					
					
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
