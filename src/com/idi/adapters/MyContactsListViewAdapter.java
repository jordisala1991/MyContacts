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
import com.idi.classes.Item;
import com.idi.classes.Section;
import com.idi.mycontacts.R;

public class MyContactsListViewAdapter extends ArrayAdapter<Item>
{
    private static ArrayList<Item> items;
    private LayoutInflater layoutInflater;
    private static final int TYPE_SECTION_HEADER = 0;
    private static final int TYPE_LIST_ITEM  = 1;

    public MyContactsListViewAdapter(Context context, int textViewResourceId, ArrayList<Item> items) 
    {
        super(context, textViewResourceId, items);
        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        MyContactsListViewAdapter.items = items;
    }
    
    @Override
    public int getViewTypeCount()
    {
        return 2;
    }

    @Override
    public int getItemViewType(int position)
    {
        Item item = items.get(position);
        if (item.isSection()) return TYPE_SECTION_HEADER;
        else return TYPE_LIST_ITEM;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder holder;
        Item item = items.get(position);
        if (convertView == null)
        {
        	holder = new ViewHolder();
        	if (item.isSection())
        	{
        		convertView = layoutInflater.inflate(R.layout.contact_section, null);
        		convertView.setOnClickListener(null);
				convertView.setOnLongClickListener(null);
				convertView.setLongClickable(false);
				convertView.setFocusable(false);
				convertView.setFocusableInTouchMode(false);
        		holder.text = (TextView) convertView.findViewById(R.id.section_text);
        	}
        	else
        	{
        		convertView = layoutInflater.inflate(R.layout.contact_row, null);
                holder.name = (TextView) convertView.findViewById(R.id.row_contactname);
                holder.phone = (TextView) convertView.findViewById(R.id.row_contactphone);
                holder.photo = (ImageView) convertView.findViewById(R.id.row_contactphoto);
                holder.favCheck = (CheckBox) convertView.findViewById(R.id.row_favourite);
        	}
        	convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();
        if (holder.text != null) holder.text.setText(((Section) item).getTitle());
        else
        {
        	Contact contact = (Contact) item;
        	holder.name.setText(contact.getName());
        	if (contact.getHasPhoneNumber()) holder.phone.setText(contact.getPhones().get(0).getKey());
        	else holder.phone.setText(null);
        	holder.photo.setImageBitmap(contact.getPhoto());
        	holder.favCheck.setOnCheckedChangeListener(null);
        	holder.favCheck.setChecked(contact.getIsFavourite());
        	holder.favCheck.setOnCheckedChangeListener(new MyOnCheckedChangeListener(getContext(), contact.getId(), position));
        }
        return convertView;
    }

	private class ViewHolder
	{
	    public TextView text = null;
	    public TextView name = null;
	    public TextView phone = null;
	    public ImageView photo = null;
		public CheckBox favCheck = null;
	}

	public static void setFavourite(int position, boolean isChecked) {
		Contact contact = (Contact) items.get(position);
		contact.setIsFavourite(isChecked);
		items.set(position, contact);
	}

}


