package es.jab.noteandreminderandroid.activity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionPost;
import es.jab.noteandreminderandroid.model.Token;
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
		if(connectionToken == null || !connectionToken.getAuth()){
			menu.getItem(MenuActivity.PROFILE_ITEM).setEnabled(false);
			menu.getItem(MenuActivity.LOGOUT_ITEM).setEnabled(false);
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
		case R.id.LogoutItemMenu: 
			Log.d("Preferencias", "Logout selected");
			logout();
			break;
		case R.id.ProfileItemMenu: 
			Log.d("Preferencias", "My profile selected");
			break;
		default:
			break;
		}
        return super.onOptionsItemSelected(item);
    }
    
    private void logout(){
    	openConnection(WSConnection.AUTH_ROUTE, MenuActivity.METHOD, null);
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
