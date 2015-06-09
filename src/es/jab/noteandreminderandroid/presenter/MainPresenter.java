package es.jab.noteandreminderandroid.presenter;

import android.content.Intent;
import android.view.View;
import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.LoginActivity;
import es.jab.noteandreminderandroid.activity.MainActivity;
import es.jab.noteandreminderandroid.activity.NotesActivity;
import es.jab.noteandreminderandroid.activity.RemindersActivity;
import es.jab.noteandreminderandroid.model.Token;

public class MainPresenter {
	
	private MainActivity mainActivity;
	
	public MainActivity getMainActivity(){
		return mainActivity;
	}
	
	public MainPresenter(MainActivity mainActivity){
		this.mainActivity = mainActivity;
	}

	public void clickLogin(View v) {
		Intent intent = new Intent(v.getContext(), LoginActivity.class);
    	intent.putExtra("message", "Login request");
    	mainActivity.startActivityForResult(intent, LoginActivity.LOGIN_ACTIVITY);
		
	}
	
	public void clickNotes(View v) {
		Intent intent = new Intent(v.getContext(), NotesActivity.class);
    	intent.putExtra("message", "Notes request");
    	mainActivity.startActivityForResult(intent, NotesActivity.NOTES_ACTIVITY);
		
	}
	
	public void clickReminders(View v) {
		Intent intent = new Intent(v.getContext(), RemindersActivity.class);
    	intent.putExtra("message", "Reminders request");
    	mainActivity.startActivityForResult(intent, RemindersActivity.REMINDERS_ACTIVITY);
		
	}
	
	public void onResume() {
		
		Token connectionToken = ((NoteAndReminderApplication) mainActivity.getApplication()).getToken();
		if(connectionToken != null && connectionToken.getAuth()){
			mainActivity.getLoginButtonLayout().setVisibility(View.GONE);
			mainActivity.getLoggedButtonsLayout().setVisibility(View.VISIBLE);
		}
		else{
			mainActivity.getLoginButtonLayout().setVisibility(View.VISIBLE);
			mainActivity.getLoggedButtonsLayout().setVisibility(View.GONE);
		}
	}
	
}
