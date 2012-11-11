package com.idi.mycontacts;

import java.util.ArrayList;
import com.idi.adapters.MyDialogDeleteGroupFromDetails;
import com.idi.adapters.MyGroupContactsWithOrderListViewAdapter;
import com.idi.classes.Contact;
import com.idi.classes.Group;
import com.idi.database.ContactsHelper;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

public class GroupDetailsActivity extends ListActivity implements OnCheckedChangeListener
{
	
	private TextView mEmptyView;
	private ArrayList<Contact> mContacts;
    private MyGroupContactsWithOrderListViewAdapter mAdapter;
    private ContactsHelper contactsHelper;
	private Group group;
	private ImageView photoGroup;
	private TextView nameGroup;
	private ToggleButton mModeButton;
	public static final int MODIFIED_RESULT = -1;
	private static final int DEFAULT_RESULT = 0;
	private static final int MODIFY_GROUP = 1;
	private static final int DETAILS_CONTACT = 2;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);
        setResult(DEFAULT_RESULT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) group = (Group) extras.getParcelable("group");
        photoGroup = (ImageView) findViewById(R.id.fotoGrupDetall);
        nameGroup = (TextView) findViewById(R.id.nomGrupDetall);
        mEmptyView = (TextView) findViewById(R.id.emptyViewFavourites);
        mModeButton = (ToggleButton) findViewById(R.id.button_mode);
        mContacts = new ArrayList<Contact>();
        mModeButton.setOnCheckedChangeListener(this);
        mAdapter = new MyGroupContactsWithOrderListViewAdapter(this, R.layout.contact_row_without_fav, mContacts, group);
        contactsHelper = new ContactsHelper(this);
        setListAdapter(mAdapter);
        getListView().setEmptyView(mEmptyView);
        registerForContextMenu(getListView());
        fillData();
    }
    
    @Override
    public void onResume()
    {
    	super.onResume();
    	fillData();
    }
    
	private void fillData()
	{
		photoGroup.setImageBitmap(group.getPhoto());
		nameGroup.setText(group.getName());
		mContacts = contactsHelper.getItemsViewGroupDetails(group.getContactsId());
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
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.group_details_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	    	case R.id.GroupDetailsOpt1:
	            Bundle extras = new Bundle();
	            extras.putParcelable("group", group);
	            Intent intent = new Intent(getBaseContext(), EditGroupActivity.class);
	            intent.putExtras(extras);
	            startActivityForResult(intent, MODIFY_GROUP);
	    		return true;
	    	case R.id.GroupDetailsOpt2:
	        	AlertDialog.Builder deleteBuilderDialog = new AlertDialog.Builder(this);
	        	deleteBuilderDialog.setTitle(R.string.delete_group_dialog_title);
	        	deleteBuilderDialog.setIcon(R.drawable.dialog_icon);
	        	deleteBuilderDialog.setMessage(R.string.delete_group_dialog_message);
	        	deleteBuilderDialog.setPositiveButton(R.string.delete_dialog_ok, new MyDialogDeleteGroupFromDetails(this, group));
	        	deleteBuilderDialog.setNegativeButton(R.string.delete_dialog_no, null);
	        	AlertDialog deleteDialog = deleteBuilderDialog.create();
	        	deleteDialog.setCanceledOnTouchOutside(true);
	        	deleteDialog.show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		switch (requestCode)
		{
			case MODIFY_GROUP:
				if (resultCode == -1)
				{
					setResult(MODIFIED_RESULT);
					finish();
				}
				break;
			case DETAILS_CONTACT:
				if (resultCode == -1) fillData();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }

	public void onCheckedChanged(CompoundButton arg0, boolean marcat) {
		if (marcat) mAdapter.EditMode();
		else mAdapter.NormalMode();
		setResult(MODIFIED_RESULT);
	}

}