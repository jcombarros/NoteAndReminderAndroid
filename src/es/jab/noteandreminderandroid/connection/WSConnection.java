package es.jab.noteandreminderandroid.connection;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.util.EntityUtils;

import es.jab.noteandreminderandroid.NoteAndReminderApplication;
import es.jab.noteandreminderandroid.activity.GenericConnectionActivity;
import android.app.ProgressDialog;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;

public abstract class WSConnection extends AsyncTask<String, Void, String>{
	
	public static final String URL = "http://10.0.2.2:8080/NoteAndReminderApi/";
	public static final String AUTH_ROUTE = "auth/";
	public static final String API_ROUTE = "api/";

	protected ProgressDialog pDialog;
	protected GenericConnectionActivity activity;
	protected String accessToken;
	
	private boolean error;
	
	public WSConnection(GenericConnectionActivity activity) {
        this.activity = activity;
        accessToken = ((NoteAndReminderApplication) activity.getApplication()).getAccessToken();
    }
	
	@Override
	protected void onPreExecute(){
		super.onPreExecute();
		error = false;
		pDialog = new ProgressDialog(activity);
		pDialog.setMessage("In process...");
		pDialog.setIndeterminate(false);
		pDialog.setCancelable(true);
		pDialog.show();
	}
	
	@Override
	protected String doInBackground(String... params) {
		String route = params[0];
		String method = params[1];
		String data = params[2];
		String queryString = params[3];

		String dataResponse = "";
		
		AndroidHttpClient httpClient  = AndroidHttpClient.newInstance("AndroidHttpClient");
		try {
			HttpUriRequest httpUriRequest = processMethod(route, method, data, queryString);
		
			HttpResponse response = httpClient.execute(httpUriRequest);
			dataResponse = EntityUtils.toString(response.getEntity());
			
		} catch (Exception e) {
			Log.e("Error al recuperar los datos", e.toString());
			error = true;
		}finally{
			httpClient.close();
		}
		
		return dataResponse;
	}
	
	@Override
	protected void onPostExecute(String json) {
		pDialog.dismiss();
		
		activity.closeConnection(error, json);
	}
	
	protected abstract HttpUriRequest processMethod(String route, String method, String data, String queryString) throws UnsupportedEncodingException;

}
