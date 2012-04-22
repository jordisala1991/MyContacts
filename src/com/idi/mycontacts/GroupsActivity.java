package com.idi.mycontacts;

import java.util.ArrayList;
import java.util.Collections;
import com.idi.adapters.MyDialogDeleteGroupFromList;
import com.idi.adapters.MyGroupsListViewAdapter;
import com.idi.classes.Group;
import com.idi.database.GroupsHelper;
import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Groups;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class GroupsActivity extends ListActivity
{
	
	private TextView mEmptyView;
	private ArrayList<Group> mGroups;
	private MyGroupsListViewAdapter mAdapter;
	private GroupsHelper groupsHelper;
	private static final int INSERT_GROUP = 1;
	private static final int MODIFY_GROUP = 2;
	private static final int DETAILS_GROUP = 3;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        mEmptyView = (TextView) findViewById(R.id.emptyViewContacts);
        mGroups = new ArrayList<Group>();
        mAdapter = new MyGroupsListViewAdapter(this, R.layout.group_row, mGroups);
        groupsHelper = new GroupsHelper(this);
        setListAdapter(mAdapter);
        getListView().setEmptyView(mEmptyView);
        registerForContextMenu(getListView());
        fillData();
    }

	public void fillData()
	{
		mGroups = groupsHelper.getItemsViewAllGroups();
		Collections.sort(mGroups);
		mAdapter.clear();
		for (int i = 0; i < mGroups.size(); ++i) mAdapter.add(mGroups.get(i));
		mAdapter.notifyDataSetChanged();
	}
	
	@Override
    protected void onListItemClick(ListView listView, View view, int position, long id)
    {
		Group group = (Group) listView.getItemAtPosition(position);        
        Bundle extras = new Bundle();
        extras.putParcelable("group", group);
        Intent intent = new Intent(getBaseContext(), GroupDetailsActivity.class);
        intent.putExtras(extras);
        startActivityForResult(intent, DETAILS_GROUP);     
    }
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.groups_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	        case R.id.GroupsOpt1:
	            Intent intent = new Intent(Intent.ACTION_INSERT);
	            intent.setType(Groups.CONTENT_TYPE);
	            startActivityForResult(intent, INSERT_GROUP);
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
	    Group group = (Group) getListView().getAdapter().getItem(info.position);
        menu.setHeaderTitle(group.getName());
        menu.setHeaderIcon(R.drawable.dialog_icon);
        getMenuInflater().inflate(R.menu.groups_click_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{	 
		AdapterView.AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
		Group group = (Group) getListView().getAdapter().getItem(info.position);
	    switch (item.getItemId())
	    {
	        case R.id.GroupsClickOpt1:
	            return true;
	        case R.id.GroupsClickOpt2:
	        	AlertDialog.Builder deleteBuilderDialog = new AlertDialog.Builder(this);
	        	deleteBuilderDialog.setTitle(R.string.delete_group_dialog_title);
	        	deleteBuilderDialog.setIcon(R.drawable.dialog_icon);
	        	deleteBuilderDialog.setMessage(R.string.delete_group_dialog_message);
	        	deleteBuilderDialog.setPositiveButton(R.string.delete_dialog_ok, new MyDialogDeleteGroupFromList(this, group));
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
			case INSERT_GROUP:
				if (resultCode == -1) fillData();
				break;
			case MODIFY_GROUP:
				if (resultCode == -1) fillData();
				break;
			case DETAILS_GROUP:
				if (resultCode == -1) fillData();
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }

}