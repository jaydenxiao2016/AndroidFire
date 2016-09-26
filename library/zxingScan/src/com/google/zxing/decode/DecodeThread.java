/*
 * Copyright (C) 2008 ZXing authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.zxing.decode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

import android.os.Handler;
import android.os.Looper;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.DecodeHintType;
import com.google.zxing.activity.CaptureActivity;

/**
 * This thread does all the heavy lifting of decoding the images.
 * 
 * @author dswitkin@google.com (Daniel Switkin)
 */
public class DecodeThread extends Thread {

	public static final String BARCODE_BITMAP = "barcode_bitmap";

	public static final int BARCODE_MODE = 0X100;
	public static final int QRCODE_MODE = 0X200;
	public static final int ALL_MODE = 0X300;

	private final CaptureActivity activity;
	private final Map<DecodeHintType, Object> hints;
	private Handler handler;
	private final CountDownLatch handlerInitLatch;

	public DecodeThread(CaptureActivity activity, int decodeMode) {

		this.activity = activity;
		handlerInitLatch = new CountDownLatch(1);

		hints = new EnumMap<DecodeHintType, Object>(DecodeHintType.class);

		Collection<BarcodeFormat> decodeFormats = new ArrayList<BarcodeFormat>();
		decodeFormats.addAll(EnumSet.of(BarcodeFormat.AZTEC));
		decodeFormats.addAll(EnumSet.of(BarcodeFormat.PDF_417));

		switch (decodeMode) {
		case BARCODE_MODE:
			decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
			break;

		case QRCODE_MODE:
			decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
			break;

		case ALL_MODE:
			decodeFormats.addAll(DecodeFormatManager.getBarCodeFormats());
			decodeFormats.addAll(DecodeFormatManager.getQrCodeFormats());
			break;

		default:
			break;
		}

		hints.put(DecodeHintType.POSSIBLE_FORMATS, decodeFormats);
	}

	public Handler getHandler() {
		try {
			handlerInitLatch.await();
		} catch (InterruptedException ie) {
			// continue?
		}
		return handler;
	}

	@Override
	public void run() {
		Looper.prepare();
		handler = new DecodeHandler(activity, hints);
		handlerInitLatch.countDown();
		Looper.loop();
	}

}
