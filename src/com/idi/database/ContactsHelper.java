package com.idi.database;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;

import com.idi.classes.Contact;
import com.idi.classes.Item;
import com.idi.classes.Pair;
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
import android.util.Log;

public class ContactsHelper
{
	
	private ContentResolver contentResolver;
	private Resources resources;
	private ArrayList<Contact> contacts;
	private ArrayList<Section> sections;
	
	public ContactsHelper(Context context)
	{
		contentResolver = context.getContentResolver();
		resources = context.getResources();
		contacts = new ArrayList<Contact>();
		sections = new ArrayList<Section>();
	}

	public ArrayList<Item> getItemsViewAllContacts()
	{
		ArrayList<Item> res = new ArrayList<Item>();
		getContactsId();
		if (contacts.size() > 0)
		{
			getContactsName();
			//getPhotos();
			//getPhones();
			//getEmails();
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
		cursorContacts.moveToFirst();
		while(!cursorContacts.isAfterLast())
		{
			int contactId = cursorContacts.getInt(contactIdColumn);
			boolean deleted = (cursorContacts.getInt(deletedColumn) == 1);
			if(!deleted)
			{
				Contact contact = new Contact();
				contact.setId(contactId);
				contacts.add(contact);
			}
			cursorContacts.moveToNext();
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

//	private void getPhotos()
//	{
//		int contactsIndex = 0;
//		String[] projection = new String[] { Photo.CONTACT_ID, Photo.PHOTO };
//		Cursor cursorPhotos = contentResolver.query(Data.CONTENT_URI, projection, null, null, Photo.CONTACT_ID + " ASC");
//		int contactIdColumn = cursorPhotos.getColumnIndex(Photo.CONTACT_ID);
//		int photoColumn = cursorPhotos.getColumnIndex(Photo.PHOTO);
//		cursorPhotos.moveToFirst();
//		while (!cursorPhotos.isAfterLast())
//		{
//			int contactId = cursorPhotos.getInt(contactIdColumn);
//			byte[] photoBlob = cursorPhotos.getBlob(photoColumn);
//			Log.d("id", String.valueOf(contactId));
//			//Bitmap photo = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
//			Contact contact = new Contact();
//			while (contact.getId() < contactId && contactsIndex < items.size())
//			{
//				Item item = items.get(contactsIndex);
//				if (item instanceof Contact) contact = (Contact) item;
//				++contactsIndex;
//			}
//			if (contact.getId() == contactId)
//			{
//				//contact.setPhoto(photo);
//				items.set(contactsIndex, contact);
//			}
//			cursorPhotos.moveToNext();
//		}
//	}
	
	/*public ArrayList<Item> getItemsViewAllContacts()
	{
		ArrayList<Item> res = new ArrayList<Item>();
		ArrayList<Integer> contactId = getAllContactsId();
		HashMap<String, Boolean> sections = new HashMap<String, Boolean>();
		for (int i = 0; i < contactId.size(); ++i)
		{
			Contact aux = getItemInfo(contactId.get(i));
			if (aux != null)
			{
				String lletraActual = aux.getName().substring(0, 1).toUpperCase();
				if (!lletraActual.matches("[A-Z]")) lletraActual = "#";
				if (!sections.containsKey(lletraActual))
				{
					sections.put(lletraActual, true);
					res.add(new Section(lletraActual));
				}
				res.add(aux);
			}
		}
		return res;
	}

	private ArrayList<Integer> getAllContactsId() 
	{
		ArrayList<Integer> res = new ArrayList<Integer>();
		String[] projection = new String[] { RawContacts.CONTACT_ID, RawContacts.DELETED };
		Cursor rawContacts = contentResolver.query(RawContacts.CONTENT_URI, projection, null, null, null);
		rawContacts.moveToFirst();
		while(!rawContacts.isAfterLast())
		{
			int contactId = rawContacts.getInt(rawContacts.getColumnIndex(RawContacts.CONTACT_ID));
			boolean deleted = (rawContacts.getInt(rawContacts.getColumnIndex(RawContacts.DELETED)) == 1);
			if(!deleted) res.add(contactId);
			rawContacts.moveToNext();
		}
		rawContacts.close();
		return res;
	}
	
	private Contact getItemInfo(int contactId)
	{
		Contact res = null;
		final String[] projection = new String[] { Contacts.DISPLAY_NAME, Contacts.PHOTO_ID, Contacts.HAS_PHONE_NUMBER };
		final Cursor contact = contentResolver.query(Contacts.CONTENT_URI, projection, Contacts._ID + "=?", new String[]{ String.valueOf(contactId) }, null);
		if(contact.moveToFirst())
		{
			String name = contact.getString(contact.getColumnIndex(Contacts.DISPLAY_NAME));
			String photoId = contact.getString(contact.getColumnIndex(Contacts.PHOTO_ID));
			boolean hasPhoneNumber = (contact.getInt(contact.getColumnIndex(Contacts.HAS_PHONE_NUMBER)) == 1);
			Bitmap photo;
			ArrayList< Pair<String, Integer> > phones = null;
			if (hasPhoneNumber) phones = getPhones(contactId);
			ArrayList< Pair<String, Integer> > emails = getEmails(contactId);
			boolean hasEmailAddress = emails != null;
			if (photoId != null) photo = getPhoto(photoId);
			else photo = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_contact_photo", "drawable", "com.idi.mycontacts"));
			res = new Contact(contactId, name, Bitmap.createScaledBitmap(photo, 96, 96, true), hasPhoneNumber, hasEmailAddress, phones, emails);
		}
		contact.close();
		return res;
	}

	private Bitmap getPhoto(String photoId)
	{
		Cursor photo = contentResolver.query(Data.CONTENT_URI, new String[] {Photo.PHOTO, Photo.}, Data._ID + "=?", new String[]{ photoId }, null);
		Bitmap photoBitmap = null;
		if(photo.moveToFirst())
		{
			byte[] photoBlob = photo.getBlob(photo.getColumnIndex(Photo.PHOTO));
			photoBitmap = BitmapFactory.decodeByteArray(photoBlob, 0, photoBlob.length);
		}
		photo.close();
		return photoBitmap;
	}
	
	private ArrayList< Pair<String, Integer> > getPhones(int contactId)
	{
		ArrayList< Pair<String, Integer> > res = new ArrayList< Pair<String, Integer> >();
		final String[] projection = new String[] { Phone.NUMBER, Phone.TYPE };
		final Cursor phone = contentResolver.query(Phone.CONTENT_URI, projection, Data.CONTACT_ID + "=?", new String[]{ String.valueOf(contactId) },null);
		if(phone.moveToFirst())
		{
			while(!phone.isAfterLast())
			{
				String number = phone.getString(phone.getColumnIndex(Phone.NUMBER));
				int type = phone.getInt(phone.getColumnIndex(Phone.TYPE));
				int typeLabelResource = Phone.getTypeLabelResource(type);
				res.add(new Pair<String, Integer>(number, typeLabelResource));
				phone.moveToNext();
			}

		}
		phone.close();
		return res;
	}
	
	private ArrayList< Pair<String, Integer> > getEmails(int contactId)
	{
		ArrayList< Pair<String, Integer> > res = new ArrayList< Pair<String, Integer> >();
		final String[] projection = new String[] { Email.DATA, Email.TYPE };
		final Cursor email = contentResolver.query(Email.CONTENT_URI, projection, Data.CONTACT_ID + "=?", new String[]{String.valueOf(contactId)}, null);
		if(email.moveToFirst())
		{
			while(!email.isAfterLast())
			{
				String address = email.getString(email.getColumnIndex(Email.DATA));
				int type = email.getInt(email.getColumnIndex(Email.TYPE));
				int typeLabelResource = Email.getTypeLabelResource(type);
				res.add(new Pair<String, Integer>(address, typeLabelResource));
				email.moveToNext();
			}

		}
		email.close();
		return res;
	}*/

}