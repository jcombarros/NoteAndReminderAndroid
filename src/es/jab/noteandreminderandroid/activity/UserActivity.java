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
import es.jab.noteandreminderandroid.presenter.UserPresenter;

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
	
	private UserPresenter userPresenter;
	
	private EditText nameEditView;
	private EditText surnameEditView;
	private EditText emailEditView;
	private EditText passwordEditView;
	
	public EditText getNameEditView(){
		return nameEditView;
	}
	
	public EditText getSurnameEditView(){
		return surnameEditView;
	}
	
	public EditText getEmailEditText(){ 
		return emailEditView;
	}
	
	public EditText getPasswordEditView(){
		return passwordEditView;
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user);
				
		userPresenter = new UserPresenter(this);
		
		nameEditView = (EditText) findViewById(R.id.NameInputUser);
		surnameEditView = (EditText) findViewById(R.id.SurnameInputUser);
		emailEditView = (EditText) findViewById(R.id.EmailInputUser);
		passwordEditView = (EditText) findViewById(R.id.PasswordInputUser);
		
		nameEditView.setText("");
		surnameEditView.setText("");	
		emailEditView.setText("");	
		passwordEditView.setText("");	

		
		userPresenter.onCreate();
	}

	@Override
	public void openConnection(String route, String method, String queryString) {
		userPresenter.openConnection(route, method, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		userPresenter.closeConnection(error, json);
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
