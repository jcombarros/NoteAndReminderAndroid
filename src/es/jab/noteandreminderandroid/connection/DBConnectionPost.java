package es.jab.noteandreminderandroid.connection;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import es.jab.noteandreminderandroid.activity.GenericConnectionActivity;

public class DBConnectionPost extends DBConnection{

	public DBConnectionPost(GenericConnectionActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpUriRequest processMethod(String route, String method, String data) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(DBConnection.URL + method);
		StringEntity entity = new StringEntity(data, HTTP.UTF_8);
		httpPost.setEntity(entity);
		
		return httpPost;
	}

}
