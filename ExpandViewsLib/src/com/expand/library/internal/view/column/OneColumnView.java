package com.expand.library.internal.view.column;

import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.expand.library.R;
import com.expand.library.internal.Model;
import com.expand.library.internal.view.ViewBaseAction;


public class OneColumnView extends RelativeLayout implements ViewBaseAction{

	private ListView mListView;
	private OnSelectListener<Model> mOnSelectListener;
	private OneColumnAdapter adapter;
	private String mDistance;
	private String showText = "排序";
	private Context mContext;
	private List<Model> mModelList;
	public String getShowText() {
		return showText;
	}

	public OneColumnView(Context context ,List<Model> ModelList) {
		super(context);
		this.mModelList = ModelList;
		init(context);
		
		
	}

	public OneColumnView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public OneColumnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		mContext = context;
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_onelist, this, true);
		mListView = (ListView) findViewById(R.id.listView);
		adapter = new OneColumnAdapter(context, mModelList,R.drawable.choose_parent_item_selector, R.drawable.choose_item_right);
		adapter.setTextSize(17);
		mListView.setAdapter(adapter);
		if (mDistance != null) {
			for (int i = 0; i < mModelList.size(); i++) {
				if (mModelList.get(i).getModelName().equals(mDistance)) {
					adapter.setSelectedPositionNoNotify(i);
					showText = mModelList.get(i).getModelName();
					break;
				}
			}
		}
		mListView.setAdapter(adapter);
		adapter.setOnItemClickListener(new OneColumnAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, int position) {

				if (mOnSelectListener != null) {
					mOnSelectListener.onSelected(OneColumnView.this, mModelList.get(position));
				}
			}
		});
	}

	public void setOnSelectListener(OnSelectListener<Model> onSelectListener) {
		mOnSelectListener = onSelectListener;
	}


	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
