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
		if (obj instanceof Section)
		{
			Section aux = (Section) obj;
			return getTitle().compareToIgnoreCase(aux.getTitle());
		}
		else if (obj instanceof Contact)
		{
			Contact aux = (Contact) obj;
			return getTitle().compareToIgnoreCase(aux.getName());
		}
		return 0;
	}

}

