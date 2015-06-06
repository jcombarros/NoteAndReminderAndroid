package es.jab.noteandreminderandroid.activity;

import es.jab.noteandreminderandroid.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button boton = (Button) findViewById(R.id.LoginButtonHome);
		boton.setOnClickListener(new OnClickListener(){
        	public void onClick(View v){
        		click(v);
        	}
        });
    }


	private void click(View v) {
		Intent intent = new Intent(v.getContext(), LoginActivity.class);
    	intent.putExtra("message", "From MainActivity");
    	startActivityForResult(intent, 0);
		
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
