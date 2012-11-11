package com.idi.database;

import android.content.Context;
import android.database.Cursor;

public class GroupContactsHelper
{
	
	private MyDbController db;
	
	public GroupContactsHelper(Context context)
	{
		db = new MyDbController(context);
	}

	public int getPosition(int groupId, int contactId) {
		db.open();
		Cursor positionCursor = db.fetchContactPosition(groupId, contactId);
		int positionColumnIndex = positionCursor.getColumnIndex(MyDbController.KEY_POSITIONINGROUP);
		int position = -1;
		for (positionCursor.moveToFirst(); !positionCursor.isAfterLast(); positionCursor.moveToNext())
		{
			position = positionCursor.getInt(positionColumnIndex);
		}
		db.close();
		return position;
	}

}