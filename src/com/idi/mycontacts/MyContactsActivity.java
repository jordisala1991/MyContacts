package com.idi.mycontacts;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MyContactsActivity extends TabActivity
{
	
	private TabHost mTabHost;
	private Resources mResources;
	private SharedPreferences mSharedPreferences;
	private static final String TAG_CONTACTS = "Contacts";
	private static final String TAG_GROUPS = "Groups";
	private static final String TAG_FAVOURITES = "Favourites";
	private static final String PREFERENCES = "settings";
	private static final String INITIALIZED = "initialized";

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        mTabHost = getTabHost();
        mResources = getResources();
        mSharedPreferences = getApplicationContext().getSharedPreferences(PREFERENCES, MODE_PRIVATE);
        if (!mSharedPreferences.getBoolean(INITIALIZED, false))
        {
        	SharedPreferences.Editor editor = mSharedPreferences.edit();
        	editor.putBoolean(INITIALIZED, true);
        	editor.commit();
        }
        addTabContacts();
        addTabGroups();
        addTabFavourites();
    }
    
    private void addTabContacts()
    {
    	Intent intent = new Intent(this, ContactsActivity.class);
        TabSpec spec = mTabHost.newTabSpec(TAG_CONTACTS);
        spec.setIndicator(getString(R.string.tabContacts), mResources.getDrawable(R.drawable.tab_contacts));
        spec.setContent(intent);
        mTabHost.addTab(spec);
    }
    
    private void addTabGroups()
    {
    	Intent intent = new Intent(this, GroupsActivity.class);
        TabSpec spec = mTabHost.newTabSpec(TAG_GROUPS);
        spec.setIndicator(getString(R.string.tabGroups), mResources.getDrawable(R.drawable.tab_groups));
        spec.setContent(intent);
        mTabHost.addTab(spec);
    }
    
    private void addTabFavourites()
    {
    	Intent intent = new Intent(this, FavouritesActivity.class);
        TabSpec spec = mTabHost.newTabSpec(TAG_FAVOURITES);
        spec.setIndicator(getString(R.string.tabFavourites), mResources.getDrawable(R.drawable.tab_favourites));
        spec.setContent(intent);
        mTabHost.addTab(spec);
    }
    
}