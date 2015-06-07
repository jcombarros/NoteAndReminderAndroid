package es.jab.noteandreminderandroid.adapter;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import es.jab.noteandreminderandroid.R;
import es.jab.noteandreminderandroid.model.Reminder;

public class ReminderAdapter extends BaseAdapter{

private Context context;
	
    private List<Reminder> reminders;
    
    public ReminderAdapter(Context context, List<Reminder> items) {
    	this.context = context;
        this.reminders = items;
    }
	
	@Override
	public int getCount() {
		return this.reminders.size();
	}

	@Override
	public Object getItem(int position) {
		return this.reminders.get(position);
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
            rowView = inflater.inflate(R.layout.activity_reminders_reminder, parent, false);
        }
 
        TextView textView = (TextView) rowView.findViewById(R.id.TitleReminder);
        TextView subTextView = (TextView) rowView.findViewById(R.id.SubTitleReminder);
 
        Reminder reminder = this.reminders.get(position);
        textView.setText(reminder.getTitle());
        
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        subTextView.setText("Is Completed? " + Boolean.toString(reminder.isCompleted()));
        subTextView.setTypeface(null, Typeface.ITALIC);
 
        return rowView;
	}
}
