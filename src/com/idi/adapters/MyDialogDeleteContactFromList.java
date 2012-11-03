package com.idi.adapters;

import com.idi.classes.Contact;
import com.idi.database.MyDbController;
import com.idi.mycontacts.ContactsActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.ContactsContract.RawContacts;

public class MyDialogDeleteContactFromList implements OnClickListener
{	

	private ContactsActivity contactsActivity;
	private Contact contact;
	private MyDbController db;

	public MyDialogDeleteContactFromList(ContactsActivity contactsActivity, Contact contact)
	{
		this.contactsActivity = contactsActivity;
		this.contact = contact;
		db = new MyDbController(contactsActivity);
		
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		contactsActivity.getContentResolver().delete(RawContacts.CONTENT_URI, RawContacts._ID+"=?", new String[] { String.valueOf(contact.getId())});
		db.open();
		db.deleteGroupContactRelation(contact.getId());
		db.close();
		contactsActivity.fillData();
	}

}
