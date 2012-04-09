package com.idi.database;

import java.util.ArrayList;
import java.util.Collections;
import com.idi.classes.Contact;
import com.idi.classes.Item;
import com.idi.classes.Section;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.provider.ContactsContract.Contacts;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.RawContacts;

public class ContactsHelper
{
	
	private ContentResolver contentResolver;
	private Resources resources;
	private ArrayList<Contact> contacts;
	private ArrayList<Section> sections;
	private MyDbController db;
	
	public ContactsHelper(Context context)
	{
		contentResolver = context.getContentResolver();
		resources = context.getResources();
		contacts = new ArrayList<Contact>();
		sections = new ArrayList<Section>();
		db = new MyDbController(context);
	}

	public ArrayList<Item> getItemsViewAllContacts()
	{
		ArrayList<Item> res = new ArrayList<Item>();
		getContactsId();
		if (contacts.size() > 0)
		{
			getContactsName();
			getPhotos();
			getPhones();
			getEmails();
			getIsFavourite();
		}
		res.addAll(contacts);
		res.addAll(sections);
		Collections.sort(res);
		return res;
	}

	private void getContactsId()
	{
		String[] projection = new String[] { RawContacts.CONTACT_ID, RawContacts.DELETED };
		Cursor cursorContacts = contentResolver.query(RawContacts.CONTENT_URI, projection, null, null, RawContacts.CONTACT_ID + " ASC");
		int contactIdColumn = cursorContacts.getColumnIndex(RawContacts.CONTACT_ID);
		int deletedColumn = cursorContacts.getColumnIndex(RawContacts.DELETED);
		for(cursorContacts.moveToFirst(); !cursorContacts.isAfterLast(); cursorContacts.moveToNext())
		{
			int contactId = cursorContacts.getInt(contactIdColumn);
			boolean deleted = (cursorContacts.getInt(deletedColumn) == 1);
			if(!deleted)
			{
				Contact contact = new Contact();
				contact.setId(contactId);
				contacts.add(contact);
			}
		}
		cursorContacts.close();
	}
	
	private void getContactsName()
	{
		ArrayList<String> lettersUsed = new ArrayList<String>();
		int contactsIndex = 0;
		int contactId = 0;
		String name = "";
		String[] projection = new String[] { Contacts._ID, Contacts.DISPLAY_NAME };
		Cursor cursorContacts = contentResolver.query(Contacts.CONTENT_URI, projection, null, null, Contacts._ID + " ASC");
		int contactIdColumn = cursorContacts.getColumnIndex(Contacts._ID);
		int nameColumn = cursorContacts.getColumnIndex(Contacts.DISPLAY_NAME);
		cursorContacts.moveToFirst();
		while(!cursorContacts.isAfterLast() && contactsIndex < contacts.size())
		{
			Contact contact = contacts.get(contactsIndex);
			while (contactId < contact.getId() && !cursorContacts.isAfterLast())
			{
				contactId = cursorContacts.getInt(contactIdColumn);
				name = cursorContacts.getString(nameColumn);
				cursorContacts.moveToNext();
			}
			if (contactId == contact.getId())
			{
				if (name != null && name.length() > 0)
				{
					String newLetter = name.substring(0, 1).toUpperCase();
					if (!newLetter.matches("[A-Z]")) newLetter = "#";
					if (!lettersUsed.contains(newLetter))
					{
						lettersUsed.add(newLetter);
						sections.add(new Section(newLetter));
					}
				}
				contact.setName(name);
				contacts.set(contactsIndex, contact);
				++contactsIndex;
			}
			else contacts.remove(contactsIndex);			
		}
		cursorContacts.close();		
	}

