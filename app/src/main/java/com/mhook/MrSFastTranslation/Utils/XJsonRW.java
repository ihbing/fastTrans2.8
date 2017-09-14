package com.mhook.MrSFastTranslation.Utils;
import org.json.*;
import android.annotation.SuppressLint;
import android.os.*;
import android.text.*;
import com.mhook.MrSFastTranslation.*;

public class XJsonRW {

	private static String jsonName="fyXposed";
	
	public static class JArray extends JSONArray
	{

		private static JArray instance;

		private JArray() {

			super();

		}

		private JArray(java.util.Collection copyFrom) {

			super(copyFrom);

		}

		private JArray(org.json.JSONTokener readFrom) throws org.json.JSONException {

			super(readFrom);

		}

		private JArray(java.lang.String json) throws org.json.JSONException {

			super(json);

		}

		@SuppressLint("NewApi")
		private JArray(java.lang.Object array) throws org.json.JSONException {

			super(array);

		}

		public static synchronized JArray getInstance(){

			if(instance==null){

				try
				{
					String strSettings=ReadFile();

					if(TextUtils.isEmpty(strSettings)){

						instance=new JArray();

					}else if(strSettings.charAt(0)=='['){

						instance = new JArray(strSettings);

					}else{

						//throw new Error("自定义错误:不是JArray格式数据");

						//Toast.makeText(getApplication(), getAppName()+": 读取数据失败,不是JSONArray格式数据",1).show();

					}

				}
				catch (JSONException e)
				{}
			}

			return instance;

		}

		public void save(){

			WriteFile(this.toString());

		}
		
		public void freed(){

			instance=null;

		}

	}


	public static class JObject extends JSONObject{

		private static JObject instance;

		private JObject(){

			super();

		}

		private JObject(java.util.Map copyFrom) {

			super(copyFrom);

		}

		private JObject(org.json.JSONTokener readFrom) throws org.json.JSONException {

			super(readFrom);

		}

		private JObject(java.lang.String json) throws org.json.JSONException {

			super(json);

		}

		private JObject(org.json.JSONObject copyFrom, java.lang.String[] names) throws org.json.JSONException {

			super(copyFrom,names);

		}


		public static synchronized JObject getInstance(){

			if(instance==null){

				try
				{
					String strSettings=ReadFile();

					if(TextUtils.isEmpty(strSettings)){

						instance=new JObject();

					}else if(strSettings.charAt(0)=='{'){

						instance = new JObject(strSettings);

					}else{

						//Toast.makeText(getApplication(), getAppName()+":读取数据失败,不是JSONObject格式数据",1).show();

					}

				}
				catch (JSONException e)
				{}
			}

			return instance;

		}

		public void save(){

			WriteFile(this.toString());

		}
		
		public void freed(){
			
			instance=null;
			
		}

	}

	//读取文件

	public static String ReadFile(){

		return FileUtils.ReadFile(getJsonPath());

	}




	static boolean WriteFile(String str){

		return  FileUtils.WriteFileCover(getJsonPath(),str);

	}

	public void setJsonName(String jsonName){

		this.jsonName=jsonName;

	}

	public String getJsonName(){

		return jsonName;

	}
	
	static String getJsonPath(){
		
		//return Environment.getExternalStorageDirectory().getAbsolutePath()+"/"+jsonName+".json";
		
		return Environment.getDataDirectory().getAbsolutePath()+"/data/"+AllResources.pkgname+"/files/"+jsonName+".json";
		
	}

}

