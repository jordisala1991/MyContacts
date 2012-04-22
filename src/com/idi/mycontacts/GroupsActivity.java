package com.idi.mycontacts;

import java.util.ArrayList;
import java.util.Collections;

import com.idi.adapters.MyGroupsListViewAdapter;
import com.idi.classes.Group;
import com.idi.database.GroupsHelper;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

public class GroupsActivity extends ListActivity
{
	
	private ArrayList<Group> mGroups;
	private MyGroupsListViewAdapter mAdapter;
	private GroupsHelper groupsHelper;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.groups);
        mGroups = new ArrayList<Group>();
        mAdapter = new MyGroupsListViewAdapter(this, R.layout.group_row, mGroups);
        groupsHelper = new GroupsHelper(this);
        setListAdapter(mAdapter);
        registerForContextMenu(getListView());
        fillData();
    }

	private void fillData()
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
        Toast.makeText(this, group.getName(), Toast.LENGTH_LONG).show();      
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
	        	Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
	            return true;
	        default:
	            return super.onOptionsItemSelected(item);
	    }
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo)
	{
	    super.onCreateContextMenu(menu, v, menuInfo);
	    MenuInflater inflater = getMenuInflater();
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
	    Group group = (Group) getListView().getAdapter().getItem(info.position);
        menu.setHeaderTitle(group.getName());
        menu.setHeaderIcon(R.drawable.dialog_icon);
        inflater.inflate(R.menu.groups_click_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item)
	{	 
	    switch (item.getItemId())
	    {
	        case R.id.GroupsClickOpt1:
	        	Toast.makeText(this, item.getTitle(), Toast.LENGTH_LONG).show();
	            return true;
	        case R.id.GroupsClickOpt2:
	        	AlertDialog.Builder deleteBuilderDialog = new AlertDialog.Builder(this);
	        	deleteBuilderDialog.setTitle(R.string.delete_group_dialog_title);
	        	deleteBuilderDialog.setIcon(R.drawable.dialog_icon);
	        	deleteBuilderDialog.setMessage(R.string.delete_group_dialog_message);
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

}