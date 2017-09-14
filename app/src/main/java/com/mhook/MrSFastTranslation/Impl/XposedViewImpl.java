package com.mhook.MrSFastTranslation.Impl;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.*;
import android.os.*;
import android.graphics.*;
import android.view.View.*;
import android.content.*;
import android.view.*;
import com.mhook.MrSFastTranslation.Utils.*;
import com.mhook.MrSFastTranslation.mvp.*;
import com.mhook.MrSFastTranslation.*;

public class XposedViewImpl implements OnTouchListener,XposedView
{

	public void setWindowIsClickAbled(boolean isClickAbled)
	{
		this.isClickAbled = isClickAbled;
	}

	public boolean isWindowClickAbled()
	{
		return isClickAbled;
	}

	@Override
	public Boolean isShowingWindow()
	{
		// TODO: Implement this method
		return isShowing;
	}


	@Override
	public void setShowTransparency(int transparency)
	{
		// TODO: Implement this method
		
		this.showTransparency=transparency;
		
	}


	@Override
	public void copyFanyiStrToClipData()
	{
		// TODO: Implement this method
		
		android.content.  ClipboardManager clip=( android.content.  ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);

		clip.setPrimaryClip(ClipData.newPlainText("fanyi",strFanyi));
		
	}


	@Override
	public void showInfo(String msg)
	{
		// TODO: Implement this method
		
		T.ShowToast(mContext,msg);
		
	}


	@Override
	public void updateWindow()
	{
		// TODO: Implement this method
		
		// 更新悬浮窗位置
		
		textvShowFanyiStr.getBackground().setAlpha(showTransparency);

		for(int i=0;i<((LinearLayout)viewBottomBtn).getChildCount();i++){


			((LinearLayout)viewBottomBtn).getChildAt(i).getBackground().setAlpha(showTransparency);

		}
		
		
		mWindowManager.updateViewLayout(mKqwToast, mParams);
		
	}


	public void setMParams(WindowManager.LayoutParams mParams)
	{
		this.mParams = mParams;
	}

	public WindowManager.LayoutParams getMParams()
	{
		return mParams;
	}

	@Override
	public void setIsShowBottomBtn(boolean isShow)
	{
		// TODO: Implement this method
		
		this.isShowBottomBtn=isShow;
		
	}


	@Override
	public void setShowTime(int showTime)
	{
		// TODO: Implement this method
		
		this.showTime=showTime;
		
	}

	
	public void createWindow(Context context,int x,int y){

		if(null==context){ Utils.printf("con未知");  return;}

		Utils.printf("进入悬浮窗处理阶段");
		
		mContext = context;

		windowX=(x==0?120:x);

		windowY=y;

		hideWindow();
		
		mWindowManager = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);

        mParams = new WindowManager.LayoutParams();
        // 设置显示位置
        mParams.width = LayoutParams.WRAP_CONTENT;
        mParams.height = LayoutParams.WRAP_CONTENT;
        mParams.y =windowY;
		mParams.x=windowX;
        mParams.gravity = Gravity.TOP;
        mParams.flags =WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL|WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
		mParams.format=PixelFormat.RGBA_8888;
		mParams.type=WindowManager.LayoutParams.TYPE_TOAST;
		
