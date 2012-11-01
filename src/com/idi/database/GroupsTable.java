package com.idi.database;

import android.database.sqlite.SQLiteDatabase;

public class GroupsTable
{

	private static String DATABASE_CREATE = 
			"create table groups (_id integer primary key autoincrement, " +
			"name string not null);";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		database.execSQL("DROP TABLE IF EXISTS groups");
		onCreate(database);
	}

}
