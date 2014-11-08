package org.vkedco.mobappdev.picture_browser;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

public class LuminosityConverter {
	
	public static Bitmap convertToGrayLevel(Bitmap bm) {
		if ( bm == null ) return null;
	
		Bitmap grayscaleBitmap = Bitmap.createBitmap(bm.getWidth(), bm.getHeight(),
				Bitmap.Config.RGB_565);
		Canvas can = new Canvas(grayscaleBitmap);
		Paint paint = new Paint();
		ColorMatrix color_matrix = new ColorMatrix();
		color_matrix.setSaturation(0);
		ColorMatrixColorFilter color_matrix_filter = new ColorMatrixColorFilter(color_matrix);
		paint.setColorFilter(color_matrix_filter);
		can.drawBitmap(bm, 0, 0, paint);
		return grayscaleBitmap;
	}

}
