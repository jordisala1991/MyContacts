package com.idi.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class MyDbController
{

	public static final String KEY_ROWID = "_id";
	public static final String KEY_IDCONTACT = "contactId";
	private static final String DB_TABLE = "favourites";
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
		ContentValues values = createContentValues(contactId);
		return db.insert(DB_TABLE, null, values);
	}

	public boolean deleteFavourite(int contactId)
	{
		return db.delete(DB_TABLE, KEY_IDCONTACT + "=" + contactId, null) > 0;
	}
	
	public boolean isFavourite(int contactId)
	{
		Cursor mCursor = db.query(true, DB_TABLE, new String[] { KEY_ROWID, KEY_IDCONTACT }, KEY_IDCONTACT + "="
				+ contactId, null, null, null, null, null);
		boolean isFavourite = mCursor.getCount() > 0;
		mCursor.close();
		return isFavourite;
	}

	public Cursor fetchFavourites()
	{
		return db.query(DB_TABLE, new String[] { KEY_ROWID, KEY_IDCONTACT }, null, null, null, null, KEY_IDCONTACT + " ASC");
	}

	private ContentValues createContentValues(int contactId)
	{
		ContentValues values = new ContentValues();
		values.put(KEY_IDCONTACT, contactId);
		return values;
	}
}