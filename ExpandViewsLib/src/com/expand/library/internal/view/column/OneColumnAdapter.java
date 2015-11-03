package com.expand.library.internal.view.column;

import java.util.List;

import android.content.Context;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.expand.library.R;
import com.expand.library.internal.Model;

public class OneColumnAdapter extends ArrayAdapter<Model> {

	private Context mContext;
	private List<Model> mListData;
	private Model[] mArrayData;
	private int selectedPos = -1;
	private String selectedText = "全部";
	private int mNormalDrawbleId;
	private int mSelectedDrawble;
	private float textSize;
	private OnClickListener onClickListener;
	private OnItemClickListener mOnItemClickListener;
	private OnItemIconVisibleListener mOnItemIconVisibleListener;

	public OneColumnAdapter(Context context, List<Model> listData, int normalDrawbleId, int selectedDrawble) {
		super(context, R.string.app_name, listData);
		mContext = context;
		mListData = listData;
		mNormalDrawbleId = normalDrawbleId;
		mSelectedDrawble = selectedDrawble;
		

		init();
	}
	public OneColumnAdapter(Context context, Model[] arrayData, int normalDrawbleId, int selectedDrawble) {
		super(context, R.string.app_name, arrayData);
		mContext = context;
		mArrayData = arrayData;
		mNormalDrawbleId = normalDrawbleId;
		mSelectedDrawble = selectedDrawble;
		
		
		init();
	}
	
	private void init() {
		onClickListener = new OnClickListener() {

			@Override
			public void onClick(View view) {
				selectedPos = (Integer) view.getTag(R.id.tag_position);
				setSelectedPosition(selectedPos);
				if (mOnItemClickListener != null) {
					mOnItemClickListener.onItemClick(view, selectedPos);
				}
			}
		};
	}

	public void setSelectedText(final String selectText) {
		selectedText = selectText;
	}

	/**
	 * 设置选中的position,并通知列表刷新
	 */
	public void setSelectedPosition(int pos) {
		if (mListData != null && pos < mListData.size()) {
			selectedPos = pos;
			selectedText = mListData.get(pos).getModelName();
			notifyDataSetChanged();
		} else if (mArrayData != null && pos < mArrayData.length) {
			selectedPos = pos;
			selectedText = mArrayData[pos].getModelName();
			notifyDataSetChanged();
		}

	}
	

	/**
	 * 设置选中的position,但不通知刷新
	 */
	public void setSelectedPositionNoNotify(int pos) {
		selectedPos = pos;
		if (mListData != null && pos < mListData.size()) {
			selectedText = mListData.get(pos).getModelName();
		}else if (mArrayData != null && pos < mArrayData.length) {
			selectedText = mArrayData[pos].getModelName();
		}
	}

	/**
	 * 获取选中的position
	 */
	public int getSelectedPosition() {
		
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}
		if (mListData != null && selectedPos < mListData.size()) {
			return selectedPos;
		}

		return -1;
	}

	/**
	 * 设置列表字体大小
	 */
	public void setTextSize(float tSize) {
		textSize = tSize;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.choose_item, parent, false);
			holder = new ViewHolder();
			holder.ivIcon = (ImageView) convertView.findViewById(R.id.iv_icon);
			holder.tvName = (TextView) convertView.findViewById(R.id.tv_name);
			holder.tvNumber = (TextView) convertView.findViewById(R.id.tv_number);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(mOnItemIconVisibleListener != null) {
			mOnItemIconVisibleListener.OnItemIconVisible(holder.ivIcon, position);
			mOnItemIconVisibleListener.OnItemRightNumVisible(holder.tvNumber, position);
		}
		
		convertView.setTag(R.id.tag_position, position);
		String mString = "";
		if (mListData != null) {
			if (position < mListData.size()) {
				mString = mListData.get(position).getModelName();
			}
		} else if (mArrayData != null) {
			if (position < mArrayData.length) {
				mString = mArrayData[position].getModelName();
			}
		}
		if (mString.contains("不限"))
			holder.tvName.setText("不限");
		else
			holder.tvName.setText(mString);
		holder.tvName.setTextSize(TypedValue.COMPLEX_UNIT_SP,textSize);

		if (selectedText != null && selectedText.equals(mString)) {
			convertView.setBackgroundResource(mSelectedDrawble);
		} else {
			convertView.setBackgroundResource(mNormalDrawbleId);
		}
		holder.tvName.setPadding(20, 0, 0, 0);
		convertView.setOnClickListener(onClickListener);
		return convertView;
	}
	
	static class ViewHolder {
		ImageView ivIcon;
		TextView tvName;
		TextView tvNumber;
	}

	public void setOnItemClickListener(OnItemClickListener l) {
		mOnItemClickListener = l;
	}

	/**
	 * 重新定义菜单选项单击接口
	 */
	public interface OnItemClickListener {
		public void onItemClick(View view, int position);
	}
	
	//itemicon显示隐藏
	
	public void setOnItemIconVisibleListener(OnItemIconVisibleListener l) {
		mOnItemIconVisibleListener = l;
	}
	
	public interface OnItemIconVisibleListener{
		public void OnItemIconVisible(ImageView view, int position);
		public void OnItemRightNumVisible(TextView view, int position);
	}


}
