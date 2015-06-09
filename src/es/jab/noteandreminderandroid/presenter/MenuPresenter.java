package es.jab.noteandreminderandroid.presenter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.activity.MainActivity;
import es.jab.noteandreminderandroid.activity.MenuActivity;
import es.jab.noteandreminderandroid.activity.NoteActivity;
import es.jab.noteandreminderandroid.activity.UserActivity;
import es.jab.noteandreminderandroid.connection.GenericConnector;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionPost;
import es.jab.noteandreminderandroid.model.Token;

public class MenuPresenter implements GenericConnector{
	
	private MenuActivity menuActivity;
	
	private Gson gson;
	
	public MenuActivity getMenuActivity(){
		return menuActivity;
	}
	
	public MenuPresenter(MenuActivity menuActivity){
		this.menuActivity = menuActivity;
		gson = new GsonBuilder().create();
	}
	
	public boolean onCreateOptionsMenu(Menu menu){
		menuActivity.getMenuInflater().inflate(R.menu.main, menu);
        return true;
	}
	
	public boolean onPrepareOptionsMenu(Menu menu){
		Token connectionToken = ((NoteAndReminderApplication) menuActivity.getApplication()).getToken();
	    MenuItem profile = menu.getItem(MenuActivity.PROFILE_ITEM);
	    MenuItem logout = menu.getItem(MenuActivity.LOGOUT_ITEM);
		if(connectionToken == null || !connectionToken.getAuth()){
			profile.setEnabled(false);
			logout.setEnabled(false);
		}
		else{
			profile.setEnabled(true);
			logout.setEnabled(true);
		}
	    return true;
	}
	
	public void onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.HomeItemMenu: 
			Log.d("Menu", "Home selected");
			home();
			break;
		case R.id.ProfileItemMenu: 
			Log.d("Menu", "My profile selected");
			myProfile();
			break;
		case R.id.LogoutItemMenu: 
			Log.d("Menu", "Logout selected");
			logout();
			break;
		default:
			break;
		}
	}
	
	private void logout(){
    	openConnection(WSConnection.AUTH_ROUTE, MenuActivity.METHOD, null);
    }
    
    private void home(){
    	Intent intent = new Intent(menuActivity, MainActivity.class);
    	intent.putExtra("message", "Home request");
    	menuActivity.startActivityForResult(intent, MainActivity.MAIN_ACTIVITY);
    }
    
    private void myProfile(){
		Intent intent = new Intent(menuActivity, UserActivity.class);
    	intent.putExtra("message", "My profile request");
    	menuActivity.startActivityForResult(intent, UserActivity.USER_ACTIVITY);
    }
    
    @Override
	public void openConnection(String route, String method, String queryString) {
		gson = new GsonBuilder().create();
		
		Token token = ((NoteAndReminderApplication) menuActivity.getApplication()).getToken();
		String gsonToken = gson.toJson(token);
		new WSConnectionPost(menuActivity).execute(route, method, gsonToken, queryString);
		
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
			
			if(returnToken != null && !returnToken.getAuth()){				
				Toast.makeText(menuActivity, "Disconnected", Toast.LENGTH_SHORT).show();
				connectionFinished();
			}
	
		}
		if(error){
			Toast.makeText(menuActivity, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
		}
	}
	
	@Override
	public void connectionEstablished(Token returnToken) {
		((NoteAndReminderApplication) menuActivity.getApplication()).setToken(returnToken);
		Intent intent = menuActivity.getIntent();
		intent.putExtra("message", "Response - connected");
		menuActivity.setResult(Activity.RESULT_OK, intent);
		menuActivity.finish();
		menuActivity.onBackPressed();
	}

	@Override
	public void connectionFailed() {
		((NoteAndReminderApplication) menuActivity.getApplication()).setToken(null);
		Intent intent = menuActivity.getIntent();
		intent.putExtra("message", "Response - failed");
		menuActivity.setResult(Activity.RESULT_CANCELED, intent);
		menuActivity.finish();
		menuActivity.onBackPressed();
	}

	@Override
	public void connectionFinished() {
		((NoteAndReminderApplication) menuActivity.getApplication()).setToken(null);
		Intent intent = menuActivity.getIntent();
		intent.putExtra("message", "Response - disconnected");
		menuActivity.setResult(Activity.RESULT_CANCELED, intent);
		menuActivity.onResume();
	}

}
