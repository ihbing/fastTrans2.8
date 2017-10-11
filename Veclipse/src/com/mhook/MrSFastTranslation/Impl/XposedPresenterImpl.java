package com.mhook.MrSFastTranslation.Impl;
import android.content.*;
import android.os.*;
import com.mhook.MrSFastTranslation.*;
import com.mhook.MrSFastTranslation.Utils.*;
import com.mhook.MrSFastTranslation.mvp.*;
import de.robv.android.xposed.*;
import android.view.*;

public class XposedPresenterImpl implements XposedPresenter
{

	@Override
	public void beforeInputHookedMethod(XC_MethodHook.MethodHookParam param)
	{
		// TODO: Implement this method
		
		Utils.printf("开始创建翻译广播");
		
		Context mContext= xpModel. getOnInputContext(param);

		if(null==mContext)return;

		if(! xpModel. checkPremission(mContext))return;

		xpModel.receiverBroad(mContext);
		
		Utils.printf("翻译广播创建完毕！");
	}


	
	int lastX, lastY;
	int paramX, paramY;
	int dx = 0,dy = 0;
	boolean isBemoved=false;
	long downtime=0,uptime=0;
	
	@Override
	public Boolean onTouched(View p1, MotionEvent event)
	{
		// TODO: Implement this method
		
		WindowManager.LayoutParams mParams=xpView.getMParams();
		
		switch (event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				isBemoved=false;
				downtime=System.currentTimeMillis();
				lastX = (int) event.getRawX();
				lastY = (int) event.getRawY();
				paramX = mParams.x;
				paramY = mParams.y;

				break;
			case MotionEvent.ACTION_MOVE:


				dx = (int) event.getRawX() - lastX;
				dy = (int) event.getRawY() - lastY;
				if(Math.abs(dy)>=40||Math.abs(dx)>=40){
					isBemoved=true;

					mParams.x = paramX + dx;
					mParams.y = paramY + dy;

					xpView.setMParams(mParams);
					
					xpView.updateWindow();
					
				}
				break;
			case MotionEvent.ACTION_UP:
				uptime=System.currentTimeMillis();
				if( uptime-downtime<300||  isBemoved){
					if(!isBemoved){

						Utils.printf("点击取消");

						 xpView. hideWindow();

						break;

					}

					Utils.printf("移动中...x="+paramX+dx+"y="+paramY+dy);

					xpView.saveWindowXY(paramX + dx,paramY + dy);

					isBemoved=false;

					break;

				}

				xpView.showInfo("翻译结果已复制");
		        
				Utils.printf("长按复制完毕");

				xpView.copyFanyiStrToClipData();
				
				 xpView. hideWindow();

				break;

		}
		
		return true;
		
	}

	
	public void postFanyiLoopToWindow(final Context con, final int windowX, final int windowY, final String fanyiResult)
	{
		// TODO: Implement this method

		Looper.prepare();
		Handler handler=new Handler(Looper.getMainLooper());
		handler.post(new Runnable(){
				public void run()
				{
					
					xpView.createWindow(con,windowX,windowY);
					
					xpView.setIsShowBottomBtn(true);
					
					xpView.setWindowIsClickAbled(true);
					
					xpView.setShowTime(xpModel.getShowTime());
					
					xpView.setShowTransparency(xpModel.getShowWindowTransparency());
					
					xpView.showWindow(Utils.UnicodeToString(fanyiResult));
					
					xpModel.setIsShowedWindow(true);

					Utils.printf("--------翻译完成-------");

					Utils.printf("当前包名:"+con.getPackageName());
					
					return;

				}

			});
		Looper.loop();

	}

	@Override
	public void beforeSetPrimaryClipHookedMethod(XC_MethodHook.MethodHookParam param)
	{
		// TODO: Implement this method
		
		Context mContext = null;

		Utils.printf("hook剪切板");

		mContext=xpModel. getOnFanyiContext(param);

		if(null==mContext)return;

		if(! xpModel. fixSetPrimaryClipProblem(mContext))return;

		Utils.printf("-------开始获取剪切板内容-------");

		Utils.printf("-------当前版本"+AllResources.ApkVersion+"--------");

		android.content.ClipData clipData= xpModel. getClipData(param);

		if(null==clipData)return;
		
		if(!xpModel.checkLabel(clipData))return;

		//发送剪切板字符到广播
		
		XposedUtils.disXposed(true);

		xpModel.  SendClipDataToBroadcast(mContext,  clipData.getItemAt(0).getText().toString(),xpModel.getAimsLanguage( xpModel. getToLanguage(clipData),clipData.getItemAt(0).getText().toString()));
		
		XposedUtils.disXposed(false);
		
	}


	@Override
	public void onReceive(final Context p1, Intent p2)
	{
		// TODO: Implement this method
		
		if(! xpModel. getModuleEnabledOnReceive())return;
		
		if(xpModel.isShowedWindow()){
			
			xpView.hideWindow();
			
			xpModel.setIsShowedWindow(false);
			
		}
		
		Utils.printf("---接收成功！---");

		Bundle bundleFanyiData=p2.getBundleExtra("bundle");

		final String fanyiData= xpModel. clearOldProblem( xpModel.  sortOutCharacter(bundleFanyiData.getString("data")));

		final String fanyiLanguage=bundleFanyiData.getString("tolanguage");

		if(! xpModel. checkCharacter(fanyiData))return;

		String urlFanyiGoogle= xpModel. getGoogleFanyiUrl(fanyiLanguage,fanyiData);

		if(! xpModel. checkCharacterLength(urlFanyiGoogle))return;

		 xpModel. getGoogleFanyiResult(urlFanyiGoogle, new XposedModelImpl.OnFanyiListener(){

				@Override
				public void OnSuccess( String result)
				{
					// TODO: Implement this method

					if(xpModel.  checkIsSame(fanyiData,result))return;

					 postFanyiLoopToWindow(p1, xpModel. getWindowXY(p1)[0],xpModel.  getWindowXY(p1)[1],result);

				}

				@Override
				public void OnFailed()
				{
					// TODO: Implement this method

					 xpModel. getYoudaoFanyiResult(xpModel.getYoudaoFanyiUrl(xpModel.getYoudaoRandomKey(),fanyiLanguage,fanyiData), new XposedModelImpl.OnFanyiListener(){

							@Override
							public void OnSuccess( String result)
							{
								// TODO: Implement this method

								if(xpModel. checkIsSame(fanyiData,result))return;

								 postFanyiLoopToWindow(p1,xpModel.  getWindowXY(p1)[0], xpModel. getWindowXY(p1)[1],result);

							}

							@Override
							public void OnFailed()
							{
								// TODO: Implement this method
							}
						});

				}

			});
		
	}

	
	private XposedModel xpModel;
	
	private XposedView xpView;
	
	public XposedPresenterImpl(XposedModel xpModel){
		
		this.xpModel=xpModel;
		
		this.xpView=new XposedViewImpl(this);
		
	}
	
}
