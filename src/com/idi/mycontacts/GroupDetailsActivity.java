package com.idi.mycontacts;

import java.util.ArrayList;

import com.idi.adapters.MyDialogDeleteGroupFromDetails;
import com.idi.classes.Group;
import com.idi.database.GroupsHelper;
import android.app.Activity;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class GroupDetailsActivity extends Activity
{
	
	private Group group;
	private ImageView photoGroup;
	private TextView nameGroup;
	private TextView contactsGroup;
	private GroupsHelper groupsHelper;
	public static final int MODIFIED_RESULT = -1;
	private static final int DEFAULT_RESULT = 0;
	
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
        contactsGroup = (TextView) findViewById(R.id.contactesGrupDetall);
        groupsHelper = new GroupsHelper(this);
        fillData();
    }
    
	private void fillData()
	{
		photoGroup.setImageBitmap(group.getPhoto());
		nameGroup.setText(group.getName());
		contactsGroup.setText(getContactsFromGroup());
	}

	private String getContactsFromGroup()
	{
		String res = "";
		ArrayList<String> contactsFromGroup = groupsHelper.getContactsNamesFromGroup(group.getId());
		for (int i = 0; i < contactsFromGroup.size(); ++i) res += contactsFromGroup.get(i) + "\n";
		return res;
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

}