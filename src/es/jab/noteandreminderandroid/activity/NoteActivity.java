package es.jab.noteandreminderandroid.activity;



import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.presenter.NotePresenter;
import android.os.Bundle;
import android.widget.TextView;

public class NoteActivity extends GenericConnectionActivity {
	
	public static final int NOTE_ACTIVITY = 004;
	
	private NotePresenter notePresenter;
	
	private TextView headerViewNote;
	private TextView subHeaderViewNote;
	private TextView descriptionViewNote;
	
	public TextView getHeaderViewNote(){
		return headerViewNote;
	}
	
	public TextView getSubHeaderViewNote(){
		return subHeaderViewNote;
	}
	
	public TextView getDescriptionViewNote(){
		return descriptionViewNote;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_note);
		
		notePresenter = new NotePresenter(this);
		
		headerViewNote = (TextView) findViewById(R.id.HeaderViewNote);
		subHeaderViewNote = (TextView) findViewById(R.id.SubHeaderViewNote);
		descriptionViewNote = (TextView) findViewById(R.id.DescriptionViewNote);
		
		headerViewNote.setText("");		
		subHeaderViewNote.setText("");
		descriptionViewNote.setText("");
		
		notePresenter.onCreate();
	}

	@Override
	public void closeConnection(boolean error, String json) {
		notePresenter.closeConnection(error, json);
	}
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
	}
	
	@Override
	public void onResume(){
		super.onResume();
	}
}
