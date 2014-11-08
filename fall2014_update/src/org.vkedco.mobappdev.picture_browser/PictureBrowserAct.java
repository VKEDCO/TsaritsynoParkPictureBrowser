package org.vkedco.mobappdev.picture_browser;

/*
 ***************************************************************** 
 * PictureBrowserAct.java - main activity of an application to
 * browse a few images of Tsaritsyno Park in Moscow, Russia. 
 * 
 * 
 * bugs to vladimir dot kulyukin at gmail dot com
 *****************************************************************
 */

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class PictureBrowserAct extends Activity {
	
	static final String LOGTAG = PictureBrowserAct.class.getSimpleName() + "_LOG";
	
	Button mBtnUp = null;
	Button mBtnDown = null;
	ImageView mPicture = null;
	
	static int[] mImgIds = {
		R.drawable.img_01, R.drawable.img_02,
		R.drawable.img_03, R.drawable.img_04,
		R.drawable.img_05, R.drawable.img_06,
		R.drawable.img_07, R.drawable.img_08,
		R.drawable.img_09, R.drawable.img_10,
	};
	
	int mCurImgId = 0; 
	Resources mRes = null; // resources object
	
	final int CONTEXT_MENU_WIKI_EN_ITEM   		= 1;
	final int CONTEXT_MENU_WIKI_EN_ORDR			= 1;
	
	final int CONTEXT_MENU_WIKI_RU_ITEM	  		= 2;
	final int CONTEXT_MENU_WIKI_RU_ORDR	  		= 2;
	
	final int CONTEXT_MENU_GRAYSCALE_ITEM 		= 3;
	final int CONTEXT_MENU_GRAYSCALE_ORDR	  	= 3;
	
	final int CONTEXT_MENU_FINISH_ITEM    	  	= 4;
	final int CONTEXT_MENU_FINISH_ORDR	  		= 4;
	
	final int CONTEXT_MENU_GROUP_ID       		= ContextMenu.FIRST;
	
	SharedPreferences mSharedPrefs = null;
	Editor mPrefsEditor = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOGTAG, "onCreate()");
        
        setContentView(R.layout.activity_picture_browser);
        
        mRes = getResources();
        mSharedPrefs = PreferenceManager
							.getDefaultSharedPreferences(this.getApplicationContext());
        mPrefsEditor = mSharedPrefs.edit();
        
        checkSavedInstanceState(savedInstanceState);
        loadSharedPrefs();
        buildGUI();
  
    }
    
    private void buildGUI() {
    	mBtnUp   = (Button)    this.findViewById(R.id.btn_up);
    	mBtnDown = (Button)    this.findViewById(R.id.btn_down);
    	mPicture = (ImageView) this.findViewById(R.id.img_view);
    	
    	mBtnUp.setOnClickListener(
    		new OnClickListener() {

				@Override
				public void onClick(View v) {
					if ( mCurImgId <= 0 )
						mCurImgId = 9;
					else
						mCurImgId -= 1;
					// Note: The new version of this method is setBackground().
					// You can do something like this:
					//int sdk = android.os.Build.VERSION.SDK_INT;
					//if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
					//    setBackgroundDrawable(some_background_drawable);
					//} else {
					//    setBackground(some_background_drawable);
					//}
					mPicture.setBackgroundDrawable(mRes.getDrawable(mImgIds[mCurImgId]));
				}
    		}
    	);
    	
    	mBtnDown.setOnClickListener(
        		new OnClickListener() {

    				@Override
    				public void onClick(View v) {
    					mCurImgId = ( mCurImgId + 1 ) % 10;
    					mPicture.setBackgroundDrawable(mRes.getDrawable(mImgIds[mCurImgId]));
    				}
        		}
        );
    	
    	mPicture.setBackgroundDrawable(mRes.getDrawable(mImgIds[mCurImgId]));
    	
    	this.registerForContextMenu(mPicture);
    }
    
    private void saveSharedPrefs() {
    	mPrefsEditor.putInt(mRes.getString(R.string.img_id_key), mCurImgId);
    	boolean rslt = mPrefsEditor.commit();
    	if ( rslt == true ) {
    		Log.d(LOGTAG, "Saved shared img ID =  " + mCurImgId);
    	}
    	else {
    		Log.d(LOGTAG, "mCurImgId not saved");
    	}
    }
    
    private void loadSharedPrefs() {
		mSharedPrefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		mCurImgId = mSharedPrefs.getInt(mRes.getString(R.string.img_id_key), 0);
		Log.d(LOGTAG, "Retrieved shared img ID = " + mCurImgId);
	}
    
    private void checkSavedInstanceState(Bundle savedInstanceState) {
    	if ( savedInstanceState != null ) {
        	Log.d(LOGTAG, "savedInstanceState != null");
        	mCurImgId = savedInstanceState.getInt(mRes.getString(R.string.img_id_key), 0);
        	Log.d(LOGTAG, "mCurImgId = " + mCurImgId);
        }
        else {
        	Log.d(LOGTAG, "savedInstanceState == null");
        }
    }
    
    @Override
	protected void onRestart() {
		super.onRestart();
		Log.d(LOGTAG, "onRestart()");
	}
    
    @Override
	protected void onStart() {
		super.onStart();
		Log.d(LOGTAG, "onStart()");
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		super.onRestoreInstanceState(savedInstanceState);
		Log.d(LOGTAG, "onRestoreInstanceState()");
	}

	@Override
	protected void onResume() {
		super.onResume();
		Log.d(LOGTAG, "onResume()");
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		Log.d(LOGTAG, "onPause()");
		saveSharedPrefs();
	}

	@Override
	protected void onStop() {
		super.onStop();
		Log.d(LOGTAG, "onStop()");
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(LOGTAG, "onSaveInstanceState()");
		
		outState.putInt(mRes.getString(R.string.img_id_key), mCurImgId);
		
		Log.d(LOGTAG, "onSaveInstanceState(): img id = " + mCurImgId);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		Log.d(LOGTAG, "onDestroy()");
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_picture_browser, menu);
        return true;
    }

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		switch ( item.getItemId() ) {
		case CONTEXT_MENU_WIKI_EN_ITEM:
			Intent wiki_en = new Intent(Intent.ACTION_VIEW);
			wiki_en.setData(Uri.parse(mRes.getString(R.string.wiki_en_url)));
			startActivity(wiki_en);
			return true;
		case CONTEXT_MENU_WIKI_RU_ITEM:
			Intent wiki_ru = new Intent(Intent.ACTION_VIEW);
			wiki_ru.setData(Uri.parse(mRes.getString(R.string.wiki_ru_url)));
			startActivity(wiki_ru);
			return true;
		case CONTEXT_MENU_GRAYSCALE_ITEM:
			saveSharedPrefs();
			Intent grayscale = new Intent(this.getApplicationContext(), PictureProcessorAct.class);
			startActivity(grayscale);
			return true;
		case CONTEXT_MENU_FINISH_ITEM:
			this.finish();
			return true;
		default: return false;
		}
		
	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {
		super.onCreateContextMenu(menu, v, menuInfo);
		menu.setHeaderTitle(mRes.getString(R.string.context_menu_title));
		
		menu.add(CONTEXT_MENU_GROUP_ID, 
				CONTEXT_MENU_WIKI_EN_ITEM, 
				CONTEXT_MENU_WIKI_EN_ORDR, 
				mRes.getString(R.string.context_menu_wiki_en_item));
		menu.add(CONTEXT_MENU_GROUP_ID, CONTEXT_MENU_WIKI_RU_ITEM, CONTEXT_MENU_WIKI_RU_ORDR, 
				mRes.getString(R.string.context_menu_wiki_ru_item));
		menu.add(CONTEXT_MENU_GROUP_ID, CONTEXT_MENU_GRAYSCALE_ITEM, CONTEXT_MENU_GRAYSCALE_ORDR, 
				mRes.getString(R.string.context_menu_grayscale_item));
		menu.add(CONTEXT_MENU_GROUP_ID, CONTEXT_MENU_FINISH_ITEM, CONTEXT_MENU_FINISH_ORDR, 
				mRes.getString(R.string.context_menu_finish_item));
	}
}
