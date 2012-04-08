package com.idi.classes;

import java.util.ArrayList;
import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Item, Parcelable
{

	private int id;
	private String name;
	private Bitmap photo;
	private boolean hasPhoneNumber;
	private boolean hasEmailAddress;
	private ArrayList< Pair<String, Integer> > phones;
	private ArrayList< Pair<String, Integer> > emails;
	private boolean isFavourite;
	
	public Contact()
	{
		
	}
	
	public Contact(int id, String name, Bitmap photo, boolean hasPhoneNumber, boolean hasEmailAddress,
				ArrayList< Pair<String, Integer> > phones, ArrayList< Pair<String, Integer> > emails, boolean isFavourite)
	{
		this.id = id;
		this.name = name;
		this.photo = photo;
		this.hasPhoneNumber = hasPhoneNumber;
		this.hasEmailAddress = hasEmailAddress;
		this.phones = phones;
		this.emails = emails;
		this.isFavourite = isFavourite;
	}
	
	public Contact(Parcel in)
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
	
	public boolean getHasPhoneNumber()
	{
		return hasPhoneNumber;
	}
	
	public void setHasPhoneNumber(boolean hasPhoneNumber)
	{
		this.hasPhoneNumber = hasPhoneNumber;
	}
	
	public boolean getHasEmailAddress()
	{
		return hasEmailAddress;
	}
	
	public void setHasEmailAddress(boolean hasEmailAddress)
	{
		this.hasEmailAddress = hasEmailAddress;
	}
	
	public ArrayList< Pair<String, Integer> > getPhones()
	{
		return phones;
	}

	public void setPhones(ArrayList< Pair<String, Integer> > phones)
	{
		this.phones = phones;
	}

	public ArrayList< Pair<String, Integer> > getEmails()
	{
		return emails;
	}

	public void setEmails(ArrayList< Pair<String, Integer> > emails)
	{
		this.emails = emails;
	}
	
	public boolean getIsFavourite()
	{
		return isFavourite;
	}
	
	public void setIsFavourite(boolean isFavourite)
	{
		this.isFavourite = isFavourite;
	}

	public boolean isSection()
	{
		return false;
	}

	public int compareTo(Item obj)
	{
		if (obj instanceof Section)
		{
			Section aux = (Section) obj;
			return getName().compareToIgnoreCase(aux.getTitle());
		}
		else if (obj instanceof Contact)
		{
			Contact aux = (Contact) obj;
			return getName().compareToIgnoreCase(aux.getName());
		}
		return 0;
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
		parcel.writeInt(hasPhoneNumber ? 0 : 1 );
		parcel.writeInt(hasEmailAddress ? 0 : 1);
		parcel.writeList(phones);
		parcel.writeList(emails);
		parcel.writeInt(isFavourite ? 0 : 1);
	}
	
	public void readFromParcel(Parcel parcel)
	{
		id = parcel.readInt();
		name = parcel.readString();
		photo = parcel.readParcelable(Bitmap.class.getClassLoader());
		hasPhoneNumber = parcel.readInt() == 0;
		hasEmailAddress = parcel.readInt() == 0;
		phones = new ArrayList< Pair<String, Integer> >();
		emails = new ArrayList< Pair<String, Integer> >();
		parcel.readList(phones, null);
		parcel.readList(emails , null);
		isFavourite = parcel.readInt() == 0;
	}
	
	@SuppressWarnings("rawtypes")
	public static final Parcelable.Creator CREATOR = new Parcelable.Creator()
	{
        public Contact createFromParcel(Parcel in)
        {
            return new Contact(in);
        }
 
        public Contact[] newArray(int size)
        {
            return new Contact[size];
        }
    };
	
}
