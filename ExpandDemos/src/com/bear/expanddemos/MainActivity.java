package com.bear.expanddemos;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.bear.expanddemos.bean.StoreTypeNode;
import com.bear.expanddemos.cache.CacheInfoManager;
import com.expand.library.ExpandTabView;
import com.expand.library.internal.Model;
import com.expand.library.internal.view.ViewBaseAction;
import com.expand.library.internal.view.column.OneColumnView;
import com.expand.library.internal.view.column.TwoColumnView;

public class MainActivity extends Activity {

	private static final String TAG = MainActivity.class.getSimpleName();
	private ExpandTabView mExpandTabView;
	private List<Model> mModelList = new ArrayList<Model>();
	private List<Model> mOneModelList = new ArrayList<Model>();
	private TwoColumnView mModelListView;
	private OneColumnView mOneModelListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		initExpandTabView();
		mModelListView.setDefaultSelected("东北菜");
		
		
	}

	private void initData() {
		CacheInfoManager cacheInfoManager = CacheInfoManager.getInstance(this);
		List<StoreTypeNode> listNode = cacheInfoManager.getStoreTypeList();
		for(StoreTypeNode nodeParent : listNode){
			Model model = new Model();
			model.setModelName(nodeParent.getStoreTypeName());
			List<Model> mModelListChild = new ArrayList<Model>();
			for(StoreTypeNode node : nodeParent.getChildren()) {
				Model modelc = new Model();
				modelc.setModelName(node.getStoreTypeName());
				mModelListChild.add(modelc);
			}
			model.setModelChilds(mModelListChild);
			mModelList.add(model);	
			
			
			cacheInfoManager.updateModelList(mModelList);
		}
		OneModelList();
		
		
	}

	private void OneModelList() {
		for (int i = 0; i < 20; i++) {
			Model m = new Model();
			m.setModelName("dange "+i);
			mOneModelList.add(m);
		}
		
	}

	private void initExpandTabView() {
		mExpandTabView = (ExpandTabView)findViewById(R.id.expandtab_view);
		mModelListView = new TwoColumnView(this,mModelList);
		mOneModelListView = new OneColumnView(this,mOneModelList);
		final ArrayList<View> filterConditionViews = new ArrayList<View>();

		filterConditionViews.add(mOneModelListView);
		filterConditionViews.add(mModelListView);

		mModelListView.setOnSelectListener(new ViewBaseAction.OnSelectListener<Model>() {
					public void onSelected(View selectView, Model item) {
						mExpandTabView.updateExpandTabView(filterConditionViews, selectView, item.getModelName());
					}

				});
		mOneModelListView.setOnSelectListener(new ViewBaseAction.OnSelectListener<Model>() {
			public void onSelected(View selectView, Model item) {
				mExpandTabView.updateExpandTabView(filterConditionViews, selectView, item.getModelName());
			}
			
		});

		ArrayList<String> tabArray = new ArrayList<String>();
		tabArray.add("全部单个");
		tabArray.add("全部");

		mExpandTabView.setValue(tabArray, filterConditionViews);
		mExpandTabView.setTitle(tabArray.get(0), 0);
		mExpandTabView.setTitle(tabArray.get(1), 1);
	}

	public void onStop() {
		mExpandTabView.onPressBack();
		super.onStop();
	}
}
