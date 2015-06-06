package es.jab.noteandreminderandroid.connection;

import java.io.UnsupportedEncodingException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.protocol.HTTP;

import es.jab.noteandreminderandroid.activity.GenericConnectionActivity;

public class WSConnectionPost extends WSConnection{

	public WSConnectionPost(GenericConnectionActivity activity) {
		super(activity);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected HttpUriRequest processMethod(String route, String method, String data) throws UnsupportedEncodingException {
		HttpGet httpGet = new HttpGet(WSConnection.URL + route + method);
		//httpGet.addHeader("Authorization", value);
		
		return httpGet;
	}

}
