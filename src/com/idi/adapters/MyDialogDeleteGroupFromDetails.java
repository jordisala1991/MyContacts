package com.idi.adapters;

import com.idi.classes.Group;
import com.idi.mycontacts.GroupDetailsActivity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.provider.ContactsContract.Groups;

public class MyDialogDeleteGroupFromDetails implements OnClickListener
{	

	private GroupDetailsActivity groupDetailsActivity;
	private Group group;

	public MyDialogDeleteGroupFromDetails(GroupDetailsActivity groupDetailsActivity, Group group)
	{
		this.groupDetailsActivity = groupDetailsActivity;
		this.group = group;
	}

	public void onClick(DialogInterface arg0, int arg1)
	{
		groupDetailsActivity.getContentResolver().delete(Groups.CONTENT_URI, Groups._ID+"=?", new String[] { String.valueOf(group.getId())});
		groupDetailsActivity.setResult(GroupDetailsActivity.MODIFIED_RESULT);
		groupDetailsActivity.finish();
	}

}
