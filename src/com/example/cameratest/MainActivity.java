package com.example.cameratest;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Audio.Media;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public static final int take_photo=1;
	public static final int corp_photo=1;
	public static final String RUNCAMERA=MediaStore.ACTION_IMAGE_CAPTURE;
	
	
	private Button btn_takePhoto;
	private ImageView img;
	private Uri  imageUri;
	private String pic_path;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		getImagePath();
		
		btn_takePhoto=(Button) findViewById(R.id.button_take_photo);
		img=(ImageView) findViewById(R.id.imageViewshow_photo);
		
		btn_takePhoto.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				pic_path=getImagePath();
				File outPutImag=new File(pic_path);
				try {
					outPutImag.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				imageUri=Uri.fromFile(outPutImag);
				
				
				Intent it=new Intent(RUNCAMERA);
				it.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
				startActivityForResult(it, take_photo);
				
				
			
							
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		
		if(resultCode==RESULT_OK){
			try {
				runOnUiThread(new Runnable() {
					
					@Override
					public void run() {
						Bitmap bm=BitmapFactory.decodeFile(pic_path);
						img.setImageBitmap(bm);
					}
				});
				
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public String getImagePath(){
		SimpleDateFormat format = new SimpleDateFormat("yyyy年mm月dd日hh时mm分ss秒");
		String time = format.format(new Date());
		File file = null;
		try {
			file = new File(Environment.getExternalStorageDirectory()+"/SimpleGlary--------------------------");// 创建自定义目录
			
			file.mkdir();//创建文件夹记得配置写权限  不然没法创建！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
			//血泪的教训啊！！！！！！！！！！！！！！！！！！！！！！！！！！！
			
			Log.d("创建filefolder", file.getPath());
		} catch (Exception e) {
			Log.d("MainActivity.getFileName()", "已存在");
		}
		String pathreturn=file.getPath() + "/" + time + ".jpg";
		
		return pathreturn;
		}


}
