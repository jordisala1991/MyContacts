package com.idi.mycontacts;

import java.util.ArrayList;
import java.util.Collections;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.idi.adapters.MyContactsListViewAdapter;
import com.idi.adapters.MyDialogDeleteContactFromList;
import com.idi.adapters.MyTextWatcher;
import com.idi.classes.Contact;
import com.idi.classes.Item;
import com.idi.database.ContactsHelper;

public class ContactsActivity extends ListActivity
{
	
	private TextView mEmptyView;
	private EditText mSearch;
	private LinearLayout mSearchLayout;
	private ArrayList<Item> mContacts;
    private MyContactsListViewAdapter mAdapter;
    private ContactsHelper contactsHelper;
    private static final int INSERT_CONTACT = 1;
    private static final int MODIFY_CONTACT = 2;
    private static final int DETAILS_CONTACT = 3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contacts);
        mEmptyView = (TextView) findViewById(R.id.emptyViewContacts);
        mSearch = (EditText) findViewById(R.id.search);
        mSearchLayout = (LinearLayout) findViewById(R.id.SearchLayout);
        mSearchLayout.setBackgroundColor(Color.LTGRAY);
        mContacts = new ArrayList<Item>();
        mAdapter = new MyContactsListViewAdapter(this, R.layout.contact_row, mContacts);
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
    	mContacts = contactsHelper.getItemsViewAllContacts();
    	Collections.sort(mContacts);
    	mAdapter.clear();
    	for (int i = 0; i < mContacts.size(); ++i) mAdapter.add(mContacts.get(i));
    	mAdapter.notifyDataSetChanged();
        mSearch.addTextChangedListener(new MyTextWatcher(mContacts, mAdapter));
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contacts_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	        case R.id.ContactsOpt1:
	            Intent intent = new Intent(Intent.ACTION_INSERT);
	            intent.setType(Contacts.CONTENT_TYPE);
	            startActivityForResult(intent, INSERT_CONTACT);
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
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
				Intent callIntent = new Intent(Intent.ACTION_CALL);
				callIntent.setData(Uri.parse("tel:" + contact.getPhones().get(0).getKey()));
				startActivity(callIntent);
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
	        	deleteBuilderDialog.setPositiveButton(R.string.delete_dialog_ok, new MyDialogDeleteContactFromList(this, contact));
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
			case INSERT_CONTACT:
				if (resultCode == -1) fillData();
				break;
			case MODIFY_CONTACT:
				if (resultCode == -1) fillData();
				break;
			case DETAILS_CONTACT:
				if (resultCode == -1) fillData();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }
	
}