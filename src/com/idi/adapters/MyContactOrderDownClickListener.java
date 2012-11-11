package com.idi.adapters;

import android.view.View;
import android.view.View.OnClickListener;

public class MyContactOrderDownClickListener implements OnClickListener {
	
	private int position;
	private MyGroupContactsWithOrderListViewAdapter mAdapter;

	public MyContactOrderDownClickListener(MyGroupContactsWithOrderListViewAdapter mAdapter, int position) {
		this.position = position;
		this.mAdapter = mAdapter;
	}

	public void onClick(View v) {
		this.mAdapter.ContactMoveDown(position);
	}

}
