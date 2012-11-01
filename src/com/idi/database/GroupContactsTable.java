package com.idi.database;

import android.database.sqlite.SQLiteDatabase;

public class GroupContactsTable
{

	private static String DATABASE_CREATE = 
			"create table groupcontacts (_id integer primary key autoincrement, " +
			"groupId integer not null, " +
			"contactId integer not null, " + 
			"position integer not null );";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		database.execSQL("DROP TABLE IF EXISTS groupcontacts");
		onCreate(database);
	}

}
