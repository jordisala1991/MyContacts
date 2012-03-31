package com.idi.classes;

@SuppressWarnings("serial")
public class Pair<A, B> implements Comparable< Pair<A, B> >, java.io.Serializable
{
	protected A key;
	protected B value;

	public Pair(A key, B value)
	{
		this.key = key;
		this.value = value;
	}

	public A getKey()
	{
		return key;
	}
	
	public B getValue()
	{
		return value;
	}
	
	public String toString()
	{
		return "(" + key + ", " + value + ")"; 
	}
  
	public int compareTo(Pair<A, B> p1)
	{ 
		if (p1 != null)
		{ 
			if (p1.equals(this)) return 0; 
			else if (p1.hashCode() > this.hashCode()) return 1;
			else return -1;  
		}
		return -1;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((key == null) ? 0 : key.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Pair<A, B> other = (Pair<A, B>) obj;
		if (key == null)
		{
			if (other.key != null) return false;
		}
		else if (!key.equals(other.key)) return false;
		if (value == null)
		{
			if (other.value != null) return false;
		}
		else if (!value.equals(other.value)) return false;
		return true;
	}

}