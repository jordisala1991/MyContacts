package com.idi.mycontacts;

import com.idi.adapters.MyDialogDeleteContactFromDetails;
import com.idi.classes.Contact;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class ContactDetailsActivity extends Activity
{
	
	private Contact contact;
	private static final int MODIFY_CONTACT = 1;
	private static final int DEFAULT_RESULT = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        setResult(DEFAULT_RESULT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) contact = (Contact) extras.getParcelable("contact");
        fillData();
    }
    
	private void fillData()
	{
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.contact_details_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	    switch (item.getItemId())
	    {
	        case R.id.ContactDetailsOpt1:
	        	Intent intent = new Intent(Intent.ACTION_EDIT);
	            intent.setData(Uri.parse(ContactsContract.Contacts.CONTENT_LOOKUP_URI + "/" + contact.getId()));
	            startActivityForResult(intent, MODIFY_CONTACT);
	            return true;
	        case R.id.ContactDetailsOpt2:
	        	AlertDialog.Builder deleteBuilderDialog = new AlertDialog.Builder(this);
	        	deleteBuilderDialog.setTitle(R.string.delete_contact_dialog_title);
	        	deleteBuilderDialog.setIcon(R.drawable.dialog_icon);
	        	deleteBuilderDialog.setMessage(R.string.delete_contact_dialog_message);
	        	deleteBuilderDialog.setPositiveButton(R.string.delete_dialog_ok, new MyDialogDeleteContactFromDetails(this, contact));
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
			case MODIFY_CONTACT:
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