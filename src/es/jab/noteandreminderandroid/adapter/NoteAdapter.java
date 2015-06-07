package es.jab.noteandreminderandroid.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.model.Note;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoteAdapter extends BaseAdapter{

	private Context context;
	
    private List<Note> notes;
    
    public NoteAdapter(Context context, List<Note> items) {
    	this.context = context;
        this.notes = items;
    }
	
	@Override
	public int getCount() {
		return this.notes.size();
	}

	@Override
	public Object getItem(int position) {
		return this.notes.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		 
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.activity_notes_note, parent, false);
        }
 
        TextView textView = (TextView) rowView.findViewById(R.id.TitleViewNote);
        TextView subTextView = (TextView) rowView.findViewById(R.id.SubTitleNote);
 
        Note note = this.notes.get(position);
        textView.setText(note.getTitle());
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        subTextView.setText("Creation date: " + df.format(note.getCreationDate()));
        subTextView.setTypeface(null, Typeface.ITALIC);
 
        return rowView;
	}

}
