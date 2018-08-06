package com.grocery.common;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;

public class Utils {

	private static Typeface tahomaTypeFace;
	private static Typeface utmHelveTypeFace;
	private static Typeface utmHelveItalicTypeFace;
	private static Typeface utmHelveBoldTypeFace;

	/**
	 * Convert Dp to Pixel
	 */
	public static int dpToPx(float dp, Resources resources){
		float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.getDisplayMetrics());
		return (int) px;
	}
	
	public static int getRelativeTop(View myView) {
	    if(myView.getId() == android.R.id.content)
	        return myView.getTop();
	    else
	        return myView.getTop() + getRelativeTop((View) myView.getParent());
	}
	
	public static int getRelativeLeft(View myView) {
		if(myView.getId() == android.R.id.content)
			return myView.getLeft();
		else
			return myView.getLeft() + getRelativeLeft((View) myView.getParent());
	}

	public static Typeface getTahomaTypeFace(Context context) {
		if (tahomaTypeFace == null) {
			tahomaTypeFace = Typeface.createFromAsset(context.getAssets(), "tahoma.ttf");
		}
		return tahomaTypeFace;
	}

	public static Typeface getUtmHelveTypeFace(Context context) {
		if (utmHelveTypeFace == null) {
			utmHelveTypeFace = Typeface.createFromAsset(context.getAssets(), "UTM_Helve.ttf");
		}
		return utmHelveTypeFace;
	}

	public static Typeface getUtmHelveItalicTypeFace(Context context) {
		if (utmHelveItalicTypeFace == null) {
			utmHelveItalicTypeFace = Typeface.createFromAsset(context.getAssets(), "UTM_Helve_Italic.ttf");
		}
		return utmHelveItalicTypeFace;
	}

	public static Typeface getUtmHelveBoldTypeFace(Context context) {
		if (utmHelveBoldTypeFace == null) {
			utmHelveBoldTypeFace = Typeface.createFromAsset(context.getAssets(), "UTM_HelveBold.ttf");
		}
		return utmHelveBoldTypeFace;
	}
}
