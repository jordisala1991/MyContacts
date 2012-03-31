package com.idi.adapters;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.idi.classes.Group;
import com.idi.mycontacts.R;

public class MyGroupsListViewAdapter extends ArrayAdapter<Group>
{
    private ArrayList<Group> groups;
    private LayoutInflater layoutInflater;

    public MyGroupsListViewAdapter(Context context, int textViewResourceId, ArrayList<Group> groups) 
    {
        super(context, textViewResourceId, groups);
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.groups = groups;
    }
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
    	ViewHolder holder;
        Group group = groups.get(position);
        if (convertView == null)
        {
        	holder = new ViewHolder();
    		convertView = layoutInflater.inflate(R.layout.group_row, null);
    		holder.photo = (ImageView) convertView.findViewById(R.id.row_groupphoto);
            holder.name = (TextView) convertView.findViewById(R.id.row_groupname);
            convertView.setTag(holder);
        }
        else holder = (ViewHolder) convertView.getTag();
        holder.photo.setImageBitmap(group.getPhoto());
        holder.name.setText(group.getName());
        return convertView;

    }

	private class ViewHolder
	{
		public ImageView photo;
	    public TextView name;
	}
}


