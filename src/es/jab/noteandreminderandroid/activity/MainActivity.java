package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.model.Token;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity {
	
	public static final int MAIN_ACTIVITY = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button loginButton = (Button) findViewById(R.id.LoginButtonHome);
        Button notesButton = (Button) findViewById(R.id.NotesButtonHome);
        
        loginButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		clickLogin(v);
        	}
        });

        notesButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		clickNotes(v);
        	}
        });
    }


	private void clickLogin(View v) {
		Intent intent = new Intent(v.getContext(), LoginActivity.class);
    	intent.putExtra("message", "Login request");
    	startActivityForResult(intent, LoginActivity.LOGIN_ACTIVITY);
		
	}
	
	private void clickNotes(View v) {
		Intent intent = new Intent(v.getContext(), NotesActivity.class);
    	intent.putExtra("message", "Notes request");
    	startActivityForResult(intent, NotesActivity.NOTES_ACTIVITY);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LinearLayout loginButtonLayout = (LinearLayout) findViewById(R.id.LoginButtonLayoutHome);
		LinearLayout loggedButtonsLayout = (LinearLayout) findViewById(R.id.LoggedButtonsLayoutHome);
		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			loginButtonLayout.setVisibility(View.GONE);
			loggedButtonsLayout.setVisibility(View.VISIBLE);
		}
		else{
			loginButtonLayout.setVisibility(View.VISIBLE);
			loggedButtonsLayout.setVisibility(View.GONE);
		}
	}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
}
