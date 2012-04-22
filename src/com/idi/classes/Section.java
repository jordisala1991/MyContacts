package com.idi.classes;

public class Section implements Item
{

	private final String title;
	
	public Section(String title)
	{
		this.title = title;
	}
	
	public String getTitle()
	{
		return title;
	}
	
	public boolean isSection()
	{
		return true;
	}

	@Override
	public String toString()
	{
		return title;
	}

	public int compareTo(Item obj)
	{
		if (getTitle() == "#") return -1;
		if (obj.isSection())
		{
			Section aux = (Section) obj;
			return getTitle().compareToIgnoreCase(aux.getTitle());
		}
		else
		{
			Contact aux = (Contact) obj;
			int result = getTitle().compareToIgnoreCase(aux.getName());
			return (result == 0)? -1: result;
		}
	}

}

