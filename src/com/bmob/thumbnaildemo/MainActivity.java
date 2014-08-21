package com.bmob.thumbnaildemo;

import java.io.File;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UploadFileListener;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity implements OnClickListener{
	
	ImageView iv_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Bmob.initialize(this, "");
		showToast("请记得将你的AppId填写在MainActivity的BmobSDK初始化方法中");
		iv_icon = (ImageView) findViewById(R.id.iv_pic);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_lodimg:
			// 查询数据
			BmobQuery<Person> query = new BmobQuery<Person>();
			query.findObjects(this, new FindListener<Person>() {
				
				@Override
				public void onSuccess(List<Person> arg0) {
					// TODO Auto-generated method stub
					if(arg0.size()>0){
						// 如果查询结果大于0，取第一条数据的icon缩略图进行显示
						arg0.get(0).getIcon().loadImageThumbnail(MainActivity.this, iv_icon, 300, 300);
					}
					showToast("查询数据成功："+arg0.size());
				}
				
				@Override
				public void onError(int arg0, String arg1) {
					// TODO Auto-generated method stub
					showToast("查询数据失败："+arg1);
				}
			});
			break;
		case R.id.btn_uploadimg:
			// 上传图片
			String imgpath = Environment.getExternalStorageDirectory()+"/DCIM/Camera/IMG_20131202_212050.jpg";
			final BmobFile icon = new BmobFile(new File(imgpath));
			icon.upload(this, new UploadFileListener() {
				
				@Override
				public void onSuccess() {
					// TODO Auto-generated method stub
					Person person = new Person();
					person.setIcon(icon);
					person.save(MainActivity.this);
					showToast("图片上传成功");
				}
				
				@Override
				public void onProgress(Integer arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					// TODO Auto-generated method stub
					showToast("图片上传失败："+arg1);
				}
			});
			break;

		default:
			break;
		}
	}
	
	private void showToast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
