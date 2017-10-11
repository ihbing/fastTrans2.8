package com.mhook.MrSFastTranslation;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.content.*;
import android.widget.CompoundButton.*;
import android.widget.AdapterView.*;
import android.view.*;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import java.util.*;
import com.mhook.MrSFastTranslation.Utils.*;
import android.text.*;
import android.graphics.*;
import android.widget.SeekBar.*;
import com.mhook.MrSFastTranslation.mvp.*;
import com.mhook.MrSFastTranslation.Impl.*;

public class SettingActivity extends Activity implements TransView,OnClickListener,OnSeekBarChangeListener,OnCheckedChangeListener,OnItemSelectedListener
{

	void showTestWindow(int transparency){
		
		if(xpView.isShowingWindow()){
			
			xpView.setShowTransparency(transparency);
			
			xpView.updateWindow();
			
			return;
			
		}
		
		xpView.setIsShowBottomBtn(true);

		xpView.setWindowIsClickAbled(false);
		
		xpView.setShowTime(10);

		xpView.showWindow("hello,world");
		
		xpView.setShowTransparency(transparency);
		
	}
	
	@Override
	public void showShowTransparencySettingAlert()
	{
		// TODO: Implement this method
		View viewShowTransparencySetting=LayoutInflater.from(this).inflate(R.layout.show_transparency_setting,null);
		
		final EditText edtShowTransparency=(EditText)viewShowTransparencySetting.findViewById(R.id.show_edt_transparency);
		
		Button btnShowTransparencyLess=(Button)viewShowTransparencySetting.findViewById(R.id.showtransparency_btn_less);
		
		Button btnShowTransparencyAdd=(Button)viewShowTransparencySetting.findViewById(R.id.showtransparency_btn_add);
		
		final SeekBar skbShowTransparency=(SeekBar)viewShowTransparencySetting.findViewById(R.id.show_skb_transparency);
		
		edtShowTransparency.setHint("0-255");
		
		skbShowTransparency.setMax(255);
		
		edtShowTransparency.setText(""+transPresenter.getShowTransparency());
		
		skbShowTransparency.setProgress(transPresenter.getShowTransparency());
		
		xpView.createWindow(this,120,0);
		
		edtShowTransparency.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					
					try{
						
						if(TextUtils.isEmpty(p1.toString())){

							skbShowTransparency.setProgress(0);
							
							showTestWindow(0);
							
							
							return;

						}
						
						if(Integer.parseInt(p1.toString())>255){
							
							skbShowTransparency.setProgress(255);
							
							showTestWindow(255);
							
						}
						
						if(Integer.parseInt(p1.toString())<0){

							skbShowTransparency.setProgress(0);
							
							showTestWindow(0);

						}
						
						skbShowTransparency.setProgress(Integer.parseInt(p1.toString()));
						
						showTestWindow(Integer.parseInt(p1.toString()));
						
					}catch(Throwable t){
						
						showError(""+AppUtils.getStackTrace(t));
						
					}
					
				
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}
			});
		
		skbShowTransparency.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean isFromUser)
				{
					// TODO: Implement this method
					
					if(isFromUser){
					
					edtShowTransparency.setText(""+p2);
					
					}
					
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
			});
			
		btnShowTransparencyLess.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					try{
					
					if(Integer.parseInt(edtShowTransparency.getText().toString())==0)return;
					
					edtShowTransparency.setText(""+( Integer.parseInt( edtShowTransparency.getText().toString())-1));
					
					skbShowTransparency.setProgress(skbShowTransparency.getProgress()==0?0:(skbShowTransparency.getProgress()-1));
					
					}catch(Throwable t){
						
						showError(""+t.getMessage());
						
					}
					
				}
			});
			
		btnShowTransparencyAdd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					try{
					
					edtShowTransparency.setText(""+( Integer.parseInt( edtShowTransparency.getText().toString())+1));
					
					if(Integer.parseInt(edtShowTransparency.getText().toString())==100)return;
					
					skbShowTransparency.setProgress(skbShowTransparency.getProgress()+1);
					
					}catch(Throwable t){
						
						showError(""+t.getMessage());
						
					}
					
				}
			});
		
		AlertUtils.DiyViewAlertWithOneBtn(this, "设置悬浮窗透明度", viewShowTransparencySetting, "保存", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					
					transPresenter.setShowTransparency(skbShowTransparency.getProgress());
					
					xpView.hideWindow();
					
				}
			});
			
		
	}


	@Override
	public void showRebootInputAlert()
	{
		// TODO: Implement this method
		
		AlertUtils.OneBtnAlert(this, "提示", "当前设置需要重启输入法才能生效", "重启输入法(root)", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					
					transPresenter.onClickRebootInput();
					
				}
			});
		
	}


	//创建输入法spinner
	
	void createSpinnerInput(){
		
		ArrayList<String> arrayListInput=new ArrayList<String>();
		
		arrayInputPkgNmae=new ArrayList<String>();
		
		arrayListInput.add("选择输入法");
		
		arrayInputPkgNmae.add("选择包名");
		
		for(String inputPkgName:AppUtils.GetInputPkgName(this)){
			
			arrayListInput.add(AppUtils.getAppName(this,inputPkgName));
			
			arrayInputPkgNmae.add(inputPkgName);
			
		}
		
		ArrayAdapter<String> arrayAdapterInput=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayListInput);
		
		spnnInput.setAdapter(arrayAdapterInput);
		
		for(int i=0;i<arrayInputPkgNmae.size();i++){
			
			if(arrayInputPkgNmae.get(i).equals(transPresenter.getOnInputPkgName())){
				
				spnnInput.setSelection(i);
				
			}
			
		}
		
	}
	
	@Override
	public void onItemSelected(AdapterView<?> p1, View p2, int p3, long p4)
	{
		// TODO: Implement this method
		
		transPresenter.onItemSelectInput(arrayInputPkgNmae.get(p3));
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> p1)
	{
		// TODO: Implement this method
	}
	
	@Override
	public void showShowTimeSettingAlert()
	{
		// TODO: Implement this method
		
		View viewShowTimeSetting=LayoutInflater.from(this).inflate(R.layout.show_time_setting,null);
		
		final EditText edtShowTime=(EditText)viewShowTimeSetting.findViewById(R.id.showtimesetting_edt_show_time);
		
		Button btnShowTimeLess=(Button)viewShowTimeSetting.findViewById(R.id.showtimesetting_btn_less);
		
		Button btnShowTimeAdd=(Button)viewShowTimeSetting.findViewById(R.id.showtimesetting_btn_add);
		
		final SeekBar skbShowTime=(SeekBar)viewShowTimeSetting.findViewById(R.id.showtimesetting_skb_show_time);
		
		edtShowTime.setHint("显示时间,单位:秒[s];");
		
		skbShowTime.setMax(100);
		
		edtShowTime.setText(""+transPresenter.getShowTime());
		
		skbShowTime.setProgress(transPresenter.getShowTime());
		
		edtShowTime.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
					
					try{
						
						if(TextUtils.isEmpty(p1.toString())){

							skbShowTime.setProgress(0);
							
							return;

						}
						
						if(Integer.parseInt(p1.toString())>100){
							
							skbShowTime.setProgress(100);
							
						}
						
						if(Integer.parseInt(p1.toString())<0){

							skbShowTime.setProgress(0);

						}
						
						skbShowTime.setProgress(Integer.parseInt(p1.toString()));
						
					}catch(Throwable t){
						
						showError(t.getMessage());
						
					}
					
				
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					// TODO: Implement this method
				}
			});
		
		skbShowTime.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean isFromUser)
				{
					// TODO: Implement this method
					
					if(isFromUser){
					
					edtShowTime.setText(""+p2);
					
					transPresenter.onProgressChanged(p1,p2,isFromUser);
					
					}
					
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					// TODO: Implement this method
				}
			});
			
		btnShowTimeLess.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					try{
					
					if(Integer.parseInt(edtShowTime.getText().toString())==0)return;
					
					edtShowTime.setText(""+( Integer.parseInt( edtShowTime.getText().toString())-1));
					
					skbShowTime.setProgress(skbShowTime.getProgress()==0?0:(skbShowTime.getProgress()-1));
					
					}catch(Throwable t){
						
						showError(t.getMessage());
						
					}
					
				}
			});
			
		btnShowTimeAdd.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					
					try{
					
					edtShowTime.setText(""+( Integer.parseInt( edtShowTime.getText().toString())+1));
					
					if(Integer.parseInt(edtShowTime.getText().toString())==100)return;
					
					skbShowTime.setProgress(skbShowTime.getProgress()+1);
					
					}catch(Throwable t){
						
						showError(t.getMessage());
						
					}
					
				}
			});
		
		AlertUtils.DiyViewAlertWithOneBtn(this, "设置悬浮窗显示时间", viewShowTimeSetting, "保存", new DialogInterface.OnClickListener(){

				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					// TODO: Implement this method
					
					transPresenter.setShowTime(Integer.parseInt(edtShowTime.getText().toString()));
					
				}
			});
			
	}

	@Override
	public void setModuleEnabled(boolean status)
	{
		// TODO: Implement this method
		
		swhModuleEnabled.setChecked(status);
		
	}

	@Override
	public void setHideIcon(boolean bool)
	{
		// TODO: Implement this method
		
		if(bool){AppUtils.HideApkIcon(this);}else{AppUtils.ShowApkIcon(this);}
		
		swhHideIcon.setChecked(bool);

		}

	@Override
	public void setBrightTheme(boolean bool)
	{
		// TODO: Implement this method
		
		swhBrightTheme.setChecked(bool);
		
	}
	
	@Override
	public void setWhiteListEnabled(boolean bool)
	{
		// TODO: Implement this method
		
		swhWhiteListEnabled.setChecked(bool);
		
	}

	@Override
	public void setStrWhiteList(String StrWhiteList)
	{
		// TODO: Implement this method
		
		edtWhiteList.setText(StrWhiteList);
		
	}

	@Override
	public void setStrBlackList(String strBlackList)
	{
		// TODO: Implement this method
		
		edtBlackList.setText(strBlackList);
		
	}

	@Override
	public void setBlackListEnabled(boolean bool)
	{
		// TODO: Implement this method
		
		swhBlackListEnabled.setChecked(bool);
		
	}

	
	@Override
	public void setDebugMode(boolean bool)
	{
		// TODO: Implement this method
		
		swhDebugModeEnabled.setChecked(bool);
		
	}

	@Override
	public Boolean isPayed()
	{
		// TODO: Implement this method
		return isPayed;
	}

	@Override
	public void setPayed(boolean bool)
	{
		// TODO: Implement this method
		
		isPayed=bool;
		
	}

	@Override
	public Boolean isScored()
	{
		// TODO: Implement this method
		return isScored;
	}

	@Override
	public void setScored(boolean bool)
	{
		// TODO: Implement this method
		
		isScored=bool;
		
	}


	@Override
	public void setXposedActiveLayout(boolean isHideLayout)
	{
		// TODO: Implement this method
		
		if(isHideLayout){

			textvShowXposedActive.setText("您已经在xposed激活本模块");

			textvShowXposedActive.setTextColor(Color.GREEN);

			//textvShowXposedActive.setVisibility(View.VISIBLE);

		}else{

			textvShowXposedActive.setText("您需要在xposed框架里激活本模块！");

			textvShowXposedActive.setTextColor(Color.RED);

			//textvShowXposedActive.setVisibility(View.VISIBLE);

			//禁用xposed功能

			swhModuleEnabled.setEnabled(false);

			swhDebugModeEnabled.setEnabled(false);

			btnOpenLogAlert.setEnabled(false);

		}
		
	}


	@Override
	public String getStrWhiteList()
	{
		// TODO: Implement this method
		return edtWhiteList.getText().toString();
	}

	@Override
	public String getStrBlackList()
	{
		// TODO: Implement this method
		return edtBlackList.getText().toString();
		
	}


	@Override
	public void setWhiteListLayout(boolean isShowLayout)
	{
		// TODO: Implement this method
		
		if(isShowLayout){

			((LinearLayout)findViewById(R.id.main_linear_white_list)).setVisibility(View.VISIBLE);

			((TextView)findViewById(R.id.main_textv_description_white_list)).setVisibility(View.VISIBLE);

		}else{

			((LinearLayout)findViewById(R.id.main_linear_white_list)).setVisibility(View.GONE);

			((TextView)findViewById(R.id.main_textv_description_white_list)).setVisibility(View.GONE);

		}
	}

	@Override
	public void setBlackListLayout(boolean isShowLayout)
	{
		// TODO: Implement this method
		
		if(isShowLayout){

			((LinearLayout)findViewById(R.id.main_linear_black_list)).setVisibility(View.VISIBLE);

			((TextView)findViewById(R.id.main_textv_description_black_list)).setVisibility(View.VISIBLE);

		}else{

			((LinearLayout)findViewById(R.id.main_linear_black_list)).setVisibility(View.GONE);

			((TextView)findViewById(R.id.main_textv_description_black_list)).setVisibility(View.GONE);

		}
	}


	@Override
	public void onCheckedChanged(CompoundButton p1, boolean p2)
	{
		// TODO: Implement this method
		
		transPresenter.onCheckedChanged(p1,p2);
		
	}


	@Override
	public void onProgressChanged(SeekBar p1, int p2, boolean p3)
	{
		// TODO: Implement this method
		
		transPresenter.onProgressChanged(p1,p2,p3);
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}

	@Override
	public void onStopTrackingTouch(SeekBar p1)
	{
		// TODO: Implement this method
	}


	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
		
		transPresenter.onClicked(p1);
		
	}


	@Override
	public void showError(String msg)
	{
		// TODO: Implement this method
		
		String className = null;//类名
		String methodName = null;//方法名
		int lineNumber = 0;//行数

		StackTraceElement[] sElements=new Throwable().getStackTrace();
		className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();

		StringBuffer buffer = new StringBuffer();
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append("):");
        buffer.append(msg);

       T.ShowToast(this,"出现错误:" + buffer.toString());
		
		
	}

	@Override
	public void showInfo(String msg)
	{
		// TODO: Implement this method
		
		T.ShowToast(this,msg);
		
	}

	@Override
	public void showWarn(String msg)
	{
		// TODO: Implement this method
		
		T.ShowToast(this,msg);
		
	}

	@Override
	public void showLogAlert(final String logStr)
	{
		// TODO: Implement this method
		
		DialogInterface.OnClickListener mOnClickListener2=new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface p1, int p2)
			{
				
				transPresenter.onLogAlertCopyClicked(logStr);
				
				// TODO: Implement this method
			}
		};

		DialogInterface.OnClickListener mOnClickListener3=new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface p1, int p2)
			{
				
				transPresenter.onLogAlertClearClicked();

			}
		};
		
		DialogInterface.OnClickListener onClickListenerShareBtn=new DialogInterface.OnClickListener(){

			@Override
			public void onClick(DialogInterface p1, int p2)
			{

				transPresenter.onLogAlertShareClicked(mActivity,logStr);

			}
		};

		ScrollView ScrollView1=new ScrollView(mActivity);

		ScrollView1.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

		ScrollView1.setBackgroundColor(Color.WHITE);

		TextView TextView3=new TextView(mActivity);

		TextView3.setLayoutParams(new ViewGroup.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT));

		TextView3.setText(logStr);

		TextView3.setTextSize(10);

		TextView3.setTextColor(Color.RED);

		TextView3.setBackgroundColor(Color.WHITE);

		TextView3.setPadding(5,5,5,5);

		ScrollView1.addView(TextView3);

		AlertUtils.DiyViewAlertWithThreeBtn(mActivity,"运行日志",getResources().getDrawable(R.drawable.icon),ScrollView1,"复制",mOnClickListener2,"清除",mOnClickListener3,"分享",onClickListenerShareBtn);
		
	}

	public static Activity mActivity;
	
	private TransPresenter transPresenter;
	
	private TextView textvDescription;
	
	private TextView textvShowXposedActive;
	
	private EditText edtWhiteList;
	
	private EditText edtBlackList;
	
	private Button btnOpenShowTimeAlert;
	
	private Button btnOpenLogAlert;
	
	private Button btnJoinTestGroup;
	
	private Button btnOpenAlipay;
	
	private Button btnOpenMarket;
	
	private Button btnSaveWhiteList;
	
	private Button btnSaveBlackList;
	
	private Button btnShowWindowTransparency;
	
	private Switch swhModuleEnabled;
	
	private Switch swhHideIcon;
	
	private Switch swhBrightTheme;
	
	private Switch swhWhiteListEnabled;
	
	private Switch swhBlackListEnabled;
	
	private Switch swhDebugModeEnabled;
	
	private Spinner spnnInput;
	
	private boolean isPayed=false;
	
	private boolean isScored=false;
	
	private XposedView xpView;
	
	private ArrayList<String> arrayInputPkgNmae;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		mActivity=this;
		
		transPresenter=new TransPresenterImpl(this);
		
		this.xpView=new XposedViewImpl(this);
		
		setTheme(transPresenter.isBrightTheme()?android.R.style.Theme_Holo_Light:android.R.style.Theme_Holo);
		
		setContentView(R.layout.mainsetting);
		
		initId();
		
		initListener();
		
		initSettings();
		
	}
	
	//初始化设置
	
	void initId(){
		
		textvDescription=(TextView)findViewById(R.id.main_textv_description_app);
		
		textvShowXposedActive=(TextView)findViewById(R.id.main_textv_show_xposed_active);
		
		edtWhiteList=(EditText)findViewById(R.id.main_edt_white_list);
		
		edtBlackList=(EditText)findViewById(R.id.main_edt_black_list);
		
		btnOpenShowTimeAlert=(Button)findViewById(R.id.main_btn_setting_show_time);
		
		btnOpenLogAlert=(Button)findViewById(R.id.main_btn_show_log);
		
		btnJoinTestGroup=(Button)findViewById(R.id.main_btn_join_test_group);
		
		btnOpenAlipay=(Button)findViewById(R.id.main_btn_alipay);
		
		btnOpenMarket=(Button)findViewById(R.id.main_btn_open_app_market);
		
		btnSaveWhiteList=(Button)findViewById(R.id.main_btn_save_white_list);
		
		btnSaveBlackList=(Button)findViewById(R.id.main_btn_save_black_list);
		
		btnShowWindowTransparency=(Button)findViewById(R.id.main_btn_setting_show_transparency);
		
		swhModuleEnabled=(Switch)findViewById(R.id.main_swh_module_enabled);
		
		swhHideIcon=(Switch)findViewById(R.id.main_swh_hide_icon);
		
		swhBrightTheme=(Switch)findViewById(R.id.main_swh_bright_theme_enabled);
		
		swhWhiteListEnabled=(Switch)findViewById(R.id.main_swh_white_list_enabled);
		
		swhBlackListEnabled=(Switch)findViewById(R.id.main_swh_black_list_enabled);
		
		swhDebugModeEnabled=(Switch)findViewById(R.id.main_swh_debug_mode_enabled);
		
		spnnInput=(Spinner)findViewById(R.id.main_spinner_select_input);
		
	}
	
	void initListener(){
		
		btnOpenShowTimeAlert.setOnClickListener(this);
		
		btnOpenMarket.setOnClickListener(this);
		
		btnOpenLogAlert.setOnClickListener(this);
		
		btnJoinTestGroup.setOnClickListener(this);
		
		btnOpenAlipay.setOnClickListener(this);
		
		btnSaveWhiteList.setOnClickListener(this);
		
		btnSaveBlackList.setOnClickListener(this);
		
		btnShowWindowTransparency.setOnClickListener(this);
		
		swhModuleEnabled.setOnCheckedChangeListener(this);
		
		swhHideIcon.setOnCheckedChangeListener(this);
		
		swhBrightTheme.setOnCheckedChangeListener(this);
		
		swhWhiteListEnabled.setOnCheckedChangeListener(this);
		
		swhBlackListEnabled.setOnCheckedChangeListener(this);
		
		swhDebugModeEnabled.setOnCheckedChangeListener(this);
		
		spnnInput.setOnItemSelectedListener(this);
		
	}
	
	void initSettings(){
		
		//设置标题
		
		setTitle("Xp快译["+AllResources.ApkVersion+"]");
		
		//设置描述

		textvDescription.setText(AllResources.apkIntroduction);
		
		transPresenter.initSettings();
		
		createSpinnerInput();
		
	}
	
}
