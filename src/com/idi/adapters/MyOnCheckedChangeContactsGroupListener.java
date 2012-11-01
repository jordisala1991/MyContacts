package com.idi.adapters;

import com.idi.database.MyDbController;
import android.content.Context;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyOnCheckedChangeContactsGroupListener implements OnCheckedChangeListener
{
	
	private int contactId;
	private int position;
	private MyDbController db;
	
	public MyOnCheckedChangeContactsGroupListener(Context context, int contactId, int position)
	{
		this.contactId = contactId;
		this.position = position;
		this.db = new MyDbController(context);
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
	{
		db.open();
		if (isChecked) db.addFavourite(contactId);
		else db.deleteFavourite(contactId);
		MyFavouritesListViewAdapter.setFavourite(position, isChecked);
	    db.close();
	}

}
