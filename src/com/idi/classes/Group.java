package com.idi.classes;

import android.graphics.Bitmap;

public class Group {
	
	private long id;
	private String name;
	private Bitmap photo;
	
	public Group()
	{
		
	}
	
	public Group(long id, String name, Bitmap photo)
	{
		this.id = id;
		this.name = name;
		this.photo = photo;
	}
	
	public long getId()
	{
		return id;
	}
	
	public void setId(long id)
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

}
