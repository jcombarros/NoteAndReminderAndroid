package es.jab.noteandreminderandroid;
 
import es.jab.noteandreminderandroid.model.Token;
import android.app.Application;

public class NoteAndReminderApplication extends Application {
	
	private Token token;

    public Token getToken() {
        return token;
    }

    public void setToken(Token token) {
        this.token = token;
    }
    
    public String getAccessToken(){
    	if(token != null){
    		return token.getToken();
    	}
    	return null;
    }
    
}