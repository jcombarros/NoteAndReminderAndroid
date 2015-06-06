package es.jab.noteandreminderandroid.model;

public class Token {
	
	private boolean auth;
	
	private int userId;
	
	private String email;
	
	private String password;
	
	private String token;
	
	private String message;

	public Token() {
		super();
	}

	public Token(boolean auth, int userId, String email, String password, String token, String message) {
		super();
		this.auth = auth;
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.token = token;
		this.setMessage(message);
	}

	public boolean getAuth() {
		return auth;
	}

	public void setAuth(boolean auth) {
		this.auth = auth;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public void authorize(int userId, String signature){
		this.setAuth(true);
		this.setToken(signature);
		this.setUserId(userId);
		this.setPassword("");
	}
	
	public void deny(String message){
		this.setAuth(false);
		this.setMessage(message);
		this.setUserId(0);
		this.setPassword("");
	}
	
	public void disable(){
		this.setAuth(false);
		this.setMessage("");
		this.setUserId(0);
		this.setEmail("");
		this.setPassword("");
		this.setToken("");
	}

}
