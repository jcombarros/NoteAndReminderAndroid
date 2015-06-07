package es.jab.noteandreminderandroid.activity;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.adapter.ReminderAdapter;
import es.jab.noteandreminderandroid.connection.WSConnection;
import es.jab.noteandreminderandroid.connection.WSConnectionGet;
import es.jab.noteandreminderandroid.model.Reminder;
import es.jab.noteandreminderandroid.model.Token;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class RemindersActivity extends GenericConnectionActivity {
	
	public static final int REMINDERS_ACTIVITY = 005;
	
	public static final String METHOD = "Reminder/";
	public static final String QUERY_STRING = "userId=";
	
	private Gson gson;
	
	private ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);
		
		gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm").create();
		
		listView = (ListView) findViewById(R.id.ListReminders);
		
		Token connectionToken = ((NoteAndReminderApplication) this.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			openConnection(WSConnection.API_ROUTE, RemindersActivity.METHOD, 
					RemindersActivity.QUERY_STRING + connectionToken.getUserId());
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reminders, menu);
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
		new WSConnectionGet(this).execute(route, method, null, queryString);
	}

	@Override
	public void closeConnection(boolean error, String json) {
		List<Reminder> reminders = null;
		if(!error){
			try {
				Type listType = new TypeToken<List<Reminder>>(){}.getType();
				reminders = gson.fromJson(json, listType);

				listView.setAdapter(new ReminderAdapter(this, reminders));
				listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				    @Override
				    public void onItemClick(AdapterView adapter, View view, int position, long arg) {
				    	clickReminder(view, position);
				    }
				});
				
			} catch (JsonSyntaxException e) {
				Log.e("Json syntax error: ", e.toString());
				error = true;
			}
			
			if(reminders != null && !reminders.isEmpty()){				
				Toast.makeText(this, "Notes retrieved", Toast.LENGTH_SHORT).show();
			}

		}
		if(error){
			Toast.makeText(this, "Something wrong has happened, try again", Toast.LENGTH_SHORT).show();
			connectionError();
		}
	}
	
	private void clickReminder(View view, int position){

	}
}
