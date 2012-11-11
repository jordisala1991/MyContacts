package com.idi.adapters;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.idi.classes.Contact;
import com.idi.classes.Group;
import com.idi.database.GroupContactsHelper;
import com.idi.database.MyDbController;
import com.idi.mycontacts.R;

public class MyGroupContactsWithOrderListViewAdapter extends ArrayAdapter<Contact>
{
    private static ArrayList<Contact> contacts;
    private Group group;
    private LayoutInflater layoutInflater;
    private GroupContactsHelper mGroupContactsHelper;
    private MyDbController db;
    private int mode;
    
    private static final int NORMAL_MODE = 0;
    private static final int EDIT_POSITIONS = 1;
    
    public MyGroupContactsWithOrderListViewAdapter(Context context, int textViewResourceId, ArrayList<Contact> contacts, Group group) 
    {
        super(context, textViewResourceId, contacts);
        this.mGroupContactsHelper = new GroupContactsHelper(context);
        this.db = new MyDbController(context);
        this.group = group;
        this.mode = NORMAL_MODE;
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyGroupContactsWithOrderListViewAdapter.contacts = contacts;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder holder;
        Contact contact = contacts.get(position);
        if (convertView == null)
        {
        	holder = new ViewHolder();
    		convertView = layoutInflater.inflate(R.layout.contact_row_without_fav, null);
            holder.name = (TextView) convertView.findViewById(R.id.row_contactname);
            holder.phone = (TextView) convertView.findViewById(R.id.row_contactphone);
            holder.photo = (ImageView) convertView.findViewById(R.id.row_contactphoto);
            holder.up = (Button) convertView.findViewById(R.id.button_up);
            holder.down = (Button) convertView.findViewById(R.id.button_down);
        	convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();	
    	holder.name.setText(contact.getName());
    	if (contact.getHasPhoneNumber()) holder.phone.setText(contact.getPhones().get(0).getKey());
    	else holder.phone.setText(null);
    	holder.photo.setImageBitmap(contact.getPhoto());
    	
    	if (mode == EDIT_POSITIONS)
    	{
	    	if (position > 0)
	    	{
	    		holder.up.setOnClickListener(new MyContactOrderUpClickListener(this, position));
	    		holder.up.setVisibility(0);
	    	}
	    	else
			{
	    		holder.up.setOnClickListener(null);
	    		holder.up.setVisibility(4);
			}
	    	if (position < contacts.size() - 1)
	    	{
	    		holder.down.setOnClickListener(new MyContactOrderDownClickListener(this, position));
	    		holder.down.setVisibility(0);
	    	}
	    	else
	    	{
	    		holder.down.setOnClickListener(null);
	    		holder.down.setVisibility(4);
	    	}   		
    	}
    	else if (mode == NORMAL_MODE)
    	{
    		holder.up.setOnClickListener(null);
    		holder.up.setVisibility(4);
    		holder.down.setOnClickListener(null);
    		holder.down.setVisibility(4);
    	}

    	return convertView;
    }

	private class ViewHolder
	{
	    public TextView name = null;
	    public TextView phone = null;
	    public ImageView photo = null;
	    public Button up = null;
	    public Button down = null;
	}
	
	public void ContactMoveUp(int position)
	{
		Contact contactToMoveUp = contacts.get(position);
		Contact contactToMoveDown = contacts.get(position - 1);
		contacts.remove(contactToMoveUp);
		contacts.remove(contactToMoveDown);
		contacts.add(position - 1, contactToMoveUp);
		contacts.add(position, contactToMoveDown);
		this.remove(contactToMoveDown);
		this.remove(contactToMoveUp);
		this.insert(contactToMoveUp, position - 1);
		this.insert(contactToMoveDown, position);
		int orderBefore = mGroupContactsHelper.getPosition(group.getId(), contactToMoveUp.getId());
		int orderAfter = mGroupContactsHelper.getPosition(group.getId(), contactToMoveDown.getId());
		db.open();
		db.updateGroupContactPosition(group.getId(), contactToMoveUp.getId(), orderAfter);
		db.updateGroupContactPosition(group.getId(), contactToMoveDown.getId(), orderBefore);
		db.close();
		this.notifyDataSetChanged();
	}
	
	public void ContactMoveDown(int position)
	{
		Contact contactToMoveDown = contacts.get(position);
		Contact contactToMoveUp = contacts.get(position + 1);
		contacts.remove(contactToMoveDown);
		contacts.remove(contactToMoveUp);
		contacts.add(position, contactToMoveUp);
		contacts.add(position + 1, contactToMoveDown);
		this.remove(contactToMoveDown);
		this.remove(contactToMoveUp);
		this.insert(contactToMoveUp, position);
		this.insert(contactToMoveDown, position + 1);
		int orderBefore = mGroupContactsHelper.getPosition(group.getId(), contactToMoveDown.getId());
		int orderAfter = mGroupContactsHelper.getPosition(group.getId(), contactToMoveUp.getId());
		db.open();
		db.updateGroupContactPosition(group.getId(), contactToMoveDown.getId(), orderAfter);
		db.updateGroupContactPosition(group.getId(), contactToMoveUp.getId(), orderBefore);
		db.close();
		this.notifyDataSetChanged();
	}
	
	public void EditMode()
	{
		this.mode = EDIT_POSITIONS;
		notifyDataSetChanged();
	}
	
	public void NormalMode()
	{
		this.mode = NORMAL_MODE;
		notifyDataSetChanged();
	}
	
}


