package com.idi.mycontacts;

import java.util.ArrayList;
import java.util.Collections;
import com.idi.adapters.MyGroupContactsListViewAdapter;
import com.idi.classes.Contact;
import com.idi.classes.Group;
import com.idi.database.ContactsHelper;
import com.idi.database.GroupsHelper;
import android.app.ListActivity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class EditGroupActivity extends ListActivity implements OnClickListener
{
	
	private TextView mEmptyView;
	private ArrayList<Contact> mContacts;
    private MyGroupContactsListViewAdapter mAdapter;
    private ContactsHelper contactsHelper;
    private GroupsHelper groupsHelper;
    private LinearLayout mButtonsLayout;
    private LinearLayout mGroupNameLayout;
    private Button mButtonCancel;
    private Button mButtonSave;
    private EditText mGroupNameTextEdit;
    private Group group;
	
	private static final int DEFAULT_RESULT = 0;
	private static final int SAVED_RESULT = -1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_group);
        mEmptyView = (TextView) findViewById(R.id.emptyViewContacts);
        mButtonsLayout = (LinearLayout) findViewById(R.id.ButtonsLayout);
        mGroupNameLayout = (LinearLayout) findViewById(R.id.GroupNameLayout);
        mButtonCancel = (Button) findViewById(R.id.button_cancel);
        mButtonSave = (Button) findViewById(R.id.button_save);
        mGroupNameTextEdit = (EditText) findViewById(R.id.group_name);
        mButtonCancel.setOnClickListener(this);
        mButtonSave.setOnClickListener(this);
        mButtonsLayout.setBackgroundColor(Color.LTGRAY);
        mGroupNameLayout.setBackgroundColor(Color.DKGRAY);
        mContacts = new ArrayList<Contact>();
        mAdapter = new MyGroupContactsListViewAdapter(this, R.layout.contact_row, mContacts);
        contactsHelper = new ContactsHelper(this);
        groupsHelper = new GroupsHelper(this);
        Bundle extras = getIntent().getExtras();
        if (extras != null) group = (Group) extras.getParcelable("group");
        setResult(DEFAULT_RESULT);
        setListAdapter(mAdapter);
        getListView().setEmptyView(mEmptyView);
        registerForContextMenu(getListView());
        fillData();
    }

	private void fillData() {
		mGroupNameTextEdit.setText(group.getName());
		mContacts = contactsHelper.getItemsViewAllContactsForCreateGroup();
    	Collections.sort(mContacts);
    	ArrayList<Contact> contactsOfGroup = new ArrayList<Contact>();
    	for (int i = 0; i < mContacts.size(); ++i) {
    		Contact contact = mContacts.get(i);
    		if (group.containsContact(contact.getId()))
    			contactsOfGroup.add(contact);
    	}
    	MyGroupContactsListViewAdapter.setContactsOfGroup(contactsOfGroup);
    	mAdapter.clear();
    	for (int i = 0; i < mContacts.size(); ++i) mAdapter.add(mContacts.get(i));
    	mAdapter.notifyDataSetChanged();
	}

	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.button_save:
				setResult(SAVED_RESULT);
				groupsHelper.editGroup(group.getId(), mGroupNameTextEdit.getText().toString(), MyGroupContactsListViewAdapter.getContactsAddedToGroup());
				finish();
				break;
			case R.id.button_cancel:
				setResult(DEFAULT_RESULT);
				finish();
				break;
		}
	}

}