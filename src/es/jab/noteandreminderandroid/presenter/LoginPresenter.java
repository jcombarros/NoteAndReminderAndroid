package es.jab.noteandreminderandroid.presenter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.LoginActivity;
import es.jab.noteandreminderandroid.activity.MainActivity;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionPost;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.utils.TextUtils;

public class LoginPresenter implements GenericConnector {

	private LoginActivity loginActivity;
	
	private Gson gson;
	
	public LoginActivity getLoginActivity(){
		return loginActivity;
	}
	
	public LoginPresenter(LoginActivity loginActivity){
		this.loginActivity = loginActivity;
		this.gson = new GsonBuilder().create();
	}
	
	public void clickLogin(View v) {
		this.openConnection(WSConnection.AUTH_ROUTE, LoginActivity.METHOD, null);
	}

	@Override
	public void openConnection(String route, String method, String queryString) {

		String inputEmailString = loginActivity.getInputEmail().getText().toString();
		String inputPasswordString = loginActivity.getInputPassword().getText().toString();
		if(TextUtils.isNullOrEmpty(inputEmailString) 
				|| TextUtils.isNullOrEmpty(inputPasswordString)){
			Toast.makeText(loginActivity, "You must set an email and a password", Toast.LENGTH_SHORT).show();
		}
		else{
			Token token = new Token();
			token.setEmail(inputEmailString);
			token.setPassword(inputPasswordString);
			String gsonToken = gson.toJson(token);
			new WSConnectionPost(loginActivity).execute(route, method, gsonToken, queryString);
		}
		
	}

	@Override
	public void closeConnection(boolean error, String json) {
		Token returnToken = null;
		if(!error){
			try {		
				returnToken = gson.fromJson(json, Token.class);
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
			if(returnToken != null && returnToken.getAuth()){				
				Toast.makeText(loginActivity, "Connected", Toast.LENGTH_SHORT).show();
				
				connectionEstablished(returnToken);
			}
			else{
				Toast.makeText(loginActivity, "Incorrect email/password", Toast.LENGTH_SHORT).show();
				loginActivity.getInputPassword().setText("");
			}
	
		}
		if(error){
			Toast.makeText(loginActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			loginActivity.getInputPassword().setText("");
		}
		
	}

	@Override
	public void connectionEstablished(Token returnToken) {
		((NoteAndReminderApplication) loginActivity.getApplication()).setToken(returnToken);
		Intent intent = loginActivity.getIntent();
		intent.putExtra("message", "Response - connected");
		loginActivity.setResult(Activity.RESULT_OK, intent);
		loginActivity.finish();
		loginActivity.onBackPressed();
	}

	@Override
	public void connectionFailed() {
		((NoteAndReminderApplication) loginActivity.getApplication()).setToken(null);
		Intent intent = loginActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		loginActivity.setResult(Activity.RESULT_CANCELED, intent);
		loginActivity.finish();
		loginActivity.onBackPressed();
	}

	@Override
	public void connectionFinished() {
		((NoteAndReminderApplication) loginActivity.getApplication()).setToken(null);
		Intent intent = loginActivity.getIntent();
		intent.putExtra("message", "Response - disconnected");
		loginActivity.setResult(Activity.RESULT_CANCELED, intent);
		loginActivity.onResume();
	}
}
