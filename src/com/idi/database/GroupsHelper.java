package com.idi.database;

import java.util.ArrayList;

import com.idi.classes.Contact;
import com.idi.classes.Group;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class GroupsHelper
{
	
	private Resources resources;
	private MyDbController db;
	
	public GroupsHelper(Context context)
	{
		this.resources = context.getResources();
		db = new MyDbController(context);
	}

	public ArrayList<Group> getItemsViewAllGroups() {
		db.open();
		ArrayList<Group> groups = new ArrayList<Group>();
		Bitmap photo = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_group_photo", "drawable", "com.idi.mycontacts"));
		Cursor groupsCursor = db.fetchGroups();
		int groupNameColumnIndex = groupsCursor.getColumnIndex(MyDbController.KEY_GROUPNAME);
		int groupIdColumnIndex = groupsCursor.getColumnIndex(MyDbController.KEY_ROWID);
		for (groupsCursor.moveToFirst(); !groupsCursor.isAfterLast(); groupsCursor.moveToNext())
		{
			int groupId = groupsCursor.getInt(groupIdColumnIndex);
			String groupName = groupsCursor.getString(groupNameColumnIndex);
			groups.add(new Group(groupId, groupName, photo));
		}
		db.close();
		return groups;
	}

	public ArrayList<Integer> getContactsNamesFromGroup(int id) {
		db.open();
		ArrayList<Integer> groupContacts = new ArrayList<Integer>();
		Cursor groupContactsCursor = db.fetchGroupContacts(id);
		int contactIdColumn = groupContactsCursor.getColumnIndex(MyDbController.KEY_CONTACTID);
		for (groupContactsCursor.moveToFirst(); !groupContactsCursor.isAfterLast(); groupContactsCursor.moveToNext())
		{
			int contactId = groupContactsCursor.getInt(contactIdColumn);
			groupContacts.add(contactId);
		}
		db.close();
		return groupContacts;
	}
	
	public void createGroup(String groupName, ArrayList<Contact> contacts)
	{
		db.open();
		int idGroup = (int) db.addGroup(groupName);
		for (int i = 0; i < contacts.size(); ++i)
			db.addContactToGroup(idGroup, contacts.get(i).getId(), i);
		db.close();
	}

}