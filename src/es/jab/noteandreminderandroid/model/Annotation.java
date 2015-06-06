package es.jab.noteandreminderandroid.model;

import java.util.Date;

import com.google.gson.annotations.Expose;

public abstract class Annotation {
	
	@Expose
	protected int id;
	
	@Expose
	protected String title;
	
	protected User user;
	
	protected Category category;
	
	@Expose
	protected Date creationDate;
	
	protected Annotation() {
		this(0, "", null, null);
	}

	protected Annotation(int id, String title, User user, Date creationDate) {
		super();
		this.setId(id);
		this.setTitle(title);
		this.setUser(user);
		this.setCreationDate(creationDate);
	}

	public int getId() {
		return id;
	}

	protected void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public User getUser() {
		return user;
	}

	public abstract void setUser(User user);

	public Category getCategory() {
		return category;
	}

	public abstract void setCategory(Category category);

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	
	
}
