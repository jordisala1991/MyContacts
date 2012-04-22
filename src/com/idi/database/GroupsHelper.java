package com.idi.database;

import java.util.ArrayList;

import com.idi.classes.Group;
import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.ContactsContract.Groups;

public class GroupsHelper
{
	
	private ContentResolver contentResolver;
	private Resources resources;
	
	public GroupsHelper(Context context)
	{
		this.contentResolver = context.getContentResolver();
		this.resources = context.getResources();
	}

	public ArrayList<Group> getItemsViewAllGroups() {
		ArrayList<Group> groups = new ArrayList<Group>();
		final String[] projection = new String[] { Groups._ID, Groups.TITLE, Groups.ACCOUNT_TYPE, Groups.NOTES};
		Cursor cursorGroups = contentResolver.query(Groups.CONTENT_URI, projection, null, null, Groups.TITLE + " ASC");
		int groupIdColumn = cursorGroups.getColumnIndex(Groups._ID);
		int groupNameColumn = cursorGroups.getColumnIndex(Groups.TITLE);
		Bitmap photo = BitmapFactory.decodeResource(resources, resources.getIdentifier("default_group_photo", "drawable", "com.idi.mycontacts"));
		for (cursorGroups.moveToFirst(); !cursorGroups.isAfterLast(); cursorGroups.moveToNext())
		{
			int groupId = cursorGroups.getInt(groupIdColumn);
			String name = cursorGroups.getString(groupNameColumn);
			groups.add(new Group(groupId, name, photo));
		}
		cursorGroups.close();
		return groups;
	}

}