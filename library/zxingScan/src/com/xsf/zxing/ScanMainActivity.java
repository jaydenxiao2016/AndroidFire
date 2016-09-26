package com.xsf.zxing;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.activity.CaptureActivity;
import com.google.zxing.common.BitMatrix;

import java.util.Hashtable;

/**
 * @author J!nl!n
 * @date 2015年1月14日 下午2:21:11
 * @type ScanMainActivity
 * @todo ZXing生成和扫描二维码
 */
public class ScanMainActivity extends Activity implements OnClickListener {
	private EditText et;
	private ImageView iv;
	private Button btn1, btn2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		initView();
	}

	/**
	 * 
	 */
	private void initView() {
		et = (EditText) findViewById(R.id.et);
		btn1 = (Button) findViewById(R.id.btn1);
		btn1.setOnClickListener(this);
		btn2 = (Button) findViewById(R.id.btn2);
		btn2.setOnClickListener(this);
		iv = (ImageView) findViewById(R.id.iv);
	}

	/**
	 * 用字符串生成二维码
	 * 
	 * @param str
	 * @author J!nl!n
	 * @return
	 * @throws WriterException
	 */
	public Bitmap Create2DCode(String str) throws WriterException {
		Hashtable<EncodeHintType, String> hints = new Hashtable<EncodeHintType, String>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		// 生成二维矩阵,编码时指定大小,不要生成了图片以后再进行缩放,这样会模糊导致识别失败
		BitMatrix matrix = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, 480, 480, hints);
		int width = matrix.getWidth();
		int height = matrix.getHeight();
		// 二维矩阵转为一维像素数组,也就是一直横着排了
		int[] pixels = new int[width * height];
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				if (matrix.get(x, y)) {
					pixels[y * width + x] = 0xff000000;
				}
			}
		}

		Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
		// 通过像素数组生成bitmap,具体参考api
		bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
		return bitmap;
	}

	@Override
	public void onClick(View v) {
		final int id = v.getId();
		if(id==R.id.btn1){
			String content = et.getText().toString().trim();
			try {
				if (!TextUtils.isEmpty(content)) {
					Bitmap bitmap = Create2DCode(content);
					iv.setImageBitmap(bitmap);
				} else {
					Toast.makeText(ScanMainActivity.this, "请输入要生成的字符串", Toast.LENGTH_SHORT).show();
				}
			} catch (WriterException e) {
				e.printStackTrace();
			}
		}else if(id==R.id.btn2){
			startActivity(new Intent(ScanMainActivity.this, CaptureActivity.class));
		}

	}
}
