package com.idi.adapters;

import com.idi.classes.Contact;
import com.idi.database.MyDbController;
import com.idi.mycontacts.ContactDetailsActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.ContactsContract.RawContacts;

public class MyDialogDeleteContactFromDetails implements OnClickListener
{	

	private ContactDetailsActivity contactsDetailsActivity;
	private Contact contact;
	private MyDbController db;

	public MyDialogDeleteContactFromDetails(ContactDetailsActivity contactDetailsActivity, Contact contact)
	{
		this.contactsDetailsActivity = contactDetailsActivity;
		this.contact = contact;
		db = new MyDbController(contactDetailsActivity);
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		contactsDetailsActivity.getContentResolver().delete(RawContacts.CONTENT_URI, RawContacts._ID+"=?", new String[] { String.valueOf(contact.getId())});
		contactsDetailsActivity.setResult(ContactDetailsActivity.MODIFIED_RESULT);
		db.open();
		db.deleteGroupContactRelation(contact.getId());
		db.close();
		contactsDetailsActivity.finish();
	}

}
