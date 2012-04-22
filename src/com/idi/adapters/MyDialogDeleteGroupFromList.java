package com.idi.adapters;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.ContactsContract.Groups;
import com.idi.classes.Group;
import com.idi.mycontacts.GroupsActivity;

public class MyDialogDeleteGroupFromList implements OnClickListener
{	

	private GroupsActivity groupsActivity;
	private Group group;

	public MyDialogDeleteGroupFromList(GroupsActivity groupsActivity, Group group)
	{
		this.groupsActivity = groupsActivity;
		this.group = group;
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		groupsActivity.getContentResolver().delete(Groups.CONTENT_URI, Groups._ID+"=?", new String[] { String.valueOf(group.getId())});
		groupsActivity.fillData();
	}

}
