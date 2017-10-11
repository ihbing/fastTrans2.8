package com.mhook.MrSFastTranslation.Impl;
import com.mhook.MrSFastTranslation.mvp.*;
import android.view.*;
import android.widget.*;
import com.mhook.MrSFastTranslation.*;
import com.mhook.MrSFastTranslation.Utils.*;

public class TransPresenterImpl implements TransPresenter
{

	@Override
	public void setShowTransparency(int transparency)
	{
		// TODO: Implement this method
		
		transModel.setShowWindowTransparency(transparency);
		
		transModel.saveSettings();
		
	}

	@Override
	public int getShowTransparency()
	{
		// TODO: Implement this method
		return transModel.getShowWindowTransparency();
	}


	@Override
	public void onClickRebootInput()
	{
		// TODO: Implement this method
		
		if(!transModel.isRooted()){
			
			transView.showInfo("无root权限或权限被拒绝!");
			
			return;
			
		}
		
		if(transModel.rebootOnInput()){
			
			transView.showInfo("输入法已停止,请手动重启!");
			
			transModel.saveSettings();
			
			return;
			
		}
		
		transView.showInfo("输入法停止失败！");
		
		transModel.initSettings();
		
	}

	boolean isUserSelect=false;

	@Override
	public void onItemSelectInput(String pkgName)
	{
		// TODO: Implement this method
		
		if(!isUserSelect){isUserSelect=true;return;}
		
		if((!transModel.getOnInputPkgName().equals(pkgName))){
		
		transView.showRebootInputAlert();
		
		transModel.setOnInputPkgName(pkgName);
		
		}
		
	}

	@Override
	public String getOnInputPkgName()
	{
		// TODO: Implement this method
		return transModel.getOnInputPkgName();
		
	}


	@Override
	public int getShowTime()
	{
		// TODO: Implement this method
		return transModel.getShowWindowTime();
	}


	@Override
	public void setShowTime(int time)
	{
		// TODO: Implement this method
		
		transModel.setShowWindowTime(time);
		
		transModel.saveSettings();
		
	}


	@Override
	public Boolean isBrightTheme()
	{
		// TODO: Implement this method
		return transModel.isBrightTheme();
	}

	
	public void initSettings(){
		
		//处理首次运行

		if(transModel.isFirstRun()){

			transModel.clearSettings();

			transModel.setFirstRun(false);



		}
		
		//处理激活

		transView.setXposedActiveLayout(transModel.isXposedActive());
		
		

		transView.setModuleEnabled(transModel.isModuleEnabled());

		//设置图标
		
		transView.setHideIcon(transModel.isHideIcon());

		transView.setBrightTheme(transModel.isBrightTheme());
		
		//名单设置

		transView.setWhiteListEnabled(transModel.isWhiteListEnabled());
		
		transView.setWhiteListLayout(transModel.isWhiteListEnabled());

		transView.setBlackListEnabled(transModel.isBlackListEnabled());

		transView.setBlackListLayout(transModel.isBlackListEnabled());
		
		transView.setStrWhiteList(""+transModel.getStrWhiteList());
		
		transView.setStrBlackList(""+transModel.getStrBlackList());

		//调试
		
		transView.setDebugMode(transModel.isDebugMode());

	}

	@Override
	public void onLogAlertCopyClicked(String logStr)
	{
		// TODO: Implement this method
		
		JsonRW.JObject jObect=JsonRW.JObject.getInstance();
		
		AppUtils.SetPrimaryClip(MyApplication.getContext(),logStr+"\nJSON settings:"+jObect.toString());

		  transView. showInfo("翻译日志已复制");
		
	}

	@Override
	public void onLogAlertClearClicked()
	{
		
		if(!transModel.isRooted()){
			
			transView.showInfo("无root权限或权限被拒绝!");

			return;
			
		}
		
		if(transModel.clearLog())

			 transView. showInfo("日志清除完毕");
		else
			
			transView.showError("日志清除失败！");
		
	}


	@Override
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		// TODO: Implement this method
		
		if(!p1.isPressed())return;
		
		switch(p1.getId()){

			case R.id.main_swh_hide_icon:

				transModel.setHideIcon(p2);
				
				transView.setHideIcon(p2);

				transView.  showInfo("图标已"+(p2?"隐藏":"显示"));

				break;

			case R.id.main_swh_debug_mode_enabled:
				
				if(p2){
				
				if(!transModel.isRooted()){

					transView.showInfo("无root权限或权限被拒绝!");

					return;

				}

				transModel.startGetLog();
				
				}else{
					
					transModel.stopGetLog();
					
				}
				
				transModel.setDebugMode(p2);

				 transView. showInfo("调试模式已"+(p2?"开启":"关闭"));

				break;

			case R.id.main_swh_bright_theme_enabled:

				transModel.setBrightTheme(p2);

				 transView. showInfo("重启应用后生效");

				break;

			case R.id.main_swh_module_enabled:

				transModel.setModuleEnabled(p2);

				transView.  showInfo("模块已"+(p2?"启用":"关闭"));

				break;

			case R.id.main_swh_white_list_enabled:
				
				transModel.setWhiteListEnabled(p2);

				transView.setWhiteListLayout(p2);

				break;

			case R.id.main_swh_black_list_enabled:
				
				transModel.setBlackListEnabled(p2);
				
				transView.setBlackListLayout(p2);

				break;


		}
		
		transModel.saveSettings();
		
	}


	@Override
	public void onProgressChanged(SeekBar p1, int p2, boolean p3)
	{
		// TODO: Implement this method
		
		switch(p1.getId()){

			case R.id.showtimesetting_skb_show_time:

				transModel.setShowWindowTime(p2);

				break;

		}
	}


	@Override
	public void onClicked(View view)
	{
		// TODO: Implement this method
		
		switch(view.getId()){

			case R.id.main_btn_save_white_list:

				transModel.setStrWhiteList(transView.getStrWhiteList());
				
				break;
				
			case R.id.main_btn_save_black_list:

				transModel.setStrBlackList(transView.getStrBlackList());

				break;

			case R.id.main_btn_show_log:
				
				transView.showLogAlert(transModel.getRunningLog());

				break;
				
			case R.id.main_btn_alipay:
				
				transModel.openAliay();
				
				break;

			case R.id.main_btn_join_test_group:
				
				transModel.JoinTestGroup();
				
				break;
				
			case R.id.main_btn_open_app_market:
				
				transModel.openAppMarket();
				
				break;
				
			case R.id.main_btn_setting_show_time:
				
				transView.showShowTimeSettingAlert();
				
				break;
				
			case R.id.main_btn_setting_show_transparency:
				
				transView.showShowTransparencySettingAlert();
				
				break;
				
		}
		
		transModel.saveSettings();
		
	}

	private TransView transView;
	
	private TransModel transModel;
	
	
	
	public TransPresenterImpl(TransView transView){
		
		if(transView==null)return;
		
		this.transView=transView;
		
		this.transModel=new TransModelImpl(this);
		
		
		
	}

}
