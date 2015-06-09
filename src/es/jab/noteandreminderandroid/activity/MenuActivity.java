package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.presenter.MenuPresenter;
import android.view.Menu;
import android.view.MenuItem;

public abstract class MenuActivity extends GenericConnectionActivity {
	
	public static final int MENU_ACTIVITY = 007;
	
	public static final String METHOD = "logout";
	
	public static final int HOME_ITEM = 0;
	public static final int PROFILE_ITEM = 1;
	public static final int LOGOUT_ITEM = 2;
	
	
	private MenuPresenter menuPresenter;
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
		menuPresenter = new MenuPresenter(this);
		return menuPresenter.onCreateOptionsMenu(menu);
    }
	
	@Override
	public boolean onPrepareOptionsMenu (Menu menu) {
	    return menuPresenter.onPrepareOptionsMenu(menu);
	}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	menuPresenter.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

	@Override
	public void closeConnection(boolean error, String json) {
		menuPresenter.closeConnection(error, json);
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
