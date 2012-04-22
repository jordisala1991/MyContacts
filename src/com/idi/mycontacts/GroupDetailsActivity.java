package com.idi.mycontacts;

import com.idi.adapters.MyDialogDeleteGroupFromDetails;
import com.idi.classes.Group;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class GroupDetailsActivity extends Activity
{
	
	private Group group;
	private static final int MODIFY_GROUP = 1;
	private static final int DEFAULT_RESULT = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.group_details);
        setResult(DEFAULT_RESULT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) group = (Group) extras.getParcelable("group");
        fillData();
    }
    
	private void fillData()
	{
		
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
					
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }

}