		//mParams.alpha=0.6f;
		
	}

	@Override
	public void showWindow(String fanyiData)
	{
		// TODO: Implement this method

		try{
			
			strFanyi=fanyiData;
			
			mKqwToast=getView(mContext);
			
			textvShowFanyiStr.getBackground().setAlpha(showTransparency);

			for(int i=0;i<((LinearLayout)viewBottomBtn).getChildCount();i++){


				((LinearLayout)viewBottomBtn).getChildAt(i).getBackground().setAlpha(showTransparency);

				((LinearLayout)viewBottomBtn).getChildAt(i).setEnabled(isClickAbled);
				
			}
			

			setDelay(showTime);

			if(fanyiData.length()>100)textvShowFanyiStr.setTextSize(12);

			mWindowManager.addView(mKqwToast, mParams);
			
			isShowing=true;

		}catch(Throwable t){
			
			AlertUtils.Alert(mContext,AppUtils.getStackTrace(t));

			T.ShowToast(mContext,fanyiData);
			
		}
		
	}

	@Override
	public void hideWindow()
	{
		// TODO: Implement this method
		
		if(mHandler!=null){
			mHandler.removeCallbacks(rb_off);

		}
        try {
            if (null != mWindowManager && mKqwToast != null) {
                mWindowManager.removeView(mKqwToast);

            }
        } catch (Exception e) {
			
			Utils.printf("窗口隐藏失败！:"+e.getMessage());
			
			return;
        }
		
		Utils.printf("窗口已隐藏");
		
		isShowing=false;
		
	}

    // 上下文
    private Context mContext;

    private static WindowManager mWindowManager;

    private static View mKqwToast;

    private WindowManager.LayoutParams mParams;
	
	private XposedPresenter xpPresenter;
	
	private Boolean isShowBottomBtn=false;
	
	private int windowX=0;
	
	private int windowY=0;
	
	private int showTime=0;
	
	private int showTransparency=0;
	
	private TextView textvShowFanyiStr;
	
	private View viewBottomBtn;
	
	private String strFanyi;
	
	private boolean isShowing=false;
	
	private boolean isClickAbled=true;
	
	/*
     * 构造方法
     *
     * @param context
     */

    public XposedViewImpl(XposedPresenter xpPresenter) {
		
		this.xpPresenter=xpPresenter;
		
    }
	
	public XposedViewImpl(Context con){
		
		mContext=con;
		
		viewBottomBtn=layUtils.addlinearLay(mContext);
		
	}

	@Override
	public boolean onTouch(View p1, MotionEvent event)
	{
		
		return xpPresenter.onTouched(p1,event);

	}

	/*
	 设置显示时间
	 */

	 Handler mHandler=new Handler();
	 Runnable rb_off=new Runnable(){

		@Override
		public void run()
		{
			
			hideWindow();
			
			// TODO: Implement this method
		}
	};
	public void setDelay(int time){

		Utils.printf("显示时间:"+time+"s");

		mHandler.postDelayed(rb_off,(long)(time*1000));

		}

	public LinearLayout getView(final Context con){
		
		LinearLayout mLinearLayout = new LinearLayout(con);

		mLinearLayout.setLayoutParams(new LinearLayout.LayoutParams(
										  LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));

		mLinearLayout.setGravity(Gravity.CENTER);
		mLinearLayout.setOrientation(LinearLayout.VERTICAL);
		mLinearLayout.setBackgroundColor(Color.TRANSPARENT);
		
		textvShowFanyiStr=layUtils.getFyView(mContext);
		
		textvShowFanyiStr.setText(""+strFanyi);
		
		if(isClickAbled)textvShowFanyiStr.setOnTouchListener(this);
		
		mLinearLayout.addView(textvShowFanyiStr);
		
		if(!isShowBottomBtn){
		
		Utils.printf("显示底部按钮:否");
		
		return mLinearLayout;
		
		}

		Utils.printf("显示底部按钮:是");
		
		viewBottomBtn=layUtils.addlinearLay(mContext);
		
		mLinearLayout.addView(viewBottomBtn);

		Utils.printf("全部窗口创建成功");

		return mLinearLayout;

	}

	@Override
	public void saveWindowXY( int windowX, int windowY)
	{
		// TODO: Implement this method
		
		SharedPreferences sp=mContext.getSharedPreferences(AllResources.sharename,Context.MODE_WORLD_READABLE);
		
		sp.edit().putInt("windowX",windowX).commit();
		
		sp.edit().putInt("windowY",windowY).commit();
		
		
	}

}
