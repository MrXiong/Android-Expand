package com.expand.library.internal.view;

import android.view.View;

public interface ViewBaseAction {
	
	/**
	 * 菜单隐藏操作
	 */
	public void hide();
	
	/**
	 * 菜单显示操作
	 */
	public void show();
	
	public interface OnSelectListener<T> {
		public void onSelected(View selectView, T item);
	}
}
