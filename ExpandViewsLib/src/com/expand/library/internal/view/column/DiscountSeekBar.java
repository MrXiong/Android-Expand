package com.expand.library.internal.view.column;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.expand.library.R;
import com.expand.library.internal.view.ViewBaseAction;
import com.expand.library.internal.view.ViewBaseAction.OnSelectListener;


public class DiscountSeekBar extends RelativeLayout implements ViewBaseAction{

	protected static final String TAG = DiscountSeekBar.class.getSimpleName();
	private String showText = "筛选";
	private OnSelectListener<String> mOnSelectListener;
	private float discountNum;
	public String getShowText() {
		return showText;
	}

	public DiscountSeekBar(Context context) {
		super(context);
		init(context);
		
		
	}

	public DiscountSeekBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init(context);
	}

	public DiscountSeekBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(context);
	}

	private void init(Context context) {
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		inflater.inflate(R.layout.view_seekbar, this, true);
		SeekBar mSbarDiscount = (SeekBar) findViewById(R.id.sbar_discount);
		final TextView mTvDiscountNum = (TextView) findViewById(R.id.tv_discount_num);
		Button mBtnDiscountConfirm = (Button) findViewById(R.id.btn_discount_confirm);
		mSbarDiscount.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub  
				
			}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
				// TODO Auto-generated method stub  
				
			}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, final int progress,
					boolean fromUser) {
		        
		        seekBar.setOnTouchListener(new OnTouchListener() {
					
					

					@Override
					public boolean onTouch(View v, MotionEvent event) {
						LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mTvDiscountNum.getLayoutParams();  
						int eventX = (int) event.getX();
						
						if(eventX < v.getWidth() && eventX>30) {
							params.leftMargin = eventX+30;
							mTvDiscountNum.setLayoutParams(params);  
						}
							
				        
				        discountNum = (float)progress/10;
				        mTvDiscountNum.setText(discountNum+"折");
						return false;
					}
				});
		        
			}
		});
		mBtnDiscountConfirm.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (mOnSelectListener != null) {
					mOnSelectListener.onSelected(DiscountSeekBar.this, String.valueOf(discountNum));
				}
			}
		});
	}
	public void setOnSelectListener(OnSelectListener<String> onSelectListener) {
		mOnSelectListener = onSelectListener;
	}

	@Override
	public void hide() {
		
	}

	@Override
	public void show() {
		
	}

}
