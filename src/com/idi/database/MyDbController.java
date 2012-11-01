package com.idi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDbController
{

	public static final String KEY_ROWID = "_id";
	public static final String KEY_CONTACTID = "contactId";
	public static final String KEY_GROUPNAME = "name";
	public static final String KEY_GROUPID = "groupId";
	public static final String KEY_POSITIONINGROUP = "position";
	private static final String FAVOURITES_TABLE = "favourites";
	private static final String GROUPS_TABLE = "groups";
	private static final String GROUPCONTACTS_TABLE = "groupcontacts";
	private Context context;
	private SQLiteDatabase db;
	private DatabaseHelper dbHelper;

	public MyDbController(Context context)
	{
		this.context = context;
	}

	public void open() throws SQLException
	{
		dbHelper = new DatabaseHelper(context);
		db = dbHelper.getWritableDatabase();
	}

	public void close()
	{
		dbHelper.close();
	}

	public long addFavourite(int contactId)
	{
		ContentValues values = createFavouriteValues(contactId);
		return db.insert(FAVOURITES_TABLE, null, values);
	}

	public boolean deleteFavourite(int contactId)
	{
		return db.delete(FAVOURITES_TABLE, KEY_CONTACTID + "=" + contactId, null) > 0;
	}
	
	public boolean isFavourite(int contactId)
	{
		Cursor mCursor = db.query(true, FAVOURITES_TABLE, new String[] { KEY_ROWID, KEY_CONTACTID }, KEY_CONTACTID + "="
				+ contactId, null, null, null, null, null);
		boolean isFavourite = mCursor.getCount() > 0;
		mCursor.close();
		return isFavourite;
	}

	public Cursor fetchFavourites()
	{
		return db.query(FAVOURITES_TABLE, new String[] { KEY_ROWID, KEY_CONTACTID }, null, null, null, null, KEY_CONTACTID + " ASC");
	}
	
	public long addGroup(String name)
	{
		ContentValues values = createGroupValues(name);
		return db.insert(GROUPS_TABLE, null, values);
	}
	
	public boolean deleteGroup(int groupId)
	{
		return db.delete(GROUPS_TABLE, KEY_ROWID + "=" + groupId, null) > 0;
	}
	
	public Cursor fetchGroups()
	{
		return db.query(GROUPS_TABLE, new String[] { KEY_ROWID, KEY_GROUPNAME }, null, null, null, null, KEY_GROUPNAME + " ASC");
	}
	
	public long addContactToGroup(int groupId, int contactId, int position)
	{
		ContentValues values = createGroupContactValues(groupId, contactId, position);
		return db.insert(GROUPCONTACTS_TABLE, null, values);
	}
	
	public boolean deleteContactRelations(int contactId)
	{
		return db.delete(GROUPCONTACTS_TABLE, KEY_CONTACTID + "=" + contactId, null) > 0;
	}
	
	public Cursor fetchGroupContacts(int groupId)
	{
		return db.query(GROUPCONTACTS_TABLE, new String[] { KEY_ROWID, KEY_GROUPID, KEY_CONTACTID}, KEY_GROUPID + "=" + groupId, null, null, null, KEY_CONTACTID + " ASC");
	}

	private ContentValues createGroupContactValues(int groupId, int contactId, int position) {
		ContentValues values = new ContentValues();
		values.put(KEY_GROUPID, groupId);
		values.put(KEY_CONTACTID, contactId);
		values.put(KEY_POSITIONINGROUP, position);
		return values;
	}

	private ContentValues createGroupValues(String name)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_GROUPNAME, name);
		return values;
	}

	private ContentValues createFavouriteValues(int contactId)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_CONTACTID, contactId);
		return values;
	}
}