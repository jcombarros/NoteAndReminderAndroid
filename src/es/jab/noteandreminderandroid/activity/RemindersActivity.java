package es.jab.noteandreminderandroid.activity;

import com.google.gson.Gson;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.RemindersPresenter;
import android.os.Bundle;
import android.widget.ListView;

public class RemindersActivity extends GenericConnectionActivity {
	
	public static final int REMINDERS_ACTIVITY = 005;
	
	public static final String METHOD = "Reminder/";
	public static final String QUERY_STRING = "userId=";
	
	private RemindersPresenter remindersPresenter;
		
	private ListView listView;
	
	public ListView getListView(){
		return listView;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminders);
		
		remindersPresenter = new RemindersPresenter(this);
		
		listView = (ListView) findViewById(R.id.ListReminders);
		remindersPresenter.onCreate();
	}

	@Override
	public void closeConnection(boolean error, String json) {
		remindersPresenter.closeConnection(error, json);
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
