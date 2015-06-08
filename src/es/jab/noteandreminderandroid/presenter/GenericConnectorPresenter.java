package es.jab.noteandreminderandroid.presenter;

import es.jab.noteandreminderandroid.model.Token;

public interface GenericConnectorPresenter {

	public void openConnection(String route, String method, String queryString);
	
	public void closeConnection(boolean error, String json);
	
	public void connectionEstablished(Token returnToken);
		
	public void connectionFailed();
	
	public void connectionFinished();
	
}
