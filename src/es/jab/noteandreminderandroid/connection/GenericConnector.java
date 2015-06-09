package es.jab.noteandreminderandroid.connection;

public interface GenericConnector {

	public void openConnection(String route, String method, String queryString);
	
	public void closeConnection(boolean error, String json);
	
}
