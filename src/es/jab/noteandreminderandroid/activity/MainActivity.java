package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.model.Token;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


public class MainActivity extends Activity {
	
	public static final int MAIN_ACTIVITY = 001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button loginButton = (Button) findViewById(R.id.LoginButtonHome);
        loginButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		click(v);
        	}
        });
    }


	private void click(View v) {
		Intent intent = new Intent(v.getContext(), LoginActivity.class);
    	intent.putExtra("message", "Login request");
    	startActivityForResult(intent, LoginActivity.LOGIN_ACTIVITY);
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		LinearLayout loginButtonLayout = (LinearLayout) findViewById(R.id.LoginButtonLayoutHome);
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			loginButtonLayout.setVisibility(View.INVISIBLE);
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