	private void getPhotos()
	{
		int contactsIndex = 0;
		int oldContactId = -1;
		String[] projection = new String[] { Photo.CONTACT_ID, Photo.PHOTO };
		Cursor cursorPhotos = contentResolver.query(Data.CONTENT_URI, projection, null, null, Photo.CONTACT_ID + " ASC");
		int contactIdColumn = cursorPhotos.getColumnIndex(Photo.CONTACT_ID);
		int photoColumn = cursorPhotos.getColumnIndex(Photo.PHOTO);
		for (cursorPhotos.moveToFirst(); !cursorPhotos.isAfterLast(); cursorPhotos.moveToNext())
		{
			int contactId = cursorPhotos.getInt(contactIdColumn);
			while (contactId == oldContactId && !cursorPhotos.isLast())
			{
				cursorPhotos.moveToNext();
				contactId = cursorPhotos.getInt(contactIdColumn);
			}
			byte[] photoBlob = cursorPhotos.getBlob(photoColumn);
			Bitmap photo = null;
			if (photoBlob != null) photo = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
			else photo = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_contact_photo", "drawable", "com.idi.mycontacts"));
			photo = Bitmap.createScaledBitmap(photo, 96, 96, true);
			while (contactsIndex < contacts.size())
			{
				Contact contact = contacts.get(contactsIndex);
				++contactsIndex;
				if (contactId == contact.getId())
				{
					contact.setPhoto(photo);
					contacts.set(contactsIndex - 1, contact);
					break;
				}
			}
			oldContactId = contactId;
		}
		cursorPhotos.close();
	}
	
	private void getPhones() {
		int contactsIndex = 0;
		final String[] projection = new String[] { Phone.CONTACT_ID, Phone.NUMBER, Phone.TYPE };
		final Cursor cursorPhones = contentResolver.query(Phone.CONTENT_URI, projection, null, null, Phone.CONTACT_ID + " ASC");
		int contactIdColumn = cursorPhones.getColumnIndex(Phone.CONTACT_ID);
		int phoneColumn = cursorPhones.getColumnIndex(Phone.NUMBER);
		int typeColumn = cursorPhones.getColumnIndex(Phone.TYPE);
		for (cursorPhones.moveToFirst(); !cursorPhones.isAfterLast(); cursorPhones.moveToNext())
		{
			int contactId = cursorPhones.getInt(contactIdColumn);
			String number = cursorPhones.getString(phoneColumn);
			int type = cursorPhones.getInt(typeColumn);
			int typeLabelResource = Phone.getTypeLabelResource(type);
			while (contactsIndex < contacts.size())
			{
				Contact contact = contacts.get(contactsIndex);
				++contactsIndex;
				if (contactId == contact.getId())
				{
					contact.addPhoneNumber(number, typeLabelResource);
					contact.setHasPhoneNumber(true);
					contacts.set(contactsIndex - 1, contact);
					break;
				}
			}
		}
		cursorPhones.close();
	}
	
	private void getEmails() {
		int contactsIndex = 0;
		final String[] projection = new String[] { Email.CONTACT_ID, Email.DATA, Email.TYPE };
		final Cursor cursorEmails = contentResolver.query(Email.CONTENT_URI, projection, null, null, Email.CONTACT_ID + " ASC");
		int contactIdColumn = cursorEmails.getColumnIndex(Email.CONTACT_ID);
		int emailColumn = cursorEmails.getColumnIndex(Email.DATA);
		int typeColumn = cursorEmails.getColumnIndex(Email.TYPE);
		for (cursorEmails.moveToFirst(); !cursorEmails.isAfterLast(); cursorEmails.moveToNext())
		{
			int contactId = cursorEmails.getInt(contactIdColumn);
			String email = cursorEmails.getString(emailColumn);
			int type = cursorEmails.getInt(typeColumn);
			int typeLabelResource = Phone.getTypeLabelResource(type);
			while (contactsIndex < contacts.size())
			{
				Contact contact = contacts.get(contactsIndex);
				++contactsIndex;
				if (contactId == contact.getId())
				{
					contact.addEmailAddress(email, typeLabelResource);
					contact.setHasEmailAddress(true);
					contacts.set(contactsIndex - 1, contact);
					break;
				}
			}
		}
		cursorEmails.close();
	}
	
	private void getIsFavourite() {
		db.open();
		Cursor favourites = db.fetchFavourites();
		int contactIdColumnIndex = favourites.getColumnIndex(MyDbController.KEY_IDCONTACT);
		int contactsIndex = 0;
		for (favourites.moveToFirst(); !favourites.isAfterLast(); favourites.moveToNext())
		{
			int favourite = favourites.getInt(contactIdColumnIndex);
			while (contactsIndex < contacts.size())
			{
				Contact contact = contacts.get(contactsIndex);
				++contactsIndex;
				if (contact.getId() == favourite)
				{
					contact.setIsFavourite(true);
					contacts.set(contactsIndex -1, contact);
					break;
				}
			}
		}
		db.close();
	}

}