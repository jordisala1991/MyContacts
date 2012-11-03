package com.idi.classes;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Comparable<Group>, Parcelable {
	
	private int id;
	private String name;
	private Bitmap photo;
	private ArrayList<Integer> contactsId;
	
	public Group()
	{
		contactsId = new ArrayList<Integer>();
	}
	
	public Group(int id, String name, Bitmap photo, ArrayList<Integer> contactsId)
	{
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.contactsId = contactsId;
	}
	
	public Group(Parcel in)
	{
		readFromParcel(in);
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}
	
	public String getName()
	{
		return name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public Bitmap getPhoto()
	{
		return photo;
	}
	
	public void setPhoto(Bitmap photo)
	{
		this.photo = photo;
	}
	
	public ArrayList<Integer> getContactsId()
	{
		return contactsId;
	}
	
	public void setContactsId(ArrayList<Integer> contactsId)
	{
		this.contactsId = contactsId;
	}
	
	public void addContactId(Integer contactId)
	{
		this.contactsId.add(contactId);
	}
	
	public Boolean containsContact(Integer contactId)
	{
		return contactsId.contains(contactId);
	}

	public int compareTo(Group aux)
	{
		return getName().compareToIgnoreCase(aux.getName());
	}

	public int describeContents()
	{
		return 0;
	}

	public void writeToParcel(Parcel parcel, int flags)
	{
		parcel.writeInt(id);
		parcel.writeString(name);
		parcel.writeParcelable(photo, flags);
		parcel.writeList(contactsId);
	}
	
	public void readFromParcel(Parcel parcel)
	{
		id = parcel.readInt();
		name = parcel.readString();
		photo = parcel.readParcelable(Bitmap.class.getClassLoader());
		contactsId = new ArrayList<Integer>();
		parcel.readList(contactsId, null);
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
        public Group createFromParcel(Parcel in)
        {
            return new Group(in);
        }
 
        public Group[] newArray(int size)
        {
            return new Group[size];
        }
    };

}
