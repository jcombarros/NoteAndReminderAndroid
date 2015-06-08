package es.jab.noteandreminderandroid.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.UserActivity;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionPost;
import es.jab.noteandreminderandroid.model.Token;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public abstract class MenuActivity extends GenericConnectionActivity {
	
	public static final int MENU_ACTIVITY = 007;
	
	public static final String METHOD = "logout";
	
	public static final int HOME_ITEM = 0;
	public static final int PROFILE_ITEM = 1;
	public static final int LOGOUT_ITEM = 2;
	
	
	private Gson gson;
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
	    Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
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

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        return super.onOptionsItemSelected(item);
    }
    
    private void logout(){
    	openConnection(WSConnection.AUTH_ROUTE, MenuActivity.METHOD, null);
    }
    
    private void home(){
    	Intent intent = new Intent(MenuActivity.this, MainActivity.class);
    	intent.putExtra("message", "Home request");
    	startActivityForResult(intent, MainActivity.MAIN_ACTIVITY);
    }
    
    private void myProfile(){
		Intent intent = new Intent(MenuActivity.this, UserActivity.class);
    	intent.putExtra("message", "My profile request");
    	startActivityForResult(intent, UserActivity.USER_ACTIVITY);
    }

	@Override
	public void openConnection(String route, String method, String queryString) {
		gson = new GsonBuilder().create();
		
		Token token = ((NoteAndReminderApplication) this.getApplication()).getToken();
		String gsonToken = gson.toJson(token);
		new WSConnectionPost(this).execute(route, method, gsonToken, queryString);
		
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
				Toast.makeText(this, "Disconnected", Toast.LENGTH_SHORT).show();
				connectionFinished();
			}
	
		}
		if(error){
			Toast.makeText(this, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
		}
	}
	
}
