package com.idi.adapters;

import com.idi.classes.Contact;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class MyOnCheckedChangeGroupContactsListener implements OnCheckedChangeListener {

	Contact contact;
	
	public MyOnCheckedChangeGroupContactsListener(Contact contact) {
		this.contact = contact;
	}

	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (isChecked) MyGroupContactsListViewAdapter.addContactToGroup(contact);
		else MyGroupContactsListViewAdapter.removeContactFromGroup(contact);
	}

}
