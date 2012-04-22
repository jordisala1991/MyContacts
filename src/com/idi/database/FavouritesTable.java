package com.idi.database;

import android.database.sqlite.SQLiteDatabase;

public class FavouritesTable
{

	private static String DATABASE_CREATE = 
			"create table favourites (_id integer primary key autoincrement, " +
			"contactId integer not null);";

	public static void onCreate(SQLiteDatabase database)
	{
		database.execSQL(DATABASE_CREATE);
	}

	public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion)
	{
		database.execSQL("DROP TABLE IF EXISTS favourites");
		onCreate(database);
	}

}
