package es.jab.noteandreminderandroid.presenter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.UserActivity;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.model.User;

public class UserPresenter implements GenericConnector{
	
	private UserActivity userActivity;
	
	private Gson gson;
	
	public UserActivity getUserActivity(){
		return userActivity;
	}
	
	public UserPresenter(UserActivity userActivity){
		this.userActivity = userActivity;
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
	}
	
	public void onCreate(){
		Token connectionToken = ((NoteAndReminderApplication) userActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, UserActivity.METHOD, 
					Integer.toString(connectionToken.getUserId()));
		}
	}
	
	@Override
	public void openConnection(String route, String method, String queryString) {
		new WSConnectionGet(userActivity).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		User user = null;
		if(!error){
			try {
				user = gson.fromJson(json, User.class);
				
				if(user != null){				
					Toast.makeText(userActivity, "User retrieved", Toast.LENGTH_SHORT).show();
					
					userActivity.getNameEditView().setText(user.getName());
					
					userActivity.getSurnameEditView().setText(user.getSurname());
					
					userActivity.getEmailEditText().setText(user.getEmail());
					
					userActivity.getPasswordEditView().setText(user.getName());
					userActivity.getPasswordEditView().setTypeface(null, Typeface.BOLD);
					
					
				}
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
		}
		if(error){
			Toast.makeText(userActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionFailed();
		}

	}

	public void connectionFailed() {
		((NoteAndReminderApplication) userActivity.getApplication()).setToken(null);
		Intent intent = userActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		userActivity.setResult(Activity.RESULT_CANCELED, intent);
		userActivity.finish();
		userActivity.onBackPressed();
	}

}
