package com.idi.adapters;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.idi.classes.Group;
import com.idi.database.MyDbController;
import com.idi.mycontacts.GroupsActivity;

public class MyDialogDeleteGroupFromList implements OnClickListener
{	

	private Group group;
	private GroupsActivity groupsActivity;
	private MyDbController db;

	public MyDialogDeleteGroupFromList(GroupsActivity groupsActivity, Group group)
	{
		this.groupsActivity = groupsActivity;
		this.group = group;
		db = new MyDbController(groupsActivity);
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		db.open();
		db.deleteGroup(this.group.getId());
		db.close();
		groupsActivity.fillData();
	}

}
