package com.idi.mycontacts;

import java.util.ArrayList;

import com.idi.adapters.MyDialogDeleteContactFromDetails;
import com.idi.classes.Contact;
import com.idi.classes.Pair;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ContactDetailsActivity extends Activity
{
	
	private Contact contact;
	private ImageView photoContact;
	private TextView nameContact;
	private LinearLayout listPhones;
	private LinearLayout listEmails;
	public static final int MODIFIED_RESULT = -1;
	private static final int DEFAULT_RESULT = 0;
	private static final int MODIFY_CONTACT = 1;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_details);
        setResult(DEFAULT_RESULT);
        Bundle extras = getIntent().getExtras();
        if (extras != null) contact = (Contact) extras.getParcelable("contact");
        photoContact = (ImageView) findViewById(R.id.fotoContacteDetall);
        nameContact = (TextView) findViewById(R.id.nomContacteDetall);
        listPhones = (LinearLayout) findViewById(R.id.llistaTelefons);
        listEmails = (LinearLayout) findViewById(R.id.llistaEmails);
        fillData();
    }
    
	private void fillData()
	{
		photoContact.setImageBitmap(contact.getPhoto());
		nameContact.setText(contact.getName());
		fillPhones();
		fillEmails();
	}

	private void fillEmails() {
		ArrayList< Pair<String, Integer>> emails = contact.getEmails();
		for (int i = 0; i < emails.size(); ++i)
		{
			TextView email = new TextView(this);
			final String emailAddress = emails.get(i).getKey();
			email.setText(emailAddress);
			listEmails.addView(email);
			email.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("plain/text");
					intent.putExtra(Intent.EXTRA_EMAIL, new String[] { emailAddress });
					startActivity(Intent.createChooser(intent, ""));
				}
	        });
		}
	}

	private void fillPhones()
	{
		ArrayList< Pair<String, Integer>> phones = contact.getPhones();
		for (int i = 0; i < phones.size(); ++i)
		{
			TextView phone = new TextView(this);
			final String phoneNumber = phones.get(i).getKey();
			phone.setText(phoneNumber);
			listPhones.addView(phone);
			phone.setOnClickListener(new OnClickListener() {
				
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:" + phoneNumber));
					startActivity(callIntent);
				}
	        });
		}
		
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
					setResult(MODIFIED_RESULT);
					finish();
				}
				break;
			default:
				super.onActivityResult(requestCode, resultCode, data);
				break;
				
		}
    }

}