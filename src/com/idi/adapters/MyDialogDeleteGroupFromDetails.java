package com.idi.adapters;

import com.idi.classes.Group;
import com.idi.database.MyDbController;
import com.idi.mycontacts.GroupDetailsActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;

public class MyDialogDeleteGroupFromDetails implements OnClickListener
{	

	private GroupDetailsActivity groupDetailsActivity;
	private Group group;
	private MyDbController db;

	public MyDialogDeleteGroupFromDetails(GroupDetailsActivity groupDetailsActivity, Group group)
	{
		this.groupDetailsActivity = groupDetailsActivity;
		this.db = new MyDbController(groupDetailsActivity.getBaseContext());
		this.group = group;
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		db.open();
		db.deleteGroup(this.group.getId());
		db.deleteGroupContactsRelations(this.group.getId());
		db.close();
		groupDetailsActivity.setResult(GroupDetailsActivity.MODIFIED_RESULT);
		groupDetailsActivity.finish();
	}

}
