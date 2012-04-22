package com.idi.adapters;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import com.idi.classes.Contact;
import com.idi.mycontacts.R;

public class MyFavouritesListViewAdapter extends ArrayAdapter<Contact>
{
    private static ArrayList<Contact> contacts;
    private LayoutInflater layoutInflater;

    public MyFavouritesListViewAdapter(Context context, int textViewResourceId, ArrayList<Contact> contacts) 
    {
        super(context, textViewResourceId, contacts);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyFavouritesListViewAdapter.contacts = contacts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder holder;
        Contact contact = contacts.get(position);
        if (convertView == null)
        {
        	holder = new ViewHolder();
    		convertView = layoutInflater.inflate(R.layout.contact_row, null);
            holder.name = (TextView) convertView.findViewById(R.id.row_contactname);
            holder.phone = (TextView) convertView.findViewById(R.id.row_contactphone);
            holder.photo = (ImageView) convertView.findViewById(R.id.row_contactphoto);
            holder.favCheck = (CheckBox) convertView.findViewById(R.id.row_favourite);
        	convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();	
    	holder.name.setText(contact.getName());
    	if (contact.getHasPhoneNumber()) holder.phone.setText(contact.getPhones().get(0).getKey());
    	else holder.phone.setText(null);
    	holder.photo.setImageBitmap(contact.getPhoto());
    	holder.favCheck.setOnCheckedChangeListener(null);
    	holder.favCheck.setChecked(contact.getIsFavourite());
    	holder.favCheck.setOnCheckedChangeListener(new MyOnCheckedChangeFavouritesListener(getContext(), contact.getId(), position));
        return convertView;
    }

	private class ViewHolder
	{
	    public TextView name = null;
	    public TextView phone = null;
	    public ImageView photo = null;
		public CheckBox favCheck = null;
	}

	public static void setFavourite(int position, boolean isChecked) {
		Contact contact = contacts.get(position);
		contact.setIsFavourite(isChecked);
		contacts.set(position, contact);
	}

}


