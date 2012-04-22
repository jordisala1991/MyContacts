package com.idi.classes;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class Group implements Comparable<Group>, Parcelable {
	
	private int id;
	private String name;
	private Bitmap photo;
	
	public Group()
	{
		
	}
	
	public Group(int id, String name, Bitmap photo)
	{
		this.id = id;
		this.name = name;
		this.photo = photo;
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

	@Override
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
	}
	
	public void readFromParcel(Parcel parcel)
	{
		id = parcel.readInt();
		name = parcel.readString();
		photo = parcel.readParcelable(Bitmap.class.getClassLoader());
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
