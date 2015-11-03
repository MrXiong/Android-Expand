package com.expand.library.internal.view.column;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.expand.library.R;
import com.expand.library.internal.CacheModelManager;
import com.expand.library.internal.Model;
import com.expand.library.internal.ModelPosition;
import com.expand.library.internal.view.ViewBaseAction;

public class TwoColumnView extends LinearLayout implements ViewBaseAction {
	
	private static final String TAG = TwoColumnView.class.getSimpleName();
	private ListView mLvParent;
	private ListView mLvChild;
	private TwoColumnParentAdapter mLvChildAdapter;
	private TwoColumnParentAdapter mLvParentAdapter;
	private OnSelectListener<Model> mOnSelectListener;
	private String showString = "不限";
	private List<Model> mModel;
	private HashMap<Integer, Integer> mSelectedPositions;
	public int saveChildPosition;
	public final static int NO_DEFAULT_SELECTED = 999;
	
	private View mContentView;
	private LinearLayout mLlContainer;

	public TwoColumnView(Context context ,List<Model> storeTypeList) {
		super(context);
		this.mModel = storeTypeList;
		init(context);
	}

	public TwoColumnView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}


	private void init(final Context context) {
		mContentView = LayoutInflater.from(context).inflate(R.layout.view_twolist, this, true);
		mLlContainer = (LinearLayout) mContentView.findViewById(R.id.ll_container);
		
		mLvParent = new ListView(context);
		mLvChild = new ListView(context);
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT, 1);
		
		
		mLvParent.setLayoutParams(params);
		mLvChild.setLayoutParams(params);
		mLlContainer.addView(mLvParent);
		mLlContainer.addView(mLvChild);
		
		

		mLvParentAdapter = new TwoColumnParentAdapter(context, mModel,
				R.drawable.choose_parent_item_selector,
				R.color.expand_tab_pressed
				);
		mLvParentAdapter.setTextSize(17);
		mLvParent.setAdapter(mLvParentAdapter);
		//是否显示图标
		mLvParentAdapter.setOnItemIconVisibleListener(new TwoColumnParentAdapter.OnItemIconVisibleListener() {
			
			public void OnItemIconVisible(ImageView view, int position) {
				view.setVisibility(View.VISIBLE);
			}

			public void OnItemRightNumVisible(TextView view, int position) {
				
			}
		});
		
		
		mLvParentAdapter.setOnItemClickListener(new TwoColumnParentAdapter.OnItemClickListener() {

					@Override
					public void onItemClick(View view, final int position) {
						if(mModel.get(position).getModelName().equals("全部")) {
							if (mOnSelectListener != null) {
								mOnSelectListener.onSelected(TwoColumnView.this, mModel.get(position));
							}
						}
						setDefaultSelect(position, NO_DEFAULT_SELECTED);
					}
					
				});
		
	}
	
	
	public void setDefaultSelect(final int selectedParentId, final int selectedChildId) {
		mLvParentAdapter.setSelectedPositionNoNotify(selectedParentId);
		mLvChildAdapter = new TwoColumnParentAdapter(getContext(), mModel.get(selectedParentId).getModelChilds(),
				R.drawable.choose_child_item_selector,
				R.drawable.choose_item_right
				);
		mLvChildAdapter.setTextSize(15);
		if(selectedChildId != NO_DEFAULT_SELECTED) {
			mLvChildAdapter.setSelectedPositionNoNotify(selectedChildId);
			putPositions(selectedParentId, selectedChildId);
		}
		if(mSelectedPositions != null) {
			if(mSelectedPositions.get(selectedParentId) != null) {
				mLvChildAdapter.setSelectedPositionNoNotify(mSelectedPositions.get(selectedParentId));
			}
		}
		
		mLvChild.setAdapter(mLvChildAdapter);
		mLvChildAdapter.setOnItemClickListener(new TwoColumnParentAdapter.OnItemClickListener() {

			@Override
			public void onItemClick(View view, final int p) {
				if (mOnSelectListener != null) {
					mOnSelectListener.onSelected(TwoColumnView.this, mModel.get(selectedParentId).getModelChilds().get(p));
					putPositions(selectedParentId, p);
				}

			}
		});
	}
	
	public void setDefaultSelected(String StrDefault){
		
		CacheModelManager cacheInfoManager = CacheModelManager.getInstance(getContext());
		ModelPosition position = cacheInfoManager.getModelPositionByName(StrDefault);
		if(position != null) {
			setDefaultSelect(position.getParentPosition(),position.getChildPosition());
		}
	}

	public void putPositions(final int selectedParentId, final int selectedChildId){
		if(mSelectedPositions == null) {
			mSelectedPositions = new HashMap<Integer, Integer>();
		}
		mSelectedPositions.put(selectedParentId, selectedChildId);
	}
	public String getShowText() {
		return showString;
	}

	public void setOnSelectListener(OnSelectListener<Model> onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	@Override
	public void hide() {
		// TODO Auto-generated method stub

	}

	@Override
	public void show() {
		// TODO Auto-generated method stub

	}
}
