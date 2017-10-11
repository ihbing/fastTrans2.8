package com.mhook.MrSFastTranslation;
import android.content.*;
import com.mhook.MrSFastTranslation.mvp.*;
import com.mhook.MrSFastTranslation.Impl.*;
import android.widget.*;
import com.mhook.MrSFastTranslation.Utils.*;

public class TransReceiver extends BroadcastReceiver
{
	
	public TransReceiver(){
		
		Utils.printf("执行TransReceiver");
		
		XposedUtils.disXposed(true);
		
	}

	@Override
	public void onReceive(Context p1, Intent p2)
	{
		// TODO: Implement this method
		
		new XposedPresenterImpl(new TransReceiverImpl()).onReceive(p1,p2);
		
		abortBroadcast();
		
		XposedUtils.disXposed(false);
		
	}

}
