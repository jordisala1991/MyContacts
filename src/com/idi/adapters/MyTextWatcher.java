package com.idi.adapters;

import java.util.ArrayList;
import android.text.Editable;
import android.text.TextWatcher;

import com.idi.classes.Contact;
import com.idi.classes.Item;

public class MyTextWatcher implements TextWatcher
{

	private ArrayList<Item> itemsArray;
	private MyContactsListViewAdapter itemsAdapter;
	
	public MyTextWatcher(ArrayList<Item> itemsArray, MyContactsListViewAdapter itemsAdapter)
	{
		this.itemsArray = itemsArray;
		this.itemsAdapter = itemsAdapter;
	}

	public void afterTextChanged(Editable s)
	{
		
	}

	public void beforeTextChanged(CharSequence s, int start, int count, int after)
	{
		
	}

	public void onTextChanged(CharSequence s, int start, int before, int count)
	{
		itemsAdapter.clear();
		String buscar = s.toString().toLowerCase();
		ArrayList<Item> mostrar = new ArrayList<Item>();
		if (s.length() > 0)
		{
			for (int i = 0; i < itemsArray.size(); ++i)
			{
				Item item = itemsArray.get(i);
				if (item instanceof Contact && ((Contact) item).getName().toLowerCase().startsWith(buscar))
				{
					mostrar.add(item);
				}
			}
		}
		else mostrar = itemsArray;
		Omplir(mostrar);
		itemsAdapter.notifyDataSetChanged();
	}

	private void Omplir(ArrayList<Item> mostrar) {
		for (int i = 0; i < mostrar.size(); ++i) itemsAdapter.add(mostrar.get(i));
	}

}