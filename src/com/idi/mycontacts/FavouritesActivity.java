package com.idi.mycontacts;

import java.util.ArrayList;
import java.util.Collections;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.idi.adapters.MyFavouritesListViewAdapter;
import com.idi.classes.Contact;
import com.idi.database.ContactsHelper;

public class FavouritesActivity extends ListActivity
{
	
	private TextView mEmptyView;
	private ArrayList<Contact> mContacts;
    private MyFavouritesListViewAdapter mAdapter;
    private ContactsHelper contactsHelper;
    private static final int MODIFY_CONTACT = 1;
    private static final int DETAILS_CONTACT = 2;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);
        mEmptyView = (TextView) findViewById(R.id.emptyViewFavourites);
        mContacts = new ArrayList<Contact>();
        mAdapter = new MyFavouritesListViewAdapter(this, R.layout.contact_row, mContacts);
        contactsHelper = new ContactsHelper(this);
        setListAdapter(mAdapter);
        getListView().setEmptyView(mEmptyView);
        registerForContextMenu(getListView());
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	fillData();
    }
    
    public void fillData()
    {
    	mContacts = contactsHelper.getItemsViewFavourites();
    	Collections.sort(mContacts);
    	mAdapter.clear();
    	for (int i = 0; i < mContacts.size(); ++i) mAdapter.add(mContacts.get(i));
    	mAdapter.notifyDataSetChanged();
	}

	@Override
    protected void onListItemClick(ListView listView, View view, int position, long id)
    {
        Contact contact = (Contact) listView.getItemAtPosition(position);        
        Bundle extras = new Bundle();
        extras.putParcelable("contact", contact);
        Intent intent = new Intent(getBaseContext(), ContactDetailsActivity.class);
        intent.putExtras(extras);
        startActivityForResult(intent, DETAILS_CONTACT);  
    }
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
        AdapterContextMenuInfo info = (AdapterContextMenuInfo) menuInfo;
	    Contact contact = (Contact) getListView().getAdapter().getItem(info.position);
        menu.setHeaderTitle(contact.getName());
        menu.setHeaderIcon(R.drawable.dialog_icon);
        getMenuInflater().inflate(R.menu.contacts_click_menu, menu);
	    if (!contact.getHasPhoneNumber()) menu.setGroupVisible(R.id.CallGroup, false);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Contact contact = (Contact) getListView().getAdapter().getItem(info.position);
	    switch (item.getItemId())
	    {
	        case R.id.ContactsClickOpt1:
	        	Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
	            return true;
	        case R.id.ContactsClickOpt2:
	            Intent intent = new Intent(Intent.ACTION_EDIT);
	            intent.setData(Uri.parse(Contacts.CONTENT_LOOKUP_URI + "/" + contact.getId()));
	            startActivityForResult(intent, MODIFY_CONTACT);
	            return true;
	        case R.id.ContactsClickOpt3:
	        	AlertDialog.Builder deleteBuilderDialog = new AlertDialog.Builder(this);
	        	deleteBuilderDialog.setTitle(R.string.delete_contact_dialog_title);
	        	deleteBuilderDialog.setIcon(R.drawable.dialog_icon);
	        	deleteBuilderDialog.setMessage(R.string.delete_contact_dialog_message);
	        	deleteBuilderDialog.setPositiveButton(R.string.delete_dialog_ok, null);
	        	deleteBuilderDialog.setNegativeButton(R.string.delete_dialog_no, null);
	        	AlertDialog deleteDialog = deleteBuilderDialog.create();
	        	deleteDialog.setCanceledOnTouchOutside(true);
	        	deleteDialog.show();
	            return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case MODIFY_CONTACT:
			case DETAILS_CONTACT:
				if (resultCode == -1) fillData();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }
	
}