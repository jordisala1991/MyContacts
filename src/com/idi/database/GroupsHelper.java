package com.idi.database;

import java.util.ArrayList;

import com.idi.classes.Group;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.CommonDataKinds.GroupMembership;
import android.provider.ContactsContract.Data;
import android.provider.ContactsContract.Groups;

public class GroupsHelper
{
	
	private ContactsHelper contactsHelper;
	private ContentResolver contentResolver;
	private Resources resources;
	
	public GroupsHelper(Context context)
	{
		contactsHelper = new ContactsHelper(context);
		this.contentResolver = context.getContentResolver();
		this.resources = context.getResources();
	}

	public ArrayList<Group> getItemsViewAllGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		String[] projection = new String[] { Groups._ID, Groups.TITLE, Groups.DELETED };
		Cursor cursorGroups = contentResolver.query(Groups.CONTENT_URI, projection, null, null, Groups.TITLE + " ASC");
		int groupIdColumn = cursorGroups.getColumnIndex(Groups._ID);
		int groupNameColumn = cursorGroups.getColumnIndex(Groups.TITLE);
		int deletedColumn = cursorGroups.getColumnIndex(Groups.DELETED);
		Bitmap photo = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_group_photo", "drawable", "com.idi.mycontacts"));
		for (cursorGroups.moveToFirst(); !cursorGroups.isAfterLast(); cursorGroups.moveToNext())
		{
			int groupId = cursorGroups.getInt(groupIdColumn);
			String name = cursorGroups.getString(groupNameColumn);
			boolean deleted = (cursorGroups.getInt(deletedColumn) == 1);
			if (!deleted)
			{
				groups.add(new Group(groupId, name, photo));	
			}
		}
		cursorGroups.close();
		return groups;
	}

	public ArrayList<String> getContactsNamesFromGroup(int id) {
		ArrayList<String> res = new ArrayList<String>();
	    String[] projection = new String[] { GroupMembership.GROUP_ROW_ID , GroupMembership.CONTACT_ID };
	    Cursor cursorContacts = contentResolver.query(Data.CONTENT_URI, projection, GroupMembership.GROUP_ROW_ID + " = " + id, null, null);
	    int contactIdColumn = cursorContacts.getColumnIndex(GroupMembership.CONTACT_ID);
	    for (cursorContacts.moveToFirst(); !cursorContacts.isAfterLast(); cursorContacts.moveToNext())
	    {
	    	int contactId = cursorContacts.getInt(contactIdColumn);
	    	res.add(contactsHelper.getContactName(contactId));
	    }
		return res;
	}
	
	

}