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

public class MyGroupContactsListViewAdapter extends ArrayAdapter<Contact>
{
    private static ArrayList<Contact> contacts;
    private static ArrayList<Contact> contactsOfGroup;
    private LayoutInflater layoutInflater;

    public MyGroupContactsListViewAdapter(Context context, int textViewResourceId, ArrayList<Contact> contacts) 
    {
        super(context, textViewResourceId, contacts);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyGroupContactsListViewAdapter.contacts = contacts;
        MyGroupContactsListViewAdapter.contactsOfGroup = new ArrayList<Contact>();
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder holder;
        Contact contact = contacts.get(position);
        if (convertView == null)
        {
        	holder = new ViewHolder();
    		convertView = layoutInflater.inflate(R.layout.contact_add_group_row, null);
            holder.name = (TextView) convertView.findViewById(R.id.row_contactname);
            holder.phone = (TextView) convertView.findViewById(R.id.row_contactphone);
            holder.photo = (ImageView) convertView.findViewById(R.id.row_contactphoto);
            holder.groupCheck = (CheckBox) convertView.findViewById(R.id.row_group_check);
        	convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();	
    	holder.name.setText(contact.getName());
    	if (contact.getHasPhoneNumber()) holder.phone.setText(contact.getPhones().get(0).getKey());
    	else holder.phone.setText(null);
    	holder.groupCheck.setOnCheckedChangeListener(null);
    	if (contactsOfGroup.contains(contact)) holder.groupCheck.setChecked(true);
    	else holder.groupCheck.setChecked(false);
    	holder.groupCheck.setOnCheckedChangeListener(new MyOnCheckedChangeGroupContactsListener(contact));
    	holder.photo.setImageBitmap(contact.getPhoto());
        return convertView;
    }
    
    public static void addContactToGroup(Contact contact)
    {
    	contactsOfGroup.add(contact);
    }
    
    public static void removeContactFromGroup(Contact contact)
    {
    	contactsOfGroup.remove(contact);
    }
    
    public static ArrayList<Contact> getContactsAddedToGroup()
    {
    	return contactsOfGroup;
    }

	private class ViewHolder
	{
	    public TextView name = null;
	    public TextView phone = null;
	    public ImageView photo = null;
		public CheckBox groupCheck = null;
	}

}


