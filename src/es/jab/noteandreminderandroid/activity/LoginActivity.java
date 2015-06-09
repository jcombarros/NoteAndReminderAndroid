package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.LoginPresenter;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends GenericConnectionActivity{
	
	public static final int LOGIN_ACTIVITY = 002;
	
	public static final String METHOD = "authenticate";
	
	private LoginPresenter loginPresenter;
	
	private Button loginButton;
	private EditText inputEmail;
	private EditText inputPassword;
	
	public Button getLoginButton(){
		return loginButton;
	}
	
	public EditText getInputEmail(){
		return inputEmail;
	}
	
	public EditText getInputPassword(){
		return inputPassword;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		loginPresenter = new LoginPresenter(this);
		
		loginButton = (Button) findViewById(R.id.LoginButtonLogin);
		inputEmail = (EditText) findViewById(R.id.EmailInputLogin);
		inputPassword = (EditText) findViewById(R.id.PasswordInputLogin);
		
		loginButton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		loginPresenter.clickLogin(v);
        	}
        });
	}

	@Override
	public void closeConnection(boolean error, String json) {
		loginPresenter.closeConnection(error, json);
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
