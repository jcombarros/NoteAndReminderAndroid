package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.ReminderPresenter;
import android.os.Bundle;
import android.widget.TextView;

public class ReminderActivity extends GenericConnectionActivity {
	
	public static final int REMINDER_ACTIVITY = 006;
	
	private ReminderPresenter reminderPresenter;
	
	private TextView headerViewReminder;
	private TextView subHeaderViewReminder;
	private TextView descriptionViewReminder;
	private TextView subDescriptionViewReminder;
	
	public TextView getHaderViewReminder(){
		return headerViewReminder;
	}
	
	public TextView getSubHeaderViewReminder(){
		return subHeaderViewReminder;
	}

	
	public TextView getDescriptionViewReminder(){
		return descriptionViewReminder;
	}

	public TextView getSubDescriptionViewReminder(){
		return subDescriptionViewReminder;
	}


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reminder);
		
		reminderPresenter = new ReminderPresenter(this);
				
		headerViewReminder = (TextView) findViewById(R.id.HeaderViewReminder);
		subHeaderViewReminder = (TextView) findViewById(R.id.SubHeaderViewReminder);
		descriptionViewReminder = (TextView) findViewById(R.id.DescriptionViewReminder);
		subDescriptionViewReminder = (TextView) findViewById(R.id.SubDescriptionViewReminder);
		
		headerViewReminder.setText("");		
		subHeaderViewReminder.setText("");
		descriptionViewReminder.setText("");
		subDescriptionViewReminder.setText("");
		
		reminderPresenter.onCreate();
	}

	@Override
	public void closeConnection(boolean error, String json) {
		reminderPresenter.closeConnection(error, json);
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
