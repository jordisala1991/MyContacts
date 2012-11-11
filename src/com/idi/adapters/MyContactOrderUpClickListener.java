package com.idi.adapters;

import android.view.View;
import android.view.View.OnClickListener;

public class MyContactOrderUpClickListener implements OnClickListener {
	
	private int position;
	private MyGroupContactsWithOrderListViewAdapter mAdapter;

	public MyContactOrderUpClickListener(MyGroupContactsWithOrderListViewAdapter mAdapter, int position) {
		this.position = position;
		this.mAdapter = mAdapter;
	}

	public void onClick(View v) {
		this.mAdapter.ContactMoveUp(position);
	}

}
