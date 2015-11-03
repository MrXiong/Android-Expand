package com.bear.expanddemos.cache;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.bear.expanddemos.bean.StoreTypeNode;
import com.expand.library.internal.Model;
import com.expand.library.internal.ModelPosition;
import com.expand.library.internal.view.column.TwoColumnView;

public class CacheInfoManager {

	
	public static final String PREF_NAME ="com.utils";
	public static final String PREF_KEY ="com.utils.";
	public static final String PREF_KEY_STORE =PREF_KEY + "store";
	public static final String PREF_KEY_MODEL =PREF_KEY + "model";
	private static final String TAG = CacheInfoManager.class.getSimpleName();
	private List<StoreTypeNode> mStoreTypeList;
	private List<Model> mModelList;
	private SharedPreferences mPrefs;
	private static CacheInfoManager mInstance;
	private Context mContext;
	private CacheInfoManager(Context ctx){
		this.mContext = ctx.getApplicationContext();
		this.mPrefs = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
	}
	
	public static CacheInfoManager getInstance(Context ctx){
		if(null == mInstance) {
			synchronized (CacheInfoManager.class) {
				if(null == mInstance) {
					mInstance = new CacheInfoManager(ctx);
				}
			}
		}
		
		
		return mInstance;
		
	}
	
	
	public List<StoreTypeNode> getStoreTypeList() {
		if (mStoreTypeList != null) {
			return mStoreTypeList;
		}
		// 从SharedPreferences数据中初始化
		String strStoreTypeList = mPrefs.getString(PREF_KEY_STORE, null);
		if (!TextUtils.isEmpty(strStoreTypeList)) {
			mStoreTypeList = GsonTools.toObjectList(strStoreTypeList, StoreTypeNode.class);
			return mStoreTypeList;
		}
		// 从预置的数据中初始化
		strStoreTypeList = readAssertFile("cache/StoreTypeList.json");
		if (!TextUtils.isEmpty(strStoreTypeList)) {
			mStoreTypeList = GsonTools.toObjectList(strStoreTypeList, StoreTypeNode.class);
			return mStoreTypeList;
		}
		return mStoreTypeList;
	}
	public List<Model> getModelList() {
		if (mModelList != null) {
			return mModelList;
		}
		// 从SharedPreferences数据中初始化
		String strModelList = mPrefs.getString(PREF_KEY_MODEL, null);
		if (!TextUtils.isEmpty(strModelList)) {
			mModelList = GsonTools.toObjectList(strModelList, Model.class);
			return mModelList;
		}
		return mModelList;
	}
	
	public void updateStoreTypeList(List<StoreTypeNode> storeTypeList) {
		if (mStoreTypeList == null) {
			mStoreTypeList = new ArrayList<StoreTypeNode>();
		}
		mStoreTypeList.clear();
		mStoreTypeList.addAll(storeTypeList);
		final String strStoreTypeList = GsonTools.toJson(storeTypeList);
		mPrefs.edit()
			  .putString(PREF_KEY_STORE, strStoreTypeList)
			  .commit();
	}
	public void updateModelList(List<Model> modelList) {
		if (mModelList == null) {
			mModelList = new ArrayList<Model>();
		}
		mModelList.clear();
		mModelList.addAll(modelList);
		final String strModelList = GsonTools.toJson(modelList);
		mPrefs.edit()
		.putString(PREF_KEY_MODEL, strModelList)
		.commit();
	}
	
	private  String readAssertFile(String fileName) {
		InputStream in = null;
		try {
			Log.d(TAG, "readAssertFile: fileName = " + fileName);
			StringBuilder sb = new StringBuilder();
			in = mContext.getAssets().open(fileName);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			while( (line = br.readLine()) != null){
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			Log.w(TAG, "Can't read assert file '" + fileName + "'", e);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
				}
			}
		}
		return null;
	}
	
	public static class StoreTypePosition{
		public static int parentPosition;
		public static int childPosition;
		
	}

	public StoreTypeNode getStoreTypeByName(final String storeTypeName) {
		List<StoreTypeNode> nodes = getStoreTypeList();
		
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getStoreTypeName().equals(storeTypeName)) {
				StoreTypePosition.parentPosition = i;
				StoreTypePosition.childPosition = TwoColumnView.NO_DEFAULT_SELECTED;
				return nodes.get(i);
			}
			StoreTypeNode[] childNodes = nodes.get(i).getChildren();
			if (childNodes != null) {

				for (int j = 0; j < childNodes.length; j++) {
					if (childNodes[j].getStoreTypeName().equals(storeTypeName)) {
						StoreTypePosition.parentPosition = i;
						StoreTypePosition.childPosition = j;
						return childNodes[j];
					}
				}
			}
		}
		return null;
	}
	public Model getModelByName(final String modelName) {
		List<Model> nodes = getModelList();
		
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getModelName().equals(modelName)) {
				StoreTypePosition.parentPosition = i;
				StoreTypePosition.childPosition = TwoColumnView.NO_DEFAULT_SELECTED;
				return nodes.get(i);
			}
			List<Model> childNodes = nodes.get(i).getModelChilds();
			if (childNodes != null) {
				
				for (int j = 0; j < childNodes.size(); j++) {
					if (childNodes.get(j).getModelName().equals(modelName)) {
						StoreTypePosition.parentPosition = i;
						StoreTypePosition.childPosition = j;
						return childNodes.get(j);
					}
				}
			}
		}
		return null;
	}
	public ModelPosition getModelPositionByName(final String modelName) {
		List<Model> nodes = getModelList();
		ModelPosition position = new ModelPosition();
		for (int i = 0; i < nodes.size(); i++) {
			if (nodes.get(i).getModelName().equals(modelName)) {
				position.setParentPosition(i);
				position.setChildPosition(TwoColumnView.NO_DEFAULT_SELECTED);
				return position;
			}
			List<Model> childNodes = nodes.get(i).getModelChilds();
			if (childNodes != null) {
				
				for (int j = 0; j < childNodes.size(); j++) {
					if (childNodes.get(j).getModelName().equals(modelName)) {
						position.setParentPosition(i);
						position.setChildPosition(j);
						return position;
					}
				}
			}
		}
		return null;
	}
}
