package es.jab.noteandreminderandroid.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionPost;
import es.jab.noteandreminderandroid.model.Token;
import es.jab.noteandreminderandroid.utils.TextUtils;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends GenericConnectionActivity{
	
	public static final int LOGIN_ACTIVITY = 002;
	
	public static final String METHOD = "authenticate";
	
	private EditText inputEmail;
	
	private EditText inputPassword;
	
	private Gson gson;
	
	public EditText getInputEmail() {
		return inputEmail;
	}
	
	public EditText getInputPassword() {
		return inputPassword;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		gson = new GsonBuilder().create();
		
		Button loginButton = (Button) findViewById(R.id.LoginButtonLogin);
		loginButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		click(v);
        	}
        });
	}
	
	private void click(View v) {
		openConnection(WSConnection.AUTH_ROUTE, LoginActivity.METHOD, null);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
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
		inputEmail = (EditText) findViewById(R.id.EmailInputLogin);
		inputPassword = (EditText) findViewById(R.id.PasswordInputLogin);
		String inputEmailString = inputEmail.getText().toString();
		String inputPasswordString = inputPassword.getText().toString();
		if(TextUtils.isNullOrEmpty(inputEmailString) 
				|| TextUtils.isNullOrEmpty(inputPasswordString)){
			Toast.makeText(this, "You must set an email and a password", Toast.LENGTH_SHORT).show();
		}
		else{
			Token token = new Token();
			token.setEmail(inputEmailString);
			token.setPassword(inputPasswordString);
			String gsonToken = gson.toJson(token);
			new WSConnectionPost(this).execute(route, method, gsonToken, queryString);
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
				((NoteAndReminderApplication) this.getApplication()).setToken(returnToken);
				
				Toast.makeText(this, "Connected", Toast.LENGTH_SHORT).show();
				
				Intent intent = getIntent();
				intent.putExtra("message", "Login response - connected");
		    	this.setResult(Activity.RESULT_OK, intent);
				this.finish();
		    	super.onBackPressed();
			}
			else{
				Toast.makeText(this, "Incorrect email/password", Toast.LENGTH_SHORT).show();
				inputPassword.setText("");
			}
	
		}
		if(error){
			Toast.makeText(this, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
		}
		
	}

}
