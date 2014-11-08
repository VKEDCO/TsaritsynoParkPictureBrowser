package org.vkedco.mobappdev.picture_browser;

/*
 ***************************************************************** 
 * PictureBrocessorAct.java - a called activity of an application to
 * browse a few images of Tsaritsyno Park in Moscow, Russia.
 * The activity is called when the user clicks on Grayscale image
 * item of the context menu associated with the image of the
 * the main activity.
 * 
 * bugs to vladimir dot kulyukin at gmail dot com
 *****************************************************************
 */

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;

public class PictureProcessorAct extends Activity {
	
	final static String LOGTAG = PictureProcessorAct.class.getSimpleName() + "_LOG";

	final static int[] mImgIds = { 
		R.drawable.img_01, R.drawable.img_02,
		R.drawable.img_03, R.drawable.img_04, 
		R.drawable.img_05, R.drawable.img_06, 
		R.drawable.img_07, R.drawable.img_08,
		R.drawable.img_09, R.drawable.img_10 
	};

    ImageView mImgView = null;
	Bitmap mOrigBitmap = null;
	Bitmap mGrayBitmap = null;
	Resources mRes     = null;
	SharedPreferences mSharedPrefs = null;
	Editor	          mPrefsEditor = null;
	int mImgId = 0;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_processor_layout);
		
		mImgView = (ImageView) this.findViewById(R.id.img_processor_view);
		mImgView.setImageDrawable(null);
		mRes = getResources();
		
		loadSharedPrefs();
		grayScaleImage();
	}
	
	private void loadSharedPrefs() {
		mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		mImgId = mSharedPrefs.getInt(mRes.getString(R.string.img_id_key), 0);
		Log.d(LOGTAG, "Retrieved shared img ID = " + mImgId);
	}
	
	private void grayScaleImage() {
		mOrigBitmap = BitmapFactory.decodeResource(getResources(), mImgIds[mImgId]);
		mGrayBitmap = LuminosityConverter.convertToGrayLevel(mOrigBitmap);
		mImgView.setImageBitmap(mGrayBitmap);
		mOrigBitmap.recycle();
		mOrigBitmap = null;
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if ( mGrayBitmap != null ) {
			mGrayBitmap.recycle();
			mGrayBitmap = null;
		}
	}

}
