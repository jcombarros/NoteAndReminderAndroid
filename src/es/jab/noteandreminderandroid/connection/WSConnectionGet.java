package es.jab.noteandreminderandroid.connection;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;

import es.jab.noteandreminderandroid.activity.GenericConnectionActivity;

public class WSConnectionGet extends WSConnection{

	public WSConnectionGet(GenericConnectionActivity activity) {
		super(activity);
	}

	@Override
	protected HttpUriRequest processMethod(String route, String method, String data, String queryString) throws UnsupportedEncodingException {
		HttpGet httpGet = new HttpGet(WSConnection.URL + route + method + queryString);
		httpGet.addHeader("Authorization", accessToken);
		
		return httpGet;
	}

}
