package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.MainPresenter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends MenuActivity {
	
	public static final int MAIN_ACTIVITY = 001;

	private MainPresenter mainPresenter;
	
	private Button loginButton;
	private Button notesButton;
	private Button remindersButton;
	private LinearLayout loginButtonLayout;
	private LinearLayout loggedButtonsLayout;
	
	public LinearLayout getLoginButtonLayout(){
		return loginButtonLayout;
	}
	
	public LinearLayout getLoggedButtonsLayout(){
		return loggedButtonsLayout;
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mainPresenter = new MainPresenter(this);
        
        loginButton = (Button) findViewById(R.id.LoginButtonHome);
        notesButton = (Button) findViewById(R.id.NotesButtonHome);
        remindersButton = (Button) findViewById(R.id.RemindersButtonHome);
        loginButtonLayout = (LinearLayout) findViewById(R.id.LoginButtonLayoutHome);
    	loggedButtonsLayout = (LinearLayout) findViewById(R.id.LoggedButtonsLayoutHome);
    	
        
        loginButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		mainPresenter.clickLogin(v);
        	}
        });

        notesButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		mainPresenter.clickNotes(v);
        	}
        });
        
        remindersButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		mainPresenter.clickReminders(v);
        	}
        });
    }
	
	@Override
	public void onResume() {
		super.onResume();
		mainPresenter.onResume();
	}
}